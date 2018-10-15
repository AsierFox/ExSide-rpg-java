package com.devdream.nightly.graphics.tiles;

import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.graphics.Tile;

public class GroundTile extends Tile {

    public GroundTile(final Sprite sprite) {
        super(sprite);
        isSolid = false;
    }

    public GroundTile(final Sprite sprite, final boolean isSolid) {
        super(sprite);
        this.isSolid = isSolid;
    }

}
