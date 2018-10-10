package com.devdream.nightly.levels;

import com.devdream.nightly.graphics.Renderer;

import java.util.Random;

public class TestLevel extends BaseLevel {

    public TestLevel(final int width, final int height) {
        super(width, height);
    }

    public TestLevel(final String path) {
        super(path);
    }

    @Override
    public void update() {
        //
    }

    @Override
    public void render(final Renderer renderer, final int xScroll, final int yScroll) {
        super.render(renderer, xScroll, yScroll);
    }

    @Override
    public void time() {
        //
    }

    @Override
    protected void load() {
        Random rand = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width] = rand.nextInt(4);
            }
        }
    }

}
