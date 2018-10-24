package com.devdream.nightly.graphics;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.devdream.nightly.utils.Logger;

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
        try {
        	System.out.println("MEW");
            BufferedImage image = ImageIO.read(new File("res/" + FILE_PATH));

            // Improves performance of the image by checking if we can accelerate by hardware,
            // using graphics card ram instead of the default ram.
            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            BufferedImage optimizedImage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparencyType);

            // Draw into image the optimized image
            Graphics g = optimizedImage.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            // Get image rastered to control all sprite sheet pixels by array
            optimizedImage.getRGB(0, 0, image.getWidth(null), image.getHeight(null), pixels, 0, image.getWidth(null));
        } catch (IOException e) {
            Logger.logError(getClass(), "Error reading " + FILE_PATH + " sprite sheet.", e);
        }
    }

}
