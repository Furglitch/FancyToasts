package net.bivrik.fancytoasts.platform.utility;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.bivrik.fancytoasts.core.Debug;
import net.bivrik.fancytoasts.utility.TextureUV;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix3x2fStack;

public class GuiContext {
    private final GuiGraphics guiGraphics;
    private final Matrix3x2fStack stack;

    public GuiContext(GuiGraphics guiGraphics) {
        this.guiGraphics = guiGraphics;
        this.stack = guiGraphics.pose();
    }

    public Matrix3x2fStack stack() {
        return stack;
    }

    public GuiGraphics guiGraphics() {
        return guiGraphics;
    }

    public void push() {
        stack.pushMatrix();
    }

    public void pop() {
        stack.popMatrix();
    }

    public void translate(float x, float y) {
        stack.translate(x, y);
    }

    public void rotateAround(float rotation, float ox, float oy) {
        stack.rotateAbout(rotation, ox, oy);
    }

    public void scaleAround(float sx, float sy, float ox, float oy) {
        stack.scaleAround(sx, sy, ox, oy);
    }

    public void scaleAround(float scale, float ox, float oy) {
        scaleAround(scale, scale, ox, oy);
    }

    public void drawText(Font font, FormattedCharSequence text, int x, int y, Color color) {
        if (color.isTransparent()) {
            return;
        }

        guiGraphics.drawString(font, text, x, y, color.toARGB());
    }

    public void drawCenteredText(Font font, FormattedCharSequence text, int x, int y, Color color) {
        drawText(font, text, x - font.width(text) / 2, y, color);
    }

    public void drawTexture(RenderPipeline pipeline, ResourceLocation textureLocation, int x, int y, int width, int height, TextureUV uv, int textureWidth, int textureHeight, Color color) {
        if (color.isTransparent()) {
            Debug.info("Texture: {}; Color: TRANSPARENT", textureLocation);
            return;
        }

        if (color.isWhite()) {
            guiGraphics.blit(pipeline, textureLocation, x, y, uv.u(), uv.v(), width, height, textureWidth, textureHeight);
            Debug.info("Texture: {}; Color: none", textureLocation);
        } else {
            guiGraphics.blit(pipeline, textureLocation, x, y, uv.u(), uv.v(), width, height, textureWidth, textureHeight, color.toARGB());
            Debug.info("Texture: {}; Color: ARGB", textureLocation);
        }
    }

    public void drawTexture(RenderPipeline pipeline, ResourceLocation textureLocation, int x, int y, int width, int height, TextureUV uv, int textureWidth, int textureHeight) {
        drawTexture(pipeline, textureLocation, x, y, width, height, uv, textureWidth, textureHeight, Color.WHITE);
    }

    public void drawGUITexture(ResourceLocation textureLocation, int x, int y, int width, int height, TextureUV uv, int textureWidth, int textureHeight, Color color) {
        if (color.isTransparent()) {
            Debug.info("GUI Texture: {}; Color: TRANSPARENT", textureLocation);
            return;
        }

        if (color.isWhite()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, textureLocation, x, y, uv.u(), uv.v(), width, height, textureWidth, textureHeight);
            Debug.info("GUI Texture: {}; Color: none", textureLocation);
        } else {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, textureLocation, x, y, uv.u(), uv.v(), width, height, textureWidth, textureHeight, color.toARGB());
            Debug.info("GUI Texture: {}; Color: ARGB", textureLocation);
        }
    }

    public void drawGUITexture(ResourceLocation textureLocation, int x, int y, int width, int height, TextureUV uv, int textureWidth, int textureHeight) {
        drawGUITexture(textureLocation, x, y, width, height, uv, textureWidth, textureHeight, Color.WHITE);
    }

    public void drawGUITexture(ResourceLocation textureLocation, int x, int y, int width, int height, TextureUV uv, Color color) {
        drawGUITexture(textureLocation, x, y, width, height, uv, 256, 256, color);
    }

    public void drawSprite(ResourceLocation spriteLocation, int x, int y, int width, int height, Color color) {
        if (color.isTransparent()) {
            Debug.info("Sprite: {}; Color: TRANSPARENT", spriteLocation);
            return;
        }

        if (color.isWhite()) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, spriteLocation, x, y, width, height);
            Debug.info("Sprite: {}; Color: none", spriteLocation);
        } else {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, spriteLocation, x, y, width, height, color.toARGB());
            Debug.info("Sprite: {}; Color: ARGB", spriteLocation);
        }
    }

    public void drawSprite(ResourceLocation spriteLocation, int x, int y, int width, int height) {
        drawSprite(spriteLocation, x, y, width, height, Color.WHITE);
    }

    public void fill(int x, int y, int width, int height, Color color) {
        if (color.isTransparent()) {
            Debug.info("Fill: {}; Color: TRANSPARENT", color);
            return;
        }

        guiGraphics.fill(x, y, x + width, y + height, color.toARGB());
        Debug.info("Fill: {}; Color: ARGB", color);
    }
}
