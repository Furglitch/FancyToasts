package net.bivrik.fancytoasts.client.toast.animation;

import net.bivrik.fancytoasts.client.toast.AnimationSetup;
import net.bivrik.fancytoasts.client.toast.Appearance;
import net.bivrik.fancytoasts.platform.utility.GuiContext;
import net.bivrik.fancytoasts.utility.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class PlayfulAnimation extends FancyToastAnimation {
    private final Appearance ICON_APPEARANCE = new Appearance(1000, 0);
    private final Appearance ICON_MOVEMENT = new Appearance(1500, 1000);
    private final Appearance BANNER_APPEARANCE = new Appearance(1000, 1500);
    private final Appearance BACKGROUND_APPEARANCE = new Appearance(800, 1600);
    private final Appearance TEXT_APPEARANCE = new Appearance(1000, 2000);

    private final int FADE_OUT_DURATION = 1500;
    private final int DURATION = 6000 + FADE_OUT_DURATION;

    @Override
    public void setup(AnimationSetup setup, Minecraft minecraft, int toastWidth, int toastHeight) {
        super.setup(setup, minecraft, toastWidth, toastHeight);

        this.setLines(displayInfo.getTitle(), displayInfo.getDescription());
    }

    @Override
    public void draw(GuiContext guiContext, long time) {
        super.draw(guiContext, time);

        float iconAppearProgress = ICON_APPEARANCE.getProgress(time);
        float iconMovementProgress = ICON_MOVEMENT.getProgress(time);
        float bannerAppearProgress = BANNER_APPEARANCE.getProgress(time);
        float backgroundAppearProgress = BACKGROUND_APPEARANCE.getProgress(time);
        float textAppearProgress = TEXT_APPEARANCE.getProgress(time);
        float fadeOutProgress = Appearance.getProgress(time, FADE_OUT_DURATION, DURATION - FADE_OUT_DURATION);

        float globalSinY = this.sinusoidLoop(time, 2.0f, 1.0f) - 3;

        if (fadeOutProgress > 0) {
            float fadeOutScale = Easing.EASE_IN.lerp(1.0f, 0.0f, fadeOutProgress);
            int toastCenterX = toastWidth / 2;
            int toastCenterY = toastHeight / 2;

            guiContext.push();
            guiContext.scaleAround(fadeOutScale, toastCenterX, toastCenterY);
        }

        if (backgroundAppearProgress > 0) {
            guiContext.push();
            if (backgroundAppearProgress != 1) {
                float scale = Easing.ELASTIC_OUT.lerp(0.0f, 1.0f, backgroundAppearProgress);
                float rotation = Easing.ELASTIC_OUT.lerp(-1.0f, 0.0f, backgroundAppearProgress);
                float y = Easing.EASE_OUT.lerp(-20.0f, 0.0f, backgroundAppearProgress);

                guiContext.scaleAround(scale, 76, 0)
                        .rotateAround(rotation, 76, 0)
                        .translate(0, y);
            }
            this.drawBackground(guiContext);
            guiContext.pop();
        }

        if (bannerAppearProgress > 0) {
            guiContext.push();
            if (bannerAppearProgress != 1) {
                float scaleX = Easing.EASE_OUT.lerp(0.0f, 1.0f, bannerAppearProgress);
                guiContext.scaleAround(scaleX, 1, 15, -12);
            }
            guiContext.translate(0, globalSinY);
            this.drawBanner(guiContext);
            guiContext.pop();
        }

        if (iconAppearProgress > 0) {
            guiContext.push();
            if (iconAppearProgress != 1) {
                float scale = Easing.ELASTIC_OUT.lerp(0.0f, 1.0f, iconAppearProgress);
                float rotation = Easing.ELASTIC_OUT.lerp(-1.0f, 0.0f, iconAppearProgress);
                guiContext.scaleAround(scale, 68 + 13, 13)
                        .rotateAround(rotation, 68 + 13, 13);
            }
            else if (iconMovementProgress > 0) {
                float x = Easing.EASE_OUT.lerp(0.0f, -60.0f, iconMovementProgress);
                guiContext.translate(x, 0);
            }
            guiContext.translate(0, globalSinY - 5);
            this.drawIcon(guiContext);
            guiContext.pop();
        }

        if (textAppearProgress > 0) {
            this.drawTitle(guiContext, textAppearProgress);
            this.drawDescription(guiContext, textAppearProgress);
        }

        if (fadeOutProgress > 0) {
            guiContext.pop();
        }
    }

    @Override
    public int getDuration() {
        return DURATION;
    }

    @Override
    public int getToastSoundTiming() {
        return TEXT_APPEARANCE.startPoint() + 200;
    }
}