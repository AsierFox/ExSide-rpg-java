package com.devdream.nightly.graphics.tiles;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.graphics.SpriteSheet;
import com.devdream.nightly.graphics.Tile;

public class GrassTile extends Tile {

    public static final Tile tile = new GrassTile(new Sprite(SpriteSheet.road, 16, 16, 0, 0));

    public GrassTile(final Sprite sprite) {
        super(sprite);
        isSolid = true;
    }

    @Override
    public void render(Renderer renderer, int x, int y) {
        // TODO Make << 4 dynamic
        // << 4 is like * 16
        renderer.renderTile(x << 4, y << 4, this);
    }

}
