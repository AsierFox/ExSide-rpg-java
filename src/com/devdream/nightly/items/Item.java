package com.devdream.nightly.items;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.maths.Vector2D;

public abstract class Item {

    public Vector2D pos;
    private Sprite sprite;

    private boolean isRemoved;


    public Item() {
        pos = new Vector2D();

        isRemoved = false;
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
