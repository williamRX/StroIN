package com.stroin.game.items.Equipments;
import com.badlogic.gdx.graphics.Texture;
import com.stroin.game.items.Items;

public class Equipment extends Items {
    protected int defense;
    protected int durability;
    protected Texture sprite;

    public Equipment(String name, int defense, int durability) {
        super(name);
        this.defense = defense;
        this.durability = durability;

    }
    public Equipment() {
    }

    public int getDefense() {
        return defense;
    }

    public int getDurability() {
        return durability;
    }

    public void use() {
        System.out.println("Equipment used");
    }

    public Texture getSprite() {
        return sprite;
    }
}
