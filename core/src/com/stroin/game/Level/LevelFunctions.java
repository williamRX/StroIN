package com.stroin.game.Level;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.stroin.game.StroIN;
import com.stroin.game.Menus.GameScreen;
import com.stroin.game.controllers.MovementController;
import com.stroin.game.data.GameData;
import com.stroin.game.elements.BlasterBall;
import com.stroin.game.elements.BouleDeFeu;
import com.stroin.game.elements.Bullet;
import com.stroin.game.elements.EnemyBall;
import com.stroin.game.elements.HorizontalPlatform;
import com.stroin.game.elements.MovablePlatform;
import com.stroin.game.elements.SonicBall;
import com.stroin.game.entity.Blaster;
import com.stroin.game.entity.BossDrake;
import com.stroin.game.entity.Enemy;
import com.stroin.game.entity.Entity;
import com.stroin.game.entity.EntityNpcMovement;
import com.stroin.game.entity.MessageNpc;
import com.stroin.game.entity.Minotaur;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Consumables.Consumable;
import com.stroin.game.items.Consumables.Seringue;
import com.stroin.game.items.Equipments.Kevlar;
import com.stroin.game.items.Weapons.Chaos;


public class LevelFunctions {

private static boolean showInventory = false;
private StroIN game;

public void setGame(StroIN game) {
  this.game = game;
}
public static void toggleInventory(Stage inventoryStage, Player player, Camera camera) {
    float cameraX = camera.position.x - camera.viewportWidth / 2;
    float cameraY = camera.position.y - camera.viewportHeight / 2;

    if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
        showInventory = !showInventory;
        inventoryStage.clear(); // Clear the inventory stage

    if (showInventory) {
        Table table = new Table();

        // Load the common sprite
        Texture commonSprite = new Texture("Seringue.png");

        for (Consumable consumable : player.getInventory().getConsomables()) {
            if (consumable != null) {
                Image itemImage = new Image(commonSprite);
                itemImage.setSize(50, 50); // Adjust the size as needed
                table.add(itemImage).pad(10); // Add padding
            }
        }

            table.pack(); // Update the table's dimensions
            // Remove this line, or use a proper Drawable if needed
            // table.setBackground((Drawable) null);

            // Set the table position to the top right
            float tableX = cameraX + camera.viewportWidth - table.getWidth();
            float tableY = cameraY + camera.viewportHeight - table.getHeight();
            table.setPosition(tableX, tableY);

            inventoryStage.getViewport().setCamera(camera);
            inventoryStage.addActor(table);
            Gdx.input.setInputProcessor(inventoryStage);
        } else {
            Gdx.input.setInputProcessor(null); // Reset the input processor when the inventory is not shown
        }
    }

    if (showInventory) {
        updateInventoryPosition(inventoryStage, player, cameraX, cameraY);
        inventoryStage.act();
        inventoryStage.draw();
    }
}

private static void updateInventoryPosition(Stage inventoryStage, Player player, float cameraX, float cameraY) {
    if (inventoryStage.getActors().size == 0) {
        return;
    }
    Table table = (Table) inventoryStage.getActors().first();

    if (table != null) {
        table.setPosition(cameraX, cameraY);
    }
}
public static void renderBullet(SpriteBatch batch, List<Bullet> bullets) {
    for (Bullet bullet : bullets) {
        bullet.update(Gdx.graphics.getDeltaTime());
        bullet.draw(batch);

    }
}
public static void renderEnemyBall(SpriteBatch batch, OrthographicCamera camera, List<EnemyBall> list) {
  for (EnemyBall ball : list) {
      ball.update(Gdx.graphics.getDeltaTime());
      ball.draw(batch);
  }
}
public static void renderEnemyBalls(SpriteBatch batch, OrthographicCamera camera, List<Enemy> enemies) {
    for (Enemy enemy : enemies) {
        for (EnemyBall ball : enemy.getBalls()) {
            ball.update(Gdx.graphics.getDeltaTime());
            ball.draw(batch);
        }
    }
}

public static boolean checkLevelCompletion(Player player, boolean levelCompleted, LevelFunctions levelFunctions, StroIN game, GameData gameData) {
  if (levelCompleted == true) {
      return true;
  } else {
      return false;
  }
}

public static void displaySuccessScreen(StroIN game, LevelFunctions levelFunctions) {
  Stage stage = new Stage();
  Gdx.input.setInputProcessor(stage);

  Label.LabelStyle fontStyle = new Label.LabelStyle(new BitmapFont(), Color.YELLOW);
  Label levelCompleteLabel = new Label("Level Completed !", fontStyle);
  levelCompleteLabel.setFontScale(8);

  // Calculate the width and height of the label
  float labelWidth = levelCompleteLabel.getWidth() * levelCompleteLabel.getFontScaleX();
  float labelHeight = levelCompleteLabel.getHeight() * levelCompleteLabel.getFontScaleY();

  // Center the label
  levelCompleteLabel.setPosition((Gdx.graphics.getWidth() - labelWidth) / 2, (Gdx.graphics.getHeight() - labelHeight) / 2);

  stage.addActor(levelCompleteLabel);

  TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
  textButtonStyle.font = new BitmapFont();
  textButtonStyle.fontColor = Color.WHITE;

  TextButton mainMenuButton = new TextButton("Next Level in 3s !", textButtonStyle);
  mainMenuButton.getLabel().setFontScale(4); // Increase the font scale


  TextButton.TextButtonStyle nextLevelButtonStyle = new TextButton.TextButtonStyle();
  nextLevelButtonStyle.font = new BitmapFont();
  nextLevelButtonStyle.fontColor = Color.RED;
  
  TextButton nextLevel = new TextButton("Stay Determined !", nextLevelButtonStyle);
  nextLevel.getLabel().setFontScale(4); // Increase the font scale

  Table table = new Table();
  table.setFillParent(true);
  table.bottom(); // Align the table at the bottom of the screen
  table.padBottom(100); // Add padding at the bottom to move the table up
  table.add(mainMenuButton).pad(10).width(400).height(100); // Set the size of the button manually
  table.row();
  table.add(nextLevel).pad(10).width(400).height(100); // Set the size of the button manually

  stage.addActor(table);

  stage.act();
  stage.draw();
}

public static void displaySuccessScreen2(StroIN game, LevelFunctions levelFunctions) {
  Stage stage = new Stage();
  Gdx.input.setInputProcessor(stage);

  Label.LabelStyle fontStyle = new Label.LabelStyle(new BitmapFont(), Color.YELLOW);
  Label levelCompleteLabel = new Label("StroIN Done !", fontStyle);
  levelCompleteLabel.setFontScale(8);

  // Calculate the width and height of the label
  float labelWidth = levelCompleteLabel.getWidth() * levelCompleteLabel.getFontScaleX();
  float labelHeight = levelCompleteLabel.getHeight() * levelCompleteLabel.getFontScaleY();

  // Center the label
  levelCompleteLabel.setPosition((Gdx.graphics.getWidth() - labelWidth) / 2, (Gdx.graphics.getHeight() - labelHeight) / 2);

  stage.addActor(levelCompleteLabel);

  TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
  textButtonStyle.font = new BitmapFont();
  textButtonStyle.fontColor = Color.WHITE;

  TextButton mainMenuButton = new TextButton("Super Sonic, Congratulations !", textButtonStyle);
  mainMenuButton.getLabel().setFontScale(4); // Increase the font scale


  TextButton.TextButtonStyle nextLevelButtonStyle = new TextButton.TextButtonStyle();
  nextLevelButtonStyle.font = new BitmapFont();
  nextLevelButtonStyle.fontColor = Color.RED;
  
  TextButton nextLevel = new TextButton("Don't forget : Stay Determined !", nextLevelButtonStyle);
  nextLevel.getLabel().setFontScale(4); // Increase the font scale

  Table table = new Table();
  table.setFillParent(true);
  table.bottom(); // Align the table at the bottom of the screen
  table.padBottom(100); // Add padding at the bottom to move the table up
  table.add(mainMenuButton).pad(10).width(400).height(100); // Set the size of the button manually
  table.row();
  table.add(nextLevel).pad(10).width(400).height(100); // Set the size of the button manually

  stage.addActor(table);

  stage.act();
  stage.draw();
}

public static void handleKeyPresses(List<Object> items, Player player) {
    if (Gdx.input.isKeyPressed(Keys.E)) {
        for (Object item : items) {
            if (item instanceof Seringue && !((Seringue) item).isUsed()) {
                ((Seringue) item).isPickable(player);
            } else if (item instanceof Kevlar && !((Kevlar) item).isUsed()) {
                ((Kevlar) item).isPickable(player);
            }
        }
    } else if (Gdx.input.isKeyJustPressed(Keys.A)) {
        useFirstAvailableSeringue(player);
    } else if (Gdx.input.isKeyPressed(Keys.SPACE)) {
        player.showInventory();
    }
}
public static void saveGameIfKeyPressed(GameData gameData) {
  if (Gdx.input.isKeyJustPressed(Keys.O)) {
      gameData.saveGame();
  }
}

public static void saveGameIfLevelCompleted(GameData gameData, boolean levelCompleted) {
  if (levelCompleted) {
      int newLevel =gameData.getLevelNumber() + 1;
      gameData.setLevelNumber(newLevel);
      gameData.setHealth(100);
      gameData.setEquippedWeapon(null);
      gameData.setPosition(new Vector2(0, 0));
      gameData.saveGame();
  }
}
public static void saveGameAtm(GameData gameData) {
      gameData.getLevelNumber();
      gameData.setHealth(100);
      gameData.setEquippedWeapon(null);
      gameData.setPosition(new Vector2(0, 0));
  gameData.saveGame();
}
public void loadLastSaveAndStartGame() {
  // Load the last save
  File savesDir = new File(".");
  File[] saves = savesDir.listFiles((d, name) -> name.endsWith(".ser"));
  File lastModifiedFile = Arrays.stream(saves)
      .filter(File::isFile)
      .max(Comparator.comparingLong(File::lastModified))
      .orElse(null);

  if (lastModifiedFile != null) {
      String filename = lastModifiedFile.getName();
      Pattern pattern = Pattern.compile("savegame(\\d+)\\.ser");
      Matcher matcher = pattern.matcher(filename);
      if (matcher.find()) {
          int saveNumber = Integer.parseInt(matcher.group(1));
          System.out.println("Loading game number: " + saveNumber); // print the save number
          GameData gameData = new GameData();  // Get or create a GameData instance
          GameData loadedData = gameData.loadGame(saveNumber);
          Player loadedPlayer = new Player(
              loadedData.getName(),
              loadedData.getHealth(),
              loadedData.getPosition(),
              loadedData.getSpeed(),
              new MovementController(),
              loadedData.getVelocity(),
              loadedData.getSpriteBaseName(),
              loadedData.getSpriteBaseName() + "weapon",
              loadedData.getNumSprites(),
              loadedData.getInventory(),
              loadedData.getEquippedWeapon()
          );
          loadedPlayer.setHealth(loadedData.getHealth());
          loadedPlayer.setPosition(loadedData.getPosition());
          // Set the level based on the loaded data
          game.levelManager.setLevelByNumber(loadedData.getLevelNumber());
          game.levelManager.getCurrentLevel().create(loadedPlayer);
          // Set other player state from loadedData...

          game.setScreen(new GameScreen(game));
      }
  } else {
      // Handle error: no valid save file found
  }
}


private static void useFirstAvailableSeringue(Player player) {
    for (Consumable consumable : player.getInventory().getConsomables()) {
        if (consumable instanceof Seringue) {
            ((Seringue) consumable).use(player);
                System.out.println("You used a seringue from your inventory.");
                break;
        }
    }

    System.out.println("You don't have a seringue in your inventory.");
}

public static void spawnMovablePlatform(int platformWidth, int platformHeight, List<MovablePlatform> platforms, Texture platformTexture, Camera camera) {
  Random random = new Random();
  int x = 2496;
  int y = random.nextInt(2200 - platformHeight); // Ensure the platform is fully visible

  // Adjust the spawning point if there's a platform too close
  for (MovablePlatform platform : platforms) {
      float distance = Math.abs(platform.getX() - x) + Math.abs(platform.getY() - y);
      if (distance < platformWidth * 2) { // Adjust this value to change the minimum distance between platforms
          System.out.println("A platform is too close to the spawning point. Adjusting the spawning point.");
          if (y < 1200) {
              y += platformHeight * 2; // Move the spawning point up
          } else {
              y -= platformHeight * 2; // Move the spawning point down
          }
          break;
      }
  }

  System.out.println("Spawned platform at " + x + ", " + y);
  float speedX = -20; // Adjust this value to change the speed of the platforms
  float speedY = 0; // Adjust this value to change the vertical speed of the platforms
  MovablePlatform platform = new MovablePlatform("MovablePlatform", x, y, platformWidth, platformHeight, speedX, speedY);
  platforms.add(platform);
  platform.setImage(platformTexture);
}

public static void spawnBouleDeFeu(int platformWidth, int platformHeight, List<BouleDeFeu> platforms, Texture platformTexture, Camera camera) {
  Random random = new Random();
  int x = 2496;
  int y = random.nextInt(Gdx.graphics.getHeight() - platformHeight); // Ensure the platform is fully visible

  // Adjust the spawning point if there's a platform too close
  for (BouleDeFeu platform : platforms) {
      float distance = Math.abs(platform.getX() - x) + Math.abs(platform.getY() - y);
      if (distance < platformWidth * 2) { // Adjust this value to change the minimum distance between platforms
          System.out.println("A platform is too close to the spawning point. Adjusting the spawning point.");
          y += platformHeight * 2; // Move the spawning point up
          break;
      }
  }

  System.out.println("Spawned platform at " + x + ", " + y);
  float speedX = -20; // Adjust this value to change the speed of the platforms
  float speedY = 0; // Adjust this value to change the vertical speed of the platforms
  BouleDeFeu platform = new BouleDeFeu("BouleDeFeu", x, y, platformWidth, platformHeight, speedX, speedY);
  platforms.add(platform);
  platform.setImage(platformTexture);
}

public static void displayMessage(String message, MessageNpc messageNpc, Stage stage) {
  Label.LabelStyle fontStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);
  Label messageLabel = new Label(message, fontStyle);
  messageLabel.setFontScale(2); // Increase the font scale
  //float labelWidth = messageLabel.getWidth() * messageLabel.getFontScaleX();
  //float labelHeight = messageLabel.getHeight() * messageLabel.getFontScaleY();
  messageLabel.setPosition(messageNpc.getPosition().x+100, messageNpc.getPosition().y+70);
  stage.addActor(messageLabel);
}

  public static void preventFallOffScreen(Entity... entities) {
    for (Entity entity : entities) {
      if (entity.getPosition().y < 0) {
        entity.getPosition().y = 0;
      }
    }
  }

  public static void updateCamera(Camera camera, Entity player, TiledMap map) {
    float mapWidth = map.getProperties().get("width", Integer.class) * (Integer)map.getProperties().get("tilewidth", Integer.class);
    float mapHeight = map.getProperties().get("height", Integer.class) * (Integer)map.getProperties().get("tileheight", Integer.class);

    float cameraX = Math.min(mapWidth - camera.viewportWidth / 2, Math.max(player.getPosition().x, camera.viewportWidth / 2));
    float cameraY = Math.min(mapHeight - camera.viewportHeight / 2, Math.max(player.getPosition().y, camera.viewportHeight / 2));

    camera.position.set(cameraX, cameraY, 0);
    camera.update();
}

  public static void renderMap(
    SpriteBatch batch,
    OrthographicCamera camera,
    OrthogonalTiledMapRenderer mapRenderer
  ) {
    batch.setProjectionMatrix(camera.combined);
    mapRenderer.setView(camera);
    mapRenderer.render();
  }


  public static void handleDamageLayer(
    TiledMapTileLayer damageLayer,
    Entity player
  ) {
    float offset = 5;
    for (int y = 0; y < damageLayer.getHeight(); y++) {
      for (int x = 0; x < damageLayer.getWidth(); x++) {
        TiledMapTileLayer.Cell cell = damageLayer.getCell(x, y);
        if (cell != null) {
          Rectangle tileBounds = new Rectangle(
            x * damageLayer.getTileWidth() + offset,
            y * damageLayer.getTileHeight() + offset,
            damageLayer.getTileWidth()-2 * offset,
            damageLayer.getTileHeight()- 2 * offset
          );
          if (player.getBounds().overlaps(tileBounds)) {
            player.takeDamage(30);
          }
        }
      }
    }
  }

  public static void createBouleDeFeu(List<BouleDeFeu> boulesDeFeu) {
      BouleDeFeu bouleDeFeu = new BouleDeFeu(
        "BouleDeFeu", 
         6775, 
         350, 
        150, // width
        100, // height
        0, // speedX
        -20 // speedY
    );
        boulesDeFeu.add(bouleDeFeu);
        
    }

      public static void setupGameOverScreen(boolean gameOverScreenSetUp) {
        Stage stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Create and position the "Game Over" label
        Label.LabelStyle fontStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label gameOverLabel = new Label("Game Over", fontStyle);
        gameOverLabel.setFontScale(5); // Increase the font scale
        float labelWidth = gameOverLabel.getWidth() * gameOverLabel.getFontScaleX();
        float labelHeight = gameOverLabel.getHeight() * gameOverLabel.getFontScaleY();
        gameOverLabel.setPosition((Gdx.graphics.getWidth() - labelWidth) / 2, (Gdx.graphics.getHeight() - labelHeight) / 2);
        stage.addActor(gameOverLabel);

        // Create and position the "Retry" and "Main Menu" buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.fontColor = Color.WHITE;

        TextButton retryButton = new TextButton("Retry in 3s", textButtonStyle);
        retryButton.getLabel().setFontScale(3);
        retryButton.setPosition((Gdx.graphics.getWidth() - retryButton.getWidth()) / 2, gameOverLabel.getY() - retryButton.getHeight() - 50);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Code to restart the game
            }
        });
        stage.addActor(retryButton);

        TextButton.TextButtonStyle redTextButtonStyle = new TextButton.TextButtonStyle();
        redTextButtonStyle.font = new BitmapFont();
        redTextButtonStyle.fontColor = Color.RED;
        
        TextButton mainMenuButton = new TextButton("Stay Determined !", redTextButtonStyle);
        mainMenuButton.getLabel().setFontScale(3);
        mainMenuButton.setPosition((Gdx.graphics.getWidth() - mainMenuButton.getWidth()) / 2, retryButton.getY() - mainMenuButton.getHeight() - 50);
        mainMenuButton.addListener(new ClickListener() {
            // Add your listener code here
        });
        stage.addActor(mainMenuButton);

        gameOverScreenSetUp = true;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }


public static void removeBullet(List<Bullet> bullets, OrthographicCamera camera) {
    bullets.removeIf(bullet -> bullet.getX() < camera.position.x - camera.viewportWidth / 2 || bullet.getX() > camera.position.x + camera.viewportWidth / 2);
}
public static void removeBullet(List<Bullet> bullets, OrthographicCamera camera, List<Enemy> enemies) {
    bullets.removeIf(bullet -> bullet.getX() < camera.position.x - camera.viewportWidth / 2 || bullet.getX() > camera.position.x + camera.viewportWidth / 2);

    for (Enemy enemy : enemies) {
        if (enemy != null) {
            enemy.getBalls().removeIf(ball -> ball.getX() < camera.position.x - camera.viewportWidth / 2 || ball.getX() > camera.position.x + camera.viewportWidth / 2);
        }
    }
}
public static void updatePosEnemies(List<Enemy> enemies, Player player) {
    for (Enemy enemy : enemies) {
      if(enemy.getState() == Enemy.State.NORMAL) {
        enemy.enemyController.updateEnemyMovement(enemy, player);
    }
  }
}
    public static void updateEnemies(List<Enemy> enemies, float deltaTime) {
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }
    }
  public static void handleDamageForBossDrake(BossDrake bossDrake, List<SonicBall> projectiles) {
    Rectangle bossHitbox = bossDrake.getBounds();
    for (SonicBall sonicBall : projectiles) {
        Rectangle sonicBallHitbox = sonicBall.getBounds();

        if (bossHitbox.overlaps(sonicBallHitbox)) {
            bossDrake.takeDamage(20); // Decrease the BossDrake's health by 1
            projectiles.remove(sonicBall); // Remove the SonicBall from the projectiles list
            break;
        }
    }
}
  public static void handleDamageForEnnemy(Entity npc, List<Bullet> bullets) {
      for (Bullet bullet : bullets) {
          Rectangle bulletBounds = bullet.getBounds();
          if (npc.getBounds().overlaps(bulletBounds)) {
              npc.takeDamage(10); // Decrease the player's health by 1
              bullets.remove(bullet); // Remove the bullet from the bullets list
              break;
          }
      }
  }

    public static void handleDamageForBlasterBall(Player player, List<BlasterBall> bullets) {
      for (BlasterBall bullet : bullets) {
          Rectangle bulletBounds = bullet.getBounds();
          if (player.getBounds().overlaps(bulletBounds)) {
              player.takeDamage(5); // Decrease the player's health by 1
              bullets.remove(bullet); // Remove the bullet from the bullets list
              break;
          }
      }
  }

public static void handleDamageForEnemy(Enemy enemy, List<Bullet> bullets) {
    java.util.Iterator<Bullet> bulletIterator = bullets.iterator();
    while (bulletIterator.hasNext()) {
        Bullet bullet = bulletIterator.next();
        Rectangle bulletBounds = bullet.getBounds();
        if (enemy.getBounds().overlaps(bulletBounds)) {
            enemy.takeDamage(10); // Decrease the enemy's health by 10
            bulletIterator.remove(); // Remove the bullet from the bullets list
            if(enemy.getHealth() <= 0) {
                // Handle enemy death here
            }
            break;
        }
    }
}
public static void updateEnemies(List<Enemy> enemies) {
    for (Enemy enemy : enemies) {
        enemy.updateEnemyState(enemies);
    }
}
public static void removeDeadEnemies(List<Enemy> enemies) {
    java.util.Iterator<Enemy> enemyIterator = enemies.iterator();
    while (enemyIterator.hasNext()) {
        Enemy enemy = enemyIterator.next();
        if (enemy.getState() == Enemy.State.DEAD) {
            enemyIterator.remove();
        }
    }
}

public static void handleDamageForEnemies(List<Enemy> enemies, List<Bullet> bullets) {
    java.util.Iterator<Bullet> bulletIterator = bullets.iterator();
    while (bulletIterator.hasNext()) {
        Bullet bullet = bulletIterator.next();
        Rectangle bulletBounds = bullet.getBounds();
        java.util.Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (enemy.getBounds().overlaps(bulletBounds)) {
                enemy.takeDamage(25); // Decrease the enemy's health by 25
                bulletIterator.remove(); // Remove the bullet
                break; // Exit the inner loop
            }
        }
    }
}
public static void handleDamageForPlayer(Player player, Enemy enemy) {
  Rectangle playerBounds = player.getBounds();
  for(EnemyBall ball : enemy.getBalls()) {
      if(playerBounds.overlaps(ball.getBounds())) {
          player.takeDamage(10);
      }
  }
}
public static void handleDamageForPlayer(Player player, List<Enemy> enemies) {
    Rectangle playerBounds = player.getBounds();
    for (Enemy enemy : enemies) {
        for (EnemyBall ball : enemy.getBalls()) {
            if (playerBounds.overlaps(ball.getBounds())) {
                player.takeDamage(10);
            }
        }
    }
}
  public static void handleDamageLayerForBouleDeFeu(List<BouleDeFeu> boulesDeFeu, Entity... entities) {
    for (BouleDeFeu bouleDeFeu : boulesDeFeu) {
        for (Entity entity : entities) {
            if (entity.getBounds().overlaps(bouleDeFeu.getBounds())) {
              if (entity instanceof Player) {
                entity.takeDamage(30);
            }
          }
        }
    }
}

  public static void handleDamageLayerForBouleDeFeu2(List<BouleDeFeu> boulesDeFeu, Entity... entities) {
    for (BouleDeFeu bouleDeFeu : boulesDeFeu) {
        for (Entity entity : entities) {
            if (entity.getBounds().overlaps(bouleDeFeu.getBounds())) {
              if (entity instanceof Player) {
                entity.takeDamage(15);
            }
          }
        }
    }
}

public static void handleBouleDeFeuCollision(List<BouleDeFeu> boulesDeFeu, TiledMapTileLayer tileLayer) {
  outerLoop:
  for (int i = boulesDeFeu.size() - 1; i >= 0; i--) {
      BouleDeFeu bouleDeFeu = boulesDeFeu.get(i);
      Rectangle bouleDeFeuBounds = bouleDeFeu.getBounds();

      for (int x = 0; x < tileLayer.getWidth(); x++) {
          for (int y = 0; y < tileLayer.getHeight(); y++) {
              Rectangle tileBounds = new Rectangle(x * tileLayer.getTileWidth(), y * tileLayer.getTileHeight(), tileLayer.getTileWidth(), tileLayer.getTileHeight());

              if (bouleDeFeuBounds.overlaps(tileBounds)) {
                  boulesDeFeu.remove(i);
                  break outerLoop;
              }
          }
      }
  }
}

public static void handlePlatformInteractions(List<MovablePlatform> platforms, Entity... entities) {
    for (MovablePlatform platform : platforms) {
        for (Entity entity : entities) {
            if (entity.getBounds().overlaps(platform.getBounds())) {
                if (
                    entity.getVelocity() < 0 &&
                    entity.getPosition().y - entity.getBounds().height / 2 < platform.getBounds().y + platform.getBounds().height &&
                    entity.getPosition().y > platform.getBounds().y
                ) {
                    entity.getPosition().y = platform.getBounds().y + platform.getBounds().height;
                    entity.setVelocityY(0);
                } else if (entity.getPosition().y <= platform.getBounds().y) {
                    entity.getPosition().set(entity.getPreviousPosition());
                }
            }
        }
    }
}

public static void handlePlatformInteractions2(List<HorizontalPlatform> platforms, Entity... entities) {
    for (HorizontalPlatform platform : platforms) {
        for (Entity entity : entities) {
            if (entity.getBounds().overlaps(platform.getBounds())) {
                if (
                    entity.getVelocity() < 0 &&
                    entity.getPosition().y - entity.getBounds().height / 2 < platform.getBounds().y + platform.getBounds().height &&
                    entity.getPosition().y > platform.getBounds().y
                ) {
                    entity.getPosition().y = platform.getBounds().y + platform.getBounds().height;
                    entity.setVelocityY(0);
                } else if (entity.getPosition().y <= platform.getBounds().y) {
                    entity.getPosition().set(entity.getPreviousPosition());
                }
            }
        }
    }
}

  public static void handleCollisionLayer(
    TiledMapTileLayer collisionLayer,
    Entity... entities
  ) {
    for (int y = 0; y < collisionLayer.getHeight(); y++) {
      for (int x = 0; x < collisionLayer.getWidth(); x++) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
        if (cell != null) {
          Rectangle tileBounds = new Rectangle(
            x * collisionLayer.getTileWidth(),
            y * collisionLayer.getTileHeight(),
            collisionLayer.getTileWidth(),
            collisionLayer.getTileHeight()
          );
          for (Entity entity : entities) {
            if (entity.getBounds().overlaps(tileBounds)) {
              if (
                entity.getVelocity() < 0 &&
                entity.getPosition().y -
                entity.getBounds().height <
                tileBounds.y +
                tileBounds.height &&
                entity.getPosition().y > tileBounds.y
              ) {
                entity.getPosition().y = tileBounds.y + tileBounds.height;
                entity.setVelocityY(0);
              } else if (entity.getPosition().y <= tileBounds.y) {
                entity.getPosition().set(entity.getPreviousPosition());
              }
            }
          }
        }
      }
    }
  }

  public static void moveLayer(TiledMap map, String layerName, float speed, float deltaTime, float screenWidth) {
    TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);

    if (layer != null) {
      for (int y = 0; y < layer.getHeight(); y++) {
        for (int x = 0; x < layer.getWidth(); x++) {
          TiledMapTileLayer.Cell cell = layer.getCell(x, y);
          if (cell != null) {
            float newX = x * layer.getTileWidth() - speed * deltaTime;
            if (newX < 0) {
              newX = screenWidth;
            }
            cell.setTile(layer.getCell((int) (newX / layer.getTileWidth()), y).getTile());
          }
        }
      }
    } else {
        System.out.println("Layer " + layerName + " not found.");
    }
}

  public static boolean phase1BossDrake(Player player, BossDrake bossDrake){
    if (player.getPosition().dst(bossDrake.getPosition()) < 2000) {
        return true;
    } else {
        return false;
    }
  }

  public static boolean phase2BossDrake(BossDrake bossDrake) {
    if (bossDrake.getHealth() < 300) {
        return true;
    } else {
        return false;
    }
  }

  public static boolean phase2Blaster(Blaster blaster) {
    if (blaster.getFinalPhase() == true) {
        return true;
    } else {
        return false;
    }
  }

  public static boolean interphase(Blaster blaster, boolean alreadyInterphased){
    if (blaster.getHealth() < 300 && !alreadyInterphased) {
        blaster.setState("TRANSITION");
        return true;
    } else {
        return false;
    }
  }
public static void drawHealthBar(
    SpriteBatch batch,
    Entity player,
    Texture healthBar,
    BitmapFont font,
    Camera camera,
    Texture shield,
    boolean drawShield
) {
    float xOffset = 100; // Adjust this value to move the health bar to the right
    float yOffset = -50; // Adjust this value to move the health bar down

    // Draw shield icon to the right of the health bar if drawShield is true
    if (drawShield) {
        batch.draw(
            shield,
            camera.position.x - camera.viewportWidth / 2 + xOffset + healthBar.getWidth(),
            camera.position.y + camera.viewportHeight / 2 - healthBar.getHeight() + yOffset
        );
    }

    // Draw health bar
    float healthPercentage = (float) player.getHealth() / player.getMaxHealth();
    batch.draw(
        healthBar,
        camera.position.x - camera.viewportWidth / 2 + xOffset,
        camera.position.y + camera.viewportHeight / 2 - healthBar.getHeight() + yOffset,
        Math.round(healthBar.getWidth() * healthPercentage),
        healthBar.getHeight()
    );

    // Draw health text
    font.setColor(Color.BLACK);
    font.draw(
        batch,
        "Health: " + player.getHealth(),
        camera.position.x - camera.viewportWidth / 2 + 60 + xOffset,
        camera.position.y +
        camera.viewportHeight /
        2 -
        healthBar.getHeight() /
        2 + 5 +
        yOffset
    );
}
  public static void drawHealthBar(
    SpriteBatch batch,
    Entity player,
    Texture healthBar,
    BitmapFont font,
    Camera camera
  ) {
    // Draw health bar
    float healthPercentage = (float) player.getHealth() / player.getMaxHealth();
    float xOffset = 100; // Adjust this value to move the health bar to the right
    float yOffset = -50; // Adjust this value to move the health bar down
    batch.draw(
      healthBar,
      camera.position.x - camera.viewportWidth / 2 + xOffset,
      camera.position.y + camera.viewportHeight / 2 - healthBar.getHeight() + yOffset,
      Math.round(healthBar.getWidth() * healthPercentage),
      healthBar.getHeight()
    );

    // Draw health text
    font.setColor(Color.BLACK);
    font.draw(
      batch,
      "Health: " + player.getHealth(),
      camera.position.x - camera.viewportWidth / 2 + 60 + xOffset,
      camera.position.y +
      camera.viewportHeight /
      2 -
      healthBar.getHeight() /
      2 + 5 +
       yOffset
    );
  }

  public static void drawMinotaur(SpriteBatch batch, Minotaur minotaur) {
    TextureRegion currentSprite = minotaur.getSprites()[minotaur.getCurrentSpriteIndex()];
    float scale = 4.0f; // Change this to the scale you want
    batch.draw(currentSprite, minotaur.getPosition().x, minotaur.getPosition().y, currentSprite.getRegionWidth() * scale, currentSprite.getRegionHeight() * scale);
}

public static void drawBlaster(SpriteBatch batch, Blaster blaster) {
  TextureRegion currentSprite = blaster.getSprites()[blaster.getCurrentSpriteIndex()];
    float scale = 2.0f; // Change this to the scale you want
    batch.draw(currentSprite, blaster.getPosition().x, blaster.getPosition().y, currentSprite.getRegionWidth() * scale, currentSprite.getRegionHeight() * scale);

}

  public static void drawBossHealthBar(
    SpriteBatch batch,
    Entity bossDrake,
    Texture healthBar,
    BitmapFont font
) {
    // Draw health bar
    float healthPercentage = (float) bossDrake.getHealth() / bossDrake.getMaxHealth();
    float xOffset = bossDrake.getPosition().x - bossDrake.getWidth()/2 - 100; // Position the health bar above the boss
    float yOffset = bossDrake.getPosition().y + bossDrake.getHeight() + 140; // 10 pixels above the boss

    batch.draw(
        healthBar,
        xOffset,
        yOffset,
        Math.round(healthBar.getWidth() * healthPercentage),
        healthBar.getHeight()
    );

    // Draw health text
    font.setColor(Color.BLACK);
    font.draw(
        batch,
        "Boss Health: " + bossDrake.getHealth(),
        xOffset,
        yOffset + healthBar.getHeight() / 2
    );
}

  public static void drawMinotaurHealthBar(
    SpriteBatch batch,
    Entity bossDrake,
    Texture healthBar,
    BitmapFont font
) {
    // Draw health bar
    float healthPercentage = (float) bossDrake.getHealth() / bossDrake.getMaxHealth();
    float xOffset = bossDrake.getPosition().x - bossDrake.getWidth() ; // Position the health bar above the boss
    float yOffset = bossDrake.getPosition().y + bossDrake.getHeight() + 160; // 10 pixels above the boss

    batch.draw(
        healthBar,
        xOffset,
        yOffset,
        Math.round(healthBar.getWidth()/2 * healthPercentage),
        healthBar.getHeight()
    );

    // Draw health text
    font.setColor(Color.BLACK);
    font.draw(
        batch,
        "Boss Health: " + bossDrake.getHealth(),
        xOffset,
        yOffset + healthBar.getHeight() / 2
    );
}

public static void drawBlasterBall(SpriteBatch batch, List<BlasterBall> blasterballs, Texture blasterBallTexture) {
  for (BlasterBall blasterball : blasterballs) {
      blasterball.setImage(blasterBallTexture);
      blasterball.update(Gdx.graphics.getDeltaTime());
      blasterball.draw(batch);
  }
}

  public static void drawBlasterHealthBar(
    SpriteBatch batch,
    Entity bossDrake,
    Texture healthBar,
    BitmapFont font
) {
    // Draw health bar
    float healthPercentage = (float) bossDrake.getHealth() / bossDrake.getMaxHealth();
    float xOffset = bossDrake.getPosition().x - bossDrake.getWidth() ; // Position the health bar above the boss
    float yOffset = bossDrake.getPosition().y + bossDrake.getHeight() + 160; // 10 pixels above the boss

    batch.draw(
        healthBar,
        xOffset,
        yOffset,
        Math.round(healthBar.getWidth()/2 * healthPercentage),
        healthBar.getHeight()
    );

    // Draw health text
    font.setColor(Color.BLACK);
    font.draw(
        batch,
        "Boss Health: " + bossDrake.getHealth(),
        xOffset,
        yOffset + healthBar.getHeight() / 2
    );
}

  public static void drawNpcHealthBar(
    SpriteBatch batch,
    Entity npc,
    Texture healthBar,
    BitmapFont font
  ) {
    // Draw health bar
    float healthPercentage = (float) npc.getHealth() / npc.getMaxHealth();
    float xOffset = npc.getPosition().x - npc.getWidth()/2 - 70; // Position the health bar above the npc
    float yOffset = npc.getPosition().y + npc.getHeight() + 40; // 10 pixels above the npc

    batch.draw(
        healthBar,
        xOffset,
        yOffset,
        Math.round(healthBar.getWidth() * healthPercentage),
        healthBar.getHeight()
    );

    // Draw health text
    font.setColor(Color.YELLOW);
    font.draw(
        batch,
        "Npc Health: " + npc.getHealth(),
        xOffset,
        yOffset + healthBar.getHeight() / 2 + 8
    );
  }

public static void drawItems(SpriteBatch batch, List<Object> items) {
    for (Object item : items) {
        if (item instanceof Seringue) {
            Seringue seringue = (Seringue) item;
            if (!seringue.isUsed()) {
                batch.draw(
                    seringue.getSprite(),
                    seringue.getPosition().x,
                    seringue.getPosition().y
                );
            } else {
                seringue.getSprite().dispose();
            }
        } else if (item instanceof Kevlar) {
            Kevlar kevlar = (Kevlar) item;
            // Assuming Kevlar has similar methods as Seringue
            if (!kevlar.isUsed()) {
                batch.draw(
                    kevlar.getSprite(),
                    kevlar.getPosition().x,
                    kevlar.getPosition().y
                );
            } else {
                kevlar.getSprite().dispose();
            }
        }
    }
}
  public static void drawWeapon(SpriteBatch batch, Chaos chaos, Player player) {
    if (!chaos.isUsed()) {
      batch.draw(
        chaos.getSprite(),
        chaos.getPosition().x,
        chaos.getPosition().y
      );
       chaos.isPickable(player);
    }
  }

  public static void drawPlatforms(
    SpriteBatch batch,
    List<HorizontalPlatform> platforms
  ) {
    for (HorizontalPlatform platform : platforms) {
      platform.draw(batch);
    }
  }

  public static void attackPlayer(
    Player player
  ) {
    if (Gdx.input.isKeyPressed(Keys.F)) {
      player.attack();
    } else {
      player.stopAttack();
    }
  }

  public static void unequipWeapon(Player player) {
    player.setEquippedWeapon(null);
  }

  public static void playerAttackWeapon(Player player) {
    System.out.println("Player is trying attacking");
    if (Gdx.input.isKeyPressed(Keys.R) && player.canAttack()) {
      System.out.println("Player is attacking");
        player.WeaponAttack();
        player.resetAttackTimer();
    } else {
        player.stopAttack();
    }
}

public static void minotaurRushAttack(Minotaur minotaur, Player player) {
  float distance = minotaur.getPosition().x - player.getPosition().x;
if (player.getPosition().x < 12635 && player.getPosition().x > 10880) {
  if (distance < 800 && distance > 100 - player.getWidth()) { 
          minotaur.setState("LEFT");
      } else if (distance > -800 - player.getWidth() && distance < -100 - player.getWidth()) {
          minotaur.setState("RIGHT");
      }

   else if (distance <= 100 - player.getWidth() && distance >= 0) { 
          minotaur.setState("ATTACKLEFT");
          handleDamageByMinotaur(minotaur, player);
      } else if (distance >= -100 - player.getWidth() && distance < 0) {
          minotaur.setState("ATTACKRIGHT");
          handleDamageByMinotaur(minotaur, player);
      }
    }
    }

    public static void BlasterReaction(Blaster blaster, Player player) {
  float distance = blaster.getPosition().x - player.getPosition().x;

  if (distance < 800 && distance > 100 - player.getWidth()) { 
          blaster.setState("LEFT");
      } else if (distance > -800 - player.getWidth() && distance < -100 ) {
          blaster.setState("RIGHT");
      }

   else if (distance <= 100 - player.getWidth() && distance >= 0) { 
          blaster.setState("ATTACKLEFT");
          handleDamageByBlaster(blaster, player);
      } else if (distance >= -100  && distance < 0) {
          blaster.setState("ATTACKRIGHT");
          handleDamageByBlaster(blaster, player);
      }
    
    }

    public static void BlasterReactionPhase2(Blaster blaster, Player player) {
  float distance = blaster.getPosition().x - player.getPosition().x;
  float epsilon = Math.abs(blaster.getPosition().y - player.getPosition().y);
    if (epsilon < 60) {
  if (distance < 600 && distance > 300) { 
          blaster.setState("FIRELEFT");
      } else if (distance > -600 - player.getWidth() && distance < -300 ) {
          blaster.setState("FIRERIGHT");
      } else if (distance > 100 - player.getWidth() && distance < 300) { 
          blaster.setState("SHORTFIRELEFT");
      } else if (distance < -100 && distance > -300) {
          blaster.setState("SHORTFIRERIGHT");
      }

   else if (distance <= 100 - player.getWidth() && distance >= 0) { 
          blaster.setState("ATTACKLEFT");
          handleDamageByBlaster(blaster, player);
      } else if (distance >= -100 && distance < 0) {
          blaster.setState("ATTACKRIGHT");
          handleDamageByBlaster(blaster, player);
      }
      } else {
        if (distance < 800 && distance > 600) { 
          blaster.setState("FLYLEFT");
      } else if (distance > -800 && distance < -600) {
          blaster.setState("FLYRIGHT");
      }
    
    }
  }



    public static void handleDamageByBlaster(Blaster blaster, Player player) {
    Rectangle blasterHitbox = blaster.getBounds();
    Rectangle playerHitbox = player.getBounds();

    if (blaster.getState().equals("ATTACKRIGHT")) {
        // Create a new Rectangle that represents the right part of the Minotaur's hitbox
        Rectangle rightPartOfMinotaurHitbox = new Rectangle(blasterHitbox.x + blasterHitbox.width / 2, blasterHitbox.y, blasterHitbox.width , blasterHitbox.height);

        // Check if the right part of the Minotaur's hitbox overlaps with the player's hitbox
        if (rightPartOfMinotaurHitbox.overlaps(playerHitbox)) {
            player.takeDamage(20); // Decrease the player's health by 20
        }
    } else if (blaster.getState().equals("ATTACKLEFT")) {
        // Create a new Rectangle that represents the left part of the Minotaur's hitbox
        Rectangle leftPartOfMinotaurHitbox = new Rectangle(blasterHitbox.x, blasterHitbox.y, blasterHitbox.width / 2, blasterHitbox.height);

        // Check if the left part of the Minotaur's hitbox overlaps with the player's hitbox
        if (leftPartOfMinotaurHitbox.overlaps(playerHitbox)) {
            player.takeDamage(20); // Decrease the player's health by 20
        }
      }
    }

      public static void handleDamageforBlaster(Blaster blaster, List<Bullet> bullets, Player player) {
    Rectangle minotaurHitbox = blaster.getBounds();
    
    for (Bullet bullet : bullets) {
        Rectangle bulletBounds = bullet.getBounds();

        if (minotaurHitbox.overlaps(bulletBounds)) {
            blaster.takeDamage(20); // Decrease the Minotaur's health by 1
            bullets.remove(bullet); // Remove the bullet from the bullets list
            break;
        }
      }
  }



    public static void handleDamageByHitBoxBossDrake(BossDrake bossDrake, Player player) {
      Rectangle bossDrakeHitbox = bossDrake.getBounds();
      Rectangle extendedBossDrakeHitbox = new Rectangle(bossDrakeHitbox.x, bossDrakeHitbox.y, bossDrakeHitbox.width + 90, bossDrakeHitbox.height);
      Rectangle playerHitbox = player.getBounds();
  
      if (extendedBossDrakeHitbox.overlaps(playerHitbox)) {
          player.takeDamage(20); // Decrease the player's health by 20
      }
  }

  public static void handleDamageforMinotaur(Minotaur minotaur, List<Bullet> bullets, Player player) {
    Rectangle minotaurHitbox = minotaur.getBounds();
    if (player.getPosition().x < 12635 && player.getPosition().x > 10880) {
    for (Bullet bullet : bullets) {
        Rectangle bulletBounds = bullet.getBounds();

        if (minotaurHitbox.overlaps(bulletBounds)) {
            minotaur.takeDamage(20); // Decrease the Minotaur's health by 1
            bullets.remove(bullet); // Remove the bullet from the bullets list
            break;
        }
      }
    }
  }

  public static void handleDamageByMinotaur(Minotaur minotaur, Player player) {
    Rectangle minotaurHitbox = minotaur.getBounds();
    Rectangle playerHitbox = player.getBounds();

    if (minotaur.getState().equals("ATTACKRIGHT")) {
        // Create a new Rectangle that represents the right part of the Minotaur's hitbox
        Rectangle rightPartOfMinotaurHitbox = new Rectangle(minotaurHitbox.x + minotaurHitbox.width / 2, minotaurHitbox.y, minotaurHitbox.width / 2, minotaurHitbox.height);

        // Check if the right part of the Minotaur's hitbox overlaps with the player's hitbox
        if (rightPartOfMinotaurHitbox.overlaps(playerHitbox)) {
            player.takeDamage(20); // Decrease the player's health by 20
        }
    } else if (minotaur.getState().equals("ATTACKLEFT")) {
        // Create a new Rectangle that represents the left part of the Minotaur's hitbox
        Rectangle leftPartOfMinotaurHitbox = new Rectangle(minotaurHitbox.x, minotaurHitbox.y, minotaurHitbox.width / 2, minotaurHitbox.height);

        // Check if the left part of the Minotaur's hitbox overlaps with the player's hitbox
        if (leftPartOfMinotaurHitbox.overlaps(playerHitbox)) {
            player.takeDamage(20); // Decrease the player's health by 20
        }
      }
    }


  public static void drawPlayerDamage(SpriteBatch batch, Player player) {
    TextureRegion region = player.getCurrentSprite();
    Sprite sprite = new Sprite(region);
    if (player.isDamageTaken()) {
        sprite.setColor(Color.YELLOW);
    } else {
        sprite.setColor(Color.WHITE);
    }
    sprite.setPosition(player.getPosition().x, player.getPosition().y);
    sprite.draw(batch);
}

  public static void updateDamageTaken(Player player) {
      if (TimeUtils.timeSinceNanos(player.getLastDamageTime()) < 2000000000 && TimeUtils.timeSinceNanos(player.getLastDamageTime()) > 0) { // 2 seconds in nanoseconds
        player.setDamageTaken(true);
      } else {
        player.setDamageTaken(false);
      }
  }

public static void displayGameOverLayer() {
    Stage stage = new Stage();
    Gdx.input.setInputProcessor(stage);

    Label.LabelStyle fontStyle = new Label.LabelStyle(new BitmapFont(), Color.RED);
    Label gameOverLabel = new Label("Game Over", fontStyle);
    gameOverLabel.setFontScale(10);
    
    // Calculate the width and height of the label
    float labelWidth = gameOverLabel.getWidth() * gameOverLabel.getFontScaleX();
    float labelHeight = gameOverLabel.getHeight() * gameOverLabel.getFontScaleY();
    
    // Center the label
    gameOverLabel.setPosition((Gdx.graphics.getWidth() - labelWidth) / 2, (Gdx.graphics.getHeight() - labelHeight) / 2);
    
    stage.addActor(gameOverLabel);

    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.font = new BitmapFont();
    textButtonStyle.fontColor = Color.WHITE;
    
    TextButton mainMenuButton = new TextButton("Main Menu", textButtonStyle);
    mainMenuButton.getLabel().setFontScale(2); // Increase the font scale
    mainMenuButton.setPosition((Gdx.graphics.getWidth() - mainMenuButton.getWidth()) / 2, gameOverLabel.getY() - mainMenuButton.getHeight() - 80);
    stage.addActor(mainMenuButton);
    
    TextButton retryButton = new TextButton("Retry", textButtonStyle);
    retryButton.getLabel().setFontScale(2); // Increase the font scale
    retryButton.setPosition((Gdx.graphics.getWidth() - retryButton.getWidth()) / 2, mainMenuButton.getY() - retryButton.getHeight() - 40);
    stage.addActor(retryButton);
        stage.act();
        stage.draw();
    }

  public static class InitializationResult {

    public BitmapFont font;
    public OrthographicCamera camera;
    public SpriteBatch batch;
    public TiledMap map;
    public OrthogonalTiledMapRenderer mapRenderer;
    public TextureRegion[] sprites;
    public TextureRegion[] weaponsprites;
    public Texture healthBar;
    public Texture minotaurHealthBar;
    public MovementController movementController;
    public EntityNpcMovement entityMovement;
    public Player player;
    public Texture bossHealthBar;
  }

  public static InitializationResult initializeBasics() {
    InitializationResult result = new InitializationResult();

    result.font = new BitmapFont();
    result.camera = new OrthographicCamera();
    result.camera.setToOrtho(
      false,
      Gdx.graphics.getWidth(),
      Gdx.graphics.getHeight()
    );
    result.batch = new SpriteBatch();
    result.map = new TmxMapLoader().load("Map0.tmx");
    result.mapRenderer = new OrthogonalTiledMapRenderer(result.map);
    result.sprites = new TextureRegion[16];
    result.weaponsprites = new TextureRegion[16];
    result.healthBar = new Texture(Gdx.files.internal("barre3.png"));
        for (int i = 0; i < 8; i++) {
            Texture weaponTexture = new Texture("spriteweapon" + i + ".png");
            result.weaponsprites[i] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8].flip(true, false);
        }
    for (int i = 0; i < 8; i++) {
        Texture texture = new Texture("sprite" + i + ".png");
        result.sprites[i] = new TextureRegion(texture);
        result.sprites[i + 8] = new TextureRegion(texture);
        result.sprites[i + 8].flip(true, false);
    }
    result.movementController = new MovementController();
    result.entityMovement = new EntityNpcMovement();
    result.player =
      new Player(
        "Player1",
        100,
        new Vector2(0, 0),
        5,
        result.movementController,
        result.sprites,
        result.weaponsprites,
        5.0f
      );
    return result;
  }

  public static InitializationResult initializeBasics3() {
    InitializationResult result = new InitializationResult();

    result.font = new BitmapFont();
    result.camera = new OrthographicCamera();
    result.camera.setToOrtho(
      false,
      Gdx.graphics.getWidth(),
      Gdx.graphics.getHeight()
    );
    result.batch = new SpriteBatch();
    result.map = new TmxMapLoader().load("MapProcedural.tmx");
    result.mapRenderer = new OrthogonalTiledMapRenderer(result.map);
    result.sprites = new TextureRegion[16];
    result.weaponsprites = new TextureRegion[16];
    result.healthBar = new Texture(Gdx.files.internal("barre3.png"));
    result.bossHealthBar = new Texture(Gdx.files.internal("BossBarre.png"));
        for (int i = 0; i < 8; i++) {
            Texture weaponTexture = new Texture("spriteweapon" + i + ".png");
            result.weaponsprites[i] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8].flip(true, false);
        }
    for (int i = 0; i < 8; i++) {
        Texture texture = new Texture("sprite" + i + ".png");
        result.sprites[i] = new TextureRegion(texture);
        result.sprites[i + 8] = new TextureRegion(texture);
        result.sprites[i + 8].flip(true, false);
    }
    result.movementController = new MovementController();
    result.entityMovement = new EntityNpcMovement();
    result.player =
      new Player(
        "Player1",
        100,
        new Vector2(0, 0),
        5,
        result.movementController,
        result.sprites,
        result.weaponsprites,
        5.0f
      );
    return result;
  }

    public static InitializationResult initializeBasics2() {
    InitializationResult result = new InitializationResult();

    result.font = new BitmapFont();
    result.camera = new OrthographicCamera();
    result.camera.setToOrtho(
      false,
      Gdx.graphics.getWidth(),
      Gdx.graphics.getHeight()
    );
    result.batch = new SpriteBatch();
    result.map = new TmxMapLoader().load("Map1.tmx");
    result.mapRenderer = new OrthogonalTiledMapRenderer(result.map);
    result.sprites = new TextureRegion[16];
    result.weaponsprites = new TextureRegion[16];
    result.healthBar = new Texture(Gdx.files.internal("barre3.png"));
    result.minotaurHealthBar = new Texture(Gdx.files.internal("BossBarre.png"));
        for (int i = 0; i < 8; i++) {
            Texture weaponTexture = new Texture("spriteweapon" + i + ".png");
            result.weaponsprites[i] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8].flip(true, false);
        }
    for (int i = 0; i < 8; i++) {
        Texture texture = new Texture("sprite" + i + ".png");
        result.sprites[i] = new TextureRegion(texture);
        result.sprites[i + 8] = new TextureRegion(texture);
        result.sprites[i + 8].flip(true, false);
    }
    result.movementController = new MovementController();
    result.entityMovement = new EntityNpcMovement();
    result.player =
      new Player(
        "Player1",
        100,
        new Vector2(0, 0),
        5,
        result.movementController,
        result.sprites,
        result.weaponsprites,
        5.0f
      );
    return result;
  }
     public static InitializationResult initializeBasics4() {
    InitializationResult result = new InitializationResult();

    result.font = new BitmapFont();
    result.camera = new OrthographicCamera();
    result.camera.setToOrtho(
      false,
      Gdx.graphics.getWidth(),
      Gdx.graphics.getHeight()
    );
    result.batch = new SpriteBatch();
    result.map = new TmxMapLoader().load("Map2.tmx");
    result.mapRenderer = new OrthogonalTiledMapRenderer(result.map);
    result.sprites = new TextureRegion[16];
    result.weaponsprites = new TextureRegion[16];
    result.healthBar = new Texture(Gdx.files.internal("barre3.png"));
    result.bossHealthBar = new Texture(Gdx.files.internal("BossBarre.png"));
        for (int i = 0; i < 8; i++) {
            Texture weaponTexture = new Texture("spriteweapon" + i + ".png");
            result.weaponsprites[i] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8] = new TextureRegion(weaponTexture);
            result.weaponsprites[i + 8].flip(true, false);
        }
    for (int i = 0; i < 8; i++) {
        Texture texture = new Texture("sprite" + i + ".png");
        result.sprites[i] = new TextureRegion(texture);
        result.sprites[i + 8] = new TextureRegion(texture);
        result.sprites[i + 8].flip(true, false);
    }
    result.movementController = new MovementController();
    result.entityMovement = new EntityNpcMovement();
    result.player =
      new Player(
        "Player1",
        100,
        new Vector2(0, 0),
        5,
        result.movementController,
        result.sprites,
        result.weaponsprites,
        5.0f
      );
    return result;
  }

  public static void cheat(Player player, Boolean cheater){
    if (Gdx.input.isKeyPressed(Keys.P)) {
      cheater = true;
      player.setHealth(100);
    }
    if (cheater) {
      player.setHealth(100);
    }
  }


}
