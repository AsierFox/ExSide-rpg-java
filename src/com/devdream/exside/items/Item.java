package com.devdream.exside.items;

import java.util.Random;

import com.devdream.exside.graphics.Sprite;
import com.devdream.exside.interfaces.Renderable;
import com.devdream.exside.maths.Rect;
import com.devdream.exside.maths.Vector2D;

/**
 * Item class.
 */
public abstract class Item implements Renderable {
    
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
    
    public void dispose() {
        isRemoved = true;
    }
    
    public boolean isRemoved() {
        return isRemoved;
    }
    
}
