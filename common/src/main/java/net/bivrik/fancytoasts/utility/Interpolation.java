package net.bivrik.fancytoasts.utility;

import net.bivrik.fancytoasts.platform.utility.Color;

public final class Interpolation {
    private static final Easing DEFAULT = Easing.LINEAR;

    private static float innerLerp(float start, float end, float delta) {
        if (start == end) return end;

        return start + (end - start) * delta;
    }

    private static int innerLerp(int start, int end, float delta) {
        if (start == end) return end;

        return start + (int) ((end - start) * delta + 0.5f);
    }

    public static float lerp(float start, float end, float delta, Easing easing) {
        float easedDelta = easing.applyEasing(delta);
        return innerLerp(start, end, easedDelta);
    }

    public static float lerp(float start, float end, float delta) {
        return lerp(start, end, delta, DEFAULT);
    }

    public static int lerp(int start, int end, float delta, Easing easing) {
        float easedDelta = easing.applyEasing(delta);
        return innerLerp(start, end, easedDelta);
    }

    public static int lerp(int start, int end, float delta) {
        return lerp(start, end, delta, DEFAULT);
    }

    public static Color lerp(Color start, Color end, float delta, Easing easing) {
        float easedDelta = easing.applyEasing(delta);
        return new Color(
                innerLerp(start.getAlpha(), end.getAlpha(), easedDelta),
                innerLerp(start.getRed(), end.getRed(), easedDelta),
                innerLerp(start.getGreen(), end.getGreen(), easedDelta),
                innerLerp(start.getBlue(), end.getBlue(), easedDelta)
        );
    }

    public static Color lerp(Color start, Color end, float delta) {
        return lerp(start, end, delta, DEFAULT);
    }
}
