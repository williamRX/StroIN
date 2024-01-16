package com.stroin.game.items.Weapons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Items;
import java.io.Serializable;


public class Weapons extends Items implements Serializable{
    protected int damage;
    boolean isMelee;
    protected Texture sprite;
    protected boolean used = false;

    public Weapons(String name, int damage , boolean isMelee) {
        super(name);
        this.damage = damage;
        this.isMelee = isMelee;
    }

    public int getDamage() {
        return damage;
    }


    public boolean isMelee() {
        return isMelee;
    }

    public Texture getSprite() {
        return sprite;
    }
}
