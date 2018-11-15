package com.devdream.exside.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Point;

import com.devdream.exside.interfaces.Renderable;

public class LightEffect implements Renderable {

	public Point pos;
	public int radius;
	public float[] luminosity;
	public Color[] colors;
	public AlphaComposite alphaEffect;
	private boolean isRemoved;
	
	public LightEffect(Point pos, int radius, float[] luminosity, Color[] colors, AlphaComposite alphaEffect) {
		this.pos = pos;
		this.radius = radius;
		this.luminosity = luminosity;
		this.colors = colors;
		this.alphaEffect = alphaEffect;
	}
	
	@Override
	public void update() {
		//
	}
	
	@Override
	public void render(final Renderer renderer) {
		renderer.renderLight(this);
	}
	
	@Override
	public void dispose() {
		isRemoved = true;
	}
	
	@Override
	public boolean isRemoved() {
		return isRemoved;
	}
	
}
