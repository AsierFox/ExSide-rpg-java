package com.devdream.exside.graphics;

import java.awt.Rectangle;

import com.devdream.exside.maths.Vector2D;
import com.devdream.exside.utils.MathUtils;

/**
 * Sprite class that manages sprite pixels from the sprite sheet raster.
 */
public class Sprite {

    private SpriteSheet fromSheet;

    public final int WIDTH;
    public final int HEIGHT;

    private int xLocation;
    private int yLocation;
    
    public int pixelsAmount;
    public int[] pixels;


    /**
     * Get sprite of specific with and height, from specific coord location.
     * @param fromSheet
     * @param width
     * @param height
     * @param xLocation
     * @param yLocation
     */
    public Sprite(final SpriteSheet fromSheet, final int width, final int height, final int xLocation, final int yLocation) {
        this.fromSheet = fromSheet;
        WIDTH = width;
        HEIGHT = height;
        
        this.xLocation = xLocation * width;
        this.yLocation = yLocation * height;
        
        pixelsAmount = width * height;
        pixels = new int[pixelsAmount];

        loadByLocation();
    }

    /**
     * Get sprite of specific size, from specific coord location.
     * @param fromSheet
     * @param size
     * @param xLocation
     * @param yLocation
     */
    public Sprite(final SpriteSheet fromSheet, final int size, final int xLocation, final int yLocation) {
        this(fromSheet, size, size, xLocation, yLocation);
    }


    /**
     * Get sprite using the grid index <b>(starting from 0)</b> of a specific width and height.
     * @param index
     * @param fromSheet
     * @param width
     * @param height
     */
    public Sprite(final int index, final SpriteSheet fromSheet, final int width, final int height) {
        this.fromSheet = fromSheet;
        WIDTH = width;
        HEIGHT = height;
        
    	final int spriteSheetTotalColumns = fromSheet.WIDTH / WIDTH;
    	final Vector2D<Integer> coords = MathUtils.getCoordsByIndex(index, spriteSheetTotalColumns);
        this.xLocation = coords.x * width;
        this.yLocation = coords.y * height;

        pixelsAmount = width * height;
        pixels = new int[pixelsAmount];

        loadByLocation();
    }

    /**
     * Get sprite using the grid index <b>(starting from 0)</b> of a specific size.
     * @param index
     * @param fromSheet
     * @param size
     */
    public Sprite(final int index, final SpriteSheet fromSheet, final int size) {
        this(index, fromSheet, size, size);
    }

    /**
     * Get an sprite of a specific width and height and color <b>(using 0x<i>HEX_CODE</i>)</b>.
     * @param color
     * @param width
     * @param height
     */
    public Sprite(final int color, final int width, final int height) {
        WIDTH = width;
        HEIGHT = height;
        
        pixelsAmount = width * height;
        pixels = new int[pixelsAmount];

        loadColor(color);
    }

    /**
     * Get an sprite of a specific size and color <b>(using 0x<i>HEX_CODE</i>)</b>.
     * @param color
     * @param size
     */
    public Sprite(final int color, final int size) {
        this(color, size, size);
    }

    /**
     * Load an Sprite from specific location in SpriteSheet.
     */
    private void loadByLocation() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                pixels[x + y * WIDTH] = fromSheet.pixels[(x + xLocation) + (y + yLocation) * fromSheet.WIDTH];
            }
        }
    }

    /**
     * Load an Sprite filled with an specific color.
     */
    private void loadColor(final int color) {
        for (int i = 0; i < pixelsAmount; i++) {
            pixels[i] = color;
        }
    }

    public int getSupressedColor() {
    	// If is an color Sprite, it doesn't have fromSheet attr
    	if (fromSheet == null) {
    		return SpriteSheet.NO_SUPRESS_COLOR;
    	}
		return fromSheet.supressedColor;
    }

    public Rectangle getLimits() {
        return new Rectangle(xLocation, yLocation, WIDTH, HEIGHT);
    }

}
