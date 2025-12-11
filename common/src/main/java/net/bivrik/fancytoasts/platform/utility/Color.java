package net.bivrik.fancytoasts.platform.utility;

import org.jetbrains.annotations.NotNull;

/**
 * Represent an ARGB color
 */
public record Color(int alpha, int red, int green, int blue) {
    public Color(int alpha, int red, int green, int blue) {
        this.alpha = clamp(alpha);
        this.red = clamp(red);
        this.green = clamp(green);
        this.blue = clamp(blue);
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
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public Color withAlpha(float a) {
        return new Color(clamp(a), red, green, blue);
    }

    public Color multiplyAlpha(float a) {
        return withAlpha(alpha * a);
    }

    public boolean isWhite() {
        return equals(Color.WHITE);
    }

    public boolean isOpaque() {
        return isMax(alpha);
    }

    public boolean isTransparent() {
        return isMin(alpha);
    }

    @Override
    public @NotNull String toString() {
        return getClass().getSimpleName() + String.format("{alpha='%s', red='%s', green='%s', blue='%s'}", alpha, red, green, blue);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Color color)) return false;
        return other == this || color.toARGB() == toARGB();
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
     * @param time given for transformation
     * @return new lerped color
     */
    public static Color lerp(Color from, Color to, float time) {
        time = Math.clamp(time, 0.0f, 1.0f);
        return new Color(
                from.alpha + (to.alpha - from.alpha) * time,
                from.red + (to.red - from.red) * time,
                from.green + (to.green - from.green) * time,
                from.blue + (to.blue - from.blue) * time
        );
    }

    public static Color fromARGB(int argb) {
        float a = (argb >> 24) & 0xFF;
        float r = (argb >> 16) & 0xFF;
        float g = (argb >> 8) & 0xFF;
        float b = argb & 0xFF;

        return new Color(a, r, g, b);
    }

    public static boolean isMax(float colorChannel) {
        return colorChannel == 1.0f;
    }

    public static boolean isMin(float colorChannel) {
        return colorChannel == 0.0f;
    }

    public static boolean isMax(int colorChannel) {
        return colorChannel == 255;
    }

    public static boolean isMin(int colorChannel) {
        return colorChannel == 0;
    }

    public static int clamp(int colorChannel) {
        if (colorChannel < 0 || colorChannel > 255) {
            return Math.clamp(colorChannel, 0, 255);
        }
        return colorChannel;
    }

    public static float clamp(float colorChannel) {
        if (colorChannel < 0.0f || colorChannel > 1.0f) {
            return Math.clamp(colorChannel, 0.0f, 1.0f);
        }
        return colorChannel;
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
