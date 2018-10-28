package com.devdream.nightly.ai;

import java.util.Random;

import com.devdream.nightly.entities.Entity;
import com.devdream.nightly.entities.Player;

/**
 * AI movement strategies for NPC entities.
 */
public class EntityMoveStrategy {

    private static final Random rand = new Random();

    /**
     * Basic returning movement, need to be set the x or y to 1 previously.
     * @param entity Entity to move
     */
    public static void basicReturnMove(Entity entity) {
        if (entity.time > 60) {
            entity.moveAmount.x *= -1;
            entity.moveAmount.y *= -1;
            entity.time = 0;
        }
    }

    /**
     * Random patrols.
     * @param entity Entity to move
     */
    public static void randomPatrol(Entity entity) {
        if (entity.time % (rand.nextInt(40) + 30) == 0) {
            // Generate random -1, 0 or 1
            entity.moveAmount.x = rand.nextInt(3) - 1;
            entity.moveAmount.y = rand.nextInt(3) - 1;

            // 3 over 1 chance to move
            if (rand.nextInt(3) == 0) {
                entity.moveAmount.x = 0;
                entity.moveAmount.y = 0;
            }
        }
    }

    /**
     * Follow the player.
     * @param entity Entity to move
     * @param player The player to follow
     */
    public static void basicFollowPlayer(Entity entity, final Player player) {
        if (entity.pos.x < player.pos.x) {
            entity.moveAmount.x = 1;
        }
        else if (entity.pos.x > player.pos.x) {
            entity.moveAmount.x = -1;
        }
        else if (entity.pos.y < player.pos.y) {
            entity.moveAmount.y = 1;
        }
        else if (entity.pos.y > player.pos.y) {
            entity.moveAmount.y = -1;
        }
        else {
            entity.moveAmount.x = 0;
            entity.moveAmount.y = 0;
        }
    }

}
