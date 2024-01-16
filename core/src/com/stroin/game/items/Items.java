package com.stroin.game.items;

public abstract class Items {
    protected String name;

    public Items() {
    }

    public Items(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
