package net.bivrik.fancytoasts.client.toast.animation;

import net.bivrik.fancytoasts.client.toast.AnimationSetup;
import net.bivrik.fancytoasts.client.toast.Appearance;
import net.bivrik.fancytoasts.core.Color;
import net.bivrik.fancytoasts.platform.utility.GuiContext;
import net.bivrik.fancytoasts.utility.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FormattedCharSequence;

public class StandardAnimation extends FancyToastAnimation {
    private final Appearance ICON_APPEARANCE = new Appearance(2000, 0);
    private final Appearance BANNER_APPEARANCE = new Appearance(500, 1500);
    private final Appearance BACKGROUND_APPEARANCE = new Appearance(800, 1600);
    private final Appearance TEXT_APPEARANCE = new Appearance(1000, 2000);

    private final int FADE_OUT_DURATION = 2000;
    private final int DURATION = 6000 + FADE_OUT_DURATION;

    private final Easing easeOut = Easing.EASE_OUT;
    private final Easing easeIn = Easing.EASE_IN;

    @Override
    public void setup(AnimationSetup setup, Minecraft minecraft, int toastWidth, int toastHeight) {
        super.setup(setup, minecraft, toastWidth, toastHeight);

        this.setLines(displayInfo.getAdvancementsAnnouncement(), displayInfo.getTitle());
    }

    @Override
    public void draw(GuiGraphics guiGraphics, long time) {
        super.draw(guiGraphics, time);

        float iconAppearProgress = ICON_APPEARANCE.getProgress(time);
        float bannerAppearProgress = BANNER_APPEARANCE.getProgress(time);
        float backgroundAppearProgress = BACKGROUND_APPEARANCE.getProgress(time);
        float textAppearProgress = TEXT_APPEARANCE.getProgress(time);
        float fadeOutProgress = Appearance.getProgress(time, FADE_OUT_DURATION, DURATION - FADE_OUT_DURATION);

        GuiContext context = new GuiContext(guiGraphics);

        if (fadeOutProgress > 0) {
            float fadeOutY = easeIn.lerp(0.0f, -80.0f, fadeOutProgress);

            context.push();
            context.translate(0, fadeOutY);
        }

        if (backgroundAppearProgress > 0) {
            context.push();
            if (backgroundAppearProgress != 1) {
                float y = easeOut.lerp(-200.0f, 0.0f, backgroundAppearProgress);
                context.translate(0, y);
            }
            this.drawBackground(context);
            context.pop();
        }

        if (bannerAppearProgress > 0) {
            context.push();
            if (bannerAppearProgress != 1) {
                float xScale = easeOut.lerp(0.0f, 1.0f, bannerAppearProgress);
                context.scaleAround(xScale, 1, 81, 0);
            }
            this.drawBanner(context);
            context.pop();
        }

        if (iconAppearProgress > 0) {
            context.push();
            if (iconAppearProgress != 1) {
                float scale = easeOut.lerp(0.0f, 1.0f, iconAppearProgress);
                context.scaleAround(scale, 81, 13);

                float y = easeOut.lerp(-100.0f, 0.0f, iconAppearProgress);
                context.translate(0, y);
            }
            float sinY = this.sinusoidLoop(time, 1.6f, 1.5f);
            context.translate(0, sinY - 5);
            this.drawIcon(context);
            context.pop();
        }

        if (textAppearProgress > 0) {
            this.drawTitle(context, textAppearProgress);
            this.drawDescription(context, textAppearProgress);
        }

        if (fadeOutProgress > 0) {
            context.pop();
        }
    }

    @Override
    protected void drawDescription(GuiContext context, float alpha) {
        var descriptionLines = getDescriptionLines();
        if (descriptionLines.isEmpty()) {
            return;
        }

        Color descriptionColor = displayInfo.getAdvancementType().getSecondaryColor().withAlpha(alpha);
        int centerToastX = this.toastWidth / 2;

        if (descriptionLines.size() == 1) {
            context.drawCenteredText(this.minecraft.font, descriptionLines.getFirst(), centerToastX, 43, descriptionColor);
        } else {
            int lineHeight = 42 - (9 * (descriptionLines.size() - 1)) / 2;
            for (FormattedCharSequence line : descriptionLines) {
                context.drawCenteredText(this.minecraft.font, line, centerToastX, lineHeight, descriptionColor);
                lineHeight += 9;
            }
        }
    }

    @Override
    public int getDuration() {
        return DURATION;
    }

    @Override
    public int getToastSoundTiming() {
        return TEXT_APPEARANCE.startPoint() + 180;
    }
}
