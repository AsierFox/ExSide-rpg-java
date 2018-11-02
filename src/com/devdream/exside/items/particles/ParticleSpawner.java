package com.devdream.exside.items.particles;

import java.util.ArrayList;
import java.util.List;

import com.devdream.exside.types.Direction;
import com.devdream.exside.types.ParticleType;

public class ParticleSpawner {
    
    public static List<Particle> particles = new ArrayList<>();
    
    public static void generateParticles(final double x, final double y, final ParticleType type, final Direction collisionDirection) {
        if (type == ParticleType.TEST) {
            for (int i = 0; i < TestParticle.AMOUNT; i++) {
                particles.add(new TestParticle(x, y, collisionDirection));
            }
        }
    }
    
}
