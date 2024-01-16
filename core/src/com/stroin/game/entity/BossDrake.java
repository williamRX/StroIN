package com.stroin.game.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.stroin.game.elements.BouleDeFeu;

public class BossDrake extends EnemyNpc {
    private float spriteChangeTimer = 0;
    private static final float SPRITE_CHANGE_INTERVAL = 0.2f;
    private List<BouleDeFeu> projectiles;
    private String state = "UP";
    private float stateTimer = 0;
    private boolean dead = false;
    private float deathTimer = 0;
    private boolean disapear = false;

    public BossDrake(String name, int health, Vector2 position, int speed, TextureRegion[] sprites, float velocityY) {
        super(name, health, position, speed, sprites, velocityY);
        this.projectiles = new ArrayList<>();
    }

    public Rectangle getBounds() {
    return new Rectangle(this.position.x, this.position.y, this.width, this.height * 1.5f);
}

    @Override
    public void attack() {
        BouleDeFeu bouleDeFeu = new BouleDeFeu(
            "BouleDeFeu", 
            (int) this.position.x, 
            (int) this.position.y, 
            100, // width
            100, // height
            -30, // speedX
            0 // speedY
        );
        this.projectiles.add(bouleDeFeu);
        System.out.println("BossDrake attack");
        // Implement the attack logic here
    }

    @Override
    public void die() {
        System.out.println("BossDrake die");
        this.dead = true;
        // Implement the die logic here
    }

    public boolean isDisapear() {
        return this.disapear;
    }

    public void moveUp(float delta) {
        if (state.equals("UP")) {
            this.position.y += 1;
            stateTimer += delta;
            if (stateTimer >= 3) {
                state = "DOWN";
                stateTimer = 0;
            }
        }
    }
    
    public void moveDown(float delta) {
        if (state.equals("DOWN")) {
            this.position.y -= 1;
            stateTimer += delta;
            if (stateTimer >= 3) {
                state = "UP";
                stateTimer = 0;
            }
        }
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    public void setSprites(TextureRegion[] sprites) {
        this.sprites = sprites;
    }

    public TextureRegion[] getSprites() {
        return this.sprites;
    }

    public List<BouleDeFeu> getProjectiles() {
        return this.projectiles;
    }

    @Override
    public void setHealth(int health) {
        // Implement the setHealth logic here
        super.setHealth(health);
    }

    public int getCurrentSpriteIndex() {
        return this.currentSpriteIndex;
    }

    @Override
    public void takeDamage(int damage) {
        if (this.health - damage <= 0) {
            this.health = 0;
            this.die();
        } else {
        this.health -= damage;
        }
    }

    public void update(float deltaTime) {
        if (dead) {
            deathTimer += deltaTime;
            if (deathTimer < 1) {
                // Change to sprite 3 for the first second after death
                currentSpriteIndex = 3;
            } else if (deathTimer < 2) {
                // Change to sprite 4 for the second second after death
                currentSpriteIndex = 4;
            } else {
                this.disapear = true;
                // Remove the BossDrake after 2 seconds
                // You need to implement this part based on how you're managing your game objects
            }
        } else {
        spriteChangeTimer += deltaTime;
        if (spriteChangeTimer >= SPRITE_CHANGE_INTERVAL) {
            // It's time to change the sprite
            updateSpriteIndex();
            spriteChangeTimer = 0;
        }
        }

        // Rest of your update code...
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }


    public void updateSpriteIndex() {
        this.currentSpriteIndex = (this.currentSpriteIndex + 1) % 3;
    }
}