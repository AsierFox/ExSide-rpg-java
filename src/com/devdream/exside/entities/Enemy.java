package com.devdream.exside.entities;

import java.util.ArrayList;

import com.devdream.exside.ai.astar.AStarNode;
import com.devdream.exside.graphics.G;
import com.devdream.exside.levels.BaseLevel;
import com.devdream.exside.maths.Rect;
import com.devdream.exside.maths.Vector2DInt;

public class Enemy extends Entity {
    
    private int colliderTopPadding;
    private int colliderLeftPadding;
    private int colliderRightPadding;
    
    private ArrayList<AStarNode> path;
    
    public Enemy() {
        super(80.0f, 80.0f, G.Sprites.enemyDefault);
        
        colliderTopPadding = 5;
        colliderLeftPadding = 6;
        colliderRightPadding = 12;
        
        path = null;
        
        collider = new Rect(pos.x.intValue(), pos.y.intValue(), sprite.WIDTH
                - colliderRightPadding, (sprite.HEIGHT >> 1) - colliderTopPadding);
    }
    
    @Override
    public void attachToLevel(BaseLevel belongsToLevel) {
        super.attachToLevel(belongsToLevel);
        belongsToLevel.addEntity(this);
    }
    
    @Override
    public void update() {
    	time++;
    	
        // Update collider
        collider.x = (pos.x.intValue() - (sprite.WIDTH >> 1)) + colliderLeftPadding;
        collider.y = pos.y.intValue() + colliderTopPadding;
        
        int playerX = belongsToLevel.getPlayer().pos.x.intValue();
        int playerY = belongsToLevel.getPlayer().pos.y.intValue();
        // TODO Make this dynamic, convert to tile precision by / 16
        Vector2DInt<Integer> startPos = new Vector2DInt<>(pos.x.intValue() >> 4, pos.y.intValue() >> 4);
        Vector2DInt<Integer> destinationPos = new Vector2DInt<>(playerX >> 4, playerY >> 4);
        
        // Update path once per second
        if (time % 60 == 0) {
        	path = belongsToLevel.findPath(startPos, destinationPos);
        	System.out.println(path);
        }
        
        if (null != path) {
        	if (!path.isEmpty()) {
        		Vector2DInt<Integer> nextStep = path.get(path.size() - 1).tileLocation;
        		// nextStep.x * 16 for convert from tile to pixel precision
        		// TODO Make tile size dynamic
        		if (pos.x < nextStep.x << 4) {
        			pos.x += speed;
        		}
        		if (pos.x > nextStep.x << 4) {
        			pos.x += speed;
        		}
        		if (pos.y < nextStep.y << 4) {
        			pos.y += speed;
        		}
        		if (pos.y > nextStep.y << 4) {
        			pos.y += speed;
        		}
        	}
        }
    }
    
}
