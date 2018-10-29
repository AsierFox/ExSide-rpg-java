package com.devdream.exside.tiled;

public class TileLayer extends TiledLayer {

    public int[] tilesLocation;

    public TileLayer(final int[] tilesLocation, final int width, final int height) {
        super(width, height);
        this.tilesLocation = tilesLocation;
    }

}
