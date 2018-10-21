package com.devdream.nightly.items.particles;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.Sprite;
import com.devdream.nightly.types.Direction;

public class TestParticle extends Particle {

	public static final int AMOUNT = 30;

	private static final int DURATION = 40;


	public TestParticle(final double x, final double y, final Direction collisionDirection) {
		super(x, y, DURATION, getSprite(), collisionDirection);
	}

	private static Sprite getSprite() {
		// Can add different color particles with random
		return G.Sprites.arrow_particle;
	}

}
