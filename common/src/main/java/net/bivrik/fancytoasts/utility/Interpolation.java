package net.bivrik.fancytoasts.utility;

public final class Interpolation {
    private static final Easing DEFAULT = Easing.LINEAR;

    public static float lerp(float start, float end, float delta, Easing easing) {
        return easing.lerp(start, end, delta);
    }

    public static float lerp(float start, float end, float delta) {
        return DEFAULT.lerp(start, end, delta);
    }

    public static int lerp(int start, int end, float delta, Easing easing) {
        return easing.lerp(start, end, delta);
    }

    public static float lerp(int start, int end, float delta) {
        return DEFAULT.lerp(start, end, delta);
    }
}
