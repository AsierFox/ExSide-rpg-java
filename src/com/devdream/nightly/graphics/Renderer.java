package com.devdream.nightly.graphics;

import com.devdream.nightly.items.Item;
import com.devdream.nightly.tiled.TiledMap;

import java.awt.*;

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
     * Renders the tiled map.
     * @param tiledMap
     */
    public void renderMap(final TiledMap tiledMap) {
    	for (int i = 0, ilen = tiledMap.tileLayers.size(); i < ilen; i++) {
    		int[] tileLocations = tiledMap.tileLayers.get(i).tilesLocation;

    		for (int y = 0; y < tiledMap.mapTilesHeight; y++) {
    			for (int x = 0; x < tiledMap.mapTilesWidth; x++) {
    				final int currentTileLocation = tileLocations[x + y * tiledMap.mapTilesWidth];

					// In readLayers() (0 converts to -1 when are not tiles)
    				if (-1 != currentTileLocation) {

						// TODO Make dynamic Tile size
						int xPosition = x << 4;
						int yPosition = y << 4;

						renderTile(xPosition, yPosition, tiledMap.sprites[currentTileLocation]);
    				}
    			}
    		}
    	}
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

        procesSpriteRender(xPosition, yPosition, sprite);
    }

    /**
     * Renders the player.
     * @param playerSprite
     * @param xPosition
     * @param yPosition
     */
    public void renderPlayer(int xPosition, int yPosition, final Sprite playerSprite) {
        xPosition -= xOffset;
        yPosition -= yOffset;

        procesSpriteRender(xPosition, yPosition, playerSprite);
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

        procesSpriteRender(xPosition, yPosition, item.sprite);
    }

    /**
     * Renders an Sprite.
     * @param xPosition
     * @param yPosition
     * @param item
     */
    public void renderSprite(int xPosition, int yPosition, final Sprite sprite) {
        xPosition -= xOffset;
        yPosition -= yOffset;

        procesSpriteRender(xPosition, yPosition, sprite);
    }

    public void renderStickySprite(int xPosition, int yPosition, final Sprite sprite) {
    	procesSpriteRender(xPosition, yPosition, sprite);
    }
    
    private void procesSpriteRender(int xPosition, int yPosition, final Sprite sprite) {
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
                // TODO Make dynamic suppress sheet color (SpriteSheet constructor)
                if (pixel != 0xff000000 && pixel != 0xffffffff) {
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
