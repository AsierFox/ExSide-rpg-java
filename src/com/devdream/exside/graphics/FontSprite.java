package com.devdream.exside.graphics;

import com.devdream.exside.utils.Logger;

public class FontSprite {

	private String wordPattern;
	private char[] patternWords;

	private final Sprite[] letterFonts;

	private final int wordWidth;
	private final int wordHeight;


	/**
	 * Loads fonts from SpriteSheet.
	 * @param fromSheet
	 * @param wordPattern
	 * @param spriteSheetStartIndex
	 * @param wordWidth
	 * @param wordHeight
	 */
	public FontSprite(final SpriteSheet fromSheet, final String wordPattern, final int spriteSheetStartIndex, final int wordWidth, final int wordHeight) {
		this.wordPattern = wordPattern;
		patternWords = wordPattern.toCharArray();

		letterFonts = new Sprite[patternWords.length];

		this.wordWidth = wordWidth;
		this.wordHeight = wordHeight;

		loadWordSprites(fromSheet, spriteSheetStartIndex);
	}

	private void loadWordSprites(final SpriteSheet fromSheet, final int spriteSheetStartIndex) {
		for (int i = 0, spriteIndex = spriteSheetStartIndex, ilen = patternWords.length; i < ilen; i++, spriteIndex++) {
	    	letterFonts[i] = new Sprite(spriteIndex, fromSheet, wordWidth, wordHeight);
		}
	}

	public void renderLetter(final Renderer renderer, final int xPosition, final int yPosition, final char letter, final boolean sticky) {
		final int indexOfLetter = wordPattern.indexOf(letter);
		if (indexOfLetter < 0) {
			Logger.logError(getClass(), "The letter " + letter + " doesn't match with any letter in the word pattern " + wordPattern);
			return;
		}
		if (sticky) {
			renderer.renderStickySprite(xPosition, yPosition, letterFonts[indexOfLetter]);
		} else {
			renderer.renderSprite(xPosition, yPosition, letterFonts[indexOfLetter]);
		}
	}

	public void renderWord(final Renderer renderer, final int xPosition, final int yPosition, final String word, final boolean sticky) {
		for (int i = 0, ilen = word.length(); i < ilen; i++) {
			char letter = word.charAt(i);
			if (sticky) {
				renderer.renderStickySprite(xPosition + i * wordWidth, yPosition, letterFonts[wordPattern.indexOf(letter)]);
			} else {
				renderer.renderSprite(xPosition + i * wordWidth, yPosition, letterFonts[wordPattern.indexOf(letter)]);
			}
		}
	}

}
