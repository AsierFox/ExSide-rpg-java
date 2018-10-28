package com.devdream.nightly.ai.dijkstra;

import java.util.ArrayList;
import java.util.Iterator;

import com.devdream.nightly.maths.Rect;
import com.devdream.nightly.maths.Vector2DInt;

public class Dijkstra {
	
	private static final double DIAGONAL_DISTANCE_COST = 1.42412d;

	private final int mapTilesWidth;
	private final int mapTilesHeight;
	
	private ArrayList<DijkstraNode> transitableMapNodes;
	/** Pending nodes to calculate */
	private ArrayList<DijkstraNode> pendingNodes;
	private ArrayList<DijkstraNode> checkedNodes;


	public Dijkstra(final Vector2DInt<Integer> fromNodePosition, final int mapTilesWidth, final int mapTilesHeight, final ArrayList<Rect> mapColliders) {
		this.mapTilesWidth = mapTilesWidth;
		this.mapTilesHeight = mapTilesHeight;
		
		transitableMapNodes = new ArrayList<>();
		
		for (int y = 0; y < mapTilesHeight; y++) {
			for (int x = 0; x < mapTilesWidth; x++) {
				// TODO Dynamic this to map tile size
				final Rect nodeUbication = new Rect(x * 16, y * 16, 16, 16);

				boolean isCollider = false;
				for (Rect mapCollider : mapColliders) {
					if (nodeUbication.intersects(mapCollider)) {
						isCollider = true;
						break;
					}
				}

				if (isCollider) {
					continue;
				}

				transitableMapNodes.add(new DijkstraNode(new Vector2DInt<Integer>(x, y), Double.MAX_VALUE));
			}
		}
		
		calculateFromNodePosition(fromNodePosition);
	}

	private void calculateFromNodePosition(final Vector2DInt<Integer> fromNodePosition) {
		pendingNodes = new ArrayList<>();
		setCenterNodeInPendings(fromNodePosition);
		checkedNodes = new ArrayList<>();
		calculateGlobalHeuristic();
	}

	private void setCenterNodeInPendings(final Vector2DInt<Integer> centerNode) {
		for (DijkstraNode node : pendingNodes) {
			if (node.position.x == centerNode.x && node.position.y == centerNode.y) {
				node.distanceFromOrigin = .0d;
			}
		}
	}

	private void calculateGlobalHeuristic() {
		while (!pendingNodes.isEmpty()) {
			int nodeChanges = 0;

			Iterator<DijkstraNode> nodeIterator = pendingNodes.iterator();
	        while (nodeIterator.hasNext()) {
	        	DijkstraNode node = nodeIterator.next();

	        	if (node.distanceFromOrigin == Double.MAX_VALUE) {
	        		continue;
	        	} else {
	        		calculateNeighboursHeuristic(node);
	        		checkedNodes.add(node);
	        		nodeIterator.remove();
	        		nodeChanges++;
	        	}
	        }
	        
	        if (nodeChanges == 0) {
	        	break;
	        }
		}
	}


	private void calculateNeighboursHeuristic(DijkstraNode node) {
		// Iterate over sides of node
		for (int y = node.position.y - 1, ylen = node.position.y + 2; y < ylen; y++) {
			for (int x = node.position.x - 1, xlen = node.position.x + 2; x < xlen; x++) {
				// Omit center node
				if (x == node.position.x && y == node.position.y) {
					continue;
				}
				// Omit out of bounds map
				if (x < 0 || y < 0 || x >= mapTilesWidth || y >= mapTilesHeight) {
					continue;
				}
				
				final int nodePendingIndex = getNodeIndexInPendings(new Vector2DInt<>(x, y));
				if (nodePendingIndex < 0) {
					continue;
				}
				
				// Check if the node has already been calculated to calculate it
				DijkstraNode pendingNode = pendingNodes.get(nodePendingIndex);
				// TODO Check the -1 (?)
				if (pendingNode.distanceFromOrigin == Double.MAX_VALUE - 1) {
					double distance;
					
					// If it is diagonal
					if (x != node.position.x && y != node.position.y) {
						distance = DIAGONAL_DISTANCE_COST;
					} else {
						distance = 1;
					}
					pendingNode.distanceFromOrigin = node.distanceFromOrigin + distance;
				}
			}
		}
	}

	private int getNodeIndexInPendings(final Vector2DInt<Integer> position) {
		for (DijkstraNode node : pendingNodes) {
			if (position.x == node.position.x && position.y == node.position.y) {
				return pendingNodes.indexOf(node);
			}
		}
		return -1;
	}

}
