package com.devdream.nightly.tiled;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.graphics.Sprite;

public class TiledTile {

	public final Sprite sprite;

	private boolean isRemoved;


	public TiledTile(final Sprite sprite) {
		this.sprite = sprite;
		isRemoved = false;
	}

	public void render(Renderer renderer, int xPosition, int yPosition) {
		renderer.renderTile(xPosition, yPosition, this);
	}

	public boolean isRemoved() {
		return isRemoved;
	}

}
