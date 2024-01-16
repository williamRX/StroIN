# EntityNpcMovement

`EntityNpcMovement` is a class that handles the movement of non-player characters (NPCs) in the game. It includes fields for gravity, jump velocity, maximum jump duration, jump duration, frame counter, last direction, and current sprite index.

The `updateMovement` method updates the NPC's movement based on the player's position. If the player is close enough to the NPC, the NPC stops moving. If the player is to the right of the NPC, the NPC moves right. If the player is to the left of the NPC, the NPC moves left.

The `jump` method makes the NPC jump if it's jumping and not yet reached the maximum jump duration, or fall if it's not jumping and not yet reached the ground.

The `updateNpcPosition` method updates the NPC's position by updating its movement, making it jump if it's jumping, and moving it right or left if it's moving right or left.

The `getCurrentSprite` method returns the current sprite of the NPC based on its jumping, running, and moving states and direction.