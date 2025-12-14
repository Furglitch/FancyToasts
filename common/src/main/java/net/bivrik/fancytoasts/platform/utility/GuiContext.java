package net.bivrik.fancytoasts.platform.utility;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.bivrik.fancytoasts.core.Color;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
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

    public GuiGraphics guiGraphics() {
        return guiGraphics;
    }

    public Matrix3x2fStack stack() {
        return stack;
    }

    public GuiContext push() {
        stack.pushMatrix();
        return this;
    }

    public void pop() {
        stack.popMatrix();
    }

    public GuiContext translate(float x, float y) {
        stack.translate(x, y);
        return this;
    }

    public GuiContext translate(Vector2 position) {
        stack.translate(position.x, position.y);
        return this;
    }

    public GuiContext rotateAround(float rotation, float ox, float oy) {
        stack.rotateAbout(rotation, ox, oy);
        return this;
    }

    public GuiContext rotateAround(float rotation, Vector2 origin) {
        stack.rotateAbout(rotation, origin.x, origin.y);
        return this;
    }

    public GuiContext scaleAround(float sx, float sy, float ox, float oy) {
        stack.scaleAround(sx, sy, ox, oy);
        return this;
    }

    public GuiContext scaleAround(float scale, float ox, float oy) {
        stack.scaleAround(scale, scale, ox, oy);
        return this;
    }

    public GuiContext scaleAround(Vector2 scale, Vector2 origin) {
        stack.scaleAround(scale.x, scale.y, origin.x, origin.y);
        return this;
    }

    public GuiContext scaleAround(float scale, Vector2 origin) {
        stack.scaleAround(scale, scale, origin.x, origin.y);
        return this;
    }

    private FormattedCharSequence toFormattedCharSequence(String string) {
        return Language.getInstance().getVisualOrder(FormattedText.of(string));
    }

    private FormattedCharSequence toFormattedCharSequence(Component component) {
        return component.getVisualOrderText();
    }

    public void drawText(Font font, FormattedCharSequence text, int x, int y, Color color) {
        if (color.isTransparent()) return;

        guiGraphics.drawString(font, text, x, y, color.getARGB());
    }

    public void drawText(Font font, String text, int x, int y, Color color) {
        drawText(font, toFormattedCharSequence(text), x, y, color);
    }

    public void drawText(Font font, Component text, int x, int y, Color color) {
        drawText(font, toFormattedCharSequence(text), x, y, color);
    }

    public void drawCenteredText(Font font, FormattedCharSequence text, int x, int y, Color color) {
        if (color.isTransparent()) return;

        guiGraphics.drawCenteredString(font, text, x, y, color.getARGB());
    }

    public void drawCenteredText(Font font, String text, int x, int y, Color color) {
        drawCenteredText(font, toFormattedCharSequence(text), x, y, color);
    }

    public void drawCenteredText(Font font, Component text, int x, int y, Color color) {
        drawCenteredText(font, toFormattedCharSequence(text), x, y, color);
    }

    public void drawTexture(RenderPipeline pipeline, ResourceLocation textureLocation, int x, int y, int width, int height, TextureUV uv, int textureWidth, int textureHeight, Color color) {
        if (color.isTransparent()) return;

        guiGraphics.blit(pipeline, textureLocation, x, y, uv.u(), uv.v(), width, height, textureWidth, textureHeight, color.getARGB());
    }

    public void drawTexture(RenderPipeline pipeline, ResourceLocation textureLocation, int x, int y, int width, int height, TextureUV uv, int textureWidth, int textureHeight) {
        drawTexture(pipeline, textureLocation, x, y, width, height, uv, textureWidth, textureHeight, Color.WHITE);
    }

    public void drawGUITexture(ResourceLocation textureLocation, int x, int y, int width, int height, TextureUV uv, int textureWidth, int textureHeight, Color color) {
        if (color.isTransparent()) return;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, textureLocation, x, y, uv.u(), uv.v(), width, height, textureWidth, textureHeight, color.getARGB());
    }

    public void drawGUITexture(ResourceLocation textureLocation, int x, int y, int width, int height, TextureUV uv, int textureWidth, int textureHeight) {
        drawGUITexture(textureLocation, x, y, width, height, uv, textureWidth, textureHeight, Color.WHITE);
    }

    public void drawGUITexture(ResourceLocation textureLocation, int x, int y, int width, int height, TextureUV uv, Color color) {
        drawGUITexture(textureLocation, x, y, width, height, uv, 256, 256, color);
    }

    public void drawSprite(ResourceLocation spriteLocation, int x, int y, int width, int height, Color color) {
        if (color.isTransparent()) return;

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, spriteLocation, x, y, width, height, color.getARGB());
    }

    public void drawSprite(ResourceLocation spriteLocation, int x, int y, int width, int height) {
        drawSprite(spriteLocation, x, y, width, height, Color.WHITE);
    }

    public void fill(int x, int y, int width, int height, Color color) {
        if (color.isTransparent()) return;

        guiGraphics.fill(x, y, x + width, y + height, color.getARGB());
    }
}
