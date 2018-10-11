package com.devdream.nightly.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

    public static final String JSONExt = ".json";

    public static String readFile(final String filePath) {
        String fileContent = null;
        try {
            fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            Logger.logError(FileReader.class, "Error reading the file " + filePath + "!", e);
        }
        return fileContent;
    }

}
