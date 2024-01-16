# Documentation

Welcome to the documentation for our game project! Below is a summary of the key components of our game engine. For more detailed information, refer to the individual documents.

## Game Engine
- **[Stroin](GameEngine.md):** Core class responsible for [provide a brief description here].
  
- **[Entity](entity/Entity.md):** Core class for each entity (Player, friend, enemy...).
    - **[Player](entity/Player.md):** Class for player.
    - **[EnemyNpc](entity/EnemyNpc.md):** Core class for enemy NPCs.
      - **[Enemy](entity/Enemy.md):** Class for the most basic enemy.
      - **[BabyDrake](entity/BabyDrake.md):** Class for Baby Drake enemy.
      - **[Blaster](entity/Blaster.md):** Class for Blaster enemy.
      - **[BossDrake](entity/BossDrake.md):** Class for Boss Drake enemy.
      - **[Minotaur](entity/Minotaur.md):** Class for Minotaur enemy.
    - **[EntityActions](entity/EntityActions.md):** Interface implemented by the Entity class.
    - **[ClassicNpc](entity/ClassicNpc.md):** Core class for classic NPCs.
      - **[MessageNpc](entity/MessageNpc.md)**
    - **[EntityNpcMovement](entity/EntityNpcMovement.md):** Class responsible for the movement of classic NPCs.

- **[Items](items/Items.md):** Core class for items (equipment, consumables...)
  - **[Consumables](items/Consumables.md):** Core class for consumable items
    - **[Syringe](items/Syringe.md):** A consumable item that regenerates HP
  - **[Equipment](items/Equipment.md):** Core class for equipment items
    - **[Kevlar](items/Kevlar.md):** Class for shield HP (Kevlar)
  - **[Inventory](items/Inventory.md):** Core class for inventory management
  - **[Weapons](items/Weapons.md):** Core class for weapon management
    - **[Chaos](items/Chaos.md):** Class for the Chaos (Gun)
  

- **[Level](levels/Level.md):** Interface for levels and management around it
  - **[Level0](levels/Level0.md):** Class for Level 0
  - **[Level1](levels/Level1.md):** Class for Level 1
  - **[Level2](levels/Level2.md):** Class for Level 2
  - **[Level3](levels/Level3.md):** Class for Level 3
  - **[LevelFunctions](levels/LevelFunctions.md):** Class with static functions called by levels
  - **[LevelManager](levels/LevelManager.md):** Class that manages which level will be instanciated and sent to the screen
- **[Menus](menus/):** Directory for menus
  - **[GameScreen](menus/GameScreen.md):** Class that displays the game on the screen
  - **[LoadSaveScreen](menus/LoadSaveScreen.md):** Menu for loading saves
  - **[MenuScreen](menus/MenuScreen.md):** The screen that appears when we start the game with play, exit, and load saves options
- **[Elements](elements/Elements.md):** Core class that manages elements in the game (platforms, projectiles, etc.)
  - **[BlasterBall](elements/BlasterBall.md):** Class for Blaster Ball element
  - **[BouleDeFeu](elements/BouleDeFeu.md):** Class for Boule De Feu element
  - **[Bullet](elements/Bullet.md):** Class for Bullet element
  - **[DamageElements](elements/DamageElements.md):** Class for Damage Elements
  - **[EnemyBall](elements/EnemyBall.md):** Class for Enemy Ball element
  - **[HorizontalPlatform](elements/HorizontalPlatform.md):** Class for Horizontal Platform element
  - **[MovablePlatform](elements/MovablePlatform.md):** Class for Movable Platform element
  - **[Platform](elements/Platform.md):** Class for Platform element
  - **[SonicBall](elements/SonicBall.md):** Class for Sonic Ball element

- **[Misc](misc/):** All others files
  - **[Controller](misc/controller/):**
    - **[MovementController](misc/controller/MovementController.md):**
    - **[EnemyController](misc/controller/EnemyController.md):**
  - **[Data](misc/data/):**
    - **[GameData](misc/data/GameData.md):** Class to store game data (Like for saves)
