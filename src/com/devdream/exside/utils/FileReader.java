package com.devdream.exside.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import javax.imageio.ImageIO;

public class FileReader {
    
    public static final String JSON_EXT = ".json";
    public static final String TTF_EXT = ".ttf";
    
    public static String readFile(final String filePath) {
        StringBuilder result = new StringBuilder("");
        
        InputStream inputStream = FileReader.class.getClassLoader().getResourceAsStream("maps/tiled-map.json");
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            Logger.logError(FileReader.class, "Error reading " + filePath, e);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Logger.logError(FileReader.class, "Error reading " + filePath, e);
                }
            }
        }
        return result.toString();
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
        try {
            Image image = ImageIO.read(FileReader.class.getResource(filePath));
            // Improves performance of the image by checking if we can
            // accelerate by hardware,
            // using graphics card ram instead of the default ram.
            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
            BufferedImage optimizedImage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparencyType);
            // Draw into image the optimized image
            Graphics g = optimizedImage.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            
            return Optional.of(optimizedImage);
        } catch (IOException e) {
            Logger.logError(FileReader.class, "Error reading " + filePath, e);
        }
        return Optional.empty();
    }
    
    public static Optional<Font> loadTTFont(final String ttfontPath, final int style, final float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, FileReader.class.getResourceAsStream(ttfontPath + TTF_EXT));
            font = font.deriveFont(style, size);
            return Optional.of(font);
        } catch (FontFormatException | IOException e) {
            Logger.logError(FileReader.class, "Error reading " + ttfontPath, e);
        }
        return Optional.empty();
    }
    
}
