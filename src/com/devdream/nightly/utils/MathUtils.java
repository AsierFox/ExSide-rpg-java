package com.devdream.nightly.utils;

import java.awt.Rectangle;

import com.devdream.nightly.entities.Entity;
import com.devdream.nightly.maths.Vector2D;
import com.devdream.nightly.types.Direction;

public class MathUtils {

	/**
	 * Gets the X & Y coords given an index and the number of the grid columns (starting from 1).
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
	 * @param collider
	 * @param obstacle
	 * @return
	 */
	public static Direction getRectangleDepthSideCollision(final Rectangle collider, final Rectangle obstacle) {
		int dx = (collider.x + (collider.width >> 1)) - (obstacle.x + (obstacle.width >> 1));
    	int dy = (collider.y + (collider.height >> 1)) - (obstacle.y + (obstacle.height >> 1));
    	int width = (collider.width + obstacle.width) >> 1;
    	int height = (collider.height + obstacle.height) >> 1;
    	int crossWidth = width * dy;
    	int crossHeight= height * dx;

    	// Detect if is collision (done with intersects)
    	//if (Math.abs(dx) <= width && Math.abs(dy) <= height) {
	    if (crossWidth > crossHeight) {
	    	if (crossWidth > -crossHeight) {
	    		System.out.println("Top!");
	    		return Direction.NORTH;
	    	}
	    	else {
	    		System.out.println("Right!");
	    		return Direction.EAST;
	    	}
	    }
	    else {
	    	if (crossWidth > -crossHeight) {
	    		System.out.println("Left!");
	    		return Direction.WEST;
	    	}
	    	else {
	    		System.out.println("Bottom!");
	    		return Direction.SOUTH;
	    	}
	    }
	}

	/**
	 * Get distance between two double coords.
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
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double getDistanceBetweenVectors(final Vector2D<Integer> v1, final Vector2D<Integer> v2) {
		return getDistanceBetweenCoords(v2.x, v1.x, v2.y, v1.y);
	}

	/**
	 * Check if entity against is inside entity radius.
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
