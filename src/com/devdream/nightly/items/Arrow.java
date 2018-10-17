package com.devdream.nightly.items;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.Renderer;

public class Arrow extends Projectile {

    private double speed;
    private double damage;
    private double range;
    private double cadence;


    public Arrow(final int xOrigin, final int yOrigin, final double direction) {
        super(xOrigin, yOrigin, direction);

        sprite = G.Sprites.arrow;

        speed = 20;
        damage = 20;
        range = 100;
        cadence = 50;

        // Calculate vectors
        // cos to update x every frame to calculate next direction angle
        xUpdate = Math.cos(angle) * speed;
        // sine to update x every frame to calculate next direction angle
        yUpdate = Math.sin(angle) * speed;
    }

    @Override
    public void update() {
        pos.x += xUpdate;
        pos.y += yUpdate;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.renderProjectile(pos.x, pos.y, this);
    }

}
