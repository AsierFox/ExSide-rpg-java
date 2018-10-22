package com.devdream.nightly.graphics;

import java.awt.Transparency;

import com.devdream.nightly.tiled.TiledMap;

public interface G {

    interface Maps {
        TiledMap testTiledMap = new TiledMap("tiled-map");
    }

	interface SpriteSheets {
		// Projectiles
		SpriteSheet projectiles = new SpriteSheet("/items/arrow.png", 14, 15, Transparency.OPAQUE, 0xff000000);
	    // Entities
	    SpriteSheet player = new SpriteSheet("/entities/player.png", 128, 192, Transparency.OPAQUE, 0xff000000);
	    SpriteSheet civilian = new SpriteSheet("/entities/civilian.png", 470, 470, Transparency.OPAQUE, 0xff000000);
	    SpriteSheet enemy = new SpriteSheet("/entities/enemy.png", 128, 192, Transparency.OPAQUE, 0xff000000);
		// HUD
	    SpriteSheet hudBackground = new SpriteSheet("/hud/hud-bg.png", 276, 37, Transparency.OPAQUE, 0xffffffff);
	}

	interface Sprites {
		/**
		 * HUD
		 */
		Sprite hudBackground = new Sprite(0, G.SpriteSheets.hudBackground, 276, 37);
		/**
		 * Projectiles
		 */
    	Sprite arrow = new Sprite(0, G.SpriteSheets.projectiles, 14, 15);
	    /**
	     * Entities
	     */
    	// Player
    	int playerWidth = 32;
    	int playerHeight = 48;
    	Sprite playerDefault = new Sprite(0, G.SpriteSheets.player, playerWidth, playerHeight);
    	// Civilian
    	int civilianWidth = 116;
    	int civilianHeight = 116;
    	Sprite civilianDefault = new Sprite(0, G.SpriteSheets.civilian, civilianWidth, civilianHeight);
    	// Enemy
    	int enemyWidth = 32;
    	int enemyHeight = 48;
    	Sprite enemyDefault = new Sprite(0, G.SpriteSheets.enemy, enemyWidth, enemyHeight);
	    /**
	     * Particles
	     */
	    Sprite arrowParticle = new Sprite(0xff00e5ff, 2);
	}

}
