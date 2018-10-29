package com.devdream.nightly.entities;

import com.devdream.nightly.ai.dijkstra.DijkstraNode;
import com.devdream.nightly.graphics.G;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.maths.Rect;

public class Enemy extends Entity {

	private int colliderTopPadding;
    private int colliderLeftPadding;
    private int colliderRightPadding;
    
    public DijkstraNode nextNode;


    public Enemy() {
		super(80.0f, 80.0f, G.Sprites.enemyDefault);

		colliderTopPadding = 5;
        colliderLeftPadding = 6;
        colliderRightPadding = 12;

        collider = new Rect(pos.x.intValue(), pos.y.intValue(), sprite.WIDTH - colliderRightPadding, (sprite.HEIGHT >> 1) - colliderTopPadding);
	}
	
	@Override
	public void attachToLevel(BaseLevel belongsToLevel) {
		super.attachToLevel(belongsToLevel);
		belongsToLevel.addEntity(this);
	}

	@Override
	public void update() {
    	// Update collider
        collider.x = (pos.x.intValue() - (sprite.WIDTH >> 1)) + colliderLeftPadding;
        collider.y = pos.y.intValue() + colliderTopPadding;
        
        if (null != nextNode) {
        	// TODO Make this dynamic
        	int xNextNode = nextNode.position.x * 16;
        	int yNextNode = nextNode.position.y * 16;

        	if (pos.x < xNextNode) {
        		pos.x += 1;
        	}
        	if (pos.x > xNextNode) {
        		pos.x -= 1;
        	}
        	if (pos.y < yNextNode) {
        		pos.y += 1;
        	}
        	if (pos.y > yNextNode) {
        		pos.y -= 1;
        	}
        }
	}

}
