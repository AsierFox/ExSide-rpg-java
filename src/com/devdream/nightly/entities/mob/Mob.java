package com.devdream.nightly.entities.mob;

import com.devdream.nightly.entities.Entity;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.items.Arrow;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;

import java.awt.*;
import java.util.ArrayList;

/**
 * Entities that can have interaction in the game.
 *
 * TODO Implement lighting here in the future.
 */
public abstract class Mob extends Entity {

    protected int animationSpeed;
    protected int animationCounter;

    protected EntityState state;
    protected Direction direction;

    protected Rectangle collider;

    protected ArrayList<Arrow> updatingArrows;

    public Mob(final Sprite sprite) {
        super();
        this.sprite = sprite;
        animationSpeed = 150;
        animationCounter = 0;
        state = EntityState.IDLE;
        direction = Direction.SOUTH;

        updatingArrows = new ArrayList<>();
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

        for (Arrow arrow : updatingArrows) {
            arrow.update();
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

        boolean isCollisionX = false;
        boolean isCollisionY = false;

        // Check collisions
        for (Rectangle mapCollider : belongsToLevel.tiledMap.mergedColliders) {

            if (collider.intersects(mapCollider)) {
/*
                // Right collision
                if (xMove > 0 && collider.x - mapCollider.x < 0) {
                    System.out.println("Right");
                    isCollisionX = true;
                    xMove = 0;
                }
                // Left collision
                if (xMove < 0 && collider.x - mapCollider.x > 0) {
                    System.out.println("Left");
                    isCollisionX = true;
                    xMove = 0;
                }
                // Top collision
                if (yMove < 0 && collider.y - mapCollider.y > 0) {
                    System.out.println("Top");
                    isCollisionY = true;
                    yMove = 0;
                }
                // Bottom collision
                if (yMove > 0 && collider.y - mapCollider.y < 0) {
                    System.out.println("Bottom");
                    isCollisionY = true;
                    yMove = 0;
                }
*/

            	isCollisionX = (xMove > 0 && collider.x - mapCollider.x < 0) || xMove < 0 && collider.x - mapCollider.x > 0;
            	isCollisionY = (yMove < 0 && collider.y - mapCollider.y > 0) || (yMove > 0 && collider.y - mapCollider.y < 0);

            	break;
            }
        }

        if (!isCollisionX) {
            pos.x += xMove;
        }
        if (!isCollisionY) {
            pos.y += yMove;
        }
    }

    @Override
    public void render(final Renderer renderer) {
        for (Arrow arrow : updatingArrows) {
            arrow.render(renderer);
        }
    }

}
