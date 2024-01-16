package com.stroin.game.items.Weapons;

import java.io.IOException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.stroin.game.entity.Player;

public class Chaos extends Weapons {
    private transient Sprite sprite;
    private Vector2 position; // Position of the weapon
    private float width; // Width of the weapon
    private float height; // Height of the weapon
    private boolean used = false; // Is the weapon used or not

    public Chaos() {
        super("Chaos", 100, false);
        this.sprite = new Sprite(new Texture("Chaos.png"));
        this.width = sprite.getWidth(); 
        this.height = sprite.getHeight(); 
        this.position = new Vector2(0, 0); 
    }

    public Texture getSprite() {
        return sprite.getTexture();
    }

    public void isPickable(Player player) {
        if (player.getBounds().overlaps(this.getBounds())) {
            player.takeWeapon(this);
            this.used = true;
            System.out.println("You picked up a weapon.");
        } else {
            System.out.println("You are not close enough to the item.");
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, width, height);
    }

    public void setPosition(Vector2 vector2) {
        this.position = vector2;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isUsed() {
        return used;
    }
        private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.sprite = new Sprite(new Texture("Chaos.png")); // Initialize sprite after deserialization
    }
}