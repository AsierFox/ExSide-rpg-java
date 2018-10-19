package com.devdream.nightly.levels;

import com.devdream.nightly.entities.mob.Player;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.maths.Vector2D;
import com.devdream.nightly.tiled.TiledMap;

public class TestLevel extends BaseLevel {

    public TestLevel(final Keyboard keyboard, final int width, final int height) {
        super(keyboard, width, height);
    }

    @Override
    protected void load() {
    	tiledMap = new TiledMap("tiled-map");

		Vector2D<Integer> playerSpawnPosition = new Vector2D<>(150, 150);
		Player.getInstance().init(keyboard, playerSpawnPosition);
		Player.getInstance().attachLevel(this);
    }

}
