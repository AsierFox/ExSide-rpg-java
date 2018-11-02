package com.devdream.exside.tiled;

import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.graphics.Sprite;

public class TiledTile {
    
    public final Sprite sprite;
    
    private boolean isRemoved;
    
    public TiledTile(final Sprite sprite) {
        this.sprite = sprite;
        isRemoved = false;
    }
    
    public void update() {
        //
    }
    
    public void render(Renderer renderer, int xPosition, int yPosition) {
        renderer.renderTile(xPosition, yPosition, this);
    }
    
    public boolean isRemoved() {
        return isRemoved;
    }
    
}
