# ClassicNpc

`ClassicNpc` is a class that extends the `Entity` class. It represents a classic non-player character (NPC) in the game. The class includes a constructor that initializes the classic NPC's name, health, position, speed, sprites, weapon sprite, and vertical velocity.

The `ClassicNpc` class overrides the `Entity` class's `attack`, `die`, `getHealth`, `setHealth`, `setDamage`, `getDamage`, `setEquippedWeapon`, and `getEquippedWeapon` methods. Currently, the `attack` and `die` methods print a message to the console, and the `getHealth`, `setHealth`, `setDamage`, `getDamage`, `setEquippedWeapon`, and `getEquippedWeapon` methods are not yet implemented and throw an `UnsupportedOperationException`.