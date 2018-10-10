package com.devdream.nightly.graphics;

import java.awt.*;

public class Sprite {

    public static final Sprite player_south = new Sprite(SpriteSheet.player, 32, 48, 0, 0);
    public static final Sprite player_west = new Sprite(SpriteSheet.player, 32, 48, 0, 1);
    public static final Sprite player_east = new Sprite(SpriteSheet.player, 32, 48, 0, 2);
    public static final Sprite player_north = new Sprite(SpriteSheet.player, 32, 48, 0, 3);

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

        loadColor(color.getRGB());
    }

    public Sprite(final int color, final int width, final int height) {
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

    private void loadColor(final int color) {
        for (int i = 0; i < pixelsAmount; i++) {
            pixels[i] = color;
        }
    }

}
