package com.devdream.nightly.entities.mob;

import java.awt.Rectangle;

import com.devdream.nightly.entities.Entity;
import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.GameWindow;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.SpriteAnimation;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.io.Mouse;
import com.devdream.nightly.items.projectiles.TestProjectile;
import com.devdream.nightly.properties.GameProperties;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;

/**
 * Singleton class player.
 */
public class Player extends Entity {

	private static Player instance;

	private Keyboard keyboard;

    private int cadenceCounter;

    private int colliderTopPadding;
    private int colliderLeftPadding;
    private int colliderRightPadding;

	private SpriteAnimation southAnimation;
	private SpriteAnimation westAnimation;
	private SpriteAnimation eastAnimation;
	private SpriteAnimation northAnimation;


	public static Player getInstance() {
    	if (null == instance) {
    		instance = new Player();
    	}
		return instance;
	}

    private Player() {
    	super(G.Sprites.playerDefault);

        colliderTopPadding = 5;
        colliderLeftPadding = 6;
        colliderRightPadding = 12;

        cadenceCounter = 0;

        southAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 0, 3);
        westAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 4, 7);
        eastAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 8, 11);
        northAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 12, 15);

        collider = new Rectangle(pos.x, pos.y, sprite.WIDTH - colliderRightPadding, (sprite.HEIGHT >> 1) - colliderTopPadding);
    }

    public void init(final Keyboard keyboard) {
        this.keyboard = keyboard;
        pos.x = 150;
        pos.y = 150;
    }

    @Override
    public void update() {
        int xMove = 0;
        int yMove = 0;

        // Read keyboard events
        if (keyboard.up) {
            yMove--;
        }
        else if (keyboard.down) {
            yMove++;
        }
        else if (keyboard.left) {
            xMove--;
        }
        else if (keyboard.right) {
            xMove++;
        }

        // Read mouse events
        if (Mouse.getBtn() == Mouse.LEFT_CLICK_BTN && canShoot()) {
            shoot();
            cadenceCounter = 0;
        }
        // Update cadence
        if (cadenceCounter <= TestProjectile.CADENCE) {
        	cadenceCounter++;
        }

        updateCollider();

        // Check diagonal movement for some reason
        //(xMove != 0 && yMove != 0)
        if (xMove != 0 || yMove != 0) {
            move(xMove, yMove);
        }
        else {
            state = EntityState.IDLE;
        }

        updateSprite();
    }

    private boolean canShoot() {
    	return cadenceCounter > TestProjectile.CADENCE;
    }

    private void shoot() {
        // >> 1 is like / 2
        int shootX = Mouse.getX() - (GameWindow.getTotalWidth() >> 1);
        int shootY = Mouse.getY() - (GameWindow.getTotalHeight() >> 1);
    	final double shootDirection = Math.atan2(shootY, shootX);
    	// Convert angle to degrees
    	//shootDirection *= 180/ Math.PI;
    	// TODO Check current weapon of the player
        belongsToLevel.addItem(new TestProjectile(pos.x, pos.y, shootDirection));
    }

    private void updateCollider() {
        collider.x = (pos.x - sprite.WIDTH / 2) + colliderLeftPadding;
        collider.y = pos.y + colliderTopPadding;
	}

	@Override
    public void render(Renderer renderer) {
        renderer.renderSprite(pos.x - sprite.WIDTH / 2, pos.y - sprite.HEIGHT / 2, sprite);

        if (GameProperties.instance().isDebug()) {
        	renderer.renderRect(collider);
        }
    }

    private void updateSprite() {
        if (direction == Direction.SOUTH) {
            if (state == EntityState.MOVING) {
            	southAnimation.update();
            } else {
            	southAnimation.reset();
            }
        	sprite = southAnimation.currentSprite;
        }
        else if (direction == Direction.WEST) {
            if (state == EntityState.MOVING) {
            	westAnimation.update();
            } else {
            	westAnimation.reset();
            }
        	sprite = westAnimation.currentSprite;
        }
        else if (direction == Direction.EAST) {
            if (state == EntityState.MOVING) {
            	eastAnimation.update();
            } else {
            	eastAnimation.reset();
            }
        	sprite = eastAnimation.currentSprite;
        }
        else if (direction == Direction.NORTH) {
            if (state == EntityState.MOVING) {
            	northAnimation.update();
            } else {
            	northAnimation.reset();
            }
        	sprite = northAnimation.currentSprite;
        }
    }

}
