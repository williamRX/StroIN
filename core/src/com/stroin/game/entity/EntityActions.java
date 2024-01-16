package com.stroin.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.stroin.game.items.Weapons.Weapons;

public interface EntityActions {
    void move(boolean b, boolean c);
    void attack();
    void die();
    Vector2 getPosition();
    int getSpeed();
    float getVelocity();
    float setVelocityY(float velocityY);
    TextureRegion getCurrentSprite();
    boolean isRunning();
    boolean isJumping();
    void setRunning(boolean isRunning);
    void setJumping(boolean isJumping);
    void setMovingRight(boolean isMovingRight);
    void setMovingLeft(boolean isMovingLeft);
    boolean isMovingRight();
    boolean isMovingLeft();
    Rectangle getBounds();
    int getHealth();
    int getMaxHealth();
    void setHealth(int health);
    void takeDamage(int damage);
    void setDamage(int damage);
    int getDamage();
    void setEquippedWeapon(Weapons weapon);
    Weapons getEquippedWeapon();
}
