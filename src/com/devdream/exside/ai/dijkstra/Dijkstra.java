package com.devdream.exside.ai.dijkstra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import com.devdream.exside.entities.Entity;
import com.devdream.exside.maths.Rect;
import com.devdream.exside.maths.Vector2DInt;

public class Dijkstra {
    
    private static final double DIAGONAL_DISTANCE_COST = 1.42412d;
    
    private final int mapTilesWidth;
    private final int mapTilesHeight;
    
    // If we are calculating on the constructor or before it on the game updates
    private boolean isConstructorExecuted;
    
    private ArrayList<DijkstraNode> transitableMapNodes;
    /** Pending nodes to calculate */
    private ArrayList<DijkstraNode> pendingNodes;
    private ArrayList<DijkstraNode> checkedNodes;
    
    public Dijkstra(final Vector2DInt<Integer> fromNodePosition, final int mapTilesWidth, final int mapTilesHeight, final ArrayList<Rect> mapColliders) {
        isConstructorExecuted = false;
        
        this.mapTilesWidth = mapTilesWidth;
        this.mapTilesHeight = mapTilesHeight;
        
        transitableMapNodes = new ArrayList<>();
        
        // Get transitableNodes
        getTransitableNodes(mapColliders);
        
        pendingNodes = new ArrayList<>(transitableMapNodes);
        
        calculateFromNodePosition(fromNodePosition);
        
        isConstructorExecuted = true;
    }
    
    public DijkstraNode findNextNodeToEntity(final Entity entity) {
        ArrayList<DijkstraNode> touchingNodes = new ArrayList<>();
        DijkstraNode nextNode = null;
        
        for (DijkstraNode node : checkedNodes) {
            if (entity.collider.intersects(node.getAreaInPixels())) {
                touchingNodes.add(node);
            }
        }
        
        // If the entity is only in one node, get the neighbour nodes
        if (touchingNodes.size() == 1) {
            DijkstraNode targetNode = checkedNodes.get(0);
            touchingNodes = getNeighbourNodes(targetNode);
        }
        
        // If the enemy is more than one node, move into one node
        for (int i = 0, ilen = touchingNodes.size(); i < ilen; i++) {
            if (i == 0) {
                nextNode = touchingNodes.get(0);
            } else {
                if (nextNode.distanceFromOrigin > touchingNodes.get(i).distanceFromOrigin) {
                    nextNode = touchingNodes.get(i);
                }
            }
        }
        return nextNode;
    }
    
    private void getTransitableNodes(final ArrayList<Rect> mapColliders) {
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
    }
    
    public void calculateFromNodePosition(final Vector2DInt<Integer> fromNodePosition) {
        if (isConstructorExecuted) {
            // If still not calculatedNodes
            if (checkedNodes.isEmpty()) {
                pendingNodes = getTransitableMapNodesCloned();
            } else {
                // Reset distance from origin to recalculate them (the position
                // of tiles does not change)
                pendingNodes = new ArrayList<>(checkedNodes);
                for (DijkstraNode node : pendingNodes) {
                    node.distanceFromOrigin = Double.MAX_VALUE;
                }
            }
        }
        // Clone transitable map nodes to pending nodes
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
    
    private void calculateNeighboursHeuristic(final DijkstraNode node) {
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
    
    private ArrayList<DijkstraNode> getNeighbourNodes(final DijkstraNode node) {
        ArrayList<DijkstraNode> neighbourNodes = new ArrayList<>();
        
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
                
                final int nodeCheckedIndex = getNodeIndexInChecked(new Vector2DInt<>(x, y));
                if (nodeCheckedIndex < 0) {
                    continue;
                }
                
                neighbourNodes.add(checkedNodes.get(nodeCheckedIndex));
            }
        }
        return neighbourNodes;
    }
    
    private ArrayList<DijkstraNode> getTransitableMapNodesCloned() {
        ArrayList<DijkstraNode> clonedNodes = new ArrayList<>();
        for (DijkstraNode transitableNode : transitableMapNodes) {
            // TODO Try to call .clone(), override clone with public access
            clonedNodes.add(new DijkstraNode(transitableNode.position, transitableNode.distanceFromOrigin));
        }
        return clonedNodes;
    }
    
    private int getNodeIndexInPendings(final Vector2DInt<Integer> position) {
        for (DijkstraNode node : pendingNodes) {
            if (position.x == node.position.x && position.y == node.position.y) {
                return pendingNodes.indexOf(node);
            }
        }
        return -1;
    }
    
    private int getNodeIndexInChecked(Vector2DInt<Integer> position) {
        for (DijkstraNode node : checkedNodes) {
            if (position.x == node.position.x && position.y == node.position.y) {
                return pendingNodes.indexOf(node);
            }
        }
        return -1;
    }
    
    public Optional<Vector2DInt<Integer>> getPlayerTileLocation(Vector2DInt<Integer> playerPosition) {
        // Set a Rect of a pixel in the player position
        // (if we set more than a pixel thers's a chance to be in 2 tiles at the same time)
        // TODO Need to divide here to get the player center (?) If yes, get
        // Entity parameter and call .getSpriteCenter()
        Rect entityExactLocation = new Rect(playerPosition.x / 16, playerPosition.y / 16, 1, 1);
        
        for (DijkstraNode node : transitableMapNodes) {
            if (node.getArea().intersects(entityExactLocation)) {
                return Optional.of(new Vector2DInt<>(node.position.x, node.position.y));
            }
        }
        return Optional.empty();
    }
    
}
