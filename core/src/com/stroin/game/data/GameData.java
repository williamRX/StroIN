package com.stroin.game.data;

import com.badlogic.gdx.math.Vector2;
import com.stroin.game.items.Inventory.Inventory;
import com.stroin.game.items.Weapons.Weapons;

import java.io.*;

public class GameData implements Serializable {
    private String name;
    private int health;
    private Vector2 position;
    private int speed;
    private Weapons equippedWeapon;
    private Inventory inventory;
    private static final long serialVersionUID = 1L;
    private int currentLevel;
    private String baseName;
    private int numSprites;

    // getters
    public String getName() {
        return this.name;
    }
    public String getSpriteBaseName(){
        return this.baseName;
    }
    public int getHealth() {
        return this.health;
    }

    public int getNumSprites() {
        return this.numSprites;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public int getSpeed() {
        return this.speed;
    }

    public Weapons getEquippedWeapon() {
        return this.equippedWeapon;
    }

    public Inventory getInventory() {
        return this.inventory;
    }
    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setEquippedWeapon(Weapons equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void saveGame() {
        int gameNumber = 1;
        File file = new File("savegame" + gameNumber + ".ser");
        while (file.exists()) {
            gameNumber++;
            file = new File("savegame" + gameNumber + ".ser");
        }
    
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public GameData loadGame(int gameNumber) {
        System.out.println("loadGame called with save number: " + gameNumber);
        GameData gameData = null;
        try {
            File saveFile = new File("savegame" + gameNumber + ".ser");
            if (!saveFile.exists()) {
                System.err.println("Save file does not exist");
                return null;
            }
    
            FileInputStream fis = new FileInputStream(saveFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            gameData = (GameData) ois.readObject();
            ois.close();
    
            this.name = gameData.getName();
            this.health = gameData.getHealth();
            this.position = gameData.getPosition();
            this.speed = gameData.getSpeed();
            this.equippedWeapon = gameData.getEquippedWeapon();
            this.inventory = gameData.getInventory();
            this.currentLevel = gameData.getLevelNumber();
            this.baseName = gameData.getSpriteBaseName();
            this.numSprites = gameData.getNumSprites();
            System.out.println(this.currentLevel);
            System.out.println("Loaded game data: " + this.name + ", " + this.health + ", " + this.position + ", " + this.speed + ", " + this.equippedWeapon + ", " + this.inventory);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameData;
    }

    public void setLevelNumber(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    public int getLevelNumber() {
        return this.currentLevel;
    }

    public float getVelocity() {
        return 0;
    }
}