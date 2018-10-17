package com.devdream.nightly.graphics;

import com.devdream.nightly.items.Projectile;

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

    /**
     * Renders a map tile.
     * @param xPosition
     * @param yPosition
     * @param sprite
     */
	public void renderTile(int xPosition, int yPosition, final Sprite sprite) {
        // Adjust location of the tiles by the offset, to reverse the map movement position
        xPosition -= xOffset;
        yPosition -= yOffset;

        for (int y = 0; y < sprite.HEIGHT; y++) {
            // Absolute position will move specific tile position
            int yAbsolute = y + yPosition;
            for (int x = 0; x < sprite.WIDTH; x++) {
                int xAbsolute = x + xPosition;

                // Only render the tiles that we can see on the screen
                if (xAbsolute < -sprite.WIDTH || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and avoiding index out of bounds
                if (xAbsolute < 0) {
                    xAbsolute = 0;
                }

                pixels[xAbsolute + yAbsolute * width] = sprite.pixels[x + y * sprite.WIDTH];
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

                // TODO Make dynamic suppress sheet color (SpriteSheet constructor)
                if (pixel != 0xff000000) {
                    pixels[xAbsolute + yAbsolute * width] = pixel;
                    // Flip sprite
                }
            }
        }
    }

    public void renderProjectile(int xPosition, int yPosition, final Projectile projectile) {
        // Adjust location of the tiles by the offset, to reverse the map movement position
        xPosition -= xOffset;
        yPosition -= yOffset;

        for (int y = 0; y < projectile.sprite.HEIGHT; y++) {
            // Absolute position will move specific tile position
            int yAbsolute = y + yPosition;
            for (int x = 0; x < projectile.sprite.WIDTH; x++) {
                int xAbsolute = x + xPosition;

                // Only render the tiles that we can see on the screen
                if (xAbsolute < -projectile.sprite.WIDTH || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and avoiding index out of bounds
                if (xAbsolute < 0) {
                    xAbsolute = 0;
                }

                int pixel = projectile.sprite.pixels[x + y * projectile.sprite.WIDTH];
                if (pixel != 0xff000000) {
                    pixels[xAbsolute + yAbsolute * width] = pixel;
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
