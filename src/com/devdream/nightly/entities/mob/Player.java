package com.devdream.nightly.entities.mob;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.GameWindow;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.io.Mouse;
import com.devdream.nightly.items.Arrow;
import com.devdream.nightly.maths.Vector2D;
import com.devdream.nightly.properties.GameProperties;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;

import java.awt.*;

/**
 * Singleton class player.
 */
public class Player extends Mob {

	private static Player instance;

	private Keyboard keyboard;

    private int cadenceCounter;


    public static Player getInstance() {
    	if (null == instance) {
    		instance = new Player();
    	}
		return instance;
	}

    private Player() {
    	super(G.Sprites.player_south);

        animationSpeed = 100;

        colliderLeftPadding = 5;
        colliderRightPadding = 10;

        cadenceCounter = 0;
    }

    public void init(final Keyboard keyboard, final Vector2D<Integer> spawnPosition) {
        this.keyboard = keyboard;
        pos.x = spawnPosition.x;
        pos.y = spawnPosition.y;
        collider = new Rectangle(pos.x, pos.y, sprite.WIDTH - colliderRightPadding, sprite.HEIGHT >> 1);
    }

    @Override
    public void update() {
        super.update();

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
        if (Mouse.getBtn() == 1 && canShoot()) {
            shoot();
            cadenceCounter = 0;
        }

        // Update
        if (cadenceCounter <= Arrow.CADENCE) {
        	cadenceCounter++;
        }

        updateCollider();

        // Check diagonal movement for some reason
        // (xMove != 0 && yMove != 0)
        if (xMove != 0 || yMove != 0) {
            move(xMove, yMove);
        }
        else {
            state = EntityState.IDLE;
        }
    }

    private boolean canShoot() {
    	return cadenceCounter > Arrow.CADENCE;
    }

    private void shoot() {
        // >> 1 is like / 2
        int shootX = Mouse.getX() - (GameWindow.getTotalWidth() >> 1);
        int shootY = Mouse.getY() - (GameWindow.getTotalHeight() >> 1);
    	final double shootDirection = Math.atan2(shootY, shootX);
    	// Convert angle to degrees
    	//shootDirection *= 180/ Math.PI;
    	// TODO Check current weapon of the player
        belongsToLevel.addItem(new Arrow(pos.x, pos.y, shootDirection));
    }

    private void updateCollider() {
        collider.x = (pos.x - sprite.WIDTH / 2) + colliderLeftPadding;
        collider.y = pos.y;
	}

	@Override
    public void render(Renderer renderer) {
        super.render(renderer);

        setSprite();

        renderer.renderPlayer(pos.x - sprite.WIDTH / 2, pos.y - sprite.HEIGHT / 2, sprite);

        if (GameProperties.instance().isDebug()) {
        	renderer.renderRect(collider);
        }
    }

    private void setSprite() {
        // TODO Refactor
        if (direction == Direction.SOUTH) {
            if (state == EntityState.MOVING) {
                if (animationCounter < (animationSpeed * .25)) {
                    sprite = G.Sprites.player_south_1;
                } else if (animationCounter > (animationSpeed * .25) && animationCounter < (animationSpeed * .5)) {
                    sprite = G.Sprites.player_south_2;
                } else if (animationCounter > (animationSpeed * .5) && animationCounter < (animationSpeed * .75)) {
                    sprite = G.Sprites.player_south_3;
                } else {
                    sprite = G.Sprites.player_south;
                }
            }
            else {
                sprite = G.Sprites.player_south;
            }
        }
        else if (direction == Direction.EAST) {
            if (state == EntityState.MOVING) {
                if (animationCounter < (animationSpeed * .25)) {
                    sprite = G.Sprites.player_east_1;
                } else if (animationCounter > (animationSpeed * .25) && animationCounter < (animationSpeed * .5)) {
                    sprite = G.Sprites.player_east_2;
                } else if (animationCounter > (animationSpeed * .5) && animationCounter < (animationSpeed * .75)) {
                    sprite = G.Sprites.player_east_3;
                } else {
                    sprite = G.Sprites.player_east;
                }
            }
            else {
                sprite = G.Sprites.player_east;
            }
        }
        else if (direction == Direction.WEST) {
            if (state == EntityState.MOVING) {
                if (animationCounter < (animationSpeed * .25)) {
                    sprite = G.Sprites.player_west_1;
                } else if (animationCounter > (animationSpeed * .25) && animationCounter < (animationSpeed * .5)) {
                    sprite = G.Sprites.player_west_2;
                } else if (animationCounter > (animationSpeed * .5) && animationCounter < (animationSpeed * .75)) {
                    sprite = G.Sprites.player_west_3;
                } else {
                    sprite = G.Sprites.player_west;
                }
            }
            else {
                sprite = G.Sprites.player_west;
            }
        }
        else if (direction == Direction.NORTH) {
            if (state == EntityState.MOVING) {
                if (animationCounter < (animationSpeed * .25)) {
                    sprite = G.Sprites.player_north_1;
                } else if (animationCounter > (animationSpeed * .25) && animationCounter < (animationSpeed * .5)) {
                    sprite = G.Sprites.player_north_2;
                } else if (animationCounter > (animationSpeed * .5) && animationCounter < (animationSpeed * .75)) {
                    sprite = G.Sprites.player_north_3;
                } else {
                    sprite = G.Sprites.player_north;
                }
            }
            else {
                sprite = G.Sprites.player_north;
            }
        }
    }

}
