package com.devdream.nightly.entities;

import java.awt.Rectangle;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.properties.GameProperties;

public class Enemy extends Entity {

	private int colliderTopPadding;
    private int colliderLeftPadding;
    private int colliderRightPadding;


    public Enemy() {
		super(G.Sprites.enemyDefault);

		pos.x = 0;
		pos.y = 0;

		colliderTopPadding = 5;
        colliderLeftPadding = 6;
        colliderRightPadding = 12;

        collider = new Rectangle(pos.x, pos.y, sprite.WIDTH - colliderRightPadding, (sprite.HEIGHT >> 1) - colliderTopPadding);
	}
	
	@Override
	public void attachToLevel(BaseLevel belongsToLevel) {
		super.attachToLevel(belongsToLevel);
		belongsToLevel.addEntity(this);
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Renderer renderer) {
		renderer.renderSprite(pos.x, pos.y, sprite);

		if (GameProperties.instance().isDebug()) {
        	renderer.renderRect(collider);
        }
	}

}
