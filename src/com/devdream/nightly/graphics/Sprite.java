package com.devdream.nightly.graphics;

import java.awt.*;

public class Sprite {

    public static final Sprite player_south = new Sprite(SpriteSheet.player, 32, 48, 0, 0);
    public static final Sprite player_south_1 = new Sprite(SpriteSheet.player, 32, 48, 1, 0);
    public static final Sprite player_south_2 = new Sprite(SpriteSheet.player, 32, 48, 2, 0);
    public static final Sprite player_south_3 = new Sprite(SpriteSheet.player, 32, 48, 3, 0);

    public static final Sprite player_west = new Sprite(SpriteSheet.player, 32, 48, 0, 1);
    public static final Sprite player_west_1 = new Sprite(SpriteSheet.player, 32, 48, 1, 1);
    public static final Sprite player_west_2 = new Sprite(SpriteSheet.player, 32, 48, 2, 1);
    public static final Sprite player_west_3 = new Sprite(SpriteSheet.player, 32, 48, 3, 1);

    public static final Sprite player_east = new Sprite(SpriteSheet.player, 32, 48, 0, 2);
    public static final Sprite player_east_1 = new Sprite(SpriteSheet.player, 32, 48, 1, 2);
    public static final Sprite player_east_2 = new Sprite(SpriteSheet.player, 32, 48, 2, 2);
    public static final Sprite player_east_3 = new Sprite(SpriteSheet.player, 32, 48, 3, 2);

    public static final Sprite player_north = new Sprite(SpriteSheet.player, 32, 48, 0, 3);
    public static final Sprite player_north_1 = new Sprite(SpriteSheet.player, 32, 48, 1, 3);
    public static final Sprite player_north_2 = new Sprite(SpriteSheet.player, 32, 48, 2, 3);
    public static final Sprite player_north_3 = new Sprite(SpriteSheet.player, 32, 48, 3, 3);

    public final int WIDTH;
    public final int HEIGHT;

    private SpriteSheet fromSheet;

    public int pixelsAmount;
    public int[] pixels;

    private int xLocation;
    private int yLocation;


    public Sprite(final SpriteSheet fromSheet, final int width, final int height, int xLocation, int yLocation) {
        this.fromSheet = fromSheet;
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
                pixels[x + y * WIDTH] = fromSheet.pixels[(x + xLocation) + (y + yLocation) * fromSheet.WIDTH];
            }
        }
    }

    private void loadColor(final int color) {
        for (int i = 0; i < pixelsAmount; i++) {
            pixels[i] = color;
        }
    }

}
