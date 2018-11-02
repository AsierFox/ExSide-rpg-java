package com.devdream.exside;

public class Main {
    
    public static void main(String[] args) {
        // Make sure that we use graphics card
        System.setProperty("sun.java2d.opengl", "true");
        
        Game game = new Game();
        game.start();
    }
    
}
