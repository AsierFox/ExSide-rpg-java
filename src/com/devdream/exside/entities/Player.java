package com.devdream.exside.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Point;

import com.devdream.exside.graphics.G;
import com.devdream.exside.graphics.GameWindow;
import com.devdream.exside.graphics.LightEffect;
import com.devdream.exside.graphics.Renderer;
import com.devdream.exside.graphics.SpriteAnimation;
import com.devdream.exside.io.Keyboard;
import com.devdream.exside.io.Mouse;
import com.devdream.exside.items.projectiles.TestProjectile;
import com.devdream.exside.maths.Rect;
import com.devdream.exside.types.Direction;
import com.devdream.exside.types.EntityState;

public class Player extends Entity {
    
    private Keyboard keyboard;
    
    private int cadenceCounter;
    
    private int colliderTopPadding;
    private int colliderLeftPadding;
    private int colliderRightPadding;
    
    private LightEffect lightEffect;
    private int lightRadiusSum;
    
    private SpriteAnimation southAnimation;
    private SpriteAnimation westAnimation;
    private SpriteAnimation eastAnimation;
    private SpriteAnimation northAnimation;
    
    
    public Player() {
        super(172.0f, 100.0f, G.Sprites.playerDefault);
        
        colliderTopPadding = 5;
        colliderLeftPadding = 6;
        colliderRightPadding = 12;
        
        cadenceCounter = 0;
        
        southAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 0, 3);
        westAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 4, 7);
        eastAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 8, 11);
        northAnimation = new SpriteAnimation(G.SpriteSheets.player, G.Sprites.playerWidth, G.Sprites.playerHeight, 12, 15);
        
        collider = new Rect(pos.x.intValue(), pos.y.intValue(), sprite.WIDTH
                - colliderRightPadding, (sprite.HEIGHT >> 1) - colliderTopPadding);
        
        int x = (GameWindow.getTotalWidth() >> 1) - (250 >> 1);
        int y = (GameWindow.getTotalHeight() >> 1) - (250 >> 1);
        lightEffect = new LightEffect(new Point(x + 125, y + 125), 375, new float[] {.0f, .6f},
        		new Color[] {new Color(.0f, .0f, .0f, .0f), Color.white}, AlphaComposite.getInstance(AlphaComposite.DST_OUT, .95f));
        lightRadiusSum = 1;
    }
    
    public void init(final Keyboard keyboard) {
        this.keyboard = keyboard;
    }
    
    @Override
    public void update() {
        moveAmount.x = 0;
        moveAmount.y = 0;
        
        // Read keyboard events
        if (keyboard.shift) {
            speed = 2;
        } else {
            speed = 1;
        }
        
        if (keyboard.up) {
            moveAmount.y -= speed;
        } else if (keyboard.down) {
            moveAmount.y += speed;
        } else if (keyboard.left) {
            moveAmount.x -= speed;
        } else if (keyboard.right) {
            moveAmount.x += speed;
        }
        
        // Read mouse events
        if (Mouse.getBtn() == Mouse.LEFT_CLICK_BTN && canShoot()) {
            shoot();
            cadenceCounter = 0;
        }
        
        // Update cadence
        if (cadenceCounter <= TestProjectile.CADENCE) {
            cadenceCounter++;
        }
        
        // Check diagonal movement for some reason
        // (xMove != 0 && yMove != 0)
        if (moveAmount.x != 0 || moveAmount.y != 0) {
            move(moveAmount);
        } else {
            state = EntityState.IDLE;
        }
        
        updateCollider();
        
        updateSprite();
        
        lightEffect.update();
        
        if (lightEffect.radius <= 350 || lightEffect.radius >= 450) {
        	lightRadiusSum *= -1;
        }
    	lightEffect.radius += lightRadiusSum;
    }
    
    private boolean canShoot() {
        return cadenceCounter > TestProjectile.CADENCE;
    }
    
    private void shoot() {
        int shootX = Mouse.getX() - (GameWindow.getTotalWidth() >> 1);
        int shootY = Mouse.getY() - (GameWindow.getTotalHeight() >> 1);
        final double shootDirection = Math.atan2(shootY, shootX);
        // Convert angle to degrees
        // shootDirection *= 180 / Math.PI;
        belongsToLevel.addItem(new TestProjectile(pos.x.intValue(), pos.y.intValue(), shootDirection));
    }
    
    @Override
    public void render(final Renderer renderer) {
        super.render(renderer);
        lightEffect.render(renderer);
        renderer.renderText("Player -> x: " + pos.x + ", y: " + pos.y, 5, 20, G.FontTypes.kirangFont, 0xffffff);
    }
    
    private void updateCollider() {
        collider.x = (pos.x.intValue() - (sprite.WIDTH >> 1)) + colliderLeftPadding;
        collider.y = pos.y.intValue() + colliderTopPadding;
    }
    
    private void updateSprite() {
        if (direction == Direction.SOUTH) {
            if (state == EntityState.MOVING) {
                southAnimation.update();
            } else {
                southAnimation.reset();
            }
            sprite = southAnimation.currentSprite;
        } else if (direction == Direction.WEST) {
            if (state == EntityState.MOVING) {
                westAnimation.update();
            } else {
                westAnimation.reset();
            }
            sprite = westAnimation.currentSprite;
        } else if (direction == Direction.EAST) {
            if (state == EntityState.MOVING) {
                eastAnimation.update();
            } else {
                eastAnimation.reset();
            }
            sprite = eastAnimation.currentSprite;
        } else if (direction == Direction.NORTH) {
            if (state == EntityState.MOVING) {
                northAnimation.update();
            } else {
                northAnimation.reset();
            }
            sprite = northAnimation.currentSprite;
        }
    }
    
}
