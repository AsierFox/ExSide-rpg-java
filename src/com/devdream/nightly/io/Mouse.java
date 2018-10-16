package com.devdream.nightly.io;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

    private static int mouseX;
    private static int mouseY;
    private static int mouseBtn;

    public Mouse() {
        mouseX = mouseY = mouseBtn = -1;
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

}
