package com.devdream.exside.levels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    
    private ArrayList<Rect> mapTilesUbicationRect = new ArrayList<>();
    
    protected ArrayList<Entity> entities;
    protected ArrayList<Item> items;
    protected ArrayList<Particle> particles;
    
    private boolean areEntitiesToRemove;
    private boolean areItemsToRemove;
    private boolean areParticlesToRemove;
    
    public BaseLevel(final Keyboard keyboard) {
        this.keyboard = keyboard;
        
        playerHUD = new HUD();
        
        entities = new ArrayList<>();
        items = new ArrayList<>();
        particles = new ArrayList<>();
        
        areEntitiesToRemove = false;
        areItemsToRemove = false;
        areParticlesToRemove = false;
        
        load();
        
        // TODO Generate this from Map Spawner
        // Collect all map tiles to rectangles
        for (int y = 0; y < tiledMap.mapTilesHeight; y++) {
            for (int x = 0; x < tiledMap.mapTilesWidth; x++) {
                // TODO Make dynamic Tile size
                mapTilesUbicationRect.add(new Rect(x << 4, y << 4, 16, 16));
            }
        }
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
        if (areEntitiesToRemove) {
            entities.removeIf(Entity::isRemoved);
            areEntitiesToRemove = false;
        }
        
        if (areItemsToRemove) {
            items.removeIf(Item::isRemoved);
            areItemsToRemove = false;
        }
        
        if (areParticlesToRemove) {
            particles.removeIf(Particle::isRemoved);
            areParticlesToRemove = false;
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
            if (!entity.isRemoved()) {
                entity.render(renderer);
            } else {
                areEntitiesToRemove = true;
            }
        }
        for (Item item : items) {
            if (!item.isRemoved()) {
                item.render(renderer);
            } else {
                areItemsToRemove = true;
            }
        }
        for (Particle particle : ParticleSpawner.particles) {
            if (!particle.isRemoved()) {
                particle.render(renderer);
            } else {
                areParticlesToRemove = true;
            }
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
    
    private boolean checkVector2InList(final ArrayList<AStarNode> nodeList, Vector2DInt<Integer> vect) {
        for (AStarNode currentNode : nodeList) {
            if (vect.x == currentNode.tileLocation.x && vect.y == currentNode.tileLocation.y) {
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<AStarNode> findPath(Vector2DInt<Integer> start, Vector2DInt<Integer> goal) {
        
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
                
                // Convert to pixel precision * 16
                // TODO Make dynamic Tile size
                int x = current.tileLocation.x << 4;
                int y = current.tileLocation.y << 4;
                
                Rect tileAt = null;
                
                // Search for current tile
                for (Rect mapTile : mapTilesUbicationRect) {
                    if (x == mapTile.x && y == mapTile.y) {
                        tileAt = mapTile;
                        break;
                    }
                }
                
                if (null == tileAt) {
                    continue;
                }
                
                boolean isSolid = false;
                // TODO Check here if the tile is solid
                for (Rect collider : tiledMap.mergedColliders) {
                 // TODO Make dynamic Tile size
                    if(collider.intersects(tileAt.x, tileAt.y, 16, 16)) {
                        isSolid = true;
                        break;
                    }
                }
                if (isSolid) {
                    continue;
                }
                
                // Get the tile "direciton" position col/row index (-1=left,
                // 0=center, 1=right)
                int xIndex = (i % 3) - 1;
                int yIndex = (i / 3) - 1;
                
                // Calculating in tile precision
                Vector2DInt<Integer> tileAtVector = new Vector2DInt<>(current.tileLocation.x + xIndex, current.tileLocation.y + yIndex);
                // If the movement is diagonal, less cost. This will provoke to move anticipate diagonals
                double gCostMovement = .95;
                // If the movement is not diagonal
                if (1 == MathUtils.getDistanceBetweenVectors(current.tileLocation, tileAtVector)) {
                	gCostMovement = 1;
                }
                double gCost = current.gCost + gCostMovement;
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
