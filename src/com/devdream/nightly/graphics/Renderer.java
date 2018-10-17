package com.devdream.nightly.graphics;

import java.awt.*;

/**
 * Class that renders the pixels to the screen.
 */
public class Renderer {

    public int width;
    public int height;

    private int pixelsAmount;
    public int[] pixels;

    private int xOffset;
    private int yOffset;


    public Renderer(final int width, final int height) {
        this.width = width;
        this.height = height;
        pixelsAmount = width * height;
        pixels = new int[pixelsAmount];
    }

	public void renderTile(int xPosition, int yPosition, final Sprite tileSprite) {
        // Adjust location of the tiles by the offset, to reverse the map movement position
        xPosition -= xOffset;
        yPosition -= yOffset;

        for (int y = 0; y < tileSprite.HEIGHT; y++) {
            // Absolute position will move specific tile position
            int yAbsolute = y + yPosition;
            for (int x = 0; x < tileSprite.WIDTH; x++) {
                int xAbsolute = x + xPosition;

                // Only render the tiles that we can see on the screen
                if (xAbsolute < -tileSprite.WIDTH || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and avoiding index out of bounds
                if (xAbsolute < 0) {
                    xAbsolute = 0;
                }

                pixels[xAbsolute + yAbsolute * width] = tileSprite.pixels[x + y * tileSprite.WIDTH];
            }
        }
    }

    /**
     * Renders the player.
     * @param playerSprite
     * @param xPosition
     * @param yPosition
     */
    public void renderPlayer(final Sprite playerSprite, int xPosition, int yPosition) {
        // Adjust location of the tiles by the offset, to reverse the map movement position
        xPosition -= xOffset;
        yPosition -= yOffset;
        for (int y = 0; y < playerSprite.HEIGHT; y++) {
            // Absolute position will move specific tile position
            int yAbsolute = y + yPosition;

            for (int x = 0; x < playerSprite.WIDTH; x++) {
                int xAbsolute = x + xPosition;

                // Only render that we can see on the screen
                if (xAbsolute < -playerSprite.WIDTH || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and avoiding index out of bounds
                if (xAbsolute < 0) {
                    xAbsolute = 0;
                }

                // Suppress sprite sheet background color
                int pixel = playerSprite.pixels[x + y * playerSprite.WIDTH];

                // TODO Make dynamic suppress sheet color
                if (pixel != 0xff000000) {
                    pixels[xAbsolute + yAbsolute * width] = pixel;
                    // Flip sprite
                }
            }
        }
    }

    /**
     * Renders a rectangle.
     * @param rect
     */
	public void renderRect(Rectangle rect) {
		// Adjust location of the tiles by the offset, to reverse the map movement position
        final int xPosition = rect.x - xOffset;
        final int yPosition = rect.y - yOffset;

        for (int y = 0; y < rect.height; y++) {
            // Absolute position will move specific tile position
            int yAbsolute = y + yPosition;

            for (int x = 0; x < rect.width; x++) {
                int xAbsolute = x + xPosition;

                // Only render that we can see on the screen
                if (xAbsolute < -rect.width || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and avoiding index out of bounds
                if (xAbsolute < 0) {
                    xAbsolute = 0;
                }

                // Only paint borders
                if (x == 0 || y == 0 || x == rect.width - 1 || y == rect.height - 1) {
                	pixels[xAbsolute + yAbsolute * width] = Color.RED.getRGB();
                }
            }
        }
	}

    /**
     * Clears all pixels of the screen.
     */
	public void clear() {
        for (int i = 0; i < pixelsAmount; i++) {
            pixels[i] = 0;
        }
    }

    public void setOffset(final int xOffset, final int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

}
