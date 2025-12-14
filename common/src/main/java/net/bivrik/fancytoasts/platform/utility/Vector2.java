package net.bivrik.fancytoasts.platform.utility;

import net.bivrik.fancytoasts.core.Debug;
import net.bivrik.fancytoasts.utility.Easing;
import net.bivrik.fancytoasts.utility.Interpolation;

import java.util.Objects;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        set(x, y);
    }

    public Vector2(Vector2 other) {
        set(other.x, other.y);
    }

    public Vector2() {
        set(0, 0);
    }

    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;

        return this;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 set(Vector2 other) {
        return set(other.x, other.y);
    }

    public Vector2 add(float x, float y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public Vector2 add(Vector2 other) {
        return add(other.x, other.y);
    }

    public Vector2 subtract(float x, float y) {
        this.x -= x;
        this.y -= y;

        return this;
    }

    public Vector2 subtract(Vector2 other) {
        return subtract(other.x, other.y);
    }

    public Vector2 multiply(float x, float y) {
        this.x *= x;
        this.y *= y;

        return this;
    }

    public Vector2 multiply(Vector2 other) {
        return multiply(other.x, other.y);
    }

    public Vector2 divide(float x, float y) {
        if (x == 0.0f || y == 0.0f) {
            Debug.error("Vector division by zero");
            return this;
        }

        this.x /= x;
        this.y /= y;

        return this;
    }

    public Vector2 divide(Vector2 other) {
        return divide(other.x, other.y);
    }

    public Vector2 lerp(Vector2 end, float delta, Easing easing) {
        x = easing.lerp(x, end.x, delta);
        y = easing.lerp(y, end.y, delta);

        return this;
    }

    public Vector2 lerp(Vector2 end, float delta) {
        x = Interpolation.lerp(x, end.x, delta);
        y = Interpolation.lerp(y, end.y, delta);

        return this;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + String.format("{x='%s', y='%s'}", x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vector2 vector2)) return false;
        return (Float.compare(x, vector2.x) == 0 && Float.compare(y, vector2.y) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static final Vector2 ZERO = new Vector2(0.0f, 0.0f);
    public static final Vector2 ONE = new Vector2(1.0f, 1.0f);
}
