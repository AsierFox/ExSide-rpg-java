package com.devdream.nightly.entities.mob;

import java.awt.Rectangle;

import com.devdream.nightly.entities.Entity;
import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.SpriteAnimation;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.properties.GameProperties;

public class Civilian extends Entity {

	private SpriteAnimation southAnimation;


	public Civilian() {
		super(G.Sprites.civilianDefault);

		pos.x = -100;
		pos.y = 0;

		southAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 0, 3);
		
		collider = new Rectangle(pos.x, pos.y, sprite.WIDTH, (sprite.HEIGHT >> 1));
	}
	
	@Override
	public void attachToLevel(BaseLevel belongsToLevel) {
		super.attachToLevel(belongsToLevel);
		belongsToLevel.addEntity(this);
	}

	@Override
	public void update() {
		collider.x = pos.x;
		collider.y = pos.y;

		southAnimation.update();

		sprite = southAnimation.currentSprite;
	}

	@Override
	public void render(Renderer renderer) {
		renderer.renderSprite(pos.x, pos.y, sprite);

    	if (GameProperties.instance().isDebug()) {
        	renderer.renderRect(collider);
        }
	}

}
