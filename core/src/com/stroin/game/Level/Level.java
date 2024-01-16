package com.stroin.game.Level;

import com.badlogic.gdx.Game;
import com.stroin.game.StroIN;
import com.stroin.game.entity.Player;

public interface Level {
    void create();
    void create(Player loadedPlayer);
    void render();
    void dispose();
    void passGame(StroIN game);
}
