package com.devdream.nightly.graphics.tiles;

import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.graphics.Tile;

public class VoidTile extends Tile {

    public VoidTile(final Sprite sprite) {
        super(sprite);
        isSolid = true;
    }

}
