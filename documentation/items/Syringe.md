# Syringe

`Syringe` is a subclass of `Consumable` that represents a health-restoring item in the game. It's initialized with a player and has a position, sprite, dimensions, and a `used` flag. The `getSprite` and `setPosition` methods manage its texture and position. The `getBounds` method returns its bounding box. The `isPickable` method checks if the player can pick up the syringe, and marks it as used if picked up. The `use` method increases the player's health and removes the syringe from the inventory. The `isUsed` method returns the `used` flag.