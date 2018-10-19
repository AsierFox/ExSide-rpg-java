package com.devdream.nightly.items.particles;

import java.util.List;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.items.Item;

public abstract class Particle extends Item {

	protected List<Particle> particles;

	protected final int duration;

	// The amount of pixels to move for each axis
	protected double xAmount;
	protected double yAmount;


	/**
	 * Creates a Particle.
	 * @param x
	 * @param y
	 * @param duration
	 * @param sprite
	 */
	protected Particle(final double x, final double y, final int duration, final Sprite sprite) {
		this.pos.x = x;
		this.pos.y = y;
		this.duration = duration;
		this.sprite = sprite;

		this.xAmount = rand.nextGaussian();
		this.yAmount = rand.nextGaussian();
	}

	@Override
	public void update() {
		pos.x += xAmount;
		pos.y += yAmount;
	}

	@Override
	public void render(Renderer renderer) {
		renderer.renderSprite(pos.x.intValue(), pos.y.intValue(), sprite);
	}
	
	@Override
	public boolean canCollide() {
    	return false;
    }

}
