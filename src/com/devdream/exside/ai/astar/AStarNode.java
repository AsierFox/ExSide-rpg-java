package com.devdream.exside.ai.astar;

import com.devdream.exside.maths.Vector2DInt;

public class AStarNode {
    
    public Vector2DInt<Integer> tileLocation;
    // Used to trace the path
    public AStarNode cameFrom;
    // Total cost for the current node from start of the path (gCost + fCost)
    public double fCost;
    // Partial cost from start
    public double gCost;
    // The direct distance to the final location (without taking into account
    // any obstacle)
    public double heuristicCost;
    
    public AStarNode(final Vector2DInt<Integer> tileLocation, final AStarNode cameFrom, final double gCost, final double heuristicCost) {
        this.tileLocation = tileLocation;
        this.cameFrom = cameFrom;
        this.gCost = gCost;
        this.heuristicCost = heuristicCost;
        
        fCost = gCost + heuristicCost;
    }
    
    public boolean isStartPosition() {
        return null == cameFrom;
    }
    
}
