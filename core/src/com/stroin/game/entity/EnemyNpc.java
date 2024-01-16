package com.stroin.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.stroin.game.items.Weapons.Weapons;

public class EnemyNpc extends Entity {
    public EnemyNpc(String name, int health, Vector2 position, int speed, TextureRegion[] sprites, float velocityY) {
        super(name, health, position, speed, sprites, sprites, velocityY); //PROBLEME POUR SPRITE ARME ALERT 
        this.entityNpcMovement = new EntityNpcMovement();
    }

    @Override
    public void attack() {
        System.out.println("EnemyNpc attack");
        // Implement the attack logic here
    }

    @Override
    public void die() {
        System.out.println("EnemyNpc die");
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEquippedWeapon'");
    }

    @Override
    public Weapons getEquippedWeapon() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEquippedWeapon'");
    }
}