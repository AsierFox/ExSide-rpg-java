package com.devdream.exside.tiled;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.devdream.exside.graphics.Sprite;
import com.devdream.exside.graphics.SpriteSheet;
import com.devdream.exside.maths.Rect;
import com.devdream.exside.utils.FileReader;

/**
 * Class that loads Tiled map.
 */
public class TiledMapSpawner {
    
    public static final String MAP_ROUTES = "/maps/";
    
    private static final String TILELAYER_TYPE = "tilelayer";
    private static final String OBJECTGROUP_TYPE = "objectgroup";
    
    // In Tiled Patterns are the rectangles marks
    private static final String SPAWN_PATTERN_NAME = "spawn";
    private static final String ENEMIES_LAYER_NAME = "item";
    
    private static final String SPAWN_PLAYER_TYPE = "player";
    private static final String SPAWN_CIVILIAN_TYPE = "civilian";
    private static final String SPAWN_ENEMY_TYPE = "enemy";
    
    private static final String ITEM_BREAKABLE_TYPE = "breakable";
    private static final String ITEM_OBTAINABLE_TYPE = "obtainable";
    private static final String ITEM_ANIMATED_TYPE = "animated";
    
    protected final int mapTilesWidth;
    protected final int mapTilesHeight;
    
    protected final int tileWidth;
    protected final int tileHeight;
    
    private final JSONObject tiledJSON;
    private final JSONArray tilesets;
    
    protected ArrayList<TileLayer> tileLayers;
    protected ArrayList<ColliderLayer> colliderLayers;
    protected ArrayList<Rect> mergedColliders;
    protected TiledTile[] tileSprites;
    protected ArrayList<Rect> mapTilesUbicationRect;
    // TODO Manage map items & enemies
    
    public TiledMapSpawner(final String mapName) {
        String fileContent = FileReader.readFile(MAP_ROUTES + mapName + FileReader.JSON_EXT);
        
        tiledJSON = new JSONObject(fileContent);
        
        mapTilesWidth = tiledJSON.getInt("width");
        mapTilesHeight = tiledJSON.getInt("height");
        
        tileWidth = tiledJSON.getInt("tilewidth");
        tileHeight = tiledJSON.getInt("tileheight");
        
        tilesets = tiledJSON.getJSONArray("tilesets");
        
        tileLayers = new ArrayList<>();
        colliderLayers = new ArrayList<>();
        mergedColliders = new ArrayList<>();
        mapTilesUbicationRect = new ArrayList<>();
    }
    
    public void load() {
        readLayers();
        
        // Combine colliders of different layers into one ArrayList for better
        // performance
        mergeCollisionLayers();
        
        int totalSprites = countAllSprites();
        tileSprites = new TiledTile[totalSprites];
        
        loadSpriteLayers();
    }
    
    /**
     * Gets the data of all layers, depending on the type.
     */
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
                    // Subtract 1 because Tiled use 1 to start counting, 0
                    // converts to -1 when are not tiles
                    tilesLocation[j] = tileLocation - 1;
                }
                tileLayers.add(new TileLayer(tilesLocation, mapTilesWidth, mapTilesHeight));
            } else if (OBJECTGROUP_TYPE.equals(layerType)) {
                JSONArray objects = layer.getJSONArray("objects");
                Rect[] rects = new Rect[objects.length()];
                
                for (int j = 0, jlen = objects.length(); j < jlen; j++) {
                    JSONObject object = objects.getJSONObject(j);
                    
                    int rectX = object.getInt("x");
                    int rectY = object.getInt("y");
                    int rectWidth = object.getInt("width");
                    int rectHeight = object.getInt("height");
                    
                    // If there is not pixels on rect area, make a pixel size
                    // area.
                    // Not having area can cause collision bugs.
                    if (rectX == 0) {
                        rectX = 1;
                    }
                    if (rectY == 0) {
                        rectY = 1;
                    }
                    if (rectWidth == 0) {
                        rectWidth = 1;
                    }
                    if (rectHeight == 0) {
                        rectHeight = 1;
                    }
                    
                    rects[j] = new Rect(rectX, rectY, rectWidth, rectHeight);
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
            Rect[] rects = colliderLayers.get(i).colliders;
            
            for (int j = 0, jlen = rects.length; j < jlen; j++) {
                mergedColliders.add(rects[j]);
            }
        }
    }
    
    /**
     * Count all sprites number of all sprite sheets.
     * 
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
    
    /**
     * Load all sprites of all layers.
     */
    private void loadSpriteLayers() {
        for (int i = 0, ilen = tilesets.length(); i < ilen; i++) {
            JSONObject tileset = tilesets.getJSONObject(i);
            
            String tilesetFilePath = tileset.getString("image");
            String tilesetFilename = tilesetFilePath.substring(tilesetFilePath.lastIndexOf("/") + 1);
            
            int tilesetWidth = tileset.getInt("imagewidth");
            int tilesetHeight = tileset.getInt("imageheight");
            
            // firstgid is the first sprite of each tileset (starts counting
            // from 1)
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
                        // If we don't have already any Sprite loaded, load it
                        // only once by the index
                        if (null == tileSprites[currentTileLocation]) {
                            tileSprites[currentTileLocation] = new TiledTile(new Sprite(currentTileLocation
                                    - firstTileGIDOfSheet, spriteSheet, tilesetTileWidth, tilesetTileHeight));
                        }
                    }
                }
            }
        }
    }
    
}
