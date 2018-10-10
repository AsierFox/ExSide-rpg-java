package com.devdream.nightly;

import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.levels.TestLevel;
import com.devdream.nightly.utils.Logger;
import com.devdream.nightly.utils.PropertiesReader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Extends Canvas for user interaction with JFrame.
 */
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 300;
    public static final int HEIGHT = WIDTH / 16 * 9; // Aspect ratio
    public static final int SCALE = 3;

    private static final String THREAD_NAME = "Game thread";

    private JFrame frame;
    private Thread thread;

    private PropertiesReader gamePropertiesReader;
    private Keyboard keyboard;
    private Renderer renderer;

    private BaseLevel currentLevel;

    private BufferedImage image;
    private int[] pixels;

    private boolean isRunning;


    public Game() {
        gamePropertiesReader = new PropertiesReader();
        keyboard = new Keyboard();

        isRunning = false;

        gamePropertiesReader.loadPropertiesFile(PropertiesReader.GameProperties.FILENAME);
        addKeyListener(keyboard);

        // Set size to Canvas
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);

        renderer = new Renderer(WIDTH, HEIGHT);

        currentLevel = new TestLevel(keyboard, 64, 64);

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // A Ruster is a group of pixels to manage them more easily, in this case the BufferedImage
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        setupFrame();
    }

    public synchronized void start() {
        isRunning = true;
        thread = new Thread(this, THREAD_NAME);
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Logger.logError(getClass(), "Error stopping " + THREAD_NAME + "!", e);
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;
        long timer = System.currentTimeMillis();

        final double nanosecondsToSeconds = 1000000000.0 / 60.0;
        int UPS = 0;
        int FPS = 0;

        requestFocus();

        while (isRunning) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / nanosecondsToSeconds;
            lastTime = currentTime;

            while (delta > 0) {
                update();
                UPS++;
                delta--;
            }

            render();
            FPS++;

            // Greater than 1 second
            if (System.currentTimeMillis() - timer > 1000) {
                // Sum 1 second, to execute this every second
                timer += 1000;

                Logger.logInfo(getClass(), "Updates x second: " + UPS + ", FPS: " + FPS);

                UPS = 0;
                FPS = 0;
            }
        }

        stop();
    }

    private void setupFrame() {
        frame = new JFrame();
        // Is important to set resizable at first instance to the frame
        frame.setResizable(false);
        frame.setTitle(gamePropertiesReader.getProperty(PropertiesReader.GameProperties.TITLE));
        frame.add(this);
        // Size frame to match the Canvas dimensions
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Center Window to the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void update() {
        keyboard.update();

        currentLevel.update();
    }

    private void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();

        if (null == bufferStrategy) {
            createBufferStrategy(3);
            Logger.logInfo(getClass(), "Created Buffer Strategy!");
            return;
        }

        renderer.clear();

        currentLevel.render(renderer);

        System.arraycopy(renderer.pixels, 0, pixels, 0, pixels.length);

        // Send data to the buffer
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        graphics.setColor(Color.WHITE);
        graphics.drawString("Player -> x: " + currentLevel.player.x + ", y: " + currentLevel.player.y, 30, 30);

        graphics.dispose();
        // Swap buffers
        bufferStrategy.show();
    }

}
