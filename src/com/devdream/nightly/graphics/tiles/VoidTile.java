package com.devdream.nightly.graphics.tiles;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.graphics.Tile;

import java.awt.*;

public class VoidTile extends Tile {

    public static final Tile tile = new VoidTile(new Sprite(Color.BLACK, 16));

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
