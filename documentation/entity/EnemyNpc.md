# EnemyNpc

`EnemyNpc` is a class that extends the `Entity` class. It represents a non-player character (NPC) enemy in the game. The class includes a constructor that initializes the enemy NPC's name, health, position, speed, sprites, and vertical velocity.

The `EnemyNpc` class overrides the `Entity` class's `attack`, `die`, `getHealth`, `setHealth`, `setDamage`, `getDamage`, `setEquippedWeapon`, and `getEquippedWeapon` methods. Currently, the `attack` and `die` methods print a message to the console, and the `getHealth` and `setHealth` methods get and set the enemy NPC's health, respectively. The `setDamage`, `getDamage`, `setEquippedWeapon`, and `getEquippedWeapon` methods are not yet implemented and throw an `UnsupportedOperationException`.