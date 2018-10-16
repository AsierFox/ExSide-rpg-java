package com.devdream.nightly.graphics.tiled;

import com.devdream.nightly.entities.mob.Player;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.properties.GameProperties;

import java.awt.*;
import java.util.ArrayList;

public class TiledMap {

    private TiledMapSpawner spawner;

    public final int mapTilesWidth;
    public final int mapTilesHeight;

    private ArrayList<TileLayer> tileLayers;
    protected ArrayList<Rectangle> mergedColliders;
    private Sprite[] sprites;

    private ArrayList<Rectangle> updatingColliders;


    public TiledMap(final String mapName) {
    	spawner = new TiledMapSpawner(mapName);

    	spawner.load();

    	mapTilesWidth = spawner.mapTilesWidth;
    	mapTilesHeight = spawner.mapTilesHeight;
    	mergedColliders = spawner.mergedColliders;
    	tileLayers = spawner.tileLayers;
    	sprites = spawner.sprites;

    	updatingColliders = new ArrayList<>();
    }

    public void update() {
    	updateColliders();
    }

    public void render(final Renderer renderer) {
    	renderTiles(renderer);

    	if (GameProperties.instance().isDebug()) {
    		renderColliders(renderer);
    	}
    }

    private void renderTiles(final Renderer renderer) {
    	int xScroll = Player.getInstance().pos.x - renderer.width / 2;
        int yScroll = Player.getInstance().pos.y - renderer.height / 2;
        renderer.setOffset(xScroll, yScroll);

    	for (int i = 0, ilen = tileLayers.size(); i < ilen; i++) {
    		int[] tileLocations = tileLayers.get(i).tilesLocation;

    		for (int y = 0; y < mapTilesHeight; y++) {
    			for (int x = 0; x < mapTilesWidth; x++) {
    				final int currentTileLocation = tileLocations[x + y * mapTilesWidth];

					// TODO * 16, make dynamic
					int xPosition = x << 4;
					int yPosition = y << 4;

					// In readLayers() (0 converts to -1 when are not tiles)
    				if (-1 != currentTileLocation) {
    					renderer.renderTile(xPosition, yPosition, sprites[currentTileLocation]);
    				}
    			}
    		}
    	}
    }

    private void renderColliders(final Renderer renderer) {
    	for (Rectangle rect : updatingColliders) {
    		renderer.renderRect(rect);
    	}
    }

    private void updateColliders() {
        if (!updatingColliders.isEmpty()) {
            updatingColliders.clear();
        }
        for (int i = 0, ilen = mergedColliders.size(); i < ilen; i++) {
            final Rectangle rect = mergedColliders.get(i);

            // TODO Is this code necessary (?)

            updatingColliders.add(rect);
        }
    }

}
