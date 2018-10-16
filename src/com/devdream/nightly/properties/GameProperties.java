package com.devdream.nightly.properties;

import com.devdream.nightly.utils.PropertiesReader;

public class GameProperties {

	private static final String FILENAME = "game";

	private static final String TITLE_ATTR = "title";
	private static final String DEBUG_ATTR = "debug";

	private static GameProperties instance;

	private PropertiesReader reader;


	public static GameProperties instance() {
		if (null == instance) {
			instance = new GameProperties();
		}
		return instance;
	}

	public GameProperties() {
		reader = new PropertiesReader();
        reader.loadPropertiesFile(GameProperties.FILENAME);
	}

	public String getTitle() {
		return reader.getProperty(TITLE_ATTR);
	}

	public boolean isDebug() {
		return reader.getProperty(DEBUG_ATTR).equals("true");
	}

}
