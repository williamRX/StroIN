package com.stroin.game.elements;

public class Platform extends Elements {
    public Platform(String name, int x, int y, int width, int height) {
        super(name, x, y, width, height);
    }

    @Override
    public void move() {
        // Implement platform-specific movement
    }

    @Override
    public void draw() {
        // Implement platform-specific drawing
    }

    @Override
    public void update() {
        // Implement platform-specific updating
    }
}
