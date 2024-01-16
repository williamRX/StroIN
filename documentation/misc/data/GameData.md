# GameData

`GameData` is a class that encapsulates the state of a game, including the player's name, health, position, speed, equipped weapon, inventory, current level, base name, and number of sprites. It implements the `Serializable` interface, allowing instances of the class to be serialized and saved to a file.

The class has getter and setter methods for each of its fields. The `saveGame` method saves the current state of the game to a file, incrementing the file name each time to avoid overwriting previous saves. The `loadGame` method loads a saved game from a file, updating the fields of the current `GameData` instance with the loaded data.