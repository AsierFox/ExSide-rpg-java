package com.devdream.exside.graphics;

import java.awt.Canvas;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.devdream.exside.Game;
import com.devdream.exside.io.Mouse;
import com.devdream.exside.properties.GameProperties;

public class GameWindow extends JFrame {
    private static final long serialVersionUID = 1745759043772177195L;
    
    public static final int WIDTH = 300;
    public static final int HEIGHT = WIDTH / 16 * 9; // Aspect ratio
    public static final int SCALE = 1;
    
    public GameWindow(final Canvas gameCanvas) {
        // Is important to set resizable at first instance to the frame
        setResizable(false);
        add(gameCanvas);
        setTitle(GameProperties.instance().getTitle());
        setIconImage(new ImageIcon(Game.class.getResource(GameProperties.instance().getIcon())).getImage());
        setCursor(Mouse.getCursor());
        if (!GameProperties.instance().areWindowborders()) {
            setUndecorated(true);
        }
        // Size frame to match the Canvas dimensions
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Center Window to the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static int getTotalWidth() {
        return WIDTH * SCALE;
    }
    
    public static int getTotalHeight() {
        return HEIGHT * SCALE;
    }
    
}
