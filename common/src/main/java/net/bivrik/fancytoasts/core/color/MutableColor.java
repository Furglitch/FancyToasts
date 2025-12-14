package net.bivrik.fancytoasts.core.color;

import net.bivrik.fancytoasts.utility.Easing;
import net.bivrik.fancytoasts.utility.Interpolation;
import net.bivrik.fancytoasts.utility.FastMath;

/**
 * Mutable representation of ARGB color
 */
@Deprecated
public final class MutableColor implements Color {
    private int a;
    private int r;
    private int g;
    private int b;

    private MutableColor(int a, int r, int g, int b) {
        set(a, r, g, b);
    }

    private MutableColor(Color other) {
        set(other);
    }

    public ImmutableColor toImmutable() {
        return ImmutableColor.create(a, r, g ,b);
    }

    public static MutableColor create(int a, int r, int g, int b) {
        return new MutableColor(a, r, g, b);
    }

    public static MutableColor create(float a, float r, float g, float b) {
        return new MutableColor(Color.floatToInt(a), Color.floatToInt(r), Color.floatToInt(g), Color.floatToInt(b));
    }

    public static MutableColor create(Color other) {
        return new MutableColor(other);
    }

    private MutableColor set(int a, int r, int g, int b) {
        this.a = Color.clamp(a);
        this.r = Color.clamp(r);
        this.g = Color.clamp(g);
        this.b = Color.clamp(b);

        return this;
    }

    public MutableColor set(float a, float r, float g, float b) {
        return set(Color.floatToInt(a), Color.floatToInt(r), Color.floatToInt(g), Color.floatToInt(b));
    }

    public MutableColor set(Color other) {
        if (other != null) {
            this.a = other.getA();
            this.r = other.getR();
            this.g = other.getG();
            this.b = other.getB();
        }

        return this;
    }

    public MutableColor withAlpha(int a) {
        this.a = Color.clamp(a);
        return this;
    }

    public MutableColor withAlpha(float a) {
        return withAlpha(Color.floatToInt(a));
    }

    public MutableColor multiplyAlpha(float scalar) {
        return withAlpha(FastMath.round(a * scalar));
    }

    public MutableColor lerp(Color end, float delta, Easing easing) {
        a = Interpolation.lerp(a, end.getA(), delta, easing);
        r = Interpolation.lerp(r, end.getR(), delta, easing);
        g = Interpolation.lerp(g, end.getG(), delta, easing);
        b = Interpolation.lerp(b, end.getB(), delta, easing);

        return this;
    }

    public MutableColor lerp(Color end, float delta) {
        a = Interpolation.lerp(a, end.getA(), delta);
        r = Interpolation.lerp(r, end.getR(), delta);
        g = Interpolation.lerp(g, end.getG(), delta);
        b = Interpolation.lerp(b, end.getB(), delta);

        return this;
    }

    @Override
    public int getA() {
        return a;
    }

    @Override
    public int getR() {
        return r;
    }

    @Override
    public int getG() {
        return g;
    }

    @Override
    public int getB() {
        return b;
    }

    @Override
    public int getARGB() {
        return Color.toARGB(a, r, g, b);
    }

    @Override
    public boolean isTransparent() {
        return a == 0;
    }

    @Override
    public boolean isOpaque() {
        return a == 255;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Color color)) return false;
        return getARGB() == color.getARGB();
    }

    @Override
    public int hashCode() {
        return getARGB();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + String.format("{a='%s', r='%s', g='%s', b='%s'}", a, r, g, b);
    }
}
