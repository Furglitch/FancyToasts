package net.bivrik.fancytoasts.platform.utility;

/**
 * Represent an ARGB color
 */
public final class Color {
    private final int alpha;
    private final int red;
    private final int green;
    private final int blue;

    private final int argb;

    private Color(int alpha, int red, int green, int blue) {
        this.alpha = clamp(alpha);
        this.red = clamp(red);
        this.green = clamp(green);
        this.blue = clamp(blue);

        this.argb = toARGB();
    }

    public Color() {
        this(255, 255, 255, 255);
    }

    public Color(float a, float r, float g, float b) {
        this(floatToInt(a, false), floatToInt(r, false), floatToInt(g, false), floatToInt(b, false));
    }

    public Color(float r, float g, float b) {
        this(1.0f, r, g, b);
    }

    public Color(float a) {
        this(a, 1.0f, 1.0f, 1.0f);
    }

    /**
     * @return 32-bit integer representation of color
     */
    public int toARGB() {
        if (argb == 0) {
            return alpha << 24 | red << 16 | green << 8 | blue;
        }

        return argb;
    }

    public Color withAlpha(int a) {
        return new Color(clamp(a), red, green, blue);
    }

    public Color withAlpha(float a) {
        return withAlpha(floatToInt(a, false));
    }

    public Color multiplyAlpha(float a) {
        return withAlpha((int) (alpha * a + 0.5f));
    }

    public boolean isOpaque() {
        return alpha == 255;
    }

    public boolean isTransparent() {
        return alpha == 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + String.format("{alpha='%s', red='%s', green='%s', blue='%s'}", alpha, red, green, blue);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Color)) return false;
        return other == this || other.hashCode() == toARGB();
    }

    @Override
    public int hashCode() {
        return toARGB();
    }

    // Static methods

    /**
     * Lerps from one color to another in given time
     *
     * @param from initial color
     * @param to   final color
     * @param delta given for transformation
     * @return new lerped color
     */
    public static Color lerp(Color from, Color to, float delta) {
        delta = Math.clamp(delta, 0.0f, 1.0f);
        return new Color(
                lerp(from.alpha, to.alpha, delta),
                lerp(from.red, to.red, delta),
                lerp(from.green, to.green, delta),
                lerp(from.blue, to.blue, delta)
        );
    }

    private static float lerp(int start, int end, float delta) {
        if (start == end) return start;

        return start + (end - start) * delta;
    }

    public static Color fromARGB(int argb) {
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        return new Color(a, r, g, b);
    }

    public static int clamp(int colorChannel) {
        return Math.clamp(colorChannel, 0, 255);
    }

    public static float clamp(float colorChannel) {
        return Math.clamp(colorChannel, 0.0f, 1.0f);
    }

    public static int floatToInt(float channel, boolean doClamp) {
        int value = (int) (channel * 255 + 0.5f);
        if (doClamp) value = clamp(value);
        return value;
    }

    public static float intToFloat(int channel, boolean doClamp) {
        float value = channel / 255.0f;
        if (doClamp) value = clamp(value);
        return value;
    }

    // Presets

    public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f);
    public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f);
    public static final Color TRANSPARENT = new Color(0.0f, 0.0f, 0.0f, 0.0f);

    public static final Color LIGHT_GRAY = fromARGB(0xFFA0A0A0);
    public static final Color YELLOW = fromARGB(0xFFFFFF00);
    public static final Color RED = fromARGB(0xFFDC4F4F);
    public static final Color PURPLE = fromARGB(0xFFFA3CFA);
    public static final Color CYAN = fromARGB(0xFF22FFFF);
}
