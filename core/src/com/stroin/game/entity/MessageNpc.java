package com.stroin.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class MessageNpc extends ClassicNpc {
    private String message;
    private boolean isMessageDisplayed;
    private boolean dead;

    public MessageNpc(String name, int health, Vector2 position, int speed, TextureRegion[] sprites, float velocityY, String message) {
        super(name, health, position, speed, sprites, sprites, velocityY);
        this.message = message;
        this.isMessageDisplayed = false;
        this.dead = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMessageDisplayed() {
        return isMessageDisplayed;
    }

    public void setMessageDisplayed(boolean isMessageDisplayed) {
        this.isMessageDisplayed = isMessageDisplayed;
    }

    public void draw(SpriteBatch batch) {
    batch.draw(getCurrentSprite(), getPosition().x, getPosition().y);
}
    public int getHealth() {
        return this.health;
    }

    public void die() {
        this.dead = true;
    }

    public boolean isDead() {
        return this.dead;
    }
}