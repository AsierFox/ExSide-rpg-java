package com.devdream.nightly.entities;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.maths.Vector2D;

public abstract class Entity {

    public Vector2D<Integer> pos;

    public Sprite sprite;

    protected BaseLevel belongsToLevel;

    private boolean isRemoved;


    public Entity() {
    	pos = new Vector2D<>(0, 0);

        isRemoved = false;
    }

    public abstract void attachLevel(final BaseLevel belongsToLevel);

    public abstract void update();

    public abstract void render(final Renderer renderer);

    public void dispose() {
        // Remove from level
    	belongsToLevel.removeEntity(this);
        isRemoved = true;
    }

    public boolean isRemoved() {
        return isRemoved;
    }
}
