package net.bivrik.fancytoasts.client.toast.animation;

import net.bivrik.fancytoasts.client.config.ToastScreenBehavior;
import net.bivrik.fancytoasts.client.toast.AnimationSetup;
import net.bivrik.fancytoasts.core.event.GeneralConfigDataEvent;
import net.bivrik.fancytoasts.platform.utility.Color;
import net.bivrik.fancytoasts.utility.TypeBasedUVs;
import net.bivrik.fancytoasts.core.Managers;
import net.bivrik.fancytoasts.platform.utility.ToastDisplayInfo;
import net.bivrik.fancytoasts.utility.TextureUV;
import net.bivrik.fancytoasts.platform.utility.GuiContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class FancyToastAnimation {
    private final static float LOOP_TIME_SCALE = 0.00125f;

    private final Consumer<GeneralConfigDataEvent> generalConfigDataEventConsumer;

    private List<FormattedCharSequence> titleLines;
    private List<FormattedCharSequence> descriptionLines;

    protected ToastDisplayInfo displayInfo;
    protected Minecraft minecraft;
    protected int toastWidth;
    protected int toastHeight;

    private ResourceLocation textureLocation;
    private TypeBasedUVs typeBasedUVs;
    private TextureUV backgroundUV;
    private TextureUV plaqueUV;

    private boolean shouldTransparentToast;
    private float guiAlpha = 1.0f;
    private float loopsStrength;
    private float loopsSpeed;

    FancyToastAnimation() {
        generalConfigDataEventConsumer = this::onGeneralConfigDataChanged;
        Managers.getEventManager().subscribeToEvent(GeneralConfigDataEvent.class, generalConfigDataEventConsumer);
    }

    public void setup(AnimationSetup setup, Minecraft minecraft, int toastWidth, int toastHeight) {
        var data = Managers.getConfigManager().getGeneralConfigData();
        this.shouldTransparentToast = data.getToastScreenBehavior().equals(ToastScreenBehavior.TRANSPARENT);
        this.loopsStrength = data.getLoopsStrength();
        this.loopsSpeed = data.getLoopsSpeed();

        this.minecraft = minecraft;
        this.toastWidth = toastWidth;
        this.toastHeight = toastHeight;
        this.textureLocation = setup.textureLocation();
        this.displayInfo = setup.displayInfo();
        this.typeBasedUVs = setup.typeBasedUVs();
        this.backgroundUV = setup.backgroundUV();
        this.plaqueUV = setup.plaqueUV();
    }

    public void unsubscribeFromGeneralConfigDataEvent() {
        Managers.getEventManager().unsubscribeFromEvent(GeneralConfigDataEvent.class, generalConfigDataEventConsumer);
    }

    private void onGeneralConfigDataChanged(GeneralConfigDataEvent event) {
        var data = event.generalConfigData();
        shouldTransparentToast = data.getToastScreenBehavior().equals(ToastScreenBehavior.TRANSPARENT);
        loopsStrength = data.getLoopsStrength();
        loopsSpeed = data.getLoopsSpeed();
    }

    protected void setLines(Component title, Component description) {
        titleLines = minecraft.font.split(title, 142);
        descriptionLines = minecraft.font.split(description, 142);
    }

    protected List<FormattedCharSequence> getTitleLines() {
        return titleLines;
    }

    protected List<FormattedCharSequence> getDescriptionLines() {
        return descriptionLines;
    }

    public void draw(GuiGraphics guiGraphics, long time) {
        if (shouldTransparentToast && Objects.requireNonNull(Managers.getToastManager()).isScreenOpened()) {
            guiAlpha = 0.5f;
        }
        else if (guiAlpha != 1.0f) {
            guiAlpha = 1.0f;
        }
    }

    public abstract int getDuration();

    public abstract int getToastSoundTiming();

    protected void drawIcon(GuiContext guiContext, Color color) {
        Color guiColor = color.multiplyAlpha(guiAlpha);
        guiContext.drawGUITexture(textureLocation, 68, 0, 26, 26, typeBasedUVs.frame(), guiColor);
        guiContext.guiGraphics().renderFakeItem(displayInfo.getIcon(), 73, 5);
    }
    protected void drawIcon(GuiContext guiContext) {
        drawIcon(guiContext, Color.WHITE);
    }

    protected void drawBanner(GuiContext guiContext, Color color) {
        Color guiColor = color.multiplyAlpha(guiAlpha);
        guiContext.drawGUITexture(textureLocation, 0, 5, 162, 14, typeBasedUVs.banner(), guiColor);
    }
    protected void drawBanner(GuiContext guiContext) {
        drawBanner(guiContext, Color.WHITE);
    }

    protected void drawBackground(GuiContext guiContext, Color color) {
        Color guiColor = color.multiplyAlpha(guiAlpha);
        guiContext.drawGUITexture(textureLocation, 0, 20, 162, 40, backgroundUV, guiColor);
        guiContext.drawGUITexture(textureLocation, 144, 56, 9, 14, plaqueUV, guiColor);
    }
    protected void drawBackground(GuiContext guiContext) {
        drawBackground(guiContext, Color.WHITE);
    }

    protected void drawTitle(GuiContext guiContext, float alpha) {
        if (titleLines.isEmpty()) {
            return;
        }

        Color titleColor = displayInfo.getAdvancementType().getMainColor().withAlpha(alpha);
        int toastCenterX = toastWidth / 2;
        FormattedCharSequence titleLine = titleLines.getFirst();

        if (titleLines.size() == 1) {
            guiContext.drawCenteredText(minecraft.font, titleLine, toastCenterX, 25, titleColor);
        } else {
            guiContext.drawCenteredText(minecraft.font, titleLine, toastCenterX - minecraft.font.width("...") / 2, 25, titleColor);
            guiContext.drawCenteredText(minecraft.font, "...", toastCenterX + 1 + minecraft.font.width(titleLine) / 2, 25, titleColor);
        }
    }
    protected void drawTitle(GuiContext guiContext) {
        drawTitle(guiContext, 1);
    }

    protected void drawDescription(GuiContext guiContext, float alpha) {
        if (descriptionLines.isEmpty()) {
            return;
        }

        Color descriptionColor = displayInfo.getAdvancementType().getSecondaryColor().withAlpha(alpha);

        guiContext.drawText(minecraft.font, descriptionLines.get(0), 8, 38, descriptionColor);
        if (descriptionLines.size() > 1) {
            var descriptionSecondLine = descriptionLines.get(1);
            guiContext.drawText(minecraft.font, descriptionSecondLine, 8, 47, descriptionColor);

            if (descriptionLines.size() > 2) {
                guiContext.drawText(minecraft.font, "...", 8 + minecraft.font.width(descriptionSecondLine), 47, descriptionColor);
            }
        }
    }
    protected void drawDescription(GuiContext guiContext) {
        drawDescription(guiContext, 1);
    }

    protected float sinusoidLoop(long time, float speed, float strength) {
        float scaledTime = time * LOOP_TIME_SCALE * speed * loopsSpeed;
        return (float) Math.sin(scaledTime) * strength * loopsStrength;
    }

    protected float cosineLoop(long time, float speed, float strength) {
        float scaledTime = time * LOOP_TIME_SCALE * speed * loopsSpeed;
        return (float) Math.cos(scaledTime) * strength * loopsStrength;
    }
}
