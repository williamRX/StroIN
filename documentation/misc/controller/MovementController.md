# MovementController

`MovementController` is a class that manages the movement of a `Player` object in a game. It handles the player's movement in response to keyboard input, including moving left and right, running, and jumping. 

The class uses several private fields to track the state of the player's movement, including whether the player is moving right or left, jumping, running, the duration of the jump, the jump velocity, the gravity, the last direction the player moved in, and the current sprite index.

The `updateMovement` method updates the movement state based on the current keyboard input. The `jump` methods handle the player's jumping, including checking if the player is on a block or platform and adjusting the player's vertical velocity and position. 

The `updatePlayerPosition` methods update the player's position based on the current movement state and call the `jump` method. The `move` method moves the player left or right based on the current movement state. The `getCurrentSprite` method returns the current sprite for the player based on the movement state.

The `run` method adjusts the player's speed based on whether the player is running and moving right or left. The `getLastDirection`, `isMovingRight`, and `isMovingLeft` methods return the last direction the player moved in, whether the player is moving right, and whether the player is moving left, respectively.