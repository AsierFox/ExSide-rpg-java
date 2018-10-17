package com.devdream.nightly.graphics;

import com.devdream.nightly.tiled.TiledMap;

public interface G {

    interface Maps {
        TiledMap tiledMap = new TiledMap("tiled-map");
    }

	interface SpriteSheets {
		SpriteSheet projectiles = new SpriteSheet("/arrow.png", 14, 15);
		SpriteSheet road = new SpriteSheet("/tilesets/road.png", 32, 32);
	    SpriteSheet player = new SpriteSheet("/01_player.png", 128, 192);
	}

	interface Sprites {

    	Sprite arrow = new Sprite(0, G.SpriteSheets.projectiles, 14, 15);

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
