package com.devdream.exside.scenes;

import com.devdream.exside.Game;
import com.devdream.exside.entities.Player;
import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.levels.BaseLevel;

public abstract class BaseScene {
	
    public final String ID;
    
    public BaseLevel level;
    
    // To call switch scene
    private Game game;
    
    public BaseScene(final String id) {
        ID = id;
    }
    
    public abstract void update();
    
    public abstract void render(final Renderer renderer);
    
    public void init(final Game game) {
        this.game = game;
    }
    
    public void dispose() {
        //
    }
    
    public Player getClientPlayer() {
    	return level.getClientPlayer();
    }
    
}
