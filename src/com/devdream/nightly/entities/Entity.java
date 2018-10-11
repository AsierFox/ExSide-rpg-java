package com.devdream.nightly.entities;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.levels.BaseLevel;

import java.util.Random;

public abstract class Entity {

    public int x;
    public int y;

    private boolean isRemoved;

    protected Random random;

    protected BaseLevel belongsToLevel;


    public Entity() {
        random = new Random();

        isRemoved = false;
    }

    public abstract void init(final BaseLevel belongsToLevel);

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
