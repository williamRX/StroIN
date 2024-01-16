package com.stroin.game.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.stroin.game.items.Weapons.Weapons;

public class ClassicNpc extends Entity {
    public ClassicNpc(String name, int health, Vector2 position, int speed, TextureRegion[] sprites,TextureRegion[]weaponsprite, float velocityY) {
        super(name, health, position, speed, sprites, weaponsprite, velocityY);
        this.entityNpcMovement = new EntityNpcMovement();
    }

    @Override
    public void attack() {
        System.out.println("ClassicNpc attack");
    }

    @Override
    public void die() {
        System.out.println("ClassicNpc die");
    }

    @Override
    public int getHealth() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHealth'");
    }

    @Override
    public void setHealth(int health) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHealth'");
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
