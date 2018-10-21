package com.devdream.nightly.entities.mob;

import java.awt.Rectangle;

import com.devdream.nightly.entities.Entity;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;
import com.devdream.nightly.utils.MathUtils;

/**
 * Entities that can have interaction in the game.
 */
public abstract class Mob extends Entity {

    protected int animationSpeed;
    protected int animationCounter;

    protected EntityState state;
    protected Direction direction;


    public Mob(final Sprite sprite) {
        super();
        this.sprite = sprite;
        animationSpeed = 150;
        animationCounter = 0;
        state = EntityState.IDLE;
        direction = Direction.SOUTH;
    }

    @Override
    public void attachLevel(final BaseLevel belongsToLevel) {
        this.belongsToLevel = belongsToLevel;
    }

    @Override
    public void update() {
        // Animation calculations
        if (state == EntityState.MOVING) {
            animationCounter++;

            if (animationCounter < animationSpeed) {
                animationCounter++;
            } else {
                animationCounter = 0;
            }
        } else {
            animationCounter = 0;
        }
    }

    protected final void move(int xMove, int yMove) {
        state = EntityState.MOVING;

        if (yMove < 0) {
            direction = Direction.NORTH;
        }
        else if (yMove > 0) {
            direction = Direction.SOUTH;
        }
        if (xMove < 0) {
            direction = Direction.WEST;
        }
        else if (xMove > 0) {
            direction = Direction.EAST;
        }

        Direction collisionDirection = Direction.NONE;

        // Check collisions
        for (Rectangle mapCollider : belongsToLevel.tiledMap.mergedColliders) {

            if (collider.intersects(mapCollider)) {

            	collisionDirection = MathUtils.getRectangleDepthSideCollision(collider, mapCollider);
            	
            	/*
            	if (direction == Direction.WEST || direction == Direction.EAST) {
            		// Right collision
                	if (collider.x + collider.width >= mapCollider.x && collider.x + collider.width <= mapCollider.x + mapCollider.width) {
                		System.out.println("right");
                		isCollisionRight = true;
                	}
                	// Left collision
                	else if (collider.x <= mapCollider.x + mapCollider.width && collider.x >= mapCollider.x) {
                		System.out.println("left");
                		isCollisionLeft = true;
                	}
            	}

            	if (direction == Direction.NORTH || direction == Direction.SOUTH) {
	            	// Top collision
	        		if (collider.y <= mapCollider.y + mapCollider.height && collider.y + collider.height >= mapCollider.y + mapCollider.height) {
	        			System.out.println("top");
	        			isCollisionTop = true;
	        		}
	            	// Bottom collision
	        		else if (collider.y <= mapCollider.y + mapCollider.height && collider.y >= mapCollider.y) {
	        			System.out.println("bottom");
	        			isCollisionBottom = true;
	        		}
            	}*/

            	break;
            }
        }

        if (direction == Direction.NORTH && collisionDirection == Direction.NORTH) {
        	pos.x += xMove;
        }
        else if (direction == Direction.SOUTH && collisionDirection == Direction.SOUTH) {
        	pos.x += xMove;
        }
        else if (direction == Direction.WEST && collisionDirection == Direction.WEST) {
            pos.y += yMove;
        }
        else if (direction == Direction.EAST && collisionDirection == Direction.EAST) {
        	pos.y += yMove;
        }
        else {
        	pos.x += xMove;
        	pos.y += yMove;
        }
    }

    @Override
    public void render(final Renderer renderer) {
    }

}
