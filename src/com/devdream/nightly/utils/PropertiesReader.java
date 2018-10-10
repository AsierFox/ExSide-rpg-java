package com.devdream.nightly.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    public static class GameProperties {
        public static final String FILENAME = "game";

        public static final String TITLE = "title";
    }

    private static final String PROPERTIES_EXTENSION = ".properties";

    private Properties properties;
    private InputStream inputStream;

    public PropertiesReader() {
        properties = new Properties();
        inputStream = null;
    }

    public void loadPropertiesFile(final String propertiesFile) {
        try {
            inputStream = new FileInputStream(propertiesFile + PROPERTIES_EXTENSION);
            properties.load(inputStream);
        } catch (IOException e) {
            Logger.logError(getClass(), "Error reading " + propertiesFile + PROPERTIES_EXTENSION + "!", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Logger.logError(getClass(), "Error closing " + propertiesFile + PROPERTIES_EXTENSION + "!", e);
                }
            }
        }
    }

    public String getProperty(final String property) {
        return properties.getProperty(property);
    }

}
