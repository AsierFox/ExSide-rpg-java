package com.devdream.nightly.utils;

import java.awt.Rectangle;

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
		int dx = (collider.x + collider.width / 2) - (obstacle.x + obstacle.width / 2);
    	int dy = (collider.y + collider.height / 2) - (obstacle.y + obstacle.height / 2);
    	int width = (collider.width + obstacle.width) / 2;
    	int height = (collider.height + obstacle.height) / 2;
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
	    } else {
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
	 * Get distance between two int coords.
	 * @param xReference
	 * @param xPosition
	 * @param yReference
	 * @param yPosition
	 * @return
	 */
	public static double getDistanceBetweenCoords(final int xReference, final int xPosition, final int yReference, final int yPosition) {
		return Math.sqrt(Math.pow(xReference - xPosition, 2) + Math.pow(yReference - yPosition, 2));
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
		return Math.sqrt(Math.pow(xReference - xPosition, 2) + Math.pow(yReference - yPosition, 2));
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

}
