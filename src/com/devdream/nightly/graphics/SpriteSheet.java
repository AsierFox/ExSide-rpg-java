package com.devdream.nightly.graphics;

import com.devdream.nightly.utils.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    public static final SpriteSheet road = new SpriteSheet("/road.png", 32);
    public static final SpriteSheet player = new SpriteSheet("/01_player.png", 95);

    public final int SIZE;

    private final String FILE_PATH;

    public int pixelsAmount;
    public int[] pixels;


    public SpriteSheet(final String filePath, final int size) {
        FILE_PATH = filePath;
        SIZE = size;
        pixelsAmount = SIZE * SIZE;
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
