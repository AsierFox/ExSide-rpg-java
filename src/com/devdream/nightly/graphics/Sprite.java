package com.devdream.nightly.graphics;

import java.awt.*;

public class Sprite {

    public static final Sprite player = new Sprite(SpriteSheet.player, 30, 47, 0, 0);

    public final int WIDTH;
    public final int HEIGHT;

    private SpriteSheet SHEET;

    public int pixelsAmount;
    public int[] pixels;

    private int xLocation;
    private int yLocation;


    public Sprite(final SpriteSheet sheet, final int width, final int height, int xLocation, int yLocation) {
        SHEET = sheet;
        WIDTH = width;
        HEIGHT = height;
        this.xLocation = xLocation * width;
        this.yLocation = yLocation * height;
        pixelsAmount = width * height;
        pixels = new int[pixelsAmount];

        load();
    }

    public Sprite(final Color color, final int width, final int height) {
        WIDTH = width;
        HEIGHT = height;
        pixelsAmount = width * height;
        pixels = new int[pixelsAmount];

        loadColor(color);
    }

    private void load() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                pixels[x + y * WIDTH] = SHEET.pixels[(x + xLocation) + (y + yLocation) * SHEET.WIDTH];
            }
        }
    }

    private void loadColor(final Color color) {
        for (int i = 0; i < pixelsAmount; i++) {
            pixels[i] = color.getRGB();
        }
    }

}
