package com.devdream.nightly.levels;

import com.devdream.nightly.entities.mob.Player;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.tiled.TiledMap;
import com.devdream.nightly.io.Keyboard;

public abstract class BaseLevel {

    protected int width;
    protected int height;

    protected Keyboard keyboard;

    public TiledMap tiledMap;


    public BaseLevel(final Keyboard keyboard, final int width, final int height) {
        this.keyboard = keyboard;
        this.width = width;
        this.height = height;

        load(null);
    }

    public BaseLevel(final String path) {
        load(path);
    }

    protected abstract void load(final String path);

    protected void time() {
        //
    }

    public void update() {
        tiledMap.update();
        Player.getInstance().update();
    }

    public void render(final Renderer renderer) {
    	tiledMap.render(renderer);
    	Player.getInstance().render(renderer);
    }

}
