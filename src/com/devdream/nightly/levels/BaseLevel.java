package com.devdream.nightly.levels;

import com.devdream.nightly.entities.Entity;
import com.devdream.nightly.entities.mob.Player;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.items.Item;
import com.devdream.nightly.tiled.TiledMap;
import com.devdream.nightly.ui.HUD;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BaseLevel {

    protected int width;
    protected int height;

    protected Keyboard keyboard;

    public TiledMap tiledMap;

    protected HUD playerHUD;
    
    protected ArrayList<Entity> entities;
    protected ArrayList<Item> items;


    public BaseLevel(final Keyboard keyboard, final int width, final int height) {
        this.keyboard = keyboard;
        this.width = width;
        this.height = height;

    	playerHUD = new HUD();

        this.entities = new ArrayList<>();
        this.items = new ArrayList<>();

        load();
    }

    protected abstract void load();

    public void update() {
    	checkDeletions();

    	tiledMap.update();

        Player.getInstance().update();

        for (Entity entity : entities) {
        	entity.update();
        }
        for (Item item : items) {
        	item.update();
        }

        playerHUD.update();

        checkCollisions();
    }

    /**
     * Checks the entities & items to be removed.
     * Using Iterator is the right way to avoid ConcurrentModificationException.
     */
    private void checkDeletions() {
    	Iterator<Entity> entitiesIterator = entities.iterator();
    	for (Entity entity : entities) {
    		if (entity.isRemoved()) {
    			entitiesIterator.remove();
    		}
    	}

    	// With Java 8
        //items.removeIf(Item::isRemoved);
    	Iterator<Item> itemsIterator = items.iterator();
    	while (itemsIterator.hasNext()) {
    		Item item = itemsIterator.next();
    		if (item.isRemoved()) {
    			itemsIterator.remove();
    		}
    	}
    }

    private void checkCollisions() {
        for (Item item : items) {

            for (Rectangle mapCollider : tiledMap.mergedColliders) {
                if (item.collider.intersects(mapCollider)) {
                    item.dispose();
                }
            }
            for (Entity entity : entities) {
                if (item.collider.intersects(entity.collider)) {
                    item.dispose();
                }
            }
        }
    }

    /**
     * Render the level.
     * @param renderer
     */
    public void render(final Renderer renderer) {
    	tiledMap.render(renderer);

    	Player.getInstance().render(renderer);

        for (Entity entity : entities) {
        	entity.render(renderer);
        }
        for (Item item : items) {
        	item.render(renderer);
        }

    	playerHUD.render(renderer);
    }

    public void addEntity(final Entity entity) {
    	entities.add(entity);
    }
    
    public void removeEntity(final Entity entity) {
    	entities.remove(entity);
    }

    public void addItem(final Item item) {
    	items.add(item);
    }
    
    public void removeItem(final Item item) {
    	items.remove(item);
    }

}
