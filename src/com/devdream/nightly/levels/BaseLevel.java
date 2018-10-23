package com.devdream.nightly.levels;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.devdream.nightly.entities.Entity;
import com.devdream.nightly.entities.Player;
import com.devdream.nightly.graphics.Renderer;
import com.devdream.nightly.io.Keyboard;
import com.devdream.nightly.items.Item;
import com.devdream.nightly.items.particles.Particle;
import com.devdream.nightly.items.particles.ParticleSpawner;
import com.devdream.nightly.tiled.TiledMap;
import com.devdream.nightly.types.ParticleType;
import com.devdream.nightly.ui.HUD;
import com.devdream.nightly.utils.MathUtils;

public abstract class BaseLevel {

    protected int width;
    protected int height;

    protected Keyboard keyboard;

    public TiledMap tiledMap;

    protected HUD playerHUD;
    
    protected ArrayList<Entity> entities;
    protected ArrayList<Item> items;
    protected ArrayList<Particle> particles;


    public BaseLevel(final Keyboard keyboard, final int width, final int height) {
        this.keyboard = keyboard;
        this.width = width;
        this.height = height;

    	playerHUD = new HUD();

        entities = new ArrayList<>();
        items = new ArrayList<>();
        particles = new ArrayList<>();

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
        for (Particle particle : ParticleSpawner.particles) {
        	particle.update();
        }

        playerHUD.update();

        checkCollisions();
    }

    /**
     * Checks the entities & items to be removed.
     * Using Iterator is the right way to avoid ConcurrentModificationException.
     */
    private void checkDeletions() {
    	// With Java 8
        //items.removeIf(Item::isRemoved);
    	Iterator<Entity> entitiesIterator = entities.iterator();
    	while (entitiesIterator.hasNext()) {
    		if (entitiesIterator.next().isRemoved()) {
    			entitiesIterator.remove();
    		}
    	}

    	Iterator<Item> itemsIterator = items.iterator();
    	while (itemsIterator.hasNext()) {
    		if (itemsIterator.next().isRemoved()) {
    			itemsIterator.remove();
    		}
    	}

    	Iterator<Particle> particleIterator = ParticleSpawner.particles.iterator();
        while (particleIterator.hasNext()) {
        	if (particleIterator.next().isRemoved()) {
        		particleIterator.remove();
        	}
        }
    }

    private void checkCollisions() {
    	ListIterator<Item> itemsIterator = items.listIterator();
        while (itemsIterator.hasNext()) {
        	Item item = itemsIterator.next();

        	for (Rectangle mapCollider : tiledMap.mergedColliders) {
                if (item.collider.intersects(mapCollider)) {
                	item.dispose();
                	ParticleSpawner.generateParticles(item.pos.x, item.pos.y, ParticleType.TEST, MathUtils.getRectangleDepthSideCollision(item.collider, mapCollider));
                }
            }

        	for (Entity entity : entities) {
                if (item.collider.intersects(entity.collider)) {
                	item.dispose();
                	ParticleSpawner.generateParticles(item.pos.x, item.pos.y, ParticleType.TEST, MathUtils.getRectangleDepthSideCollision(item.collider, entity.collider));
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
        for (Particle particle : ParticleSpawner.particles) {
        	particle.render(renderer);
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
    
    public Player getPlayer() {
    	return Player.getInstance();
    }

}
