package com.stroin.game.items.Consumables;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.stroin.game.entity.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Seringue extends Consumable implements Serializable {
    private Vector2 position;
    private transient Texture sprite;
    private float width; 
    private float height; 
    private boolean used = false;

    public Seringue(){
        // super("StroIN MKDOC Type 1 ", 10);
        //         this.sprite = new Texture("Seringue.png");
        // this.width = sprite.getWidth(); // Get the width of the sprite
        // this.height = sprite.getHeight();
        // this.player = null;
    }

    public Seringue(Player  player) {
        super("StroIN MKDOC Type 1 ", 25);
        this.player = player;
        this.sprite = new Texture("Seringue.png");
        this.width = sprite.getWidth(); // Get the width of the sprite
        this.height = sprite.getHeight(); // Get the height of the sprite
    }
        private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        this.sprite = new Texture("Seringue.png");
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
            player.getInventory().addConsumable(this);
            this.used = true; // Marquer la seringue comme utilisée lorsqu'elle est ramassée
        } else {
        }
    }

    public boolean use(Player player) {
            player.setHealth(player.getHealth() + getHealthRestore());

            player.getInventory().removeConsumable(this);

            return true;
    }

    public boolean isUsed() {
        return used;
    }
    }
