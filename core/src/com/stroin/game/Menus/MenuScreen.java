package com.stroin.game.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.stroin.game.Level.Level0;
import com.stroin.game.Level.Level1;
import com.stroin.game.Level.Level2;
import com.stroin.game.Level.Level3;
import com.stroin.game.StroIN;

public class MenuScreen implements Screen {

  private Stage stage;
  private StroIN game;
  private TextButton playButton; // Declare playButton here

  public MenuScreen(StroIN game) {
    this.game = game;
  }

  @Override
  public void show() {
    stage = new Stage();
    Gdx.input.setInputProcessor(stage);

    Texture backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
    Image backgroundImage = new Image(backgroundTexture);
    backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    stage.addActor(backgroundImage);
    final Table mainMenuTable = new Table();
    mainMenuTable.setFillParent(true);
    stage.addActor(mainMenuTable);

    final Table levelSelectionTable = new Table();
    levelSelectionTable.setFillParent(true);
    levelSelectionTable.setVisible(false);
    stage.addActor(levelSelectionTable);

    LabelStyle labelStyle = new LabelStyle();
    labelStyle.font = new BitmapFont();
    labelStyle.font.getData().setScale(5, 5);

    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.YELLOW);
    pixmap.fill();
    pixmap.dispose();

    final TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    buttonStyle.font = labelStyle.font;

    playButton = new TextButton("Play", buttonStyle);
    playButton.pack();
    playButton.setScale(1.2f);
    playButton.setSize(
      playButton.getWidth() * playButton.getScaleX(),
      playButton.getHeight() * playButton.getScaleY()
    );
    playButton.getLabel().setAlignment(Align.center);
    final TextButton exitButton = new TextButton("Exit", buttonStyle);
    exitButton.pack();
    exitButton.setScale(1.2f);
    exitButton.setSize(
      exitButton.getWidth() * exitButton.getScaleX(),
      exitButton.getHeight() * exitButton.getScaleY()
    );
    final TextButton loadSaveButton = new TextButton("Load Save", buttonStyle);
    loadSaveButton.pack();
    loadSaveButton.setScale(1.2f);
    loadSaveButton.setSize(
      loadSaveButton.getWidth() * loadSaveButton.getScaleX(),
      loadSaveButton.getHeight() * loadSaveButton.getScaleY()
    );
    mainMenuTable.add(playButton).pad(10);
    mainMenuTable.row();
    mainMenuTable.add(exitButton).pad(10);
    mainMenuTable.row();
    mainMenuTable.add(loadSaveButton).pad(10);
    playButton.addListener(
      new ClickListener() {
        @Override
        public void enter(
          InputEvent event,
          float x,
          float y,
          int pointer,
          Actor fromActor
        ) {
          playButton.setColor(Color.RED); // Change the color to red when the mouse enters the button's area
        }

        @Override
        public void exit(
          InputEvent event,
          float x,
          float y,
          int pointer,
          Actor toActor
        ) {
          playButton.setColor(Color.WHITE); // Change the color back to white when the mouse exits the button's area
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
          mainMenuTable.setVisible(false);
          levelSelectionTable.setVisible(true);
          levelSelectionTable.setTouchable(Touchable.enabled);
          TextButton level0Button = new TextButton("Level 0", buttonStyle);
          TextButton level1Button = new TextButton("Level 1", buttonStyle);
          TextButton level2Button = new TextButton("Level 2", buttonStyle);
          TextButton level3Button = new TextButton("Level 3", buttonStyle);
          // Position the buttons
          levelSelectionTable.add(level0Button).pad(10);
          levelSelectionTable.row();
          levelSelectionTable.add(level1Button).pad(10);
          levelSelectionTable.row();
          levelSelectionTable.add(level2Button).pad(10);
          levelSelectionTable.row();
          levelSelectionTable.add(level3Button).pad(10);

          level0Button.addListener(
            new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                game.levelManager.setLevel(new Level0());
                levelSelectionTable.setTouchable(Touchable.disabled);
                game.setScreen(new GameScreen(game));
                
              }
            }
          );

          level1Button.addListener(
            new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                game.levelManager.setLevel(new Level1());
                levelSelectionTable.setTouchable(Touchable.disabled);
                game.setScreen(new GameScreen(game));
              }
            }
          );

          level2Button.addListener(
            new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                game.levelManager.setLevel(new Level2());
                levelSelectionTable.setTouchable(Touchable.disabled);
                game.setScreen(new GameScreen(game));
              }
            }
          );
          level3Button.addListener(
            new ClickListener() {
              @Override
              public void clicked(InputEvent event, float x, float y) {
                game.levelManager.setLevel(new Level3());
                levelSelectionTable.setTouchable(Touchable.disabled);
                game.setScreen(new GameScreen(game));
              }
            }
          );
          
        }
      }
    );

    exitButton.addListener(
      new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          Gdx.app.exit();
        }
      }
    );

    loadSaveButton.addListener(
      new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          // Switch to the LoadSaveScreen
          game.setScreen(new LoadSaveScreen(game));
          levelSelectionTable.setTouchable(Touchable.disabled);
        }
      }
    );
  }

  ShapeRenderer shapeRenderer = new ShapeRenderer();

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
  }
}
