package com.devdream.nightly.entities;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.GameWindow;
import com.devdream.nightly.graphics.SpriteAnimation;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.io.Mouse;
import com.devdream.nightly.items.projectiles.TestProjectile;
import com.devdream.nightly.maths.Rect;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;

/**
 * Singleton class player.
 * TODO Remove singleton
 */
public class Player extends Entity {

	private static Player instance;

	private static final float MAX_SPEED = 2.0f;

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
    	super(172.0f, 100.0f, G.Sprites.playerDefault);

        colliderTopPadding = 5;
        colliderLeftPadding = 6;
        colliderRightPadding = 12;

        cadenceCounter = 0;

        southAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 0, 3);
        westAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 4, 7);
        eastAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 8, 11);
        northAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 12, 15);

        collider = new Rect(pos.x.intValue(), pos.y.intValue(), sprite.WIDTH - colliderRightPadding, (sprite.HEIGHT >> 1) - colliderTopPadding);
    }

    public void init(final Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public void update() {
        moveAmount.x = 0;
        moveAmount.y = 0;

        // Read keyboard events
        if (keyboard.shift) {
           	speed = 2;
        } else {
        	speed = 1;
        }

        if (keyboard.up) {
            moveAmount.y -= speed;
        }
        else if (keyboard.down) {
            moveAmount.y += speed;
        }
        else if (keyboard.left) {
            moveAmount.x -= speed;
        }
        else if (keyboard.right) {
            moveAmount.x += speed;
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

        // Check diagonal movement for some reason
        //(xMove != 0 && yMove != 0)
        if (moveAmount.x != 0 || moveAmount.y != 0) {
            move(moveAmount);
        }
        else {
            state = EntityState.IDLE;
        }

        updateCollider();

        updateSprite();
    }

    private boolean canShoot() {
    	return cadenceCounter > TestProjectile.CADENCE;
    }

    private void shoot() {
        int shootX = Mouse.getX() - (GameWindow.getTotalWidth() >> 1);
        int shootY = Mouse.getY() - (GameWindow.getTotalHeight() >> 1);
    	final double shootDirection = Math.atan2(shootY, shootX);
    	// Convert angle to degrees
    	//shootDirection *= 180/ Math.PI;
    	// TODO Check current weapon of the player
        belongsToLevel.addItem(new TestProjectile(pos.x.intValue(), pos.y.intValue(), shootDirection));
    }

    private void updateCollider() {
        collider.x = (pos.x.intValue() - (sprite.WIDTH >> 1)) + colliderLeftPadding;
        collider.y = pos.y.intValue() + colliderTopPadding;
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
