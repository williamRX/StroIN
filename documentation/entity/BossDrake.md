# BossDrake

`BossDrake` is a class that extends the `EnemyNpc` class. It represents a boss character in the game named Drake. The class includes fields for sprite change timer, sprite change interval, a list of projectiles, state, state timer, death timer, and boolean flags for death and disappearance.

The `BossDrake` class includes a constructor that initializes the boss's name, health, position, speed, sprites, vertical velocity, and an empty list of projectiles.

The `getBounds` method returns a new `Rectangle` object that represents the boss's bounding box for collision detection. The bounding box's width is the same as the boss's width, and its height is 1.5 times the boss's height.