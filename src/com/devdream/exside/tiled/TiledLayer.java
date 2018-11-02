package com.devdream.exside.tiled;

public abstract class TiledLayer {
    
    // Used for parallax effect, to move all layer
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    public TiledLayer(final int width, final int height) {
        this(0, 0, width, height);
    }
    
    public TiledLayer(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
}
