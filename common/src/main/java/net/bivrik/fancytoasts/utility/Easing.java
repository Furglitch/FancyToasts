package net.bivrik.fancytoasts.utility;

public enum Easing {
    LINEAR(t -> t),
    SINE_IN(t -> (float) (1 - Math.cos((t * Math.PI) / 2))),
    SINE_OUT(t -> (float) Math.sin((t * Math.PI) / 2)),
    SINE_IN_OUT(t -> (float) (-(Math.cos(Math.PI * t) - 1) / 2)),
    EASE_IN(t -> (float) (Math.pow(t, 8))),
    EASE_OUT(t -> (float) (1 - Math.pow(1 - t, 8))),
    EASE_IN_OUT(t -> (float) (Math.pow(t, 2) * (3.0f - 2.0f * t))),
    ELASTIC_OUT(t -> (float) (1 - Math.pow(2, -10 * t) * Math.cos(t * Math.PI * 4)));

    private final MathEasing mathEasing;

    Easing(MathEasing mathEasing) {
        this.mathEasing = mathEasing;
    }

    public float applyTo(float delta) {
        return mathEasing.applyTo(delta);
    }

    public static float lerp(float start, float end, float delta, Easing easing) {
        if (start == end) return end;

        float easedDelta = easing.applyTo(clamp(delta));

        return start + (end - start) * easedDelta;
    }

    public static float lerp(float start, float end, float delta) {
        return lerp(start, end, delta, Easing.LINEAR);
    }

    public static int lerp(int start, int end, float delta, Easing easing) {
        if (start == end) return end;

        float easedDelta = easing.applyTo(clamp(delta));

        return start + (int) ((end - start) * easedDelta + 0.5f);
    }

    public static float lerp(int start, int end, float delta) {
        return lerp(start, end, delta, Easing.LINEAR);
    }

    private static float clamp(float delta) {
        return Math.clamp(delta, 0.0f, 1.0f);
    }

    @FunctionalInterface
    interface MathEasing {
        float applyTo(float delta);
    }
}
