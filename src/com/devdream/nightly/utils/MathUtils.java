package com.devdream.nightly.utils;

import com.devdream.nightly.maths.Vector2D;

public class MathUtils {

	/**
	 * Gets the X & Y coords given an index and the number of the grid columns (starting from 1).
	 * @param index
	 * @param totalColumns Total of columns, starting from 1
	 * @return {@link Vector2D} The coord in a 2D Vector
	 */
	public static Vector2D getCoordsByIndex(final int index, final int totalColumns) {
		final int x = index % totalColumns;
		final int y = (int) Math.floor(index / totalColumns);
		return new Vector2D(x, y);
	}

}
