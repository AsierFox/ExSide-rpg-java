package com.devdream.exside.io;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GameFocus implements FocusListener {

	public boolean isFocused;


	public GameFocus() {
		isFocused = false;
	}

	@Override
	public void focusGained(FocusEvent e) {
		isFocused = true;
	}

	@Override
	public void focusLost(FocusEvent e) {
		isFocused = false;
	}

}
