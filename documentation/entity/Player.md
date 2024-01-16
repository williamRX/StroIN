# Player

The `Player` class extends the `Entity` class and implements the `Serializable` interface. It represents the player character in the game. The class includes fields for the player's movement controller, equipped weapon, inventory, velocity, attack timers, armor, and lists of projectiles and bullets.

The class has two constructors, one for creating a new player with default values and another for creating a player with specific values.

The `Player` class includes methods for moving the player, attacking, shooting sonic balls, and using consumables. It also includes methods for getting and setting the player's health, equipped weapon, and inventory.

The `Player` class overrides the `Entity` class's `move`, `attack`, `die`, `getHealth`, `takeDamage`, `setDamage`, and `getDamage` methods. It also includes additional methods for managing the player's state, such as `isGameOver`, `update`, `useConsumable`, `showInventory`, `takeWeapon`, and `equipArmor`.