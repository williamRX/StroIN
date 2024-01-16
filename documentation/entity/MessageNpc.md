# MessageNpc

`MessageNpc` is a class that extends the `ClassicNpc` class. It represents a non-player character (NPC) in the game that can display a message. The class includes fields for the message, a flag indicating whether the message is displayed, and a flag indicating whether the NPC is dead.

The `MessageNpc` class includes a constructor that initializes the NPC's name, health, position, speed, sprites, vertical velocity, and message.

The `getMessage`, `setMessage`, `isMessageDisplayed`, and `setMessageDisplayed` methods get and set the message and its display status. The `draw` method draws the NPC's current sprite at its current position. The `getHealth` method returns the NPC's health. The `die` method sets the NPC's dead flag to true. The `isDead` method returns whether the NPC is dead.