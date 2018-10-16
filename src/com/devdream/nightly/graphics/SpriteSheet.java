package com.devdream.nightly.graphics;

import com.devdream.nightly.utils.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Creates a raster of pixels to control the image sheet and extract sprites from it.
 */
public class SpriteSheet {

    private final String FILE_PATH;

    public final int WIDTH;
    public final int HEIGHT;

    public int pixelsAmount;
    public int[] pixels;

    private int transparencyType;

    public SpriteSheet(final String filePath, final int width, final int height) {
        this(filePath, width, height, Transparency.OPAQUE);
    }

    public SpriteSheet(final String filePath, final int width, final int height, final int transparencyType) {
        FILE_PATH = filePath;
        WIDTH = width;
        HEIGHT = height;
        pixelsAmount = WIDTH * HEIGHT;
        pixels = new int[pixelsAmount];

        this.transparencyType = transparencyType;

        load();
    }

    private void load() {
        try {
            Image image = ImageIO.read(SpriteSheet.class.getResource(FILE_PATH));

            // TODO Check if this really improves performance
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
            Logger.logError(getClass(), "Error reading " + FILE_PATH + " sprite sheet!", e);
        }
    }

}
