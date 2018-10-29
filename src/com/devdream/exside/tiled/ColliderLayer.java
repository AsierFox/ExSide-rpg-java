package com.devdream.exside.tiled;

import com.devdream.exside.maths.Rect;

public class ColliderLayer extends TiledLayer {

    public Rect[] colliders;

    public ColliderLayer(final Rect[] colliders, final int width, final int height) {
        super(width, height);
        this.colliders = colliders;
    }

}
