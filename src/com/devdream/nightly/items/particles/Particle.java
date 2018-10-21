package com.devdream.nightly.items.particles;

import java.util.List;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.items.Item;
import com.devdream.nightly.types.Direction;

public abstract class Particle extends Item {

	// Protect Particle to be removed if the duration fails
	private static final int TIME_PROTECTION = 6800;
	
	protected List<Particle> particles;

	protected final int duration;

	private double z;
	private int time;
	// The amount of pixels to move for each axis
	private double xAmount;
	private double yAmount;
	private double zAmount;


	/**
	 * Creates a Particle.
	 * @param x
	 * @param y
	 * @param duration
	 * @param sprite
	 * @param collider 
	 */
	protected Particle(final double x, final double y, final int duration, final Sprite sprite, final Direction collisionDirection) {
		pos.x = x;
		pos.y = y;
		// Add random variation between 30 & 10
		this.duration = duration + (rand.nextInt(30) - 10);
		this.sprite = sprite;

		time = 0;
		// Directional collision, if hits wall or something
		xAmount = rand.nextGaussian();
		yAmount = rand.nextGaussian();
		z = rand.nextFloat() + 3.0;
		zAmount = 0;

		controlParticleImpactResponse(collisionDirection);
	}

	@Override
	public void update() {
		time++;
		if (time > duration || time >= TIME_PROTECTION) {
			time = 0;
			dispose();
		}

		if (z < 0) {
			z = 0;
			// Reverse direction, and also slow down speed
			zAmount *= -.9;
			// Slow down x & y axis (better use multiplication over division)
			xAmount *= .1;
			yAmount *= .1;
		}

		pos.x += xAmount;
		pos.y += yAmount;
		zAmount -= .1;
		z += zAmount;
	}

	@Override
	public void render(Renderer renderer) {
		renderer.renderSprite(pos.x.intValue(), pos.y.intValue() - (int) z, sprite);
	}

	private void controlParticleImpactResponse(final Direction collisionDirection) {
		if (collisionDirection == Direction.WEST) {
			pos.x += 2;
			// Here can add or subtract (xAmount for more/less speed at impact)
			if (xAmount < 0) {
				xAmount = .1;
			}
		}
		else if (collisionDirection == Direction.EAST) {
			pos.x += 10;
			if (xAmount > 0) {
				xAmount = .1;
			}
		}
		else if (collisionDirection == Direction.NORTH) {
			pos.y += 2;
			if (yAmount < 0) {
				yAmount = .1;
			}
		}
		else if (collisionDirection == Direction.SOUTH) {
			pos.y += 10;
			if (yAmount > 0) {
				yAmount = .1;
			}
		}
	}

}
