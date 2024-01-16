package com.stroin.game.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.stroin.game.StroIN; // Import your game class

public class GameScreen implements Screen {

  private StroIN game; // Add a reference to the game

  public GameScreen(StroIN game) {
    this.game = game; // Initialize the game
  }
  public StroIN getGame() {
      return game;
  }
  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Render the current level
    if (game.levelManager.getCurrentLevel() != null) {
      game.levelManager.getCurrentLevel().passGame(this.game);
      game.levelManager.render();
    }
  }

  // Implement other methods from the Screen interface
  @Override
  public void show() {}

  @Override
  public void resize(int width, int height) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {}
}