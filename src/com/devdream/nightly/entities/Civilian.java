package com.devdream.nightly.entities;

import java.awt.Rectangle;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.SpriteAnimation;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.properties.GameProperties;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;

public class Civilian extends Entity {

	private SpriteAnimation southAnimation;
	private SpriteAnimation westAnimation;
	private SpriteAnimation eastAnimation;
	private SpriteAnimation northAnimation;

	private int time;
	private int xAmount = 0;
	private int yAmount = 0;

	public Civilian() {
		super(G.Sprites.civilianDefault);

		pos.x = -100;
		pos.y = 0;

		time = 0;
		xAmount = 0;
		yAmount = 0;

		southAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 0, 3);
		westAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 4, 7);
		eastAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 8, 11);
		northAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 12, 15);

		collider = new Rectangle(pos.x, pos.y, sprite.WIDTH, sprite.HEIGHT);
	}
	
	@Override
	public void attachToLevel(BaseLevel belongsToLevel) {
		super.attachToLevel(belongsToLevel);
		belongsToLevel.addEntity(this);
	}

	@Override
	public void update() {
		time++;

		// UPS are 60 per second, so time % 60 == 0 or time & 59 is a second
		if (time % (rand.nextInt(40) + 30) == 0) {
			// Generate random -1, 0 or 1
			xAmount = rand.nextInt(3) - 1;
			yAmount = rand.nextInt(3) - 1;

			// 3 over 1 chance to move
			if (rand.nextInt(3) == 0) {
				xAmount = 0;
				yAmount = 0;
			}
		}

		// Update collider
		collider.x = pos.x;
		collider.y = pos.y;

		if ((xAmount != 0 || yAmount != 0) && !(xAmount != 0 && yAmount != 0)) {
			move(xAmount, yAmount);
		}

		updateSprite();
	}

	@Override
	public void render(Renderer renderer) {
		renderer.renderSprite(pos.x, pos.y, sprite);

    	if (GameProperties.instance().isDebug()) {
        	renderer.renderRect(collider);
        }
	}

	private void updateSprite() {
		if (direction == Direction.SOUTH) {
			if (state == EntityState.MOVING) {
				southAnimation.update();
			} else {
				southAnimation.reset();
			}
			sprite = southAnimation.currentSprite;
		}
		else if (direction == Direction.WEST) {
			if (state == EntityState.MOVING) {
				westAnimation.update();
			} else {
				westAnimation.reset();
			}
			sprite = westAnimation.currentSprite;
		}
		else if (direction == Direction.EAST) {
			if (state == EntityState.MOVING) {
				eastAnimation.update();
			} else {
				eastAnimation.reset();
			}
			sprite = eastAnimation.currentSprite;
		}
		else if (direction == Direction.NORTH) {
			if (state == EntityState.MOVING) {
				northAnimation.update();
			} else {
				northAnimation.reset();
			}
			sprite = northAnimation.currentSprite;
		}
	}

}
