# StroIN Game

StroIN is a 2D platformer game developed using Java and LibGDX framework.

## Table of Contents
- [Introduction](#introduction)
- [Documentation](#documentation)
- [Features](#features)
- [Getting Started](#getting-started)
- [Game Structure](#game-structure)
- [Classes and Components](#classes-and-components)
- [How to Play](#how-to-play)
- [Contributing](#contributing)
- [License](#license)

## Introduction

StroIN is a platformer game built with Java and LibGDX, featuring various levels, characters, and challenges. This README provides an overview of the game structure, components, and how to get started.

## Documentation
The documentation of each class is available [here](documentation/).

## Features

- Multiple game screens (Menu, Load/Save, Game)
- Diverse levels with different challenges
- Player character with various movements and attacks
- Dynamic enemy NPCs with different behaviors
- Inventory system with consumables

## Prerequisites

Before you begin, ensure you have met the following requirement:

- Java 1.8 (or later) installed on your system(java-17-openjdk is preffered).

## Getting Started

To run the game locally, follow these steps:

1. Clone the repository: `git clone https://github.com/EpitechMscProPromo2026/T-JAV-501-LIL_13.git`
2. Open the project in your preferred Java IDE.
3. Create a `config.properties` file in the `assets/` directory with your screen resolution settings:

    ```plaintext
    # config.properties
    height=1440
    width=2560
    ```

   Adjust the `height` and `width` values to match your desired screen resolution.

4. Build and run the project with Gradle.

These steps ensure that you have the necessary files and settings to run the game with your specified screen resolution. Enjoy playing StroIN!


## Game Structure

The game is structured into different screens and classes. Here's an overview:

- **StroIN:** Main class representing the game instance.
- **GameScreen:** Class handling the main game screen.
- **MenuScreen:** Class for the menu screen.
- **LoadSaveScreen:** Class for loading and saving the game.
- **LevelManager:** Manages different game levels.
- **Level(0-3):** Sample level class with specific features.
- **LevelFunctions:** Static utility class for handling various game functions.

## Classes and Components

### Player
The `Player` class represents the main character controlled by the player. It includes functionality for movement, jumping, and attacking.

### Enemies
- **Minotaur:** Represents a strong and aggressive enemy with melee attacks.
- **BossDrake:** A boss-level enemy with unique abilities and higher difficulty.
- **Blaster:** Ranged enemy with projectile attacks.
- **BabyDrake:** Weaker enemy with basic movement and attack patterns.

### Projectiles
- **SonicBall:** High-speed projectile used by the player for ranged attacks.
- **Bullet:** Standard bullet fired by the Blaster enemy.
- **BouleDeFeu:** Fireball projectile used by certain enemies.
- **EnemyBall:** Projectile used by enemies for various attack strategies.

### Platforms
- **MovablePlatform:** A platform that can be moved by the player or other in-game events.
- **HorizontalPlatform:** Static horizontal platform for player navigation.

### Items and Weapons
- **Items:** General class for in-game items that the player can collect.
- **Consumables** Item that the player can use.
- **Syringe** A consumable to heal HP.
- **Weapons:** Represents various weapons that the player can use for attacks.
- **Chaos:** A weapon (Gun).

      

## How to Play

1. Start the game and navigate through the menu screen.
2. Click on play and then Level0 to have a classic gameplay (You will be able to load your saves from load saves on the menu).
3. Control the player character using the specified keys.
4. Explore the levels, defeat enemies, and accomplish objectives.
5. Use the inventory system to manage consumables and equipment.
## Credits

This project was a collaborative effort by the following contributors:

- **Jean-EC**
  - GitHub: [Jean-EC](https://github.com/Jean-EC)

- **BenyahiaM**
  - GitHub: [BenyahiaM](https://github.com/BenyahiaM)

- **williamRX**
  - GitHub: [williamRX](https://github.com/williamRX)