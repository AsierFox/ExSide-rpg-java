package com.devdream.exside.levels;

import com.devdream.exside.entities.Civilian;
import com.devdream.exside.entities.Clergy;
import com.devdream.exside.entities.Enemy;
import com.devdream.exside.entities.Player;
import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.io.Keyboard;
import com.devdream.exside.tiled.TiledMap;

public class TestLevel extends BaseLevel {
    
    public TestLevel(final Keyboard keyboard) {
        super(keyboard);
    }
    
    @Override
    protected void load() {
        tiledMap = new TiledMap("tiled-map");
        
        Player clientPlayer = new Player();
        clientPlayer.init(keyboard);
        clientPlayer.attachToLevel(this);
        addPlayer(clientPlayer);
        
        Civilian ci = new Civilian();
        ci.attachToLevel(this);
        addEntity(ci);
        
        Enemy e = new Enemy();
        e.attachToLevel(this);
        addEntity(e);
        
        Clergy cl = new Clergy();
        cl.attachToLevel(this);
        addEntity(cl);
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
