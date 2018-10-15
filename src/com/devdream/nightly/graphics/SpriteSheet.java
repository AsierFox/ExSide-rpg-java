package com.devdream.nightly.graphics;

import com.devdream.nightly.utils.Logger;

import javax.imageio.ImageIO;
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


    public SpriteSheet(final String filePath, final int width, final int height) {
        FILE_PATH = filePath;
        WIDTH = width;
        HEIGHT = height;
        pixelsAmount = WIDTH * HEIGHT;
        pixels = new int[pixelsAmount];

        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(FILE_PATH));
            // Get image rastered to control all sprite sheet pixels by array
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        } catch (IOException e) {
            Logger.logError(getClass(), "Error reading " + FILE_PATH + " sprite sheet!", e);
        }
    }

}
