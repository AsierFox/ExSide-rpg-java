package com.devdream.exside.maths;

public class Vector2DFloat<T> extends Vector2D<Float> {
    
    public Vector2DFloat() {
        super(.0f, .0f);
    }
    
    public Vector2DFloat(Float x, Float y) {
        super(x, y);
    }
    
    public Vector2DFloat<T> add(final Vector2DFloat<T> other) {
        x += other.x;
        y += other.y;
        return this;
    }
    
    public Vector2DFloat<T> subtract(final Vector2DFloat<T> other) {
        x -= other.x;
        y -= other.y;
        return this;
    }
    
}
