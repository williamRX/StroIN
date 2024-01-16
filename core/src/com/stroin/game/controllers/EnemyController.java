package com.stroin.game.controllers;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.stroin.game.entity.Enemy;
import com.stroin.game.entity.Player;

public class EnemyController {
    private Vector2 enemyDirection = new Vector2();
    private float agroRange = 1000f; 

    public void updateEnemyMovements(List<Enemy> enemies, Player player) {
        for (Enemy enemy : enemies) {
            updateEnemyMovement(enemy, player);
        }
    }
    
    public void updateEnemyMovement(Enemy enemy, Player player) {
        Vector2 enemyPos = enemy.getPosition().cpy();
        Vector2 playerPos = player.getPosition().cpy();

        float distance = enemyPos.dst(playerPos);

        float stayDistance = 300;
        float buffer = 10;

        if (distance < agroRange) {
            if (Math.abs(playerPos.x - enemyPos.x) < stayDistance - buffer) {
                // Move away from the player
                enemyDirection.set(enemyPos.x - playerPos.x, enemyPos.y - playerPos.y).nor();
                enemyPos.x += enemyDirection.x * 7; // Replace 2 with the desired enemy speed
            } else if (Math.abs(playerPos.x - enemyPos.x) > stayDistance + buffer) {
                // Move towards the player
                enemyDirection.set(playerPos.x - enemyPos.x, playerPos.y - enemyPos.y).nor();
                enemyPos.x += enemyDirection.x * 2; // Replace 2 with the desired enemy speed
                enemyPos.y += enemyDirection.y * 2; // Replace 2 with the desired enemy speed
            } else {
                enemy.attack(player);
                System.out.println("Enemy attack");
            }

            if (enemyPos.y < 0) {
                enemy.setPosition(enemyPos.x, 0); // Adjust position if it's below 0
            } else {
                enemy.setPosition(enemyPos.x, enemyPos.y);
            }
        } else {
            if (Math.abs(enemy.getOriginalPosition().x - enemyPos.x) > buffer) {
                // Return to original position
                enemyDirection.set(enemy.getOriginalPosition().x - enemyPos.x, enemy.getOriginalPosition().y - enemyPos.y).nor();
                enemyPos.x += enemyDirection.x * 7; // Replace 2 with the desired enemy speed
                enemyPos.y += enemyDirection.y * 7; // Replace 2 with the desired enemy speed

                enemy.setPosition(enemyPos.x, enemyPos.y);
            }
        }
    }

}