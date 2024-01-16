package com.stroin.game.items.Inventory;

import java.io.Serializable;

import com.stroin.game.items.Consumables.Consumable;
import com.stroin.game.items.Equipments.Kevlar;

public class Inventory implements Serializable {

    protected Consumable[] consomables = new Consumable[10];
    protected Kevlar equippedArmor; 

    public Inventory() {

    }

    public void addConsumable(Consumable consumable) {
        for (int i = 0; i < consomables.length; i++) {
            if (consomables[i] == null) {
                consomables[i] = consumable;
                break;
            }
        }
    }

    public void removeConsumable(Consumable consumable) {
        for (int i = 0; i < consomables.length; i++) {
            if (consomables[i] == consumable) {
                consomables[i] = null;
                break;
            }
        }
    }

    public Consumable[] getConsomables() {
        return consomables;
    }

public void showInventory() {
    System.out.println("Showing inventory:");

    for (int i = 0; i < consomables.length; i++) {
        if (consomables[i] != null) {
            System.out.println(consomables[i].getName());
        }
    }
}
public void equipArmor(Kevlar armor) {
    if (armor != null && armor.getName().equals("Kevlar")) {
        this.equippedArmor = armor;
        System.out.println("Kevlar armor equipped!");
    } else {
        this.equippedArmor = null;
        System.out.println("Invalid armor. Only Kevlar can be equipped. Armor has been unequipped.");
    }
}

    public Kevlar getEquippedArmor() {
        return equippedArmor;
    }


    
}
