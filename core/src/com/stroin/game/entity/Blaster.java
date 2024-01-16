package com.stroin.game.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.stroin.game.elements.BlasterBall;
import com.stroin.game.elements.BouleDeFeu;

public class Blaster extends EnemyNpc {
    private float spriteChangeTimer = 0;
    private static final float SPRITE_CHANGE_INTERVAL = 0.2f;
    private String state = "RIGHT";
    private float stateTimer = 0;
    private boolean dead = false;
    private float deathTimer = 0;
    private boolean disapear = false;
    private List<BlasterBall> projectiles;
    private boolean finalphase = false;
    private float attackTimer = 0.0f;
    private float attackInterval = 1f;

    

    public Blaster(String name, int health, Vector2 position, int speed, TextureRegion[] sprites, float velocityY) {
        super(name, health, position, speed, sprites, velocityY);
        this.projectiles = new ArrayList<>();
    }


    public Rectangle getBounds() {
    return new Rectangle(this.position.x, this.position.y, this.width, this.height);
}


public void attack(float deltaTime) {
    attackTimer += deltaTime;
        if (this.state.equals("FIRERIGHT")) {
            if (attackTimer >= attackInterval) {
            BlasterBall blasterBall = new BlasterBall(
                "BlasterBall", 
                (int) this.position.x+120, 
                (int) this.position.y+120, 
                20, // width
                20, // height
                50, // speedX
                -3 // speedY
            );
            this.projectiles.add(blasterBall);
            attackTimer = 0.0f;
            }
            this.position.x += 3;
        } else if (this.state.equals("FIRELEFT")) {
            if (attackTimer >= attackInterval) {
            BlasterBall blasterBall = new BlasterBall(
                "BlasterBall", 
                (int) this.position.x, 
                (int) this.position.y+120, 
                20, // width
                20, // height
                -50, // speedX
                -3 // speedY
            );
            this.projectiles.add(blasterBall);
            attackTimer = 0.0f;
            }
            this.position.x -= 3;
        }
        
    }

public void attackShort(float deltaTime) {
    attackTimer += deltaTime;
        if (this.state.equals("SHORTFIRERIGHT")) {
            if (attackTimer >= attackInterval) {
            BlasterBall blasterBall = new BlasterBall(
                "BlasterBall", 
                (int) this.position.x+120, 
                (int) this.position.y+100, 
                20, // width
                20, // height
                50, // speedX
                -20 // speedY
            );
            this.projectiles.add(blasterBall);
            attackTimer = 0.0f;
            }
            this.position.x += 2;
        } else if (this.state.equals("SHORTFIRELEFT")) {
            if (attackTimer >= attackInterval) {
            BlasterBall blasterBall = new BlasterBall(
                "BlasterBall", 
                (int) this.position.x, 
                (int) this.position.y+120, 
                20, // width
                20, // height
                -50, // speedX
                -20 // speedY
            );
            this.projectiles.add(blasterBall);
            attackTimer = 0.0f;
            }
            this.position.x -= 2;
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
            if (this.getPosition().x > 15885) {
                setState("LEFT");
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
            if (this.getPosition().x < 14275) {
                setState("RIGHT");
            }
                
            
            this.position.x -= 5;
            stateTimer += delta;
            if (stateTimer >= 3) {
                state = "RIGHT";
                stateTimer = 0;
            }
        }
    }

    public void flyLeft(float delta) {
        if (state.equals("FLYLEFT")) {
            this.position.x -= 5;
            stateTimer += delta;
            if (stateTimer >= 4) {
                state = "FLYRIGHT";
                stateTimer = 0;
            }
        }
        
    }

    public void flyRight(float delta) {
        if (state.equals("FLYRIGHT")) {
            this.position.x += 5;
            stateTimer += delta;
            if (stateTimer >= 4) {
                state = "FLYLEFT";
                stateTimer = 0;
            }
        }
        
    }

    public void transition(float delta) {
        if (state.equals("TRANSITION")) {
            if (this.getPosition().x < 15775) {
                this.position.x += 5;
                this.setHealth(300);
            } else {
                // Transition is complete, change the state
                setState("TRANSITIONUP");
            }
        }
        if (state.equals("TRANSITIONUP")) {
            if (this.getPosition().y < 580) {
                this.position.y += 5;
                this.setHealth(300);
            } else {
                // Transition is complete, change the state
                setState("FLYLEFT");
                this.finalphase = true;
                stateTimer = 0;
            }
        }
    }

    public boolean getFinalPhase() {
        return this.finalphase;
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
                this.position.y -= 0.5;
                currentSpriteIndex = 12;
            } else if (deathTimer < 2) {
                // Change to sprite 4 for the second second after death
                currentSpriteIndex = 14;
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

    public List<BlasterBall> getProjectiles() {
        return this.projectiles;
    }


    public void updateSpriteIndex() {
        if (state.equals("RIGHT")) {
            this.currentSpriteIndex = (this.currentSpriteIndex + 1) % 3;
        } else if (state.equals("LEFT")) {
            this.currentSpriteIndex = ((this.currentSpriteIndex + 1 - 15) % 3) + 15;
            if (this.currentSpriteIndex < 15) {
                this.currentSpriteIndex = 15;
            }
        } else if (state.equals("ATTACKRIGHT")) {
            this.currentSpriteIndex = ((this.currentSpriteIndex + 1 - 6) % 5) + 6;
            if (this.currentSpriteIndex < 6) {
                this.currentSpriteIndex = 6;
            }
        } else if (state.equals("ATTACKLEFT")) {
            this.currentSpriteIndex = ((this.currentSpriteIndex + 1 - 21) % 5) + 21;
            if (this.currentSpriteIndex < 21) {
                this.currentSpriteIndex = 21;
            }
        } else if (state.equals("FLYLEFT")) {
            this.currentSpriteIndex = 28;
        } else if (state.equals("FLYRIGHT")) {
            this.currentSpriteIndex = 13;
    
        } else if (state.equals("FIRERIGHT") || state.equals("SHORTFIRERIGHT")) {
            this.currentSpriteIndex = 11;
        } else if (state.equals("FIRELEFT") || state.equals("SHORTFIRELEFT")) {
            this.currentSpriteIndex = 26;
        } else if (state.equals("TRANSITION")) {
            this.currentSpriteIndex = (this.currentSpriteIndex + 1) % 3;
        } else if (state.equals("TRANSITIONUP")) {
            this.currentSpriteIndex = 25;
        }

    }
}