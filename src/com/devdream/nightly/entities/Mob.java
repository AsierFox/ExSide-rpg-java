package com.devdream.nightly.entities;

import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;

/**
 * Entities that can have interaction in the game.
 *
 * TODO Implement lighting here in the future.
 */
public abstract class Mob extends Entity {

    public Sprite sprite;

    protected EntityState state;
    protected Direction direction;


    public Mob(final Sprite sprite) {
        this.sprite = sprite;
        state = EntityState.IDLE;
        direction = Direction.SOUTH;
    }

    public void move(final int xMove, final int yMove) {
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

        if (!isCollision()) {
            x += xMove;
            y += yMove;
        }
    }

    @Override
    public void update() {
        //
    }

    private boolean isCollision() {
        return false;
    }

}
