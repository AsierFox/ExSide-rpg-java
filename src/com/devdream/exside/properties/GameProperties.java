package com.devdream.exside.properties;

import com.devdream.exside.utils.PropertiesReader;

public class GameProperties {
    
    private static final String FILENAME = "game";
    
    private static final String TITLE_ATTR = "title";
    private static final String ICON_ATTR = "icon";
    private static final String DEBUG_ATTR = "debug";
    private static final String WINDOWBORDERS_ATTR = "windowborders";
    
    private static String title = null;
    private static String icon = null;
    private static boolean isDebug = true;
    private static boolean areWindowborders = true;
    
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
    
    public String getIcon() {
        if (null == icon) {
            icon = reader.getProperty(ICON_ATTR);
        }
        return icon;
    }
    
    public boolean isDebug() {
        if (isDebug) {
            isDebug = reader.getProperty(DEBUG_ATTR).equals("true");
        }
        return isDebug;
    }
    
    public boolean areWindowborders() {
        if (areWindowborders) {
            areWindowborders = reader.getProperty(WINDOWBORDERS_ATTR).equals("true");
        }
        return areWindowborders;
    }
    
}
