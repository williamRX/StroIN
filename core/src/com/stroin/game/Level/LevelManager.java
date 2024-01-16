package com.stroin.game.Level;

import com.badlogic.gdx.Game;

public class LevelManager {
    private Level currentLevel;
    


    public void setLevelByNumber(int levelNumber) {
        switch (levelNumber) {
            case 0:
                this.setLevel(new Level0());
                break;
            case 1:
                this.setLevel(new Level1());
                break;
            case 2:
                this.setLevel(new Level2());
                break;
            case 3:
                this.setLevel(new Level3());
                break;
            // Add more cases as needed...
            default:
                System.err.println("Invalid level number: " + levelNumber);
                break;
        }
    }

    public void setLevel(Level level) {
        if (currentLevel != null) {
            currentLevel.dispose();
        }
        currentLevel = level;
        currentLevel.create();
    }

    public void render() {
        currentLevel.render();
    }

    public void dispose() {
        if (currentLevel != null) {
            currentLevel.dispose();
        }
    }
        public Level getCurrentLevel() {
        return currentLevel;
    }
}
