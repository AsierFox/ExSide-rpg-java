package com.devdream.nightly.graphics;

public abstract class Tile {

    // TODO USE this to avoid << 4, >>4, + 16 or / 16
    public static final int WORLD_TILE_SIZE = 16;

    public int x;
    public int y;
    public boolean isSolid;
    public boolean isBreakable;

    public final Sprite sprite;

    public Tile(final Sprite sprite) {
        this.sprite = sprite;
        isSolid = false;
        isBreakable = false;
    }

    public abstract void render(Renderer renderer, final int x, final int y);

}
