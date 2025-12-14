package net.bivrik.fancytoasts.platform.utility;

public class Transform {
    public Vector2 position = Vector2.ZERO.copy();
    public float rotation = 0.0f;
    public Vector2 size = Vector2.ONE.copy();

    public Transform(float x, float y, float rotation, float sizeX, float sizeY) {
        this.position.set(x, y);
        this.rotation = rotation;
        this.size.set(sizeX, sizeY);
    }

    public Transform(Vector2 position, float rotation, Vector2 size) {
        this.position.set(position);
        this.rotation = rotation;
        this.size.set(size);
    }

    public Transform() {}
}
