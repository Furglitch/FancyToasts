package net.bivrik.fancytoasts.client.toast.animation;

import net.bivrik.fancytoasts.client.toast.AnimationSetup;
import net.bivrik.fancytoasts.client.toast.Appearance;
import net.bivrik.fancytoasts.core.Color;
import net.bivrik.fancytoasts.platform.utility.GuiContext;
import net.bivrik.fancytoasts.platform.utility.Transform;
import net.bivrik.fancytoasts.utility.Easing;
import net.minecraft.client.Minecraft;

public class OldlikeAnimation extends FancyToastAnimation {
    private final Appearance ICON_APPEARANCE = new Appearance(2000, 0);
    private final Appearance BANNER_APPEARANCE = new Appearance(2000, 100);
    private final Appearance BACKGROUND_APPEARANCE = new Appearance(2000, 200);
    private final Appearance TITLE_TEXT_APPEARANCE = new Appearance(2000, 1200);
    private final Appearance DESCRIPTION_TEXT_APPEARANCE = new Appearance(2000, 1400);

    private final int FADE_OUT_DURATION = 3000;
    private final int DURATION = 3500 + FADE_OUT_DURATION;

    private final Easing fadeOutEasing = Easing.EASE_IN;
    private final Easing textEasing = Easing.ELASTIC_OUT;
    private final Easing appearingEasing = Easing.EASE_OUT;

    @Override
    public void setup(AnimationSetup setup, Minecraft minecraft, int toastWidth, int toastHeight) {
        super.setup(setup, minecraft, toastWidth, toastHeight);

        this.setLines(displayInfo.getAdvancementsAnnouncement(), displayInfo.getDescription());
    }

    @Override
    public void draw(GuiContext guiContext, long time) {
        super.draw(guiContext, time);

        float iconAppearProgress = ICON_APPEARANCE.getProgress(time);
        float bannerAppearProgress = BANNER_APPEARANCE.getProgress(time);
        float backgroundAppearProgress = BACKGROUND_APPEARANCE.getProgress(time);
        float titleAppearProgress = TITLE_TEXT_APPEARANCE.getProgress(time);
        float descriptionAppearProgress = DESCRIPTION_TEXT_APPEARANCE.getProgress(time);
        float fadeOutProgress = Appearance.getProgress(time, FADE_OUT_DURATION, DURATION - FADE_OUT_DURATION);

        if (bannerAppearProgress > 0) {
            guiContext.push();
            Color color = Color.WHITE;
            if (bannerAppearProgress != 1) {
                color = color.withAlpha(appearingEasing.lerp(0.0f, 1.0f, bannerAppearProgress));
                float x = appearingEasing.lerp(35.0f, 0.0f, bannerAppearProgress);
                guiContext.translate(x, 0);
            }
            else if (fadeOutProgress != 1 && fadeOutProgress > 0) {
                color = color.withAlpha(fadeOutEasing.lerp(1.0f, 0.0f, fadeOutProgress));
            }
            float sinY = this.sinusoidLoop(time, 1.14f, 2.0f);
            guiContext.translate(0, sinY + 5);
            this.drawBanner(guiContext, color);
            guiContext.pop();
        }

        if (backgroundAppearProgress > 0) {
            guiContext.push();
            Color backgroundColor = Color.WHITE;
            if (backgroundAppearProgress != 1) {
                backgroundColor = backgroundColor.withAlpha(appearingEasing.lerp(0.0f, 1.0f, backgroundAppearProgress));

                float x = appearingEasing.lerp(35.0f, 0.0f, backgroundAppearProgress);
                guiContext.translate(x, 0);
            }
            else if (fadeOutProgress != 1 && fadeOutProgress > 0) {
                backgroundColor = backgroundColor.withAlpha(fadeOutEasing.lerp(1.0f, 0.0f, fadeOutProgress));
            }
            this.drawBackground(guiContext, backgroundColor);
            guiContext.pop();
        }

        if (iconAppearProgress > 0) {
            guiContext.push();
            Color iconColor = Color.WHITE;
            int x = 77;
            float scale = 1;
            if (iconAppearProgress != 1) {
                iconColor = iconColor.withAlpha(appearingEasing.lerp(0.0f, 1.0f, iconAppearProgress));
                x = appearingEasing.lerp(115, 77, iconAppearProgress);
            }
            else if (fadeOutProgress != 1 && fadeOutProgress > 0) {
                iconColor = iconColor.withAlpha(fadeOutEasing.lerp(1.0f, 0.0f, fadeOutProgress));
                scale = fadeOutEasing.lerp(1.0f, 0.0f, fadeOutProgress);
            }
            float cosRotation = this.cosineLoop(time, 1.6f, 0.2f);
            guiContext.translate(x, 11)
                    .scaleAround(scale, 68 + 13, 14)
                    .rotateAround(cosRotation, 68 + 13, 14);
            this.drawIcon(guiContext, iconColor);
            guiContext.pop();
        }

        float fadeOutTextAlpha = 0;
        if (fadeOutProgress != 1 && fadeOutProgress > 0) {
            fadeOutTextAlpha = fadeOutEasing.lerp(0.0f, 1.0f, fadeOutProgress);
        }

        if (titleAppearProgress > 0) {
            guiContext.push();
            if (titleAppearProgress != 1) {
                int x = textEasing.lerp(50, 0, titleAppearProgress);
                guiContext.translate(x, 0);
            }
            this.drawTitle(guiContext, titleAppearProgress - fadeOutTextAlpha);
            guiContext.pop();
        }

        if (descriptionAppearProgress > 0) {
            guiContext.push();
            if (descriptionAppearProgress != 1) {
                int x = textEasing.lerp(50, 0, descriptionAppearProgress);
                guiContext.translate(x, 0);
            }
            this.drawDescription(guiContext, descriptionAppearProgress - fadeOutTextAlpha);
            guiContext.pop();
        }
    }

    @Override
    public int getDuration() {
        return DURATION - 25;
    }

    @Override
    public int getToastSoundTiming() {
        return TITLE_TEXT_APPEARANCE.startPoint() + 280;
    }
}