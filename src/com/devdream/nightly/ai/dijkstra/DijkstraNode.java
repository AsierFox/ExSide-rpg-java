package com.devdream.nightly.ai.dijkstra;

import com.devdream.nightly.maths.Vector2DInt;

public class DijkstraNode {

	public Vector2DInt<Integer> position;
	public double distanceFromOrigin;


	public DijkstraNode(final Vector2DInt<Integer> position, final double distanceFromOrigin) {
		this.position = position;
		this.distanceFromOrigin = distanceFromOrigin;
	}

}
