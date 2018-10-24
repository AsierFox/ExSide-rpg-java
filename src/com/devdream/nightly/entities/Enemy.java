package com.devdream.nightly.entities;

import java.awt.Rectangle;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.levels.BaseLevel;

public class Enemy extends Entity {

	private int colliderTopPadding;
    private int colliderLeftPadding;
    private int colliderRightPadding;


    public Enemy() {
		super(G.Sprites.enemyDefault);

		pos.x = 0.0f;
		pos.y = 0.0f;

		colliderTopPadding = 5;
        colliderLeftPadding = 6;
        colliderRightPadding = 12;

        collider = new Rectangle(pos.x.intValue(), pos.y.intValue(), sprite.WIDTH - colliderRightPadding, (sprite.HEIGHT >> 1) - colliderTopPadding);
	}
	
	@Override
	public void attachToLevel(BaseLevel belongsToLevel) {
		super.attachToLevel(belongsToLevel);
		belongsToLevel.addEntity(this);
	}

	@Override
	public void update() {

    	// Update collider
		collider.x = (pos.x.intValue() - (sprite.WIDTH  >> 1));
		collider.y = (pos.y.intValue() - (sprite.HEIGHT >> 1));
	}

}
