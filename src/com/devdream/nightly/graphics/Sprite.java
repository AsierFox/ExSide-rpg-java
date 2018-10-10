package com.devdream.nightly.graphics;

import java.awt.*;

public class Sprite {

    public static final Sprite player = new Sprite(SpriteSheet.player, 32, 0, 0);

    public final int SIZE;
    public final int SIZE_MASK;

    private SpriteSheet SHEET;

    public int pixelsAmount;
    public int[] pixels;

    private int xLocation;
    private int yLocation;


    public Sprite(final SpriteSheet sheet, final int size, int xLocation, int yLocation) {
        SHEET = sheet;
        SIZE = size;
        SIZE_MASK = SIZE - 1;
        this.xLocation = xLocation * SIZE;
        this.yLocation = yLocation * SIZE;
        pixelsAmount = SIZE * SIZE;
        pixels = new int[pixelsAmount];

        load();
    }

    public Sprite(final Color color, final int size) {
        SIZE = size;
        SIZE_MASK = SIZE - 1;
        pixelsAmount = SIZE * SIZE;
        pixels = new int[pixelsAmount];

        loadColor(color);
    }

    private void load() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                pixels[x + y * SIZE] = SHEET.pixels[(x + xLocation) + (y + yLocation) * SHEET.SIZE];
            }
        }
    }

    private void loadColor(final Color color) {
        for (int i = 0; i < pixelsAmount; i++) {
            pixels[i] = color.getRGB();
        }
    }

}
