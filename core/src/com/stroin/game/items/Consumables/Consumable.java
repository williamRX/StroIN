package com.stroin.game.items.Consumables;
import com.badlogic.gdx.graphics.Texture;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Items;

public class Consumable extends Items {
    protected int healthRestore;
    protected Texture sprite;
    protected Player player;

    public Consumable() {
    }

    public Consumable(String name, int healthRestore) {
        super(name);
        this.healthRestore = healthRestore;
    }

    public int getHealthRestore() {
        return healthRestore;
    }

    public Texture getSprite() {
        return sprite;
    }

    public void isPickable(Player player2) {
    }

    public boolean isUsed() {
        return false;
    }
}
