package com.devdream.nightly.ui;

import com.devdream.nightly.graphics.Renderer;

public class HUD {

	private boolean visible;

	public WorldTime worldTime;
	public HealthBar healthBar;


	public HUD() {
		visible = true;

		worldTime = new WorldTime();
		healthBar = new HealthBar();
	}

	public void update() {
		worldTime.update();
	}

	public void render(final Renderer renderer) {
		//
	}

}
