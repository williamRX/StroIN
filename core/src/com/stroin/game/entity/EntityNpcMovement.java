package com.stroin.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EntityNpcMovement {

    private float gravity = 0.98f;
    private float jumpVelocity = 8f;
    private float maxJumpDuration = 0.5f;
    private float jumpDuration;
    private int frameCounter = 0;
    private String lastDirection = "right";
    private int currentSpriteIndex = 0;

    public void updateMovement(Entity npc, Vector2 playerPosition) {
        float dx = playerPosition.x - npc.getPosition().x;

        if (Math.abs(dx) < 200.0f) {
            // If the NPC is close enough to the player, stop moving
            npc.setMovingRight(false);
            npc.setMovingLeft(false);
        } else if (dx > 0) {
            // If the player is to the right of the NPC, move right
            npc.setMovingRight(true);
            npc.setMovingLeft(false);
        } else {
            // If the player is to the left of the NPC, move left
            npc.setMovingRight(false);
            npc.setMovingLeft(true);
        }
        // if (!npc.isMovingRight() && !npc.isMovingLeft()) {
        //     npc.setMovingRight(true);
        // }
    
        // if (isStuck(npc)) {
        //     stuckFrames++;
        // } else {
        //     stuckFrames = 0;
        // }
    
        // if (npc.isMovingRight()) {
        //     if (npc.getPosition().x >= 800.0f || (stuckFrames > 10 && triedJumping)) {
        //         npc.setMovingRight(false);
        //         npc.setMovingLeft(true);
        //         triedJumping = false;
        //         stuckFrames = 0;
        //     } else if (stuckFrames > 10 && !triedJumping) {
        //         npc.setJumping(true);
        //         triedJumping = true;
        //     }
        // } else if (npc.isMovingLeft()) {
        //     if (npc.getPosition().x <= -200.0f || (stuckFrames > 10 && triedJumping)) {
        //         npc.setMovingRight(true);
        //         npc.setMovingLeft(false);
        //         triedJumping = false;
        //         stuckFrames = 0;
        //     } else if (stuckFrames > 10 && !triedJumping) {
        //         npc.setJumping(true);
        //         triedJumping = true;
        //     }
        // }
    }

    public void jump(Entity npc) {
        if (npc.isJumping()) {
            jumpDuration += Gdx.graphics.getDeltaTime();
            if (jumpDuration < maxJumpDuration) {
                npc.getPosition().y += jumpVelocity;
            } else {
                npc.setJumping(false);
                jumpDuration = 0;
            }
        } else if (npc.getPosition().y > 0) {
            npc.getPosition().y -= gravity;
        }
    }

    public void updateNpcPosition(Entity npc, Vector2 playerPosition) {
        // npc.setJumping(isJumping);
        // npc.setRunning(isRunning);
        // npc.setMovingRight(isMovingRight);
        // npc.setMovingLeft(isMovingLeft);
        updateMovement(npc, playerPosition);
        jump(npc);
        npc.move(npc.isMovingRight(), npc.isMovingLeft());
    }

    public TextureRegion getCurrentSprite(Entity npc, TextureRegion[] sprites) {
        frameCounter++;

        if (npc.isJumping()) {
            if (npc.isMovingRight()) {
                return sprites[6];
            } else if (npc.isMovingLeft()){
                return sprites[14];
            } else {
                return sprites[1];
            }
        }
        if (npc.isMovingRight()) {
            lastDirection = "right";
            if ((npc.isRunning() && frameCounter % 2 == 0) || (!npc.isRunning() && frameCounter % 6 == 0)) {
                currentSpriteIndex = (currentSpriteIndex + 1) % 3;
            }
            int spriteIndex = currentSpriteIndex == 2 ? 4 : currentSpriteIndex * 3;
            return sprites[spriteIndex];
        } else if (npc.isMovingLeft()) {
            lastDirection = "left";
            if ((npc.isRunning() && frameCounter % 2 == 0) || (!npc.isRunning() && frameCounter % 6 == 0)) {
                currentSpriteIndex = (currentSpriteIndex + 1) % 3;
            }
            int spriteIndex = currentSpriteIndex == 2 ? 12 : 8 + currentSpriteIndex * 3;
            return sprites[spriteIndex];
        }

        if (lastDirection.equals("right")) {
            return sprites[0];
        } else {
            return sprites[8];
        }
    }
}
