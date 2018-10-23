package com.devdream.nightly.maths;

public class Vector2DFloat<T> extends Vector2D<Float> {

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
