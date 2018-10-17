package com.devdream.nightly.items;

public abstract class Projectile extends Item {

    protected final int xOriginReference;
    protected final int yOriginReference;
    protected double xUpdate;
    protected double yUpdate;

    protected final double angle;


    public Projectile(final int xOrigin, final int yOrigin, final double direction) {
        pos.x = xOrigin;
        pos.y = yOrigin;
        xOriginReference = xOrigin;
        yOriginReference = yOrigin;
        angle = direction;
    }

}
