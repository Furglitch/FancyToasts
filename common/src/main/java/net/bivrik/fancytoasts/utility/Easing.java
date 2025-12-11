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

    private final MathEasing deltaEasing;

    Easing(MathEasing deltaEasing) {
        this.deltaEasing = deltaEasing;
    }

    private float apply(float delta) {
        return deltaEasing.apply(delta);
    }

    private float clamp(float delta) {
        return Math.clamp(delta, 0.0f, 1.0f);
    }

    public float lerp(float start, float end, float delta) {
        if (start == end) return end;

        float easedDelta = apply(clamp(delta));
        return start + (end - start) * easedDelta;
    }

    public int lerp(int start, int end, float delta) {
        if (start == end) return end;

        float easedDelta = apply(clamp(delta));
        return start + (int) ((end - start) * easedDelta + 0.5f);
    }

    @FunctionalInterface
    interface MathEasing {
        float apply(float delta);
    }
}
