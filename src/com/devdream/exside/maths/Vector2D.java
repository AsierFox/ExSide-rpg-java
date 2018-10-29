package com.devdream.exside.maths;

public class Vector2D<T extends Number> {

    public T x;
    public T y;


    public Vector2D(final T x, final T y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
    	return "x=" + x + ", y=" + y;
    }

}
