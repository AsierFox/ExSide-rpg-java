package com.devdream.exside.items.projectiles;

import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.graphics.Sprite;
import com.devdream.exside.items.Item;
import com.devdream.exside.properties.GameProperties;
import com.devdream.exside.utils.MathUtils;

/**
 * Item that can be throwable.
 */
public abstract class Projectile extends Item {
    
    protected final int xOriginReference;
    protected final int yOriginReference;
    protected double xUpdate;
    protected double yUpdate;
    
    protected final double angle;
    protected double speed;
    protected double damage;
    protected final double range;
    protected final double cadence;
    
    public Projectile(int xOrigin, int yOrigin, double direction, double speed, double damage, double range, double cadence, Sprite sprite) {
        super(sprite);
        pos.x = (double) xOrigin;
        pos.y = (double) yOrigin;
        
        xOriginReference = xOrigin;
        yOriginReference = yOrigin;
        angle = direction;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        this.cadence = cadence;
    }
    
    @Override
    public void update() {
        if (getDistance() >= range) {
            dispose();
        }
        
        // Update collider
        collider.x = pos.x.intValue();
        collider.y = pos.y.intValue();
    }
    
    @Override
    public void render(final Renderer renderer) {
        if (GameProperties.instance().isDebug()) {
            renderer.renderRect(collider);
        }
    }
    
    private double getDistance() {
        return MathUtils.getDistanceBetweenCoords(xOriginReference, pos.x, yOriginReference, pos.y);
    }
    
}
