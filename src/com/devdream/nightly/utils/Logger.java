package com.devdream.nightly.utils;

public class Logger {

    public static void logInfo(final Class c, final String m) {
        System.out.println("[" + c.getName() + "] " + m);
    }

    public static void logError(final Class c, final String m, final Exception e) {
        System.out.println("[" + c.getName() + "] " + m);
        e.printStackTrace();
    }

}
