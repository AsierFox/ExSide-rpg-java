package com.devdream.nightly.entities.mob;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.io.Keyboard;
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
    
    private Rectangle collider;


    public static Player getInstance() {
    	if (null == instance) {
    		instance = new Player();
    	}
		return instance;
	}

    public Player() {
    	super(G.Sprites.player_south);

    	load();
    	
    	collider = new Rectangle(pos.x, pos.y, sprite.WIDTH, sprite.HEIGHT);
    }

    public void init(final Keyboard keyboard, final Vector2D spawnPosition) {
        this.keyboard = keyboard;
        pos.x = spawnPosition.x;
        pos.y = spawnPosition.y;
    }

    @Override
    public void update() {
        super.update();

        int xMove = 0;
        int yMove = 0;

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

        if (xMove != 0 || yMove != 0) {
            move(xMove, yMove);
        }
        else {
            state = EntityState.IDLE;
        }

        // Update collider position
        collider.x = pos.x - sprite.WIDTH / 2;
        collider.y = pos.y - sprite.HEIGHT / 2;
        collider.width = sprite.WIDTH;
        collider.height = sprite.HEIGHT;
    }

    @Override
    public void render(Renderer renderer) {
        setSprite();

        // We can duplicate this line to render more things
        renderer.renderPlayer(sprite, pos.x - sprite.WIDTH / 2, pos.y - sprite.HEIGHT / 2);

        if (GameProperties.instance().isDebug()) {
        	renderer.renderRect(collider);
        }
    }

    private void load() {
        animationSpeed = 100;
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
