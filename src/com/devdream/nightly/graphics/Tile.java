package com.devdream.nightly.graphics;

public abstract class Tile {

    public int x;
    public int y;
    public boolean isSolid;

    public final Sprite sprite;

    public Tile(final Sprite sprite) {
        this.sprite = sprite;
        isSolid = false;
    }

    public abstract void render(Renderer renderer, final int x, final int y);

}
