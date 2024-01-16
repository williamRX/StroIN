package com.stroin.game;

import com.stroin.game.Level.LevelManager;
import com.stroin.game.Menus.MenuScreen;
import com.badlogic.gdx.Game;

public class StroIN extends Game {
    public LevelManager levelManager;
    private MenuScreen menuScreen;

    @Override
    public void create() {
        levelManager = new LevelManager();
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (levelManager != null) {
            levelManager.dispose();
        }
        super.dispose();
    }
}