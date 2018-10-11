package com.devdream.nightly.graphics.tiles;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.graphics.Tile;

public class VoidTile extends Tile {

    public static final Tile tile = new VoidTile(new Sprite(0x271907, Tile.WORLD_TILE_SIZE));

    public VoidTile(final Sprite sprite) {
        super(sprite);
        isSolid = true;
    }

    @Override
    public void render(Renderer renderer, int x, int y) {
        // TODO Make << 4 dynamic
        renderer.renderTile(x << 4, y << 4, this);
    }

}
