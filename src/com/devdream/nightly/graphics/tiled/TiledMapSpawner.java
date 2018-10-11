package com.devdream.nightly.graphics.tiled;

import com.devdream.nightly.maths.Vector2D;
import com.devdream.nightly.utils.FileReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public class TiledMapSpawner {

    public static final String MAP_ROUTES = "res/maps/";

    private static final String TILELAYER_TYPE = "tilelayer";
    private static final String OBJECTGROUP_TYPE = "objectgroup";

    private final int mapTilesWidth;
    private final int mapTilesHeight;

    private final int tileWidth;
    private final int tileHeight;

    private ArrayList<TileLayer> tileLayers;
    private ArrayList<ColliderLayer> colliderLayers;

    public TiledMapSpawner(final String mapName) {
        this(mapName, new Vector2D(0, 0));
    }

    public TiledMapSpawner(final String mapName, final Vector2D playerSpawn) {
        String fileContent = FileReader.readFile(MAP_ROUTES + mapName + FileReader.JSONExt);

        JSONObject tiledJSON = new JSONObject(fileContent);

        mapTilesWidth = tiledJSON.getInt("width");
        mapTilesHeight = tiledJSON.getInt("height");

        tileWidth = tiledJSON.getInt("tilewidth");
        tileHeight = tiledJSON.getInt("tileheight");

        tileLayers = new ArrayList<>();
        colliderLayers = new ArrayList<>();

        JSONArray layers = tiledJSON.getJSONArray("layers");
        for (int i = 0, ilen = layers.length(); i < ilen; i++) {
            JSONObject layer = layers.getJSONObject(i);
            String layerType = layer.getString("type");

            if (TILELAYER_TYPE.equals(layerType)) {
                JSONArray data = layer.getJSONArray("data");
                int[] tilesLocation = new int[data.length()];

                for (int j = 0, jlen = data.length(); j < jlen; j++) {
                    final int tileLocation = data.getInt(j);
                    // Subtract 1 because Tiled use 1 to start counting
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

                    rects[j] = new Rectangle(rectX, rectY, rectWidth, rectHeight);
                }
                colliderLayers.add(new ColliderLayer(rects, mapTilesWidth, mapTilesHeight));
            }
        }
    }

}
