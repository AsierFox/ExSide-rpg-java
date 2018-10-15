package com.devdream.nightly.levels;

import com.devdream.nightly.entities.mob.Player;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.tiled.TiledMap;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.maths.Vector2D;

public abstract class BaseLevel {

    protected int width;
    protected int height;

    protected Keyboard keyboard;

    protected TiledMap tiledMap;
    public Player player;

    public Vector2D playerSpawnPosition;


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
        player.update();
        tiledMap.update();
    }

    public void render(final Renderer renderer) {
    	/*
        // Center x, y positions of the player to the middle of screen
        int xScroll = player.x - renderer.width / 2;
        int yScroll = player.y - renderer.height / 2;

        renderer.setOffset(xScroll, yScroll);

        // TODO Can refactor
        // TODO Make >> 4 and + 16 dynamic
        // Divide by / 16, the size of our sprites
        int yTopSide = yScroll >> 4;
        int xLeftSize = xScroll >> 4;
        // Fix premature break render with adding + 16, rendering one more tile out of screen
        int xRightSide = (xScroll + renderer.width + 16) >> 4;
        int yBottomSide = (yScroll + renderer.height + 16) >> 4;

        for (int y = yTopSide; y < yBottomSide; y++) {
            for (int x = xLeftSize; x < xRightSide; x++) {
                getTile(x, y).render(renderer, x, y);
            }
        }
        */

    	tiledMap.render(renderer, player);

        player.render(renderer);
    }

//    public Tile getTile(final int x, final int y) {
//        // Control get out bounds of the map
//        if (x < 0 || y < 0 || x >= tiledMap.mapTilesWidth || y >= tiledMap.mapTilesHeight) {
//            return G.Sprites.voidTile;
//        }
//        return new Tile(tiled);
//    }

}
