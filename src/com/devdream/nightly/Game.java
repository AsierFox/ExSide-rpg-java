package com.devdream.nightly;

import com.devdream.nightly.entities.mob.Player;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.io.Mouse;
import com.devdream.nightly.levels.BaseLevel;
import com.devdream.nightly.levels.TestLevel;
import com.devdream.nightly.properties.GameProperties;
import com.devdream.nightly.utils.Logger;

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

    private Keyboard keyboard;
    private Renderer renderer;
    private BaseLevel currentLevel;

    private BufferedImage image;
    private int[] pixels;

    private boolean isRunning;


    public Game() {
        keyboard = new Keyboard();

        isRunning = false;

        addKeyListener(keyboard);

        Mouse mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

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
        long loopTiming = System.nanoTime();
        long timer = System.nanoTime();
        final int nanosecondPerSecond = 1000000000;
        final double nanosecondsToSeconds = 16666666; // 1000000000 / 60
        long lastTime;
        double delta = 0;
        int UPS = 0;
        int FPS = 0;

        requestFocus();

        while (isRunning) {
            long currentTime = System.nanoTime();
            lastTime = currentTime - loopTiming;
            loopTiming = currentTime;

            delta += lastTime / nanosecondsToSeconds;

            while (delta > 0) {
                update();
                UPS++;
                delta--;
            }

            render();
            FPS++;

            if (System.nanoTime() - timer > nanosecondPerSecond) {
                Logger.logInfo(getClass(), "Updates x second: " + UPS + ", FPS: " + FPS);

                UPS = 0;
                FPS = 0;

                timer = System.nanoTime();
            }
        }

        stop();
    }

    private void setupFrame() {
        frame = new JFrame();
        // Is important to set resizable at first instance to the frame
        frame.setResizable(false);
        frame.add(this);
        frame.setTitle(GameProperties.instance().getTitle());
        frame.setIconImage(new ImageIcon(Game.class.getResource(GameProperties.instance().getIcon())).getImage());
        if (!GameProperties.instance().areWindowborders()) {
            frame.setUndecorated(true);
        }
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

        // Send data to the buffers
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        graphics.setColor(Color.WHITE);
        graphics.drawString("Player -> x: " + Player.getInstance().pos.x + ", y: " + Player.getInstance().pos.y, 30, 30);

        graphics.fillRect(Mouse.getX(), Mouse.getY(), 10, 10);

        // Swap buffers
        graphics.dispose();
        bufferStrategy.show();
    }

}
