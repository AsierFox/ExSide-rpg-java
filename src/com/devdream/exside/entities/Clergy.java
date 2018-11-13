package com.devdream.exside.entities;

import com.devdream.exside.ai.EntityMoveStrategy;
import com.devdream.exside.graphics.G;
import com.devdream.exside.graphics.SpriteAnimation;
import com.devdream.exside.io.Mouse;
import com.devdream.exside.items.projectiles.TestProjectile;
import com.devdream.exside.levels.BaseLevel;
import com.devdream.exside.maths.Rect;
import com.devdream.exside.types.Direction;
import com.devdream.exside.types.EntityState;
import com.devdream.exside.utils.MathUtils;

public class Clergy extends Entity {
    
    private static int VISIBILITY_RADIUS = 120;
    
    private SpriteAnimation southAnimation;
    private SpriteAnimation westAnimation;
    private SpriteAnimation eastAnimation;
    private SpriteAnimation northAnimation;
    
    private Player levelPlayer;

    private int cadenceCounter;

    
    public Clergy() {
        super(140.0f, -110.0f, G.Sprites.civilianDefault);
        
        southAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 0, 3);
        westAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 4, 7);
        eastAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 8, 11);
        northAnimation = new SpriteAnimation(G.SpriteSheets.civilian, G.Sprites.civilianWidth, G.Sprites.civilianHeight, 12, 15);
        
        collider = new Rect(pos.x.intValue(), pos.y.intValue(), sprite.WIDTH, sprite.HEIGHT);

        cadenceCounter = 0;
    }
    
    @Override
    public void attachToLevel(BaseLevel belongsToLevel) {
        super.attachToLevel(belongsToLevel);
        levelPlayer = belongsToLevel.getPlayer();
        
        belongsToLevel.addEntity(this);
    }
    
    @Override
    public void update() {
        
        if (MathUtils.isEntityInRadius(this, levelPlayer, VISIBILITY_RADIUS)) {

            EntityMoveStrategy.basicFollowPlayer(this, levelPlayer);
            
            if (moveAmount.x != 0 || moveAmount.y != 0) {
                move(moveAmount);
            }

            if (cadenceCounter > TestProjectile.CADENCE) {
                final double shootDirection = Math.atan2(levelPlayer.pos.y - pos.y, levelPlayer.pos.x - pos.x);
                belongsToLevel.addItem(new TestProjectile(pos.x.intValue(), pos.y.intValue(), shootDirection));

                cadenceCounter = 0;
            }

            // Update cadence
            cadenceCounter++;
        }
        
        // Update collider
        collider.x = (pos.x.intValue() - (sprite.WIDTH >> 1));
        collider.y = (pos.y.intValue() - (sprite.HEIGHT >> 1));
        
        updateSprite();
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
