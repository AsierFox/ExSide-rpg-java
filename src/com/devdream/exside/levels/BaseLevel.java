package com.devdream.exside.levels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Optional;

import com.devdream.exside.ai.dijkstra.Dijkstra;
import com.devdream.exside.entities.Enemy;
import com.devdream.exside.entities.Entity;
import com.devdream.exside.entities.Player;
import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.io.Keyboard;
import com.devdream.exside.items.Item;
import com.devdream.exside.items.particles.Particle;
import com.devdream.exside.items.particles.ParticleSpawner;
import com.devdream.exside.maths.Rect;
import com.devdream.exside.maths.Vector2DInt;
import com.devdream.exside.tiled.TiledMap;
import com.devdream.exside.types.ParticleType;
import com.devdream.exside.ui.HUD;
import com.devdream.exside.utils.MathUtils;

public abstract class BaseLevel {

    protected int width;
    protected int height;

    protected Keyboard keyboard;

    public TiledMap tiledMap;

    protected HUD playerHUD;
    
    protected ArrayList<Entity> entities;
    protected ArrayList<Item> items;
    protected ArrayList<Particle> particles;
    

	Dijkstra dijkstra;


    public BaseLevel(final Keyboard keyboard, final int width, final int height) {
        this.keyboard = keyboard;
        this.width = width;
        this.height = height;

    	playerHUD = new HUD();

        entities = new ArrayList<>();
        items = new ArrayList<>();
        particles = new ArrayList<>();

        load();
        
		dijkstra = new Dijkstra(new Vector2DInt<>(10, 10), tiledMap.mapTilesWidth, tiledMap.mapTilesHeight, tiledMap.mergedColliders);
    }

    protected abstract void load();

    public void update() {
    	checkDeletions();

    	tiledMap.update();

        Player.getInstance().update();

        for (Entity entity : entities) {
        	if (entity instanceof Enemy) {
        		((Enemy) entity).nextNode = dijkstra.findNextNodeToEntity(entity);
        	}
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
        
        Optional<Vector2DInt<Integer>> playerTile = dijkstra.getPlayerTileLocation(new Vector2DInt<>(Player.getInstance().pos.x.intValue(), Player.getInstance().pos.y.intValue()));
        if (playerTile.isPresent()) {
        	dijkstra.calculateFromNodePosition(playerTile.get());
        }
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

    // TODO Review this implementing listeners
    private void checkCollisions() {
    	ListIterator<Item> itemsIterator = items.listIterator();
        while (itemsIterator.hasNext()) {
        	Item item = itemsIterator.next();

        	for (Rect mapCollider : tiledMap.mergedColliders) {
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

//    public List<Node> findPath(Vector2DInt<Integer> start, Vector2DInt<Integer> goal) {
//        List<Node> openList = new ArrayList<>();
//        List<Node> closeList = new ArrayList<>();
//
//        Node current = new Node(start, null, 0, MathUtils.getDistanceBetweenVectors(start, goal));
//        openList.add(current);
//    }

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
