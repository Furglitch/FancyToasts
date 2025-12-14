package net.bivrik.fancytoasts.core.color;

import net.bivrik.fancytoasts.utility.FastMath;

/**
 * Representation of ARGB color
 */
@Deprecated
public interface Color {
    int getA();
    int getR();
    int getG();
    int getB();
    int getARGB();

    boolean isTransparent();
    boolean isOpaque();

    static MutableColor mutable(int a, int r, int g, int b) {
        return MutableColor.create(a, r, g, b);
    }

    static MutableColor mutable(float a, float r, float g, float b) {
        return MutableColor.create(a, r, g, b);
    }

    static MutableColor mutable(Color other) {
        return MutableColor.create(other);
    }

    static ImmutableColor immutable(int a, int r, int g, int b) {
        return ImmutableColor.create(a, r, g, b);
    }

    static ImmutableColor immutable(float a, float r, float g, float b) {
        return ImmutableColor.create(a, r, g, b);
    }

    static ImmutableColor immutable(Color other) {
        return ImmutableColor.create(other);
    }

    static MutableColor mutableFromARGB(int argb) {
        int[] channels = fromARGB(argb);
        return MutableColor.create(channels[0], channels[1], channels[2], channels[3]);
    }

    static ImmutableColor immutableFromARGB(int argb) {
        int[] channels = fromARGB(argb);
        return ImmutableColor.create(channels[0], channels[1], channels[2], channels[3]);
    }

    /**
     * Converts 32-bit integer color into array of ARGB color components
     * @param argb 32-bit representation of color
     * @return array of ARGB color components <code>[a, r, g, b]</code>
     */
    static int[] fromARGB(int argb) {
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        return new int[] { a, r, g, b };
    }

    /**
     * Converts given values of ARGB color into 32-bit integer color
     * @param a alpha component
     * @param r red component
     * @param g green component
     * @param b blue component
     * @return 32-bit representation of color
     */
    static int toARGB(int a, int r, int g, int b) {
        return (a << 24) & 0xFF | (r << 16) & 0xFF | (g << 8) & 0xFF | b & 0xFF;
    }

    /**
     * Clamps int color channel
     * @param channel color component
     * @return clamped value of int color channel <code>[0-255]</code>
     */
    static int clamp(int channel) {
        return FastMath.clamp(channel, 0, 255);
    }

    /**
     * Clamps float color channel
     * @param channel color component
     * @return clamped value of float color channel <code>[0.0-1.0]</code>
     */
    static float clamp(float channel) {
        return FastMath.clamp(channel, 0.0f, 1.0f);
    }

    /**
     * Converts float color channel to int color channel (example: 0.2f -> 51)
     * @param channel color component
     * @return int color channel (without clamping)
     */
    static int floatToInt(float channel) {
        return FastMath.round(channel * 255);
    }

    /**
     * Converts int color channel to float color channel (example: 51 -> 0.2f)
     * @param channel color component
     * @return float color channel (without clamping)
     */
    static float intToFloat(int channel) {
        return channel / 255.0f;
    }

    /**
     * Immutable White color (1.0f, 1.0f, 1.0f, 1.0f)
     */
    ImmutableColor WHITE = ImmutableColor.create(255, 255, 255, 255);
    /**
     * Immutable Black color (1.0f, 0.0f, 0.0f, 0.0f)
     */
    ImmutableColor BLACK = ImmutableColor.create(255, 0, 0, 0);
    /**
     * Immutable Transparent color (0.0f, 1.0f, 1.0f, 1.0f)
     */
    ImmutableColor TRANSPARENT = ImmutableColor.create(0, 255, 255, 255);
    /**
     * Immutable Light Gray color (0.625f, 0.625f, 0.625f, 0.625f)
     */
    ImmutableColor LIGHT_GRAY = immutableFromARGB(0xFFA0A0A0);
    /**
     * Immutable Yellow color (1.0f, 1.0f, 1.0f, 0.0f)
     */
    ImmutableColor YELLOW = immutableFromARGB(0xFFFFFF00);
    /**
     * Immutable Red color (1.0f, 0.859f, 0.308f, 0.308f)
     */
    ImmutableColor RED = immutableFromARGB(0xFFDC4F4F);
    /**
     * Immutable Purple color (1.0f, 0.976f, 0.234f, 0.976f)
     */
    ImmutableColor PURPLE = immutableFromARGB(0xFFFA3CFA);
    /**
     * Immutable White color (1.0f, 0.132f, 1.0f, 1.0f)
     */
    ImmutableColor CYAN = immutableFromARGB(0xFF22FFFF);
}
