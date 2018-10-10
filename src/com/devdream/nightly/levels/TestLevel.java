package com.devdream.nightly.levels;

import com.devdream.nightly.entities.mob.Player;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.maths.Vector2D;

import java.util.Random;

public class TestLevel extends BaseLevel {

    public TestLevel(final Keyboard keyboard, final int width, final int height) {
        super(keyboard, width, height);

        player = new Player(keyboard, Sprite.player_south, playerSpawnPosition.x, playerSpawnPosition.y);
    }

    @Override
    protected void load(final String path) {
        playerSpawnPosition = new Vector2D(50, 50);

        Random rand = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width] = rand.nextInt(4);
            }
        }
    }

    @Override
    public void time() {
        //
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void render(final Renderer renderer) {
        super.render(renderer);
    }

}
