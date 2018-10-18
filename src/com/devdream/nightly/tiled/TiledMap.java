package com.devdream.nightly.tiled;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.properties.GameProperties;

import java.awt.*;
import java.util.ArrayList;

public class TiledMap {

    private TiledMapSpawner spawner;

    public final int mapTilesWidth;
    public final int mapTilesHeight;

    public ArrayList<TileLayer> tileLayers;
    public ArrayList<Rectangle> mergedColliders;
    public Sprite[] sprites;


    public TiledMap(final String mapName) {
    	spawner = new TiledMapSpawner(mapName);

    	spawner.load();

    	mapTilesWidth = spawner.mapTilesWidth;
    	mapTilesHeight = spawner.mapTilesHeight;
    	mergedColliders = spawner.mergedColliders;
    	tileLayers = spawner.tileLayers;
    	sprites = spawner.sprites;
    }

    public void update() {
    	// Remove logic, etc...
    }

    public void render(final Renderer renderer) {

    	renderer.renderMap(this);

    	if (GameProperties.instance().isDebug()) {
    		for (Rectangle rect : mergedColliders) {
        		renderer.renderRect(rect);
        	}
    	}
    }

}
