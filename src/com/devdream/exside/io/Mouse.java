package com.devdream.exside.io;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Optional;

import com.devdream.exside.utils.FileReader;

public class Mouse implements MouseListener, MouseMotionListener {
    
    public static final int LEFT_CLICK_BTN = 1;
    public static final int RIGHT_CLICK_BTN = 3;
    
    private static int mouseX;
    private static int mouseY;
    private static int mouseBtn;
    
    private static Cursor cursor;
    
    public Mouse() {
        mouseX = mouseY = mouseBtn = -1;
        
        setPrecisionCursor();
    }
    
    public static int getX() {
        return mouseX;
    }
    
    public static int getY() {
        return mouseY;
    }
    
    public static int getBtn() {
        return mouseBtn;
    }
    
    public static Cursor getCursor() {
        return cursor;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        mouseBtn = e.getButton();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        mouseBtn = -1;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public String toString() {
        return "Mouse x=" + mouseX + ", y=" + mouseY + " btn=" + mouseBtn;
    }
    
    private void setPrecisionCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Optional<BufferedImage> cursorImage = FileReader.loadImage("/cursor.png");
        // Center pointer position
        Point cursorPoint = new Point(cursorImage.get().getWidth() >> 1, cursorImage.get().getHeight() >> 1);
        cursor = toolkit.createCustomCursor(cursorImage.get(), cursorPoint, "Precise cursor");
    }
    
}
