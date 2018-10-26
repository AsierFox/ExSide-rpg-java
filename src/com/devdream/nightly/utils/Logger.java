package com.devdream.nightly.utils;

import com.devdream.nightly.properties.GameProperties;

public class Logger {

    public static void logInfo(final Class c, final String m) {
    	if (GameProperties.instance().isDebug()) {
    		System.out.println("[" + c.getName() + "] " + m);
    	}
    }

    public static void logError(final Class c, final String m) {
        System.out.println("[" + c.getName() + "] " + m);
    }

    public static void logError(final Class c, final String m, final Exception e) {
        logError(c, m);
        e.printStackTrace();
    }

}
