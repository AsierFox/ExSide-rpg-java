package com.devdream.nightly.graphics;

import com.devdream.nightly.graphics.tiles.GroundTile;
import com.devdream.nightly.graphics.tiles.VoidTile;

public interface G {


	public interface SpriteSheets {
	    public static final SpriteSheet road = new SpriteSheet("/tilesets/road.png", 32, 32);
	    public static final SpriteSheet player = new SpriteSheet("/01_player.png", 128, 192);
	}

	public interface Sprites {
	    // Tiles
	    public static final Tile voidTile = new VoidTile(new Sprite(0x271907, Tile.WORLD_TILE_SIZE));

	    public static final Tile grass = new GroundTile(new Sprite(0, G.SpriteSheets.road, Tile.WORLD_TILE_SIZE));
	    public static final Tile barro = new GroundTile(new Sprite(G.SpriteSheets.road, Tile.WORLD_TILE_SIZE, 1, 0));
	    public static final Tile sand = new GroundTile(new Sprite(G.SpriteSheets.road, Tile.WORLD_TILE_SIZE, 1, 1), true);

	    // Player
	    public static final Sprite player_south = new Sprite(G.SpriteSheets.player, 32, 48, 0, 0);
	    public static final Sprite player_south_1 = new Sprite(G.SpriteSheets.player, 32, 48, 1, 0);
	    public static final Sprite player_south_2 = new Sprite(G.SpriteSheets.player, 32, 48, 2, 0);
	    public static final Sprite player_south_3 = new Sprite(G.SpriteSheets.player, 32, 48, 3, 0);

	    public static final Sprite player_west = new Sprite(G.SpriteSheets.player, 32, 48, 0, 1);
	    public static final Sprite player_west_1 = new Sprite(G.SpriteSheets.player, 32, 48, 1, 1);
	    public static final Sprite player_west_2 = new Sprite(G.SpriteSheets.player, 32, 48, 2, 1);
	    public static final Sprite player_west_3 = new Sprite(G.SpriteSheets.player, 32, 48, 3, 1);

	    public static final Sprite player_east = new Sprite(G.SpriteSheets.player, 32, 48, 0, 2);
	    public static final Sprite player_east_1 = new Sprite(G.SpriteSheets.player, 32, 48, 1, 2);
	    public static final Sprite player_east_2 = new Sprite(G.SpriteSheets.player, 32, 48, 2, 2);
	    public static final Sprite player_east_3 = new Sprite(G.SpriteSheets.player, 32, 48, 3, 2);

	    public static final Sprite player_north = new Sprite(G.SpriteSheets.player, 32, 48, 0, 3);
	    public static final Sprite player_north_1 = new Sprite(G.SpriteSheets.player, 32, 48, 1, 3);
	    public static final Sprite player_north_2 = new Sprite(G.SpriteSheets.player, 32, 48, 2, 3);
	    public static final Sprite player_north_3 = new Sprite(G.SpriteSheets.player, 32, 48, 3, 3);
	}
	
}
