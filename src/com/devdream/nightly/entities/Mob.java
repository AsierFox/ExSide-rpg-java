package com.devdream.nightly.entities;

import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;

/**
 * Entities that can have interaction in the game.
 *
 * TODO Implement lighting here in the future.
 */
public abstract class Mob extends Entity {

    protected int animationSpeed;
    protected int animationCounter;

    protected Sprite sprite;

    protected EntityState state;
    protected Direction direction;


    public Mob(final Sprite sprite) {
        super();
        animationSpeed = 150;
        animationCounter = 0;
        this.sprite = sprite;
        state = EntityState.IDLE;
        direction = Direction.SOUTH;
    }

    @Override
    public void init(final BaseLevel belongsToLevel) {
        this.belongsToLevel = belongsToLevel;
    }

    @Override
    public void update() {
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

    public final void move(final int xMove, final int yMove) {
        if (isCollision(xMove, yMove)) {
            return;
        }

        state = EntityState.MOVING;

        if (xMove < 0) {
            direction = Direction.WEST;
        }
        else if (xMove > 0) {
            direction = Direction.EAST;
        }
        if (yMove < 0) {
            direction = Direction.NORTH;
        }
        else if (yMove > 0) {
            direction = Direction.SOUTH;
        }

        x += xMove;
        y += yMove;
    }

    private boolean isCollision(final int xMove, final int yMove) {
//        if (belongsToLevel.getTile((x + xMove) / Tile.WORLD_TILE_SIZE, (y + yMove) / Tile.WORLD_TILE_SIZE).isSolid) {
//            return true;
//        }
        return false;
    }

}
