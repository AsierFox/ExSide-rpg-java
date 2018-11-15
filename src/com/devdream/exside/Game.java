package com.devdream.exside;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.devdream.exside.graphics.G;
import com.devdream.exside.graphics.GameWindow;
import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.io.GameFocus;
import com.devdream.exside.io.Keyboard;
import com.devdream.exside.io.Mouse;
import com.devdream.exside.scenes.BaseScene;
import com.devdream.exside.scenes.GameScene;
import com.devdream.exside.utils.Logger;

/**
 * Extends Canvas for user interaction with GameWindow (JFrame).
 */
public class Game extends Canvas implements Runnable {
    
    private static final long serialVersionUID = 1L;
    
    private static final String THREAD_NAME = "Game thread";
    
    private Thread thread;
    
    public Keyboard keyboard;
    private GameFocus focus;
    private Renderer renderer;
    private BaseScene currentScene;
    
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
        currentScene = new GameScene();
        currentScene.init(this);
        
        image = new BufferedImage(GameWindow.WIDTH, GameWindow.HEIGHT, BufferedImage.TYPE_INT_RGB);
        // A Ruster is a group of pixels to manage them more easily, in this
        // case the BufferedImage
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
        currentScene.update();
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
        Graphics2D graphics2D = (Graphics2D) graphics;
        
        // Set offsets for renderer
        int xScroll = currentScene.getClientPlayer().pos.x.intValue() - (GameWindow.WIDTH >> 1);
        int yScroll = currentScene.getClientPlayer().pos.y.intValue() - (GameWindow.HEIGHT >> 1);
        renderer.setOffset(xScroll, yScroll);
        renderer.setGraphics(graphics);
        renderer.setGraphics2D(graphics2D);
        
        System.arraycopy(renderer.pixels, 0, pixels, 0, pixels.length);
        graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        renderer.clear();
        
        currentScene.render(renderer);
        
        if (!focus.isFocused) {
            renderer.renderText("Focus to resume the game :)", 100, getHeight() >> 1, G.FontTypes.verdada, Font.BOLD, 0xffffff, 40);
        }

        // Swap buffers
        graphics2D.dispose();
        graphics.dispose();
        bufferStrategy.show();
    }
    
    public void changeScene(final BaseScene sceneToLoad) {
        currentScene.dispose();
        currentScene = sceneToLoad;
        currentScene.init(this);
    }
    
}
