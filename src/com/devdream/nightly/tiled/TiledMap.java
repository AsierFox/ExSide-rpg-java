package com.devdream.nightly.tiled;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.maths.Rect;
import com.devdream.nightly.properties.GameProperties;

import java.util.ArrayList;

public class TiledMap {

    private TiledMapSpawner spawner;

    public final int mapTilesWidth;
    public final int mapTilesHeight;

    public ArrayList<TileLayer> tileLayers;
    public ArrayList<Rect> mergedColliders;
    public TiledTile[] tileSprites;


    public TiledMap(final String mapName) {
    	spawner = new TiledMapSpawner(mapName);

    	spawner.load();

    	mapTilesWidth = spawner.mapTilesWidth;
    	mapTilesHeight = spawner.mapTilesHeight;
    	mergedColliders = spawner.mergedColliders;
    	tileLayers = spawner.tileLayers;
    	tileSprites = spawner.tileSprites;
    }

    public void update() {
    	// Tile specific logic
    }

    public void render(final Renderer renderer) {

    	for (int i = 0, ilen = tileLayers.size(); i < ilen; i++) {
    		int[] tileLocations = tileLayers.get(i).tilesLocation;

    		for (int y = 0; y < mapTilesHeight; y++) {
    			for (int x = 0; x < mapTilesWidth; x++) {
    				final int currentTileLocation = tileLocations[x + y * mapTilesWidth];

					// In TiledMapSpawner.readLayers() (0 converts to -1 when are not tiles)
    				if (-1 != currentTileLocation) {

						// TODO Make dynamic Tile size
						int xPosition = x << 4;
						int yPosition = y << 4;

						tileSprites[currentTileLocation].render(renderer, xPosition, yPosition);
    				}
    			}
    		}
    	}

    	if (GameProperties.instance().isDebug()) {
    		for (Rect rect : mergedColliders) {
        		renderer.renderRect(rect);
        	}
    	}
    }

    /**
     * Get the tile at specific location in the tile layer.
     * @param xLocation
     * @param yLocation
     * @param tileLayer Tile layer in which the sprite is located (<b>starting from 0</b>)
     * @return The tile <b>(-1 if there is not Sprite on that layer)</b>
     */
    public TiledTile getTile(final int xLocation, final int yLocation, final int tileLayer) {
    	final int tileLocation = tileLayers.get(tileLayer).tilesLocation[xLocation + yLocation * mapTilesWidth];
    	return tileSprites[tileLocation];
    }

}
