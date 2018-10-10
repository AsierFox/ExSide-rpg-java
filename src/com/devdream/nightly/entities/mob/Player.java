package com.devdream.nightly.entities.mob;

import com.devdream.nightly.entities.Mob;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.types.EntityState;

public class Player extends Mob {

    private Keyboard input;

    public Player(final Keyboard input, final Sprite sprite) {
        super(sprite);
        this.input = input;
    }

    public Player(final Keyboard input, final Sprite sprite, final int x, final int y) {
        this(input, sprite);
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        int xMove = 0;
        int yMove = 0;

        if (input.up) {
            yMove--;
        }
        if (input.down) {
            yMove++;
        }
        if (input.left) {
            xMove--;
        }
        if (input.right) {
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
        // We can duplicate this line to render more things
        int xCenter = x - sprite.WIDTH / 2;
        int yCenter = y - sprite.HEIGHT / 2;

        renderer.renderPlayer(sprite, xCenter, yCenter);
    }

}
