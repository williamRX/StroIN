package com.stroin.game.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.stroin.game.Level.Level0;
import com.stroin.game.Level.Level1;
import com.stroin.game.Level.Level2;
import com.stroin.game.Level.Level3;
import com.stroin.game.controllers.MovementController;
import com.stroin.game.elements.Bullet;
import com.stroin.game.elements.SonicBall;
import com.stroin.game.items.Consumables.Consumable;
import com.stroin.game.items.Equipments.Kevlar;
import com.stroin.game.items.Inventory.Inventory;
import com.stroin.game.items.Weapons.Weapons;


public class Player extends Entity implements Serializable {
    private MovementController movementController;
    private long lastDamageTime;
    protected Weapons equippedWeapon;
    protected boolean damageTaken = false;
    protected boolean gameOver = false;
    private Inventory inventory;
    private float velocityX;
    private boolean attacking = false;
    private List<SonicBall> projectiles;
    private List<Bullet> Bullet;
    private float attackTimer = 0;
    private static final float ATTACK_DELAY = 0.5f;
    private float sonicBallAttackTimer = 0;
    private Kevlar armor;
    private static final float SONIC_BALL_ATTACK_DELAY = 0.5f;


    public Player(String name, int health, Vector2 position, int speed, MovementController movementController, TextureRegion[] sprites,TextureRegion[] weaponsprites, float velocityY) {
        super(name, health, position, speed,sprites,weaponsprites,velocityY);
        this.movementController = movementController;
        this.velocityX = 0;
        this.inventory = new Inventory();
        this.projectiles = new ArrayList<>();
        this.Bullet = new ArrayList<>();
        this.armor = null;
    }
    public Player(String name, int health, Vector2 position, int speed, MovementController movementController,float velocityY, String spriteBaseName, String weaponSpriteBaseName, int numSprites, Inventory inventory, Weapons equippedWeapon) {
        super("Joueur", health, position, speed, Entity.loadSprites("sprite",8), Entity.loadSprites("spriteweapon", 8), velocityY);
        this.movementController = movementController;
        this.inventory = inventory;
        this.setEquippedWeapon(equippedWeapon);
        this.projectiles = new ArrayList<>();
        System.out.println("Player created with the values : " + name + " " + health + " " + position + " " + speed + " " + movementController + " " + velocityY + " " + spriteBaseName + " " + weaponSpriteBaseName + " " + numSprites);
        this.Bullet = new ArrayList<>();
        this.armor = null;
        
    }

    public boolean hasWeapon() {
        if(this.getEquippedWeapon() == null) {
            return false;
        }else{
            return true;
        }
    }
    @Override
    public void move(boolean b, boolean c) {
        //The player don't move by itself
    };

    public void movePush(float deltaTime){
        if (velocityX > 0) {
            this.position.x += this.velocityX * deltaTime;
        } 
    }

public boolean canAttack() {
    boolean canAttack = attackTimer >= ATTACK_DELAY;
    System.out.println(canAttack);
    return canAttack;
}

    public void resetAttackTimer() {
        attackTimer = 0;
    }

    public boolean canShootSonicBall() {
        return sonicBallAttackTimer >= SONIC_BALL_ATTACK_DELAY;
    }

    public void resetSonicBallAttackTimer() {
        sonicBallAttackTimer = 0;
    }

    @Override
    public void attack() {
        if (this.canShootSonicBall()){
        this.attacking = true;
        System.out.println("Player attacks");
        if (this.isMovingRight) {
        SonicBall sonicBall = new SonicBall(
            "SonicBall", 
            (int) this.position.x+50, 
            (int) this.position.y+45, 
            10, // width
            10, // height
            50, // speedX
            0 // speedY
        );
            this.projectiles.add(sonicBall);
            }
        else if (this.isMovingLeft) {
        SonicBall sonicBall = new SonicBall(
            "SonicBall", 
            (int) this.position.x+40, 
            (int) this.position.y+45,  
            10, // width
            10, // height
            -50, // speedX
            0 // speedY
        );
        this.projectiles.add(sonicBall);
        } else {
            if (this.movementController.getLastDirection().equals("right")) {
                SonicBall sonicBall = new SonicBall(
                    "SonicBall", 
                    (int) this.position.x+50, 
                    (int) this.position.y+45, 
                    10, // width
                    10, // height
                    50, // speedX
                    0 // speedY
                );
                this.projectiles.add(sonicBall);
            } else {
                SonicBall sonicBall = new SonicBall(
                    "SonicBall", 
                    (int) this.position.x+40, 
                    (int) this.position.y+45,  
                    10, // width
                    10, // height
                    -50, // speedX
                    0 // speedY
                );
                this.projectiles.add(sonicBall);
            
            }
        }
        this.resetSonicBallAttackTimer();
    } else {
            System.out.println("You can't attack yet");
    }
    }
public void WeaponAttack() {
    if(this.hasWeapon()){
    Sound Soundgun;
    if (Level0.manager != null) {
        Soundgun = Level0.manager.get("gunShoot.mp3", Sound.class);
    } else if (Level1.manager != null) {
        Soundgun = Level1.manager.get("gunShoot.mp3", Sound.class);
    } else if (Level2.manager != null) {
        Soundgun = Level2.manager.get("gunShoot.mp3", Sound.class);
    } else if (Level3.manager != null) {
        Soundgun = Level3.manager.get("gunShoot.mp3", Sound.class);
    } else {
        throw new RuntimeException("All level managers are null");
    }
    Soundgun.play();
        Sprite bulletSprite = new Sprite(new Texture("bulletDroite.png")); 
        if (this.isMovingRight) {
            Bullet bullet = new Bullet(
                "Bullet", 
                (int) this.position.x+80, 
                (int) this.position.y+45, 
                10, // width
                10, // height
                50, // speedX
                0, // speedY
                bulletSprite
            );
            this.Bullet.add(bullet);
        } else if (this.isMovingLeft) {
            Bullet bullet = new Bullet(
                "Bullet", 
                (int) this.position.x+5, 
                (int) this.position.y+45,  
                10, // width
                10, // height
                -50, // speedX
                0, // speedY
                bulletSprite
            );
            this.Bullet.add(bullet);
        } else {
            if (this.movementController.getLastDirection().equals("right")) {
                Bullet bullet = new Bullet(
                    "Bullet", 
                    (int) this.position.x+80, 
                    (int) this.position.y+45, 
                    10, // width
                    10, // height
                    50, // speedX
                    0, // speedY
                    bulletSprite
                );
                this.Bullet.add(bullet);
            } else {
                Bullet bullet = new Bullet(
                    "Bullet", 
                    (int) this.position.x+5, 
                    (int) this.position.y+45,  
                    10, // width
                    10, // height
                    -50, // speedX
                    0, // speedY
                    bulletSprite
                );
                this.Bullet.add(bullet);
            }
        }
    }else{
        System.out.println("You don't have a weapon");
    }
}

    public void stopAttack() {
            this.attacking = false;
        }

    @Override
    public TextureRegion getCurrentSprite() {
        TextureRegion sprite = movementController.getCurrentSprite(this , sprites);
        this.width = sprite.getRegionWidth();
        this.height = sprite.getRegionHeight();
        if(this.hasWeapon()){
            for (int i = 0; i < weaponsprites.length; i++) {
                if (sprites[i] == sprite) {
                    sprite = weaponsprites[i];
                    break;
                }
            }
    }
                if(this.attacking){
                // If the player is moving right AND attacking , i will put the sprite number 7 , if it's moving left i will put the sprite number 15
                if (this.isMovingRight) {
                    this.currentSpriteIndex = 7;
                    sprite = sprites[currentSpriteIndex]; // Use the 8th sprite in the array when moving right
                } else if (this.isMovingLeft) {
                    this.currentSpriteIndex = 15; // Use the 16th sprite in the array when moving left
                    sprite = sprites[currentSpriteIndex];
                } else {
                    if (this.movementController.getLastDirection().equals("right")) {
                        this.currentSpriteIndex = 7;
                        sprite = sprites[currentSpriteIndex];
                    } else {
                        this.currentSpriteIndex = 15;
                        sprite = sprites[currentSpriteIndex];
                    }
                }
            }
        

        return sprite;
    }
    public List<Bullet> getBullets() {
        return Bullet;
    }
    public List<SonicBall> getProjectiles() {
        return projectiles;
    }

    @Override
    public void die() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }


    @Override
    public int getHealth() {
        return health;
    }

    public void update(float deltaTime) {
        attackTimer += deltaTime;
        sonicBallAttackTimer += deltaTime;

        // Other update logic...
    }



    public void setHealth(int newHealth) {
        if (newHealth > 100) {
            health = 100;
        } else if (newHealth <= 0) {
            health = 0;
            die();
        } else {
            health = newHealth;
        }
    }

    public void useConsumable(Consumable consumable) {
    int healthRestore = consumable.getHealthRestore();
    if(this.getHealth() == 100){
        System.out.println("You are already at full health");}
    else{
    setHealth(getHealth() + healthRestore);
    }
}

@Override
public void takeDamage(int damage) {
    if (TimeUtils.timeSinceNanos(lastDamageTime) > 2000000000) { // 2 seconds in nanoseconds
        Kevlar equippedArmor = inventory.getEquippedArmor();
        if (equippedArmor != null && equippedArmor.getName().equals("Kevlar")) {
            int remainingDamage = equippedArmor.takeDamage(damage,this);
            if (remainingDamage > 0) {
                setEquippedArmor(null);
                setHealth(getHealth() - remainingDamage);
            }
            System.out.println("Kevlar armor took the damage!");
        } else {
            setHealth(getHealth() - damage);
        }
        lastDamageTime = TimeUtils.nanoTime();
    }
}

    public boolean isDamageTaken() {
        return damageTaken;
    }

    public long getLastDamageTime() {
        return lastDamageTime;
    }

    public void setDamageTaken(boolean damageTaken) {
        this.damageTaken = damageTaken;
    }


    @Override
    public void setDamage(int damage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDamage'");
    }


    @Override
    public int getDamage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDamage'");
    }


    @Override
    public void setEquippedWeapon(Weapons weapon) {
        this.equippedWeapon = weapon;
    }


    @Override
    public Weapons getEquippedWeapon() {
        return equippedWeapon;
    }

    public Inventory getInventory() {
        return inventory;
    }
    public void showInventory() {
         this.inventory.showInventory();
    }


    public void setPosition(Vector2 position) {
        this.position = position;
    }
    public void takeWeapon(Weapons weapon) {
        this.setEquippedWeapon(weapon);
    }
    public Kevlar getEquippedArmor() {
        return this.inventory.getEquippedArmor();
    }
    public void equipArmor(Kevlar armor) {
        if (armor.getName().equals("Kevlar")) {
            this.setEquippedArmor(armor);
            System.out.println("Kevlar armor equipped!");
        } else {
            System.out.println("Invalid armor. Only Kevlar can be equipped.");
        }
    }
    private void setEquippedArmor(Kevlar armor) {
        this.armor = armor;
    }
public boolean isArmorEquipped() {
    if (this.getInventory().getEquippedArmor() != null) {
        return true; // Armor is equipped
    } else {
        return false; // Armor is not equipped
    }
}
}