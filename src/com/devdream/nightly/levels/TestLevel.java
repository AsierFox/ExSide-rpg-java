package com.devdream.nightly.levels;

import com.devdream.nightly.entities.mob.Player;
import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.tiled.TiledMap;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.maths.Vector2D;

public class TestLevel extends BaseLevel {

    public TestLevel(final Keyboard keyboard, final int width, final int height) {
        super(keyboard, width, height);
    }

    @Override
    protected void load(final String path) {
        playerSpawnPosition = new Vector2D(150, 150);

        tiledMap = new TiledMap("tiled-map");
        player = new Player(keyboard, G.Sprites.player_south, playerSpawnPosition.x, playerSpawnPosition.y);
        player.init(this);
    }

}
