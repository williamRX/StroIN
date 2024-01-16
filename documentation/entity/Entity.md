# Entity

`Entity` is an abstract class that represents an entity in the game. It implements the `EntityActions` interface and includes fields for the entity's name, health, position, speed, movement state, velocity, sprites, dimensions, and previous position.

The class includes a constructor that initializes these fields. The `move` method updates the entity's position based on its speed and whether it's moving right or left. The `savePreviousPosition` and `getPreviousPosition` methods save and return the entity's previous position, respectively.

The `attack`, `die`, `getBounds`, `getVelocity`, `setVelocityY`, `getCurrentSprite`, `getPosition`, `getSpeed`, `takeDamage`, `getMaxHealth`, `isRunning`, `isJumping`, `setRunning`, `setJumping`, `setMovingRight`, `setMovingLeft`, `isMovingRight`, and `isMovingLeft` methods are declared in the `EntityActions` interface and overridden in this class.

The `isTakingDamage` method checks if the entity's health has decreased since the last check, which is performed every second. The `loadSprites` method loads the entity's sprites from image files. The `readObject` method is used for deserialization and loads the entity's sprites.

The `getNumSprites`, `getWidth`, `getHeight`, and `getHealth` methods return the number of sprites, the width and height of the entity, and the entity's health, respectively.