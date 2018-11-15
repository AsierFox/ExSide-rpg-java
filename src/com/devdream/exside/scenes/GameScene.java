package com.devdream.exside.scenes;

import com.devdream.exside.Game;
import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.levels.TestLevel;

public class GameScene extends BaseScene {
    
    private static final String ID = "GAME_SCENE";
    
    public GameScene() {
        super(ID);
    }
    
    @Override
    public void init(final Game game) {
        super.init(game);
        
        level = new TestLevel(game.keyboard);
    }
    
    @Override
    public void update() {
        level.update();
    }
    
    @Override
    public void render(final Renderer renderer) {
        level.render(renderer);
    }
    
}
