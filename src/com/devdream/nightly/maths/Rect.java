package com.devdream.nightly.maths;

public class Rect {

    public int x;
    public int y;
    public int width;
    public int height;


    public Rect(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(Rect r) {
        return r.x + r.width > x && r.y + r.height > y &&
                r.x < x + width && r.y < y + height;
    }

    public boolean intersects(final int x, final int y, final int width, final int height) {
        return intersects(new Rect(x, y, width, height));
    }

}
