package com.stroin.game.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MovablePlatform extends Platform {
    private Texture platformTexture;
    public Vector2 speed;

    public MovablePlatform(String name, int x, int y, int width, int height, float speedX, float speedY) {
        super(name, x, y, width, height);
        this.speed = new Vector2(speedX, speedY);
    }


    public void update(float deltaTime) {
        x += speed.x * deltaTime*10;
        y += speed.y * deltaTime*10;
        
    }

    public float getVelocityX() {
        return this.speed.x;
    }
    
    @Override
    public void move() {
        // This platform doesn't move
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
    public Rectangle getBounds() {
    return new Rectangle(x, y, width, height);
}

    public void setImage(Texture platformTexture) {
        this.platformTexture = platformTexture;
    }

    public Texture getImage() {
        return this.platformTexture;
    }

    public void draw(SpriteBatch batch) {
        if (platformTexture != null) {
            batch.draw(platformTexture, x, y, width, height);
        }
    }
}
