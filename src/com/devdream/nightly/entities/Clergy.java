package com.devdream.nightly.entities;

import java.awt.Rectangle;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.SpriteAnimation;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.properties.GameProperties;
import com.devdream.nightly.types.Direction;
import com.devdream.nightly.types.EntityState;

public class Clergy extends Entity {

	private SpriteAnimation southAnimation;
	private SpriteAnimation westAnimation;
	private SpriteAnimation eastAnimation;
	private SpriteAnimation northAnimation;
	
	private Player levelPlayer;


	public Clergy() {
		super(G.Sprites.civilianDefault);

		pos.x = 140;
		pos.y = -110;

		southAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 0, 3);
		westAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 4, 7);
		eastAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 8, 11);
		northAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 12, 15);

		collider = new Rectangle(pos.x, pos.y, sprite.WIDTH, sprite.HEIGHT);
	}

	@Override
	public void attachToLevel(BaseLevel belongsToLevel) {
		super.attachToLevel(belongsToLevel);
		levelPlayer =  belongsToLevel.getPlayer();

		belongsToLevel.addEntity(this);
	}

	@Override
	public void update() {

		if (pos.x < levelPlayer.pos.x) {
			xMove = 1;
		}
		else if (pos.x > levelPlayer.pos.x) {
			xMove = -1;
		}
		else if (pos.y < levelPlayer.pos.y) {
			yMove = 1;
		}
		else if (pos.y > levelPlayer.pos.y) {
			yMove = -1;
		}
		else {
			xMove = 0;
			yMove = 0;
		}

		if (xMove != 0 || yMove != 0) {
			move(xMove, yMove);
		}

		// Update collider
		collider.x = pos.x;
		collider.y = pos.y;

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
