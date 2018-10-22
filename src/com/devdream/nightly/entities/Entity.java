package com.devdream.nightly.entities;

import java.awt.Rectangle;
import java.util.Random;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.maths.Vector2D;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;
import com.devdream.nightly.utils.MathUtils;

/**
 * Abstract Entity class for all game entities.
 */
public abstract class Entity {

    public Vector2D<Integer> pos;
    public Sprite sprite;
    public Rectangle collider;

    protected EntityState state;
    protected Direction direction;

    protected BaseLevel belongsToLevel;

    protected Random rand;

    private boolean isRemoved;


    public Entity() {
    	pos = new Vector2D<>(0, 0);
    	rand = new Random();
        isRemoved = false;

        state = EntityState.IDLE;
        direction = Direction.SOUTH;
    }
    
    public Entity(final Sprite sprite) {
    	this();
    	this.sprite = sprite;
    }

	public void attachToLevel(BaseLevel belongsToLevel) {
		this.belongsToLevel = belongsToLevel;
	}

    public abstract void update();

    public abstract void render(final Renderer renderer);

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
            // TODO Check only rectangles that are near the player
            if (collider.intersects(mapCollider)) {
            	collisionDirection = MathUtils.getRectangleDepthSideCollision(collider, mapCollider);
            	break;
            }
        }

        if (direction == Direction.NORTH && collisionDirection == Direction.NORTH
        		|| direction == Direction.SOUTH && collisionDirection == Direction.SOUTH) {
        	pos.x += xMove;
        }
        else if (direction == Direction.WEST && collisionDirection == Direction.WEST
        		|| direction == Direction.EAST && collisionDirection == Direction.EAST) {
        	pos.y += yMove;
        }
        else {
        	pos.x += xMove;
        	pos.y += yMove;
        }
    }

    public void dispose() {
        // Remove from level
    	belongsToLevel.removeEntity(this);
        isRemoved = true;
    }

    public boolean isRemoved() {
        return isRemoved;
    }
}
