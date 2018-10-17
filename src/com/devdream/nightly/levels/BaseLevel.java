package com.devdream.nightly.levels;

import com.devdream.nightly.entities.mob.Player;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.tiled.TiledMap;
import com.devdream.nightly.ui.HUD;

public abstract class BaseLevel {

    protected int width;
    protected int height;

    protected Keyboard keyboard;

    public TiledMap tiledMap;

    protected HUD playerHUD;


    public BaseLevel(final Keyboard keyboard, final int width, final int height) {
        this.keyboard = keyboard;
        this.width = width;
        this.height = height;

        load();
    }

    protected abstract void load();

    public void update() {
        tiledMap.update();
        Player.getInstance().update();
        playerHUD.update();
    }

    public void render(final Renderer renderer) {
    	tiledMap.render(renderer);
    	Player.getInstance().render(renderer);
    	playerHUD.render(renderer);
    }

}
