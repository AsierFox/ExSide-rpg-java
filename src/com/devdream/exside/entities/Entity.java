package com.devdream.exside.entities;

import java.util.Random;

import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.graphics.Sprite;
import com.devdream.exside.levels.BaseLevel;
import com.devdream.exside.maths.Rect;
import com.devdream.exside.maths.Vector2DFloat;
import com.devdream.exside.maths.Vector2DInt;
import com.devdream.exside.properties.GameProperties;
import com.devdream.exside.types.Direction;
import com.devdream.exside.types.EntityState;
import com.devdream.exside.utils.MathUtils;

/**
 * Abstract Entity class for all game entities.
 */
public abstract class Entity {

    public Vector2DFloat<Float> pos;
    public Sprite sprite;
    public Rect collider;

    protected EntityState state;
    protected Direction direction;

    protected BaseLevel belongsToLevel;

    protected Random rand;
    
    public int time;
    public Vector2DInt<Integer> moveAmount;
    protected Integer speed;

    private boolean isRemoved;


    public Entity(final float xPosition, final float yPostion, final Sprite sprite) {
    	pos = new Vector2DFloat<>(xPosition, yPostion);
    	this.sprite = sprite;

    	rand = new Random();
    	
    	time = 0;
    	moveAmount = new Vector2DInt<>();
        speed = 1;
        isRemoved = false;

        state = EntityState.IDLE;
        direction = Direction.SOUTH;
    }

    public void attachToLevel(BaseLevel belongsToLevel) {
		this.belongsToLevel = belongsToLevel;
	}

    public abstract void update();

    public void render(final Renderer renderer) {
        renderer.renderEnity(pos.x.intValue(), pos.y.intValue(), this);

        if (GameProperties.instance().isDebug()) {
            renderer.renderRect(collider);
        }
    }

    protected final void move(Vector2DInt<Integer> moveAmount) {

        state = EntityState.MOVING;

        if (moveAmount.x < 0) {
            direction = Direction.WEST;
        }
        else if (moveAmount.x > 0) {
            direction = Direction.EAST;
        }
        if (moveAmount.y < 0) {
            direction = Direction.NORTH;
        }
        else if (moveAmount.y > 0) {
            direction = Direction.SOUTH;
        }

        Direction collisionDirection = Direction.NONE;

        // Check collisions
        for (Rect mapCollider : belongsToLevel.tiledMap.mergedColliders) {
            // TODO Check only rectangles that are near the player
            if (collider.intersects(mapCollider)) {
            	collisionDirection = MathUtils.getRectangleDepthSideCollision(collider, mapCollider);
            	break;
            }
        }

        moveAmount.x *= speed;
        moveAmount.y *= speed;

        if (direction == Direction.NORTH && collisionDirection == Direction.NORTH
        		|| direction == Direction.SOUTH && collisionDirection == Direction.SOUTH) {
        	pos.x += moveAmount.x;
        }
        else if (direction == Direction.WEST && collisionDirection == Direction.WEST
        		|| direction == Direction.EAST && collisionDirection == Direction.EAST) {
        	pos.y += moveAmount.y;
        }
        else {
        	pos.x += moveAmount.x;
        	pos.y += moveAmount.y;
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

    public Vector2DInt<Integer> getSpriteCenter() {
    	return new Vector2DInt<>(sprite.WIDTH / 2, sprite.HEIGHT / 2);
    }

}
