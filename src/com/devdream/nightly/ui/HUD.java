package com.devdream.nightly.ui;

import com.devdream.nightly.graphics.G;
import com.devdream.nightly.graphics.GameWindow;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;

public class HUD {

	public WorldTime worldTime;
	public HealthBar healthBar;
	
	private Sprite hudBackground;
	
	private boolean visible;


	public HUD() {
		visible = true;

		worldTime = new WorldTime();
		healthBar = new HealthBar();
		
		hudBackground = G.Sprites.hudBackground;
	}

	public void update() {
		worldTime.update();
	}

	public void render(final Renderer renderer) {
		renderer.renderStickySprite(0, GameWindow.HEIGHT - hudBackground.HEIGHT, hudBackground);
	}

}
