package com.devdream.nightly.items.particles;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.Sprite;

public class TestParticle extends Particle {

	public static final int AMOUNT = 10;

	private static final int DURATION = 10;


	public TestParticle(final double x, final double y) {
		super(x, y, DURATION, getSprite());
	}

	private static Sprite getSprite() {
		// Can add different color particles with random
		return G.Sprites.arrow_particle;
	}

}
