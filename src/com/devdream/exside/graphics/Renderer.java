package com.devdream.exside.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.devdream.exside.entities.Entity;
import com.devdream.exside.items.Item;
import com.devdream.exside.maths.Rect;
import com.devdream.exside.tiled.TiledTile;

/**
 * Class that renders the pixels to the screen.
 */
public class Renderer {
    
    private int pixelsAmount;
    public int[] pixels;
    
    private int xOffset;
    private int yOffset;
    
    private Graphics graphics;
    
    public Renderer() {
        pixelsAmount = GameWindow.WIDTH * GameWindow.HEIGHT;
        pixels = new int[pixelsAmount];
    }
    
    /**
     * Renders a map tile.
     * 
     * @param xPosition
     * @param yPosition
     * @param tile
     */
    public void renderTile(int xPosition, int yPosition, final TiledTile tile) {
        // Adjust location of the tiles by the offset, to reverse the map
        // movement position
        xPosition -= xOffset;
        yPosition -= yOffset;
        
        processSpriteRender(xPosition, yPosition, tile.sprite);
    }
    
    /**
     * Renders an Item.
     * 
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
     * 
     * @param xPosition
     * @param yPosition
     * @param entity
     */
    public void renderEnity(int xPosition, int yPosition, final Entity entity) {
        xPosition -= xOffset;
        yPosition -= yOffset;
        
        // Center the center of the sprite
        xPosition -= entity.sprite.WIDTH >> 1;
        yPosition -= entity.sprite.HEIGHT >> 1;
        
        processSpriteRender(xPosition, yPosition, entity.sprite);
    }
    
    /**
     * Renders an Sprite.
     * 
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
     * 
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
                if (xAbsolute < -sprite.WIDTH || xAbsolute >= GameWindow.WIDTH || yAbsolute < 0
                        || yAbsolute >= GameWindow.HEIGHT) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and
                // avoiding index out of bounds
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
     * 
     * @param rect
     */
    public void renderRect(Rect rect) {
        final int xPosition = rect.x - xOffset;
        final int yPosition = rect.y - yOffset;
        
        for (int y = 0; y < rect.height; y++) {
            // Absolute position will move specific tile position
            int yAbsolute = y + yPosition;
            
            for (int x = 0; x < rect.width; x++) {
                int xAbsolute = x + xPosition;
                
                // Only render that we can see on the screen
                if (xAbsolute < -rect.width || xAbsolute >= GameWindow.WIDTH || yAbsolute < 0
                        || yAbsolute >= GameWindow.HEIGHT) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and
                // avoiding index out of bounds
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
     * Renders the rectangle filled.
     * 
     * @param rect
     */
    public void renderFilledRect(Rect rect) {
        final int xPosition = rect.x - xOffset;
        final int yPosition = rect.y - yOffset;
        
        for (int y = 0; y < rect.height; y++) {
            // Absolute position will move specific tile position
            int yAbsolute = y + yPosition;
            
            for (int x = 0; x < rect.width; x++) {
                int xAbsolute = x + xPosition;
                
                // Only render that we can see on the screen
                if (xAbsolute < -rect.width || xAbsolute >= GameWindow.WIDTH || yAbsolute < 0
                        || yAbsolute >= GameWindow.HEIGHT) {
                    break;
                }
                // Fix left side with xAbsolute < -tile.sprite.SIZE, and
                // avoiding index out of bounds
                if (xAbsolute < 0) {
                    xAbsolute = 0;
                }
                
                pixels[xAbsolute + yAbsolute * GameWindow.WIDTH] = Color.RED.getRGB();
            }
        }
    }
    
    /**
     * Render a text using graphics.
     * 
     * @param fontType
     * @param text
     * @param xPosition
     * @param yPosition
     * @param color
     */
    public void renderText(final String text, int xPosition, int yPosition, final String font, final int style, final int color, final int size) {
        renderText(text, xPosition, yPosition, new Font(font, style, size), color);
    }
    
    /**
     * Render a text using graphics and a Font type <b>(requires TTF
     * format)</b>.
     * 
     * @param text
     * @param xPosition
     * @param yPosition
     * @param fontType
     * @param color
     */
    public void renderText(final String text, int xPosition, int yPosition, final Font fontType, final int color) {
        final int r = (color & 0xff0000) >> 16;
        final int g = (color & 0xff00) >> 8;
        final int b = (color & 0xff);
        final Color fontColor = new Color(r, g, b);
        graphics.setColor(fontColor);
        graphics.setFont(fontType);
        graphics.drawString(text, xPosition, yPosition);
    }
    
    /**
     * Clears all pixels of the screen.
     */
    public void clear() {
        for (int i = 0; i < pixelsAmount; i++) {
            pixels[i] = 0;
        }
    }
    
    /**
     * Set game graphics.
     * 
     * @param graphics
     */
    public void setGraphics(final Graphics graphics) {
        this.graphics = graphics;
    }
    
    /**
     * Set offset to adjust location of the tiles by the offset, to reverse the
     * map movement position.
     * 
     * @param xOffset
     * @param yOffset
     */
    public void setOffset(final int xOffset, final int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
}
