package com.devdream.nightly.items;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.maths.Vector2D;

/**
 * Item class.
 */
public abstract class Item {

    public Vector2D<Double> pos;
    public Sprite sprite;

    private boolean isRemoved;


    public Item() {
        pos = new Vector2D<>(.0d, .0d);

        isRemoved = false;
    }

    public Item(final Sprite sprite) {
    	this();
    	this.sprite = sprite;
    }

    public abstract void update();

    public abstract void render(final Renderer renderer);

    public void dispose() {
        // Remove from level
        isRemoved = true;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

}
