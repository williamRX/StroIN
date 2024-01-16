# EnemyController

`EnemyController` is a class that manages the movement and behavior of `Enemy` objects in the game. It includes methods for updating the movements of all enemies and individual enemies based on the player's position.

The class has a private `Vector2` field `enemyDirection` for tracking the direction of an enemy's movement, and a private float `agroRange` for determining the range within which an enemy will start to move towards the player.

The `updateEnemyMovements` method updates the movements of all enemies in a list based on the player's position. It loops through the list of enemies and calls the `updateEnemyMovement` method for each one.

The `updateEnemyMovement` method updates the movement of a single enemy based on the player's position. It calculates the distance between the enemy and the player, and if the distance is less than `agroRange`, it determines whether the enemy should move away from the player, move towards the player, or attack the player based on the `stayDistance` and `buffer` values. If the enemy's position is below 0, it adjusts the enemy's position to 0. If the distance is greater than `agroRange`, it checks if the enemy is far from its original position and if so, moves the enemy back to its original position.