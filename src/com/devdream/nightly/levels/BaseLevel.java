package com.devdream.nightly.levels;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Tile;
import com.devdream.nightly.graphics.tiles.GrassTile;
import com.devdream.nightly.graphics.tiles.VoidTile;

public abstract class BaseLevel {

    protected int width;
    protected int height;
    protected int[] tiles;

    public BaseLevel(final int width, final int height) {
        this.width = width;
        this.height = height;
        tiles = new int[width * height];

        load();
    }

    public BaseLevel(final String path) {
        //

        load();
    }

    public void update() {
        //
    }

    public void render(final Renderer renderer, final int xScroll, final int yScroll) {
        renderer.setOffset(xScroll, yScroll);
        // TODO Make >> 4 and + 16 dynamic
        // Divide by / 16, the size of our sprites
        int xLeftSize = xScroll >> 4;
        int yTopSide = yScroll >> 4;
        // Fix premature break render with adding + 16, rendering one more tile out of screen
        int xRightSide = (xScroll + renderer.width + 16) >> 4;
        int yBottomSide = (yScroll + renderer.height + 16) >> 4;

        for (int y = yTopSide; y < yBottomSide; y++) {
            for (int x = xLeftSize; x < xRightSide; x++) {
                getTile(x, y).render(renderer, x, y);
            }
        }
    }

    public void time() {
        //
    }

    protected abstract void load();

    public Tile getTile(final int x, final int y) {
        // Control get out bounds of the map
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return VoidTile.tile;
        }
        switch (tiles[x + y * width]) {
            case 0: return GrassTile.tile;
        }
        return VoidTile.tile;
    }

}
