package com.devdream.exside;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.devdream.exside.entities.Player;
import com.devdream.exside.graphics.G;
import com.devdream.exside.graphics.GameWindow;
import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.io.GameFocus;
import com.devdream.exside.io.Keyboard;
import com.devdream.exside.io.Mouse;
import com.devdream.exside.levels.BaseLevel;
import com.devdream.exside.levels.TestLevel;
import com.devdream.exside.utils.Logger;

/**
 * Extends Canvas for user interaction with GameWindow (JFrame).
 */
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    private static final String THREAD_NAME = "Game thread";

    private Thread thread;

    private Keyboard keyboard;
    private GameFocus focus;
    private Renderer renderer;
    private BaseLevel currentLevel;

    private BufferedImage image;
    private int[] pixels;

    private boolean isRunning;


    public Game() {
        keyboard = new Keyboard();
        focus = new GameFocus();

        isRunning = false;

        addKeyListener(keyboard);
        addFocusListener(focus);

        Mouse mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        // Set size to Canvas
        Dimension size = new Dimension(GameWindow.getTotalWidth(), GameWindow.getTotalHeight());
        setPreferredSize(size);

        renderer = new Renderer();
        currentLevel = new TestLevel(keyboard, 64, 64);

        image = new BufferedImage(GameWindow.WIDTH, GameWindow.HEIGHT, BufferedImage.TYPE_INT_RGB);
        // A Ruster is a group of pixels to manage them more easily, in this case the BufferedImage
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        new GameWindow(this);
    }

    public synchronized void start() {
        isRunning = true;
        thread = new Thread(this, THREAD_NAME);
        thread.start();
    }

    private synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Logger.logError(getClass(), "Error stopping " + THREAD_NAME, e);
        }
    }

    @Override
    public void run() {
        long loopTiming = System.nanoTime();
        long timer = System.nanoTime();
        final int nanosecondPerSecond = 1000000000;
        final double nanosecondsToSeconds = 16666666.6667; // 1000000000 / 60
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

        // Send data to the buffers
        Graphics graphics = bufferStrategy.getDrawGraphics();

        // Set offsets for renderer
    	int xScroll = Player.getInstance().pos.x.intValue() - (GameWindow.WIDTH >> 1);
        int yScroll = Player.getInstance().pos.y.intValue() - (GameWindow.HEIGHT >> 1);
        renderer.setOffset(xScroll, yScroll);
        renderer.setGraphics(graphics);

        System.arraycopy(renderer.pixels, 0, pixels, 0, pixels.length);
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        renderer.clear();

        currentLevel.render(renderer);

        if (!focus.isFocused) {
        	renderer.renderText("Focus to resume the game :)", 100, getHeight() >> 1, G.FontTypes.verdada, Font.BOLD, 0xffffff, 40);
        }


        // Swap buffers
        graphics.dispose();
        bufferStrategy.show();
    }

}
