package com.stroin.game.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.stroin.game.elements.BouleDeFeu;

public class Minotaur extends EnemyNpc {
    private float spriteChangeTimer = 0;
    private static final float SPRITE_CHANGE_INTERVAL = 0.2f;
    private String state = "RIGHT";
    private float stateTimer = 0;
    private boolean dead = false;
    private float deathTimer = 0;
    private boolean disapear = false;

    public Minotaur(String name, int health, Vector2 position, int speed, TextureRegion[] sprites, float velocityY) {
        super(name, health, position, speed, sprites, velocityY);
    }


    public Rectangle getBounds() {
    return new Rectangle(this.position.x, this.position.y, this.width*3f, this.height * 1.5f);
}

@Override
public void attack() {
    if (this.state.equals("RIGHT")) {
        rotateSpritesRight();
    } else if (this.state.equals("LEFT")) {
        rotateSpritesLeft();
    }

    // Implement the rest of the attack logic here
}

public void rotateSpritesLeft() {
    this.stateTimer += Gdx.graphics.getDeltaTime();
    if (this.stateTimer >= SPRITE_CHANGE_INTERVAL) {
        this.currentSpriteIndex = ((this.currentSpriteIndex - 11) % 5) + 11;
        if (this.currentSpriteIndex < 11) {
            this.currentSpriteIndex = 11;
        }
        this.stateTimer = 0;
    }
}

public void rotateSpritesRight() {
    this.stateTimer += Gdx.graphics.getDeltaTime();
    if (this.stateTimer >= SPRITE_CHANGE_INTERVAL) {
        this.currentSpriteIndex = ((this.currentSpriteIndex - 3) % 5) + 3;
        if (this.currentSpriteIndex < 3) {
            this.currentSpriteIndex = 3;
        }
        this.stateTimer = 0;
    }
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

    public void moveRight(float delta) {
        if (state.equals("RIGHT")) {
            if (this.position.x > 12635) {
                state.equals("LEFT");
            }
            this.position.x += 5;
            stateTimer += delta;
            if (stateTimer >= 3) {
                state = "LEFT";
                stateTimer = 0;
            }
        }
    }
    
    public void moveLeft(float delta) {
        if (state.equals("LEFT")) {
            if (this.position.x < 10880) {
                state.equals("RIGHT");
            }
            this.position.x -= 5;
            stateTimer += delta;
            if (stateTimer >= 3) {
                state = "RIGHT";
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

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public TextureRegion[] getSprites() {
        return this.sprites;
    }

    @Override
    public void setHealth(int health) {
        // Implement the setHealth logic here
        super.setHealth(health);
    }

    public int getCurrentSpriteIndex() {
        return this.currentSpriteIndex;
    }

    public void setCurrentSpriteIndex(int currentSpriteIndex) {
        this.currentSpriteIndex = currentSpriteIndex;
    }

    public boolean isDead(){
        return this.dead;
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
                currentSpriteIndex = 4;
            } else if (deathTimer < 2) {
                // Change to sprite 4 for the second second after death
                currentSpriteIndex = 16;
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
        if (state.equals("RIGHT")) {
            this.currentSpriteIndex = (this.currentSpriteIndex + 1) % 3;
        } else if (state.equals("LEFT")) {
            this.currentSpriteIndex = ((this.currentSpriteIndex + 1 - 8) % 3) + 8;
            if (this.currentSpriteIndex < 8) {
                this.currentSpriteIndex = 8;
            }
        } else if (state.equals("ATTACKRIGHT")) {
            this.currentSpriteIndex = ((this.currentSpriteIndex + 1 - 3) % 5) + 3;
            if (this.currentSpriteIndex < 3) {
                this.currentSpriteIndex = 3;
            }
        } else if (state.equals("ATTACKLEFT")) {
            this.currentSpriteIndex = ((this.currentSpriteIndex + 1 - 11) % 5) + 11;
            if (this.currentSpriteIndex < 11) {
                this.currentSpriteIndex = 11;
            }
        }
    }
}