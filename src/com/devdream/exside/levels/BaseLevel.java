package com.devdream.exside.levels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;

import com.devdream.exside.ai.astar.AStarNode;
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
    
    protected Keyboard keyboard;
    
    public TiledMap tiledMap;
    
    protected HUD playerHUD;
    
    protected ArrayList<Entity> entities;
    protected ArrayList<Item> items;
    protected ArrayList<Particle> particles;
    
    public BaseLevel(final Keyboard keyboard) {
        this.keyboard = keyboard;
        
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
     * Checks the entities & items to be removed. Using Iterator is the right
     * way to avoid ConcurrentModificationException.
     */
    private void checkDeletions() {
        // With Java 8
        // items.removeIf(Item::isRemoved);
        // TODO Create a variable dirty to avoid checking all things every frame
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
     * 
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
    
    // A* Node Comparator
    private Comparator<AStarNode> aStarNodeComparator = new Comparator<AStarNode>() {
        @Override
        public int compare(AStarNode node1, AStarNode node2) {
            if (node1.fCost < node2.fCost) {
                return 1; // Move down in List
            }
            if (node1.fCost > node2.fCost) {
                return -1; // Move up front in List
            }
            return 0;
        }
    };
    
    private ArrayList<Rect> mapTilesRect = new ArrayList<>();
    
    private boolean checkVector2InList(final ArrayList<AStarNode> nodeList, Vector2DInt<Integer> vect) {
        for (AStarNode currentNode : nodeList) {
            if (vect.x == currentNode.tileLocation.x && vect.y == currentNode.tileLocation.y) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<AStarNode> findPath(Vector2DInt<Integer> start, Vector2DInt<Integer> goal) {
        
        // Collect map tiles to a Rectangle
        for (int y = 0; y < tiledMap.mapTilesHeight; y++) {
            for (int x = 0; x < tiledMap.mapTilesWidth; x++) {
                // TODO Dynamic this to map tile size
                mapTilesRect.add(new Rect(x * 16, y * 16, 16, 16));
            }
        }
        
        // Start A* path finding
        ArrayList<AStarNode> openList = new ArrayList<>();
        ArrayList<AStarNode> closedList = new ArrayList<>();
        
        AStarNode current = new AStarNode(start, null, 0, MathUtils.getDistanceBetweenVectors(start, goal));
        openList.add(current);
        
        while (!openList.isEmpty()) {
            // Sort from closest to farthest of the goal, less cost first
            Collections.sort(openList, aStarNodeComparator);
            // Get the first one in list, the closest to the goal
            current = openList.get(0);
            
            // Goal reached
            if (current.tileLocation.x == goal.x && current.tileLocation.y == goal.y) {
                ArrayList<AStarNode> resultPath = new ArrayList<>();
                // The start node has null came from node
                while (null != current.cameFrom) {
                	resultPath.add(current);
                	current = current.cameFrom;
                }
                openList.clear();
                closedList.clear();
                return resultPath;
            }
            
            // Move current from open to closed list
            openList.remove(current);
            closedList.add(current);
            
            // Check open neighbour tiles
            for (int i = 0; i < 9; i++) {
                // Skip our current tile
                if (i == 4) {
                    continue;
                }
                int x = current.tileLocation.x << 4;
                int y = current.tileLocation.y << 4;
                // Get the tile "direciton" position col/row index (-1=left,
                // 0=center, 1=right)
                int xIndex = (i % 3) - 1;
                int yIndex = (i / 3) - 1;
                
                // TODO Test This
                Rect tileAt = null;
                
                // Search for current tile
                for (Rect mapTile : mapTilesRect) {
                    if (x == mapTile.x && y == mapTile.y) {
                        tileAt = mapTile;
                        break;
                    }
                }
                
                if (null == tileAt) {
                    continue;
                }
                
                // TODO Check here if the tile is solid
                // if (solid) continue;
                
                Vector2DInt<Integer> tileAtVector = new Vector2DInt<>(x + xIndex, y + yIndex);
                double gCost = current.gCost + MathUtils.getDistanceBetweenVectors(current.tileLocation, tileAtVector);
                double heuristicCost = MathUtils.getDistanceBetweenVectors(tileAtVector, goal);
                
                AStarNode newNode = new AStarNode(tileAtVector, current, gCost, heuristicCost);
                // In A* a closed node is prone to reopening
                // TODO Check this gCost >= newNode.gCost
                if (checkVector2InList(closedList, tileAtVector) && gCost >= newNode.gCost) {
                    continue;
                }
                if (!checkVector2InList(openList, tileAtVector) || gCost < newNode.gCost) {
                    openList.add(newNode);
                }
            }
        }
        closedList.clear();
        return null;
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
