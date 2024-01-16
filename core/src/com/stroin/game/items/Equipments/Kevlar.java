package com.stroin.game.items.Equipments;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.stroin.game.entity.Player;


public class Kevlar extends Equipment implements Serializable {

    private String name;
    private int durability;
    private transient Texture sprite;
    private float width; 
    private float height;
    private Vector2 position;
    private boolean used = false; 

    public Kevlar() {
        this.durability = 50; 
        this.name = "Kevlar";
        this.sprite = new Texture("kevlar.png"); // Replace with your sprite
        this.position = new Vector2();
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.used = false;
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        this.sprite = new Texture("kevlar.png"); // Adjust as needed
    }

    public String getName() {
        return name;
    }
    public int takeDamage(int damage,Player player) {
        if (damage >= durability) {
            int remainingDamage = damage - durability;
            durability = 0;
            
            // Remove the armor if it's destroyed
            if (durability == 0) {
            player.getInventory().equipArmor(null);
            }
            
            return remainingDamage;
        } else {
            durability -= damage;
            return 0;
        }
    }

    public int getDurability() {
        return durability;
    }
    public Texture getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, width * 2, height * 2);
    }

    public void isPickable(Player player) {
        if (player.getBounds().overlaps(this.getBounds())) {
            player.getInventory().equipArmor(this);
            this.used = true; // Mark the Kevlar as used when it's picked up
        }
    }

    public boolean isUsed() {
        return used;
    }

}
    

