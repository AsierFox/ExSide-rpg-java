package com.devdream.nightly.utils;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import javax.imageio.ImageIO;

public class FileReader {

    public static final String JSONExt = ".json";

    public static String readFile(final String filePath) {
        String fileContent = null;
        try {
            fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            Logger.logError(FileReader.class, "Error reading the file " + filePath, e);
        }
        return fileContent;
    }
    
    public static Optional<BufferedImage> loadImage(final String filePath) {
    	Optional<BufferedImage> bufferedImage = Optional.empty();
    	try {
    		BufferedImage image = ImageIO.read(FileReader.class.getResource(filePath));
            bufferedImage = Optional.of(image);
        } catch (IOException e) {
            Logger.logError(FileReader.class, "Error reading " + filePath, e);
        }
    	return bufferedImage;
    }

    public static Optional<BufferedImage> loadImageOptimized(final String filePath) {
    	return loadImageOptimized(filePath, Transparency.OPAQUE);
    }

    public static Optional<BufferedImage> loadImageOptimized(final String filePath, final int transparencyType) {
    	Optional<BufferedImage> bufferedImage = Optional.empty();
    	try {
            Image image = ImageIO.read(FileReader.class.getResource(filePath));

            // TODO Check if this really improves performance
            // Improves performance of the image by checking if we can accelerate by hardware,
            // using graphics card ram instead of the default ram.
            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            BufferedImage optimizedImage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparencyType);

            // Draw into image the optimized image
            Graphics g = optimizedImage.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            
            bufferedImage = Optional.of(optimizedImage);
        } catch (IOException e) {
            Logger.logError(FileReader.class, "Error reading " + filePath, e);
        }
    	return bufferedImage;
    }

}
