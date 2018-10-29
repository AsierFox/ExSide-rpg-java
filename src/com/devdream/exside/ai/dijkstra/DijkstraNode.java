package com.devdream.exside.ai.dijkstra;

import com.devdream.exside.maths.Rect;
import com.devdream.exside.maths.Vector2DInt;

public class DijkstraNode {

	public Vector2DInt<Integer> position;
	public double distanceFromOrigin;


	public DijkstraNode(final Vector2DInt<Integer> position, final double distanceFromOrigin) {
		this.position = position;
		this.distanceFromOrigin = distanceFromOrigin;
	}

	public Rect getArea() {
		// TODO Make this dynamic
		return new Rect(position.x, position.y, 16, 16);
	}

	public Rect getAreaInPixels() {
		// TODO Make this dynamic
		return new Rect(position.x * 16, position.y * 16, 16, 16);
	}

}
