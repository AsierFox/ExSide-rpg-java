package com.devdream.nightly.items.particles;

import java.util.ArrayList;
import java.util.List;

import com.devdream.nightly.types.ParticleType;

public class ParticleSpawner {

	public static List<Particle> particles = new ArrayList<>();

	public static void generateParticles(final double x, final double y, final ParticleType type) {
		if (type == ParticleType.TEST) {
			for (int i = 0; i < TestParticle.AMOUNT; i++) {
				particles.add(new TestParticle(x, y));
			}
		}
	}
	
}
