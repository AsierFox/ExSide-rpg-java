package com.devdream.nightly.graphics;

import com.devdream.nightly.graphics.tiled.TiledMap;
import com.devdream.nightly.graphics.tiles.GroundTile;
import com.devdream.nightly.graphics.tiles.VoidTile;

public interface G {

    interface Maps {
        TiledMap tiledMap = new TiledMap("tiled-map");
    }

	interface SpriteSheets {
	    SpriteSheet road = new SpriteSheet("/tilesets/road.png", 32, 32);
	    SpriteSheet player = new SpriteSheet("/01_player.png", 128, 192);
	}

	interface Sprites {
	    // Tiles
	    Tile voidTile = new VoidTile(new Sprite(0x271907, Tile.WORLD_TILE_SIZE));

	    Tile grass = new GroundTile(new Sprite(0, G.SpriteSheets.road, Tile.WORLD_TILE_SIZE));
	    Tile barro = new GroundTile(new Sprite(G.SpriteSheets.road, Tile.WORLD_TILE_SIZE, 1, 0), true);
	    Tile sand = new GroundTile(new Sprite(G.SpriteSheets.road, Tile.WORLD_TILE_SIZE, 1, 1));

	    // Player
	    Sprite player_south = new Sprite(G.SpriteSheets.player, 32, 48, 0, 0);
	    Sprite player_south_1 = new Sprite(G.SpriteSheets.player, 32, 48, 1, 0);
	    Sprite player_south_2 = new Sprite(G.SpriteSheets.player, 32, 48, 2, 0);
	    Sprite player_south_3 = new Sprite(G.SpriteSheets.player, 32, 48, 3, 0);

	    Sprite player_west = new Sprite(G.SpriteSheets.player, 32, 48, 0, 1);
	    Sprite player_west_1 = new Sprite(G.SpriteSheets.player, 32, 48, 1, 1);
	    Sprite player_west_2 = new Sprite(G.SpriteSheets.player, 32, 48, 2, 1);
	    Sprite player_west_3 = new Sprite(G.SpriteSheets.player, 32, 48, 3, 1);

	    Sprite player_east = new Sprite(G.SpriteSheets.player, 32, 48, 0, 2);
	    Sprite player_east_1 = new Sprite(G.SpriteSheets.player, 32, 48, 1, 2);
	    Sprite player_east_2 = new Sprite(G.SpriteSheets.player, 32, 48, 2, 2);
	    Sprite player_east_3 = new Sprite(G.SpriteSheets.player, 32, 48, 3, 2);

	    Sprite player_north = new Sprite(G.SpriteSheets.player, 32, 48, 0, 3);
	    Sprite player_north_1 = new Sprite(G.SpriteSheets.player, 32, 48, 1, 3);
	    Sprite player_north_2 = new Sprite(G.SpriteSheets.player, 32, 48, 2, 3);
	    Sprite player_north_3 = new Sprite(G.SpriteSheets.player, 32, 48, 3, 3);
	}

}
