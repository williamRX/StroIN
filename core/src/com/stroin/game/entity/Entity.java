package com.stroin.game.entity;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json.Serializable;


public abstract class Entity implements EntityActions {
    protected String name;
    protected int health;
    protected int maxhealth;
    protected Vector2 position;
    protected int speed;
    protected boolean isMovingLeft;
    protected boolean isRunning;
    protected boolean isMovingRight;
    protected boolean isJumping;
    protected float velocityY;
    protected float velocityX;
    protected transient TextureRegion[] sprites;
    protected transient TextureRegion[] weaponsprites;
    private String spriteBaseName;
    private String weaponSpriteBaseName;
    private int numSprites;
    protected int currentSpriteIndex;
    protected EntityNpcMovement entityNpcMovement;
    protected int width;
    protected int height;
    protected Vector2 previousPosition;
    private int previousHealth;
private long lastHealthCheckTime;

    public Entity(String name, int health, Vector2 position, int speed, TextureRegion[] sprites,TextureRegion[]weaponsprite, float velocityY) {
        this.name = name;
        this.health = health;
        this.maxhealth = health;
        this.position = position;
        this.speed = speed;
        this.sprites = sprites;
        this.velocityY = velocityY;
        this.velocityX = 0;

        if (sprites != null && sprites.length > 0) {
            this.width = sprites[0].getRegionWidth();
            this.height = sprites[0].getRegionHeight();
        }
        if (weaponsprites != null && weaponsprites.length > 0) {
            this.width = weaponsprites[0].getRegionWidth();
            this.height = weaponsprites[0].getRegionHeight();
        }
        this.weaponsprites = weaponsprite;

        this.previousPosition = new Vector2(position);
        this.previousHealth = this.health;
        this.lastHealthCheckTime = System.currentTimeMillis();
        this.weaponSpriteBaseName = "spriteweapon";
        this.spriteBaseName = "sprite";
        this.numSprites = 8;
    }

    @Override
    public void move(boolean isMovingRight, boolean isMovingLeft){
        if (isMovingRight) {
            this.position.x += speed;
        } else if (isMovingLeft) {
            this.position.x -= speed;
        }
    }
    public void savePreviousPosition() {
        previousPosition.set(position);
    }
    public Vector2 getPreviousPosition() {
        return previousPosition;
    }

    @Override
    public abstract void attack();
    @Override
    public Rectangle getBounds() {
    return new Rectangle(position.x, position.y, width, height);
    }
    @Override
    public abstract void die();
    @Override
    public float getVelocity() {
        return this.velocityY;
    }
    @Override
    public float setVelocityY(float velocityY) {
        return this.velocityY = velocityY;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    @Override
    public TextureRegion getCurrentSprite() {
        TextureRegion sprite = entityNpcMovement.getCurrentSprite(this , sprites);
        this.width = sprite.getRegionWidth();
        this.height = sprite.getRegionHeight();
        return sprite;
    }
    @Override
    public Vector2 getPosition() {
        // System.out.println("getPosition, position: " + this.position);
        return this.position;
    }
    @Override
    public int getSpeed() {
        return this.speed;
    }
    @Override
    public void takeDamage(int damage) {
        if (health - damage <= 0){
            this.health = 0;
            this.die();
        } else {
        this.health -= damage;
        }
    }
    
    @Override
    public int getMaxHealth() {
        return this.maxhealth;
    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }
    @Override
    public boolean isJumping() {
        return this.isJumping;
    }
    @Override
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    @Override
    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }
    @Override
    public void setMovingRight(boolean isMovingRight) {
        this.isMovingRight = isMovingRight;
    }
    @Override
    public void setMovingLeft(boolean isMovingLeft) {
        this.isMovingLeft = isMovingLeft;
    }

    @Override
    public boolean isMovingRight() {
        return this.isMovingRight;
    }
    @Override
    public boolean isMovingLeft() {
        return this.isMovingLeft;
    }

    public boolean isTakingDamage() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastHealthCheckTime >= 1000) { // 1000 milliseconds = 1 second
            lastHealthCheckTime = currentTime;
            if (health < previousHealth) {
                previousHealth = health;
                return true;
            } else {
                previousHealth = health;
            }
        }
        return false;
    }
        protected static TextureRegion[] loadSprites(String baseName, int numSprites) {
            System.out.println("loadSprites, baseName: " + baseName + ", numSprites: " + numSprites);
        TextureRegion[] sprites = new TextureRegion[numSprites * 2];
        for (int i = 0; i < numSprites; i++) {
            Texture texture = new Texture(baseName + i + ".png");
            sprites[i] = new TextureRegion(texture);
            sprites[i + numSprites] = new TextureRegion(texture);
            sprites[i + numSprites].flip(true, false);
        }
        return sprites;
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        this.sprites = loadSprites(spriteBaseName, numSprites);
        this.weaponsprites = loadSprites(weaponSpriteBaseName, numSprites);
    }
    public int getNumSprites() {
        return this.numSprites;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getHealth() {
        return this.health;
    }
}
