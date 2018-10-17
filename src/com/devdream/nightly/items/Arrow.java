package com.devdream.nightly.items;

import com.devdream.nightly.graphics.Renderer;

import java.awt.*;

// TODO Make WeaponProjectile extended
public class Arrow extends Item {

    private final int xOriginReference;
    private final int yOriginReference;
    private final double xUpdate;
    private final double yUpdate;
    private double angle;
    private double speed;
    private double damage;
    private double range;
    private double cadence;

    public Arrow(final int xOrigin, final int yOrigin, final double direction) {
        pos.x = xOrigin;
        pos.y = yOrigin;

        xOriginReference = xOrigin;
        yOriginReference = yOrigin;
        angle = direction;

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
        renderer.renderRect(new Rectangle(pos.x, pos.y, 30, 30));
    }

}
