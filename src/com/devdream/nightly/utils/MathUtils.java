package com.devdream.nightly.utils;

import com.devdream.nightly.maths.Vector2D;

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
