package com.devdream.nightly;

public class Main {

    public static void main(String[] args) {
//    	try {
	        // Make sure that we use graphics card
	        System.setProperty("sun.java2d.opengl", "true");
	
	        Game game = new Game();
	        game.start();
//    	} catch(Exception e) {
//    		JOptionPane.showMessageDialog(null, e.getStackTrace());
//    	}
    }

}
