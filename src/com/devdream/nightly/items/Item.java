package com.devdream.nightly.items;

import java.util.Random;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.maths.Rect;
import com.devdream.nightly.maths.Vector2D;

/**
 * Item class.
 */
public abstract class Item {

    public Vector2D<Double> pos;
    public Rect collider;
    public Sprite sprite;

    protected Random rand;
    
    private boolean isRemoved;


    public Item() {
        pos = new Vector2D<>(.0d, .0d);
        rand = new Random();
        isRemoved = false;
    }

    public Item(final Sprite sprite) {
    	this();
    	this.sprite = sprite;
        collider = new Rect(pos.x.intValue(), pos.y.intValue(), sprite.WIDTH, sprite.HEIGHT);
    }

    public abstract void update();

    public abstract void render(final Renderer renderer);

    public void dispose() {
        isRemoved = true;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

}
