package com.devdream.nightly.graphics.tiles;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.graphics.SpriteSheet;
import com.devdream.nightly.graphics.Tile;

public class GroundTile extends Tile {

    public static final Tile grass = new GroundTile(new Sprite(SpriteSheet.road, 16, 16, 0, 0));
    public static final Tile barro = new GroundTile(new Sprite(SpriteSheet.road, 16, 16, 1, 0));
    public static final Tile sand = new GroundTile(new Sprite(SpriteSheet.road, 16, 16, 1, 1), true);

    public GroundTile(final Sprite sprite) {
        super(sprite);
        isSolid = false;
    }

    public GroundTile(final Sprite sprite, final boolean isSolid) {
        super(sprite);
        this.isSolid = isSolid;
    }

    @Override
    public void render(Renderer renderer, int x, int y) {
        // TODO Make << 4 dynamic
        // << 4 is like * 16
        renderer.renderTile(x << 4, y << 4, this);
    }

}
