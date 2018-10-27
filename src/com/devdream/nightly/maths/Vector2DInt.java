package com.devdream.nightly.maths;

public class Vector2DInt<T> extends Vector2D<Integer> {

	public Vector2DInt() {
		super(0, 0);
	}

	public Vector2DInt(Integer x, Integer y) {
		super(x, y);
	}

	public Vector2DInt<T> add(final Vector2DInt<T> other) {
		x += other.x;
		y += other.y;
		return this;
	}

	public Vector2DInt<T> subtract(final Vector2DInt<T> other) {
		x -= other.x;
		y -= other.y;
		return this;
	}

}
