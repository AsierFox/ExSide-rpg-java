package com.devdream.nightly.items;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.Renderer;

public class Arrow extends Projectile {

	private static final int SPEED = 2;
	private static final int DAMAGE = 20;
	private static final int RANGE = 180;
	public static final int CADENCE = 16;


	public Arrow(final int xOrigin, final int yOrigin, final double direction) {
        super(xOrigin, yOrigin, direction, SPEED, DAMAGE, RANGE, CADENCE, G.Sprites.arrow);

        // Calculate vectors
        // cos to update x every frame to calculate next direction angle
        xUpdate = Math.cos(angle) * SPEED;
        // sine to update x every frame to calculate next direction angle
        yUpdate = Math.sin(angle) * SPEED;
    }

    @Override
    public void update() {
    	super.update();

        pos.x += xUpdate;
        pos.y += yUpdate;
    }

    @Override
    public void render(Renderer renderer) {
	    super.render(renderer);

        renderer.renderItem(pos.x.intValue(), pos.y.intValue(), this);
    }

}
