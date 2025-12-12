package net.bivrik.fancytoasts.platform.utility;

import net.bivrik.fancytoasts.utility.Easing;
import net.bivrik.fancytoasts.utility.Interpolation;

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

    public int getAlpha() {
        return alpha;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
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

    public Color lerp(Color end, float delta, Easing easing) {
        return Interpolation.lerp(this, end, delta, easing);
    }

    public Color lerp(Color end, float delta) {
        return Interpolation.lerp(this, end, delta);
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
