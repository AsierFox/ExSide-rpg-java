package com.devdream.nightly.tiled;

import java.awt.*;

public class ColliderLayer extends TiledLayer {

    public Rectangle[] colliders;

    public ColliderLayer(final Rectangle[] colliders, final int width, final int height) {
        super(width, height);
        this.colliders = colliders;
    }

}
