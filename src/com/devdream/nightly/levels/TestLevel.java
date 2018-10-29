package com.devdream.nightly.levels;

import com.devdream.nightly.entities.Civilian;
import com.devdream.nightly.entities.Clergy;
import com.devdream.nightly.entities.Enemy;
import com.devdream.nightly.entities.Player;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.tiled.TiledMap;

public class TestLevel extends BaseLevel {

    public TestLevel(final Keyboard keyboard, final int width, final int height) {
        super(keyboard, width, height);
    }

    @Override
    protected void load() {
    	tiledMap = new TiledMap("tiled-map");
    	
		Player.getInstance().init(keyboard);
		Player.getInstance().attachToLevel(this);

		new Civilian().attachToLevel(this);
		new Enemy().attachToLevel(this);
		new Clergy().attachToLevel(this);
    }

    @Override
    public void update() {
    	super.update();
    }

    @Override
    public void render(Renderer renderer) {
    	super.render(renderer);
    }
    
}
