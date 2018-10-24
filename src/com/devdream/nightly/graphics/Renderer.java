package com.devdream.nightly.graphics;

import java.awt.Color;
import java.awt.Rectangle;

import com.devdream.nightly.entities.Entity;
import com.devdream.nightly.items.Item;
import com.devdream.nightly.tiled.TiledTile;

/**
 * Class that renders the pixels to the screen.
 */
public class Renderer {

    private int pixelsAmount;
    public int[] pixels;

    private int xOffset;
    private int yOffset;


    public Renderer() {
        pixelsAmount = GameWindow.WIDTH * GameWindow.HEIGHT;
        pixels = new int[pixelsAmount];
    }

    /**
     * Renders a map tile.
     * @param xPosition
     * @param yPosition
     * @param tile
     */
	public void renderTile(int xPosition, int yPosition, final TiledTile tile) {
        // Adjust location of the tiles by the offset, to reverse the map movement position
        xPosition -= xOffset;
        yPosition -= yOffset;

    	processSpriteRender(xPosition, yPosition, tile.sprite);
    }

    /**
     * Renders an Item.
     * @param xPosition
     * @param yPosition
     * @param item
     */
    public void renderItem(int xPosition, int yPosition, final Item item) {
        xPosition -= xOffset;
        yPosition -= yOffset;

        processSpriteRender(xPosition, yPosition, item.sprite);
    }

    /**
     * Renders an entity.
     * @param xPosition
     * @param yPosition
     * @param entity
     */
    public void renderEnity(int xPosition, int yPosition, final Entity entity) {
        xPosition -= xOffset;
        yPosition -= yOffset;

        // Center the center of the sprite
        xPosition -= entity.sprite.WIDTH  >> 1;
        yPosition -= entity.sprite.HEIGHT >> 1;

        processSpriteRender(xPosition, yPosition, entity.sprite);
    }

    /**
     * Renders an Sprite.
     * @param xPosition
     * @param yPosition
     * @param sprite
     */
    public void renderSprite(int xPosition, int yPosition, final Sprite sprite) {
        xPosition -= xOffset;
        yPosition -= yOffset;

        processSpriteRender(xPosition, yPosition, sprite);
    }

    public void renderStickySprite(int xPosition, int yPosition, final Sprite sprite) {
    	processSpriteRender(xPosition, yPosition, sprite);
    }

    /**
     * Process to render an sprite image to the screen.
     * @param xPosition
     * @param yPosition
     * @param sprite
     */
    private void processSpriteRender(int xPosition, int yPosition, final Sprite sprite) {
    	for (int y = 0; y < sprite.HEIGHT; y++) {
            // Absolute position will move specific tile position
            int yAbsolute = y + yPosition;

            for (int x = 0; x < sprite.WIDTH; x++) {
                int xAbsolute = x + xPosition;

                // Only render the tiles that we can see on the screen
                if (xAbsolute < -sprite.WIDTH || xAbsolute >= GameWindow.WIDTH || yAbsolute < 0 || yAbsolute >= GameWindow.HEIGHT) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and avoiding index out of bounds
                if (xAbsolute < 0) {
                    xAbsolute = 0;
                }

                int pixel = sprite.pixels[x + y * sprite.WIDTH];
                if (pixel != sprite.getSupressedColor()) {
                    pixels[xAbsolute + yAbsolute * GameWindow.WIDTH] = pixel;
                }
            }
        }
    }

    /**
     * Renders a rectangle.
     * @param rect
     */
	public void renderRect(Rectangle rect) {
        final int xPosition = rect.x - xOffset;
        final int yPosition = rect.y - yOffset;

        for (int y = 0; y < rect.height; y++) {
            // Absolute position will move specific tile position
            int yAbsolute = y + yPosition;

            for (int x = 0; x < rect.width; x++) {
                int xAbsolute = x + xPosition;

                // Only render that we can see on the screen
                if (xAbsolute < -rect.width || xAbsolute >= GameWindow.WIDTH || yAbsolute < 0 || yAbsolute >= GameWindow.HEIGHT) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and avoiding index out of bounds
                if (xAbsolute < 0) {
                    xAbsolute = 0;
                }

                // Only paint borders
                if (x == 0 || y == 0 || x == rect.width - 1 || y == rect.height - 1) {
                	pixels[xAbsolute + yAbsolute * GameWindow.WIDTH] = Color.RED.getRGB();
                }
            }
        }
	}

	/**
	 * Set offset to adjust location of the tiles by the offset, to reverse the map movement position.
	 * @param xOffset
	 * @param yOffset
	 */
    public void setOffset(final int xOffset, final int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * Clears all pixels of the screen.
     */
	public void clear() {
        for (int i = 0; i < pixelsAmount; i++) {
            pixels[i] = 0;
        }
    }

}
