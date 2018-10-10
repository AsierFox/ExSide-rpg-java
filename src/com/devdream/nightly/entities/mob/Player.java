package com.devdream.nightly.entities.mob;

import com.devdream.nightly.entities.Mob;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;

public class Player extends Mob {

    private Keyboard input;

    public Player(final Keyboard input, final Sprite sprite) {
        super(sprite);
        this.input = input;

        load();
    }

    public Player(final Keyboard input, final Sprite sprite, final int x, final int y) {
        this(input, sprite);
        this.x = x;
        this.y = y;

        load();
    }

    @Override
    public void update() {
        super.update();

        int xMove = 0;
        int yMove = 0;

        if (input.up) {
            yMove--;
        }
        else if (input.down) {
            yMove++;
        }
        else if (input.left) {
            xMove--;
        }
        else if (input.right) {
            xMove++;
        }

        if (xMove != 0 || yMove != 0) {
            move(xMove, yMove);
        }
        else {
            state = EntityState.IDLE;
        }
    }

    @Override
    public void render(Renderer renderer) {
        setSprite();

        // We can duplicate this line to render more things
        renderer.renderPlayer(sprite, x - sprite.WIDTH / 2, y - sprite.HEIGHT / 2);
    }

    private void load() {
        animationSpeed = 100;
    }

    private void setSprite() {
        if (direction == Direction.SOUTH) {
            if (state == EntityState.MOVING) {
                if (animationCounter < (animationSpeed * .25)) {
                    sprite = Sprite.player_south_1;
                } else if (animationCounter > (animationSpeed * .25) && animationCounter < (animationSpeed * .5)) {
                    sprite = Sprite.player_south_2;
                } else if (animationCounter > (animationSpeed * .5) && animationCounter < (animationSpeed * .75)) {
                    sprite = Sprite.player_south_3;
                } else {
                    sprite = Sprite.player_south;
                }
            }
            else {
                sprite = Sprite.player_south;
            }
        }
        else if (direction == Direction.EAST) {
            if (state == EntityState.MOVING) {
                if (animationCounter < (animationSpeed * .25)) {
                    sprite = Sprite.player_east_1;
                } else if (animationCounter > (animationSpeed * .25) && animationCounter < (animationSpeed * .5)) {
                    sprite = Sprite.player_east_2;
                } else if (animationCounter > (animationSpeed * .5) && animationCounter < (animationSpeed * .75)) {
                    sprite = Sprite.player_east_3;
                } else {
                    sprite = Sprite.player_east;
                }
            }
            else {
                sprite = Sprite.player_east;
            }
        }
        else if (direction == Direction.WEST) {
            if (state == EntityState.MOVING) {
                if (animationCounter < (animationSpeed * .25)) {
                    sprite = Sprite.player_west_1;
                } else if (animationCounter > (animationSpeed * .25) && animationCounter < (animationSpeed * .5)) {
                    sprite = Sprite.player_west_2;
                } else if (animationCounter > (animationSpeed * .5) && animationCounter < (animationSpeed * .75)) {
                    sprite = Sprite.player_west_3;
                } else {
                    sprite = Sprite.player_west;
                }
            }
            else {
                sprite = Sprite.player_west;
            }
        }
        else if (direction == Direction.NORTH) {
            if (state == EntityState.MOVING) {
                if (animationCounter < (animationSpeed * .25)) {
                    sprite = Sprite.player_north_1;
                } else if (animationCounter > (animationSpeed * .25) && animationCounter < (animationSpeed * .5)) {
                    sprite = Sprite.player_north_2;
                } else if (animationCounter > (animationSpeed * .5) && animationCounter < (animationSpeed * .75)) {
                    sprite = Sprite.player_north_3;
                } else {
                    sprite = Sprite.player_north;
                }
            }
            else {
                sprite = Sprite.player_north;
            }
        }
    }

}
