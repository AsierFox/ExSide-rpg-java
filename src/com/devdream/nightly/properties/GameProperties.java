package com.devdream.nightly.properties;

import com.devdream.nightly.utils.PropertiesReader;

public class GameProperties {

	private static final String FILENAME = "game";

	private static final String TITLE_ATTR = "title";
	private static final String DEBUG_ATTR = "debug";

	private static String title = null;
	private static boolean isDebug = true;

	private static GameProperties instance;

	private PropertiesReader reader;


	public static GameProperties instance() {
		if (null == instance) {
			instance = new GameProperties();
		}
		return instance;
	}

	private GameProperties() {
		reader = new PropertiesReader();
        reader.loadPropertiesFile(GameProperties.FILENAME);
	}

	public String getTitle() {
		if (null == title) {
			title = reader.getProperty(TITLE_ATTR);
		}
		return title;
	}

	public boolean isDebug() {
		if (isDebug) {
			isDebug = reader.getProperty(DEBUG_ATTR).equals("true");
		}
		return isDebug;
	}

}
