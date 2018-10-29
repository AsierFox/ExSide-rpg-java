package com.devdream.exside.graphics;

/**
 * Like the Sprite class, but can have multiple sprites to handle for animations.
 */
public class SpriteAnimation {

    private int time;
    private int frameRate;
	private int currentFrame;
	private int animationLength;

	public Sprite currentSprite;
	private Sprite[] sprites;


	/**
	 * Loads an Sprite animation.
	 * @param fromSheet The Sprite Sheet
	 * @param width Width of the sprites
	 * @param height Height of the sprites
	 * @param indexStart SpriteSheet index of the frame where animation starts <b>index from 0</b>.
	 * @param indexFinish SpriteSheet index of the frame where animation ends.
	 */
	public SpriteAnimation(final SpriteSheet fromSheet, final int width, final int height, final int indexStart, final int indexFinish) {
		time = 0;
		frameRate = 10;
		currentFrame = 0;

		animationLength = indexFinish - indexStart;
		sprites = new Sprite[animationLength + 1];

		for (int i = 0, frameCount = indexStart; frameCount <= indexFinish; i++, frameCount++) {
			sprites[i] = new Sprite(frameCount, fromSheet, width, height);
		}

		currentSprite = sprites[0];
	}

	public void update() {
		time++;
		if (time % frameRate == 0) {
			if (currentFrame < animationLength) {
				currentFrame++;
			}
			else {
				currentFrame = 0;
			}
		}

		currentSprite = sprites[currentFrame];
	}

	/**
	 * Resets the time animation and goes back to the default frame.
	 */
	public void reset() {
		time = 0;
		currentSprite = sprites[0];
	}

	public void setFrameRate(final int frameRate) {
		this.frameRate = frameRate;
	}

}
