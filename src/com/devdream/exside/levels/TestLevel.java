package com.devdream.exside.levels;

import com.devdream.exside.entities.Civilian;
import com.devdream.exside.entities.Clergy;
import com.devdream.exside.entities.Enemy;
import com.devdream.exside.entities.Player;
import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.io.Keyboard;
import com.devdream.exside.tiled.TiledMap;

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
