package com.devdream.exside.interfaces;

import com.devdream.exside.graphics.Renderer;

/**
 * Interface that is implemented by objects that can be displayed to the screen.
 */
public interface Renderable {
    
    void update();
    
    void render(final Renderer renderable);
    
    void dispose();
    
    boolean isRemoved();
    
}
