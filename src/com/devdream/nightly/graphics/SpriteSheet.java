package com.devdream.nightly.graphics;

import java.awt.Transparency;
import java.awt.image.BufferedImage;

import com.devdream.nightly.utils.FileReader;

/**
 * Creates a raster of pixels to control the image sheet and extract sprites from it.
 */
public class SpriteSheet {

	public static final int NO_SUPRESS_COLOR = 0;


	private final String FILE_PATH;

    public final int WIDTH;
    public final int HEIGHT;

    public final int supressedColor;

    public int pixelsAmount;
    public int[] pixels;

    private int transparencyType;


    /**
     * Create an default SpriteSheet with OPAQUE transparency and any suppress color.
     * @param filePath
     * @param width
     * @param height
     */
    public SpriteSheet(final String filePath, final int width, final int height) {
        this(filePath, width, height, Transparency.OPAQUE, NO_SUPRESS_COLOR);
    }

    /**
     * Loads an SpriteSheet with custom parameters.
     * @param filePath
     * @param width
     * @param height
     * @param transparencyType
     * @param supressedColor
     */
    public SpriteSheet(final String filePath, final int width, final int height, final int transparencyType, final int supressedColor) {
        FILE_PATH = filePath;
        WIDTH = width;
        HEIGHT = height;

        pixelsAmount = WIDTH * HEIGHT;
        pixels = new int[pixelsAmount];

        this.transparencyType = transparencyType;
        this.supressedColor = supressedColor;

        load();
    }

    /**
     * Loads the SpriteSheet image.
     */
    private void load() {
        BufferedImage optimizedImage = FileReader.loadImageOptimized(FILE_PATH).get();
        // Raster image to pixels to control all sprite sheet pixels by array
        optimizedImage.getRGB(0, 0, optimizedImage.getWidth(null), optimizedImage.getHeight(null), pixels, 0, optimizedImage.getWidth(null));
    }

}
