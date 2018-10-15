package com.devdream.nightly.graphics;

public class Tile {

    // TODO USE this to avoid << 4, >> 4, + 16 or / 16
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

    public void render(Renderer renderer, final int x, final int y) {
        // TODO Make << 4 dynamic
        // << 4 is like * 16
        renderer.renderTile(x << 4, y << 4, this);
    }

}
