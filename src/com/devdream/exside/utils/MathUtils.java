package com.devdream.exside.utils;

import com.devdream.exside.entities.Entity;
import com.devdream.exside.maths.Rect;
import com.devdream.exside.maths.Vector2D;
import com.devdream.exside.types.Direction;

public class MathUtils {
    
    /**
     * Gets the X & Y coords given an index and the number of the grid columns
     * <b>(starting from 1)</b>.
     * 
     * @param index
     * @param totalColumns Total of columns, starting from 1
     * @return {@link Vector2D} The coord in a 2D Vector
     */
    public static Vector2D<Integer> getCoordsByIndex(final int index, final int totalColumns) {
        final int x = index % totalColumns;
        final int y = (int) Math.floor(index / totalColumns);
        return new Vector2D<>(x, y);
    }
    
    /**
     * Gets the side of collision in depth between two rectangles.
     * 
     * @param collider
     * @param obstacle
     * @return
     */
    public static Direction getRectangleDepthSideCollision(final Rect collider, final Rect obstacle) {
        int dx = (collider.x + (collider.width >> 1)) - (obstacle.x + (obstacle.width >> 1));
        int dy = (collider.y + (collider.height >> 1)) - (obstacle.y + (obstacle.height >> 1));
        int width = (collider.width + obstacle.width) >> 1;
        int height = (collider.height + obstacle.height) >> 1;
        int crossWidth = width * dy;
        int crossHeight = height * dx;
        
        // Detect if is collision (done with intersects)
        if (crossWidth > crossHeight) {
            if (crossWidth > -crossHeight) {
                System.out.println("Top!");
                return Direction.NORTH;
            } else {
                System.out.println("Right!");
                return Direction.EAST;
            }
        } else {
            if (crossWidth > -crossHeight) {
                System.out.println("Left!");
                return Direction.WEST;
            } else {
                System.out.println("Bottom!");
                return Direction.SOUTH;
            }
        }
    }
    
    /**
     * Gets the color brighter, specifying the amount of brightness.
     * 
     * @param colour The pixel colour or in RGBA (0xff...)
     * @param amount The amount of brightness <b>(Between 0 <-> 100)</b>.
     * @return Returns the brighter color
     */
    public static int changeBrightness(int colour, int amount) {
        int r = (colour & 0xff0000) >> 16;
        int g = (colour & 0xff00) >> 8;
        int b = (colour & 0xff);
        
        if (amount > 0) {
            amount = 0;
        } else if (amount < -150) {
            amount = -150;
        }
        
        r += amount;
        g += amount;
        b += amount;
        
        if (r < 0) {
        	r = 0;
        } else if (r > 255) {
        	r = 255;
        }
        if (g < 0) {
            g = 0;
        } else if (g > 255) {
        	g = 255;
        }
        if (b < 0) {
        	b = 0;
        } else if (b > 255) {
        	b = 255;
        }
        
        return r << 16 | g << 8 | b;
    }

    /**
     * Merge the rgb color channels to a color, to tint it.
     *
     * @param colour
     * @param r
     * @param g
     * @param b
     * @return
     */
    public static int tint(int colour, double r, double g, double b) {
        int red = (colour & 0xff0000) >> 16;
        int green = (colour & 0xff00) >> 8;
        int blue = (colour & 0xff);
        
        red += (int) r;
        green += (int) g;
        blue += (int) b;
        
        // TODO Make a clamp
        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) {
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) {
            blue = 0;
        } else if (blue > 255) {
            blue = 255;
        }
        return red << 16 | green << 8 | blue;
    }
    
    /**
     * Get distance between two double coords.
     * 
     * @param xReference
     * @param xPosition
     * @param yReference
     * @param yPosition
     * @return
     */
    public static double getDistanceBetweenCoords(final double xReference, final double xPosition, final double yReference, final double yPosition) {
        double dx = Math.abs(xReference - xPosition);
        double dy = Math.abs(yReference - yPosition);
        return Math.sqrt((dx * dx) + (dy * dy));
    }
    
    /**
     * Get distance between two 2D vectors.
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static double getDistanceBetweenVectors(final Vector2D<Integer> v1, final Vector2D<Integer> v2) {
        return getDistanceBetweenCoords(v2.x, v1.x, v2.y, v1.y);
    }
    
    /**
     * Check if entity against is inside entity radius.
     * 
     * @param entity Entity current position
     * @param againstEntity Entity to check against using radius
     * @param radius The radius established
     * @return If the entity against is inside the radius of the entity
     */
    public static boolean isEntityInRadius(final Entity entity, final Entity againstEntity, final int radius) {
        final double distance = getDistanceBetweenCoords(entity.pos.x, againstEntity.pos.x, entity.pos.y, againstEntity.pos.y);
        return distance <= radius;
    }
    
}
