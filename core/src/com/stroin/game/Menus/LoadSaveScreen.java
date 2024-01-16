package com.stroin.game.Menus;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.stroin.game.StroIN;
import com.stroin.game.Level.Level0;
import com.stroin.game.Level.Level1;
import com.stroin.game.Level.Level2;
import com.stroin.game.Level.LevelFunctions;
import com.stroin.game.controllers.MovementController;
import com.stroin.game.data.GameData;
import com.stroin.game.entity.Player;

public class LoadSaveScreen implements Screen {

    private Stage stage;
    private StroIN game;
    private GameData gameData = new GameData();

    public LoadSaveScreen(StroIN game) {
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
    
    BitmapFont font = new BitmapFont();
    final TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    buttonStyle.font = font;

    TextButton cancelButton = new TextButton("Cancel", buttonStyle);
    cancelButton.pack();
    cancelButton.setScale(1.2f);
    cancelButton.setSize(cancelButton.getWidth() * cancelButton.getScaleX(), cancelButton.getHeight() * cancelButton.getScaleY());
    cancelButton.setPosition(
        Gdx.graphics.getWidth() / 2 - cancelButton.getWidth() / 2,
        Gdx.graphics.getHeight() / 2 - cancelButton.getHeight() - 10
    );
    stage.addActor(cancelButton);

    cancelButton.addListener(
        new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        }
    );

    File dir = new File(".");
    File[] files = dir.listFiles((d, name) -> name.endsWith(".ser"));
        Arrays.sort(files, (file1, file2) -> {
        Pattern pattern = Pattern.compile("savegame(\\d+)\\.ser");
        Matcher matcher1 = pattern.matcher(file1.getName());
        Matcher matcher2 = pattern.matcher(file2.getName());
        if (matcher1.find() && matcher2.find()) {
            int saveNumber1 = Integer.parseInt(matcher1.group(1));
            int saveNumber2 = Integer.parseInt(matcher2.group(1));
            return Integer.compare(saveNumber2, saveNumber1); // Compare in reverse order
        }
        return 0;
    });

    for (int i = 0; i < files.length; i++) {
        TextButton loadButton = new TextButton(files[i].getName(), buttonStyle);
        loadButton.pack();
        loadButton.setScale(1.2f);
        loadButton.setSize(loadButton.getWidth() * loadButton.getScaleX(), loadButton.getHeight() * loadButton.getScaleY());
        loadButton.setPosition(
            Gdx.graphics.getWidth() / 2 - loadButton.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - loadButton.getHeight() * (i + 2) - 10 * (i + 2)
        );
        stage.addActor(loadButton);

        final File file = files[i];
loadButton.addListener(
    new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            String filename = file.getName();
            Pattern pattern = Pattern.compile("savegame(\\d+)\\.ser");
            Matcher matcher = pattern.matcher(filename);
            if (matcher.find()) {
                int saveNumber = Integer.parseInt(matcher.group(1));
                System.out.println("Loading game number: " + saveNumber); // print the save number
                GameData loadedData = gameData.loadGame(saveNumber);
                Player loadedPlayer = new Player(loadedData.getName(),loadedData.getHealth(), loadedData.getPosition(), loadedData.getSpeed(), new MovementController(), loadedData.getVelocity(), loadedData.getSpriteBaseName(), loadedData.getSpriteBaseName()+"weapon", loadedData.getNumSprites(), loadedData.getInventory(), loadedData.getEquippedWeapon());
                loadedPlayer.setHealth(loadedData.getHealth());
                loadedPlayer.setPosition(loadedData.getPosition());
                // Set the level based on the loaded data
                game.levelManager.setLevelByNumber(loadedData.getLevelNumber());
                game.levelManager.getCurrentLevel().create(loadedPlayer);
                // Set the player state based on the loaded data
                // Set other player state from loadedData...

                game.setScreen(new GameScreen(game));
            }
        }
    }
);
    }
}

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
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }
}
