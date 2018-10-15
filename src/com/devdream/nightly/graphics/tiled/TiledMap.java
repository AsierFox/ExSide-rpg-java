package com.devdream.nightly.graphics.tiled;

import com.devdream.nightly.entities.mob.Player;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.graphics.SpriteSheet;
import com.devdream.nightly.maths.Vector2D;
import com.devdream.nightly.utils.FileReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public class TiledMap {

    public static final String MAP_ROUTES = "/maps/";

    private static final String ITEMS_LAYER_NAME = "ITEMS";
    private static final String ENEMIES_LAYER_NAME = "ENEMIES";

    private static final String TILELAYER_TYPE = "tilelayer";
    private static final String OBJECTGROUP_TYPE = "objectgroup";

    public final int mapTilesWidth;
    public final int mapTilesHeight;

    private final int tileWidth;
    private final int tileHeight;

    private JSONObject tiledJSON;
    private JSONArray tilesets;

    private ArrayList<TileLayer> tileLayers;
    private ArrayList<ColliderLayer> colliderLayers;
    private ArrayList<Rectangle> mergedColliders;
    private ArrayList<Rectangle> updatingColliders;
    // TODO Manage map items & enemies
    private ArrayList<?> mapItems;
    private ArrayList<?> mapEnemies;

    private Sprite[] sprites;

    
    public TiledMap(final String mapName) {
        this(mapName, new Vector2D(0, 0));
    }

    public TiledMap(final String mapName, final Vector2D playerSpawn) {
        String fileContent = FileReader.readFile("res/" + MAP_ROUTES + mapName + FileReader.JSONExt);

        tiledJSON = new JSONObject(fileContent);

        mapTilesWidth = tiledJSON.getInt("width");
        mapTilesHeight = tiledJSON.getInt("height");

        tileWidth = tiledJSON.getInt("tilewidth");
        tileHeight = tiledJSON.getInt("tileheight");

        tilesets = tiledJSON.getJSONArray("tilesets");

        tileLayers = new ArrayList<>();
        colliderLayers = new ArrayList<>();
        mergedColliders = new ArrayList<>();
        updatingColliders = new ArrayList<>();

        readLayers();

        // Combine colliders of different layers into one ArrayList for better performance
        mergeCollisionLayers();

        int totalSprites = countAllSprites();
        sprites = new Sprite[totalSprites];

        loadSpriteLayers();

        loadItemLayers();
        loadEnemyLayers();
    }

    public void update() {
    	updateColliders();
    }

    public void render(final Renderer renderer, final Player player) {
    	int xScroll = player.x - renderer.width / 2;
        int yScroll = player.y - renderer.height / 2;
        renderer.setOffset(xScroll, yScroll);

    	for (int i = 0, ilen = tileLayers.size(); i < ilen; i++) {
    		int[] tileLocations = tileLayers.get(i).tilesLocation;

    		for (int y = 0; y < mapTilesHeight; y++) {
    			for (int x = 0; x < mapTilesWidth; x++) {
    				final int currentTileLocation = tileLocations[x + y * mapTilesWidth];

    				// In readLayers() (0 converts to -1 when are not tiles)
    				if (-1 != currentTileLocation) {
    					// TODO * 16, make dynamic
    					int xPosition = x << 4;
    					int yPosition = y << 4;
    					renderer.renderTile(xPosition, yPosition, sprites[currentTileLocation]);
    				}
    			}
    		}
    	}
    }

    private void updateColliders() {
        if (!updatingColliders.isEmpty()) {
            updatingColliders.clear();
        }
        for (int i = 0, ilen = mergedColliders.size(); i < ilen; i++) {
            final Rectangle rect = mergedColliders.get(i);
            // TODO revisar get player
            int xPosition = rect.x; //- (int) player.x;
            int yPosition = rect.y; //- (int) player.y;
            final Rectangle updatedRect = new Rectangle(xPosition, yPosition, rect.width, rect.height);

            updatingColliders.add(updatedRect);
        }
    }

	private void readLayers() {
        JSONArray layers = tiledJSON.getJSONArray("layers");

        for (int i = 0, ilen = layers.length(); i < ilen; i++) {
            JSONObject layer = layers.getJSONObject(i);
            String layerType = layer.getString("type");

            if (TILELAYER_TYPE.equals(layerType)) {
                JSONArray data = layer.getJSONArray("data");
                int[] tilesLocation = new int[data.length()];

                for (int j = 0, jlen = data.length(); j < jlen; j++) {
                    final int tileLocation = data.getInt(j);
                    // Subtract 1 because Tiled use 1 to start counting, 0 converts to -1 when are not tiles
                    tilesLocation[j] = tileLocation - 1;
                }
                tileLayers.add(new TileLayer(tilesLocation, mapTilesWidth, mapTilesHeight));
            }
            else if (OBJECTGROUP_TYPE.equals(layerType)) {
                JSONArray objects = layer.getJSONArray("objects");
                Rectangle[] rects = new Rectangle[objects.length()];

                for (int j = 0, jlen = objects.length(); j < jlen; j++) {
                    JSONObject object = objects.getJSONObject(j);

                    int rectX = object.getInt("x");
                    int rectY = object.getInt("y");
                    int rectWidth = object.getInt("width");
                    int rectHeight = object.getInt("height");

                    // If there is not pixels on rect area, make a pixel size area.
                    // Not having area can cause collision bugs.
                    if (rectX == 0) rectX = 1;
                    if (rectY == 0) rectY = 1;
                    if (rectWidth == 0) rectWidth = 1;
                    if (rectHeight == 0) rectHeight = 1;

                    rects[j] = new Rectangle(rectX, rectY, rectWidth, rectHeight);
                }
                colliderLayers.add(new ColliderLayer(rects, mapTilesWidth, mapTilesHeight));
            }
        }
    }

	/**
	 * Get all colliders layers and collect in a single array.
	 */
    private void mergeCollisionLayers() {
        for (int i = 0, ilen = colliderLayers.size(); i < ilen; i++) {
            Rectangle[] rects = colliderLayers.get(i).colliders;

            for (int j = 0, jlen = rects.length; j < jlen; j++) {
                mergedColliders.add(rects[j]);
            }
        }
    }

    /**
     * Count all sprites number of all sprite sheets.
     * @return
     */
    private int countAllSprites() {
    	int totalSprites = 0;
    	for (int i = 0, ilen = tilesets.length(); i < ilen; i++) {
            JSONObject tileset = tilesets.getJSONObject(i);
            totalSprites += tileset.getInt("tilecount");
        }
    	return totalSprites;
	}

    private void loadSpriteLayers() {
		for (int i = 0, ilen = tilesets.length(); i < ilen; i++) {
            JSONObject tileset = tilesets.getJSONObject(i);

            String tilesetFilePath = tileset.getString("image");
            String tilesetFilename = tilesetFilePath.substring(tilesetFilePath.lastIndexOf("/") + 1);

            int tilesetWidth = tileset.getInt("imagewidth");
            int tilesetHeight = tileset.getInt("imageheight");

            // firstgid is the first sprite of each tileset (starts counting from 1)
            final int firstTileGIDOfSheet = tileset.getInt("firstgid") - 1;
            final int lastTileOfSheet = firstTileGIDOfSheet + tileset.getInt("tilecount") - 1;

            final SpriteSheet spriteSheet = new SpriteSheet(MAP_ROUTES + tilesetFilename, tilesetWidth, tilesetHeight);

            int tilesetTileWidth = tileset.getInt("tilewidth");
            int tilesetTileHeight = tileset.getInt("tileheight");

            // Read layers of sprites
            for (int j = 0, jlen = tileLayers.size(); j < jlen; j++) {
                TileLayer tileLayer = tileLayers.get(j);
                int[] tilesLocation = tileLayer.tilesLocation;

                // Load only sprites that we are using from tilesets
                for (int k = 0, klen = tilesLocation.length; k < klen; k++) {
                    int currentTileLocation = tilesLocation[k];

                    // Check that we use the correct sprite sheet
                    if (currentTileLocation >= firstTileGIDOfSheet && currentTileLocation <= lastTileOfSheet) {
                        // If we don't have already any Sprite loaded, load it only once by the index
                        if (null == sprites[currentTileLocation]) {
                            sprites[currentTileLocation] = new Sprite(currentTileLocation - firstTileGIDOfSheet, spriteSheet, tilesetTileWidth, tilesetTileHeight);
                        }
                    }
                }
            }
        }
	}

    private void loadItemLayers() {
    }

    private void loadEnemyLayers() {
    }

}
