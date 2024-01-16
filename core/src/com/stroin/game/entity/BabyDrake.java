package com.stroin.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BabyDrake extends BossDrake {

    public BabyDrake(String name, int health, Vector2 position, int speed, TextureRegion[] sprites, float velocityY) {
        super(name, health, position, speed, sprites, velocityY);
    }

    // Override any methods from BossDrake if needed...
}