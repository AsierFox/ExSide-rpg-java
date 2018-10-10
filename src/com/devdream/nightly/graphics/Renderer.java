package com.devdream.nightly.graphics;

/**
 * Class that renders the pixels to the screen.
 */
public class Renderer {

    private static int TILE_SIZE = 8;
    private static int TILE_SIZE_MASK = TILE_SIZE - 1;

    public int width;
    public int height;

    public int[] pixels;
    public int[] tiles;

    private int pixelsAmount;
    private int xOffset;
    private int yOffset;


    public Renderer(final int width, final int height) {
        this.width = width;
        this.height = height;
        pixelsAmount = width * height;
        pixels = new int[pixelsAmount];
        tiles = new int[TILE_SIZE * TILE_SIZE];
    }

    @Deprecated
    public void render(final int xOffset, final int yOffset) {
        for (int y = 0; y < height; y++) {
            int yy = y + yOffset;
            if (yy < 0 || yy >= height) {
                continue;
            }
            for (int x = 0; x < width; x++) {
                int xx = x + xOffset;
                if (xx < 0 || xx >= width) {
                    continue;
                }

                // If (x >> 4) becomes greater than 64 (0 to 63), value returns back to 0 (That's why map repeats)

                // Move pixel by pixel
                // (x >> 4) == (x / 4^2) == (x / 16)
                //int tileIndex = ((xx >> 4) & TILE_SIZE_MASK) + ((yy >> 4) & TILE_SIZE_MASK) * TILE_SIZE;

                // Move by tile
                //int tileIndex = ((xx >> 4) + xOffset & TILE_SIZE_MASK) + ((yy >> 4) + yOffset & TILE_SIZE_MASK) * TILE_SIZE;

                //pixels[x + y * width] = tiles[tileIndex];

                // Render Sprite from Sprite Sheet
                //pixels[xx + yy * width] = GrassTile.tile.sprite.pixels[(x & GrassTile.tile.sprite.SIZE_MASK) +
                //        (y & GrassTile.tile.sprite.SIZE_MASK) * GrassTile.tile.sprite.SIZE];
            }
        }
    }

    public void renderTile(int xPosition, int yPosition, final Tile tile) {
        // Adjust location of the tiles by the offset, to reverse the map movement position
        xPosition -= xOffset;
        yPosition -= yOffset;
        for (int y = 0; y < tile.sprite.HEIGHT; y++) {
            // Absolute position will move specific tile position
            int yAbsolute = y + yPosition;
            for (int x = 0; x < tile.sprite.WIDTH; x++) {
                int xAbsolute = x + xPosition;

                // Only render the tiles that we can see on the screen
                if (xAbsolute < -tile.sprite.WIDTH || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and avoiding index out of bounds
                if (xAbsolute < 0) {
                    xAbsolute = 0;
                }

                pixels[xAbsolute + yAbsolute * width] = tile.sprite.pixels[x + y * tile.sprite.WIDTH];
            }
        }
    }

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

                pixels[xAbsolute + yAbsolute * width] = playerSprite.pixels[x + y * playerSprite.WIDTH];
            }
        }
    }

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
