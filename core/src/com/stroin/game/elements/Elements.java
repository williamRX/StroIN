package com.stroin.game.elements;

public abstract class Elements {
    protected String name;
    protected int x;
    protected int y;
    protected int width;
    protected int height;


    public Elements(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        
    }

    public abstract void move();
    public abstract void draw();
    public abstract void update();
}

