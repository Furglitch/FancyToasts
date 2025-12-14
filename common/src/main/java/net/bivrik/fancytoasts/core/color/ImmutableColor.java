package net.bivrik.fancytoasts.core.color;

/**
 * Immutable representation of ARGB color
 */
@Deprecated
public final class ImmutableColor implements Color {
    private final int a;
    private final int r;
    private final int g;
    private final int b;
    private final int argb;

    private ImmutableColor(int a, int r, int g, int b) {
        this.a = Color.clamp(a);
        this.r = Color.clamp(r);
        this.g = Color.clamp(g);
        this.b = Color.clamp(b);

        this.argb = toARGB();
    }

    private ImmutableColor(Color other) {
        this.a = other.getA();
        this.r = other.getR();
        this.g = other.getG();
        this.b = other.getB();

        this.argb = toARGB();
    }

    private int toARGB() {
        return Color.toARGB(this.a, this.r, this.g, this.b);
    }

    public MutableColor toMutable() {
        return MutableColor.create(a, r, g, b);
    }

    public static ImmutableColor create(int a, int r, int g, int b) {
        return new ImmutableColor(a, r, g, b);
    }

    public static ImmutableColor create(float a, float r, float g, float b) {
        return new ImmutableColor(Color.floatToInt(a), Color.floatToInt(r), Color.floatToInt(g), Color.floatToInt(b));
    }

    public static ImmutableColor create(Color other) {
        return new ImmutableColor(other);
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
        return argb;
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
        return argb == color.getARGB();
    }

    @Override
    public int hashCode() {
        return argb;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + String.format("{a='%s', r='%s', g='%s', b='%s'}", a, r, g, b);
    }
}
