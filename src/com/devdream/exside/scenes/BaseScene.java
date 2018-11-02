package com.devdream.exside.scenes;

import com.devdream.exside.Game;
import com.devdream.exside.graphics.Renderer;

public abstract class BaseScene {
    
    public final String ID;
    
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
    
}
