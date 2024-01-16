package com.stroin.game.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.stroin.game.StroIN;
import com.stroin.game.Menus.MenuScreen;
import com.stroin.game.controllers.MovementController;
import com.stroin.game.data.GameData;
import com.stroin.game.elements.HorizontalPlatform;
import com.stroin.game.entity.ClassicNpc;
import com.stroin.game.entity.EntityNpcMovement;
import com.stroin.game.entity.MessageNpc;
import com.stroin.game.entity.Minotaur;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Consumables.Seringue;
import com.stroin.game.items.Equipments.Kevlar;
import com.stroin.game.items.Weapons.Chaos;
public class Level1 extends ApplicationAdapter implements Level {

  private MovementController movementController;
  private EntityNpcMovement entityMovement;
  private Player player;
  private ClassicNpc npc;
  private SpriteBatch batch;
  private Minotaur minotaur;
  private TextureRegion[] sprites;
  private TextureRegion[] weaponsprites;
  private TextureRegion currentSprite;
  private Texture healthBar;
  private Texture minotaurHealthBar;
  private int spriteWidth = 100;
  private TextureRegion currentSpriteNpc;
  private Texture platformTexture;
  private HorizontalPlatform platformCyber;
  private List<HorizontalPlatform> platforms = new ArrayList<>();
  private TiledMap map;
  private OrthogonalTiledMapRenderer mapRenderer;
  private OrthographicCamera camera;
  private MenuScreen menuScreen;
  private Seringue seringue;
  private Seringue seringue1;
  private Seringue seringue2;
  private Seringue seringue3;
  private boolean isLoadScheduled = false;
  private BitmapFont font;
  private LevelFunctions levelFunctions = new LevelFunctions();
    private StroIN game;
  //private boolean CheckRush;
  private Chaos chaos;
  private boolean levelCompleted = false;
  private float epsilon = 30f;
  private boolean gameOverScreenSetUp = false;
  private boolean gameSaved = false;
  private boolean isGameOverSaved = false;
  private MessageNpc messageNpc;
  private MessageNpc messageNpc2;
  private float messageDisplayDistance = 800f;
  private GameData gameData;
  private Stage inventoryStage;
  public static AssetManager manager; 
  List<Object> items = new ArrayList<>();
  Texture shield = new Texture("shield.png");





    @Override
    public void passGame(StroIN game) {
        this.game = game;
    }
    public void getGame(StroIN game) {
        this.game = game;
    }
  @Override
  public void create() {
    LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics2();

    font = result.font;
    camera = result.camera;
    batch = result.batch;
    map = result.map;
    mapRenderer = result.mapRenderer;
    sprites = result.sprites;
    weaponsprites = result.weaponsprites;
    healthBar = result.healthBar;
    minotaurHealthBar = result.minotaurHealthBar;
    movementController = result.movementController;
    entityMovement = result.entityMovement;
    player = result.player;
    manager = new AssetManager();
    manager.load("explosion.mp3", Sound.class);
    manager.load("gunShoot.mp3", Sound.class);
    manager.load("????.mp3", Music.class);
    manager.finishLoading();
    //npc = new ClassicNpc("Npc1", 100, new Vector2(0, 0), 5, sprites, weaponsprites, 5.0f);
    platformTexture = new Texture("PlateformCyber.png");
    platformCyber = new HorizontalPlatform("plat", 12460, 1200, spriteWidth + 55 , spriteWidth);
    platforms.add(platformCyber);
    chaos = new Chaos();
    chaos.setPosition(new Vector2(10700, 95));
    gameData = new GameData();
    messageNpc =
      new MessageNpc(
        "Npc",
        100,
        new Vector2(12250, 1343),
        5,
        sprites,
        5.0f,
        "Kill the Minotaur to join me !"
      );
    messageNpc2 =
      new MessageNpc(
        "Npc",
        100,
        new Vector2(10200, 223),
        5,
        sprites,
        5.0f,
        "You need to go in the arena to fight the Minotaur!"
      );

    TextureRegion[] MinotaurSprites = new TextureRegion[17];
    MinotaurSprites[0] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur1.png")));
    MinotaurSprites[1] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur2.png")));
    MinotaurSprites[2] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur3.png")));
    MinotaurSprites[3] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur4.png")));
    MinotaurSprites[4] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur5.png")));
    MinotaurSprites[5] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur6.png")));
    MinotaurSprites[6] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur7.png")));
    MinotaurSprites[7] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur8.png")));
    MinotaurSprites[8] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur1.png")));
    MinotaurSprites[9] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur2.png")));
    MinotaurSprites[10] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur3.png")));
    MinotaurSprites[11] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur4.png")));
    MinotaurSprites[12] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur5.png")));
    MinotaurSprites[13] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur6.png")));
    MinotaurSprites[14] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur7.png")));
    MinotaurSprites[15] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur8.png")));

    MinotaurSprites[8].flip(true, false);
    MinotaurSprites[9].flip(true, false);
    MinotaurSprites[10].flip(true, false);
    MinotaurSprites[11].flip(true, false);
    MinotaurSprites[12].flip(true, false);
    MinotaurSprites[13].flip(true, false);
    MinotaurSprites[14].flip(true, false);
    MinotaurSprites[15].flip(true, false);

    MinotaurSprites[16] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur11.png")));

    minotaur = new Minotaur("Minotaur", 350, new Vector2(11105, 115), 5, MinotaurSprites, 5.0f);

    for (HorizontalPlatform platform : platforms) {
      platform.setImage(platformTexture);
    }
    Kevlar kevlar = new Kevlar();
    kevlar.setPosition(new Vector2(3355, 447)); // Set the position as per your requirement
    items.add(kevlar);

    Seringue seringue = new Seringue(player);
    seringue.setPosition(new Vector2(565, 1055));
    items.add(seringue);

    Seringue seringue1 = new Seringue(player);
    seringue1.setPosition(new Vector2(3125, 1279));
    items.add(seringue1);

    Seringue seringue2 = new Seringue(player);
    seringue2.setPosition(new Vector2(7420, 703));
    items.add(seringue2);

    Seringue seringue3 = new Seringue(player);
    seringue3.setPosition(new Vector2(10665, 95));
    items.add(seringue3);
    //result.player.setEquippedWeapon(new Chaos());
    inventoryStage = new Stage();
        Gdx.input.setInputProcessor(inventoryStage);

  }
  public void create(Player loadedPlayer) {
  LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics2();

  font = result.font;
  camera = result.camera;
  batch = result.batch;
  map = result.map;
  mapRenderer = result.mapRenderer;
  sprites = result.sprites;
  weaponsprites = result.weaponsprites;
  healthBar = result.healthBar;
  movementController = result.movementController;
  entityMovement = result.entityMovement;
  player = loadedPlayer;  // Use the loaded player instead of creating a new one
  //npc = new ClassicNpc("Npc1", 100, new Vector2(0, 0), 5, sprites, weaponsprites, 5.0f);
  platformTexture = new Texture("PlateformCyber.png");
    platformCyber = new HorizontalPlatform("plat", 12460, 1200, spriteWidth + 55 , spriteWidth);
    platforms.add(platformCyber);
    chaos = new Chaos();
    chaos.setPosition(new Vector2(10700, 95));
    manager = new AssetManager();
    manager.load("explosion.mp3", Sound.class);
    manager.load("gunShoot.mp3", Sound.class);
    manager.load("????.mp3", Music.class);
    manager.finishLoading();
    messageNpc =
      new MessageNpc(
        "Npc",
        100,
        new Vector2(12250, 1343),
        5,
        sprites,
        5.0f,
        "Kill the Minotaur to join me !"
      );
    messageNpc2 =
      new MessageNpc(
        "Npc",
        100,
        new Vector2(10200, 223),
        5,
        sprites,
        5.0f,
        "You need to go in the arena to fight the Minotaur!"
      );

    TextureRegion[] MinotaurSprites = new TextureRegion[17];
    MinotaurSprites[0] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur1.png")));
    MinotaurSprites[1] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur2.png")));
    MinotaurSprites[2] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur3.png")));
    MinotaurSprites[3] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur4.png")));
    MinotaurSprites[4] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur5.png")));
    MinotaurSprites[5] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur6.png")));
    MinotaurSprites[6] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur7.png")));
    MinotaurSprites[7] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur8.png")));
    MinotaurSprites[8] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur1.png")));
    MinotaurSprites[9] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur2.png")));
    MinotaurSprites[10] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur3.png")));
    MinotaurSprites[11] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur4.png")));
    MinotaurSprites[12] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur5.png")));
    MinotaurSprites[13] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur6.png")));
    MinotaurSprites[14] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur7.png")));
    MinotaurSprites[15] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur8.png")));

    MinotaurSprites[8].flip(true, false);
    MinotaurSprites[9].flip(true, false);
    MinotaurSprites[10].flip(true, false);
    MinotaurSprites[11].flip(true, false);
    MinotaurSprites[12].flip(true, false);
    MinotaurSprites[13].flip(true, false);
    MinotaurSprites[14].flip(true, false);
    MinotaurSprites[15].flip(true, false);

    MinotaurSprites[16] =
      new TextureRegion(new Texture(Gdx.files.internal("minotaur11.png")));

    minotaur = new Minotaur("Minotaur", 350, new Vector2(11105, 115), 5, MinotaurSprites, 5.0f);

for (HorizontalPlatform platform : platforms) {
      platform.setImage(platformTexture);
    }

    Kevlar kevlar = new Kevlar();
    kevlar.setPosition(new Vector2(3355, 447)); // Set the position as per your requirement
    items.add(kevlar);

    Seringue seringue = new Seringue(player);
    seringue.setPosition(new Vector2(565, 1055));
    items.add(seringue);

    Seringue seringue1 = new Seringue(player);
    seringue1.setPosition(new Vector2(3125, 1279));
    items.add(seringue1);

    Seringue seringue2 = new Seringue(player);
    seringue2.setPosition(new Vector2(7420, 703));
    items.add(seringue2);

    Seringue seringue3 = new Seringue(player);
    seringue3.setPosition(new Vector2(10665, 95));
    items.add(seringue3);

        Stage inventoryStage = new Stage();
        Gdx.input.setInputProcessor(inventoryStage);
        gameData = new GameData();
}

  @Override
  public void render() {
    if (LevelFunctions.checkLevelCompletion(player, levelCompleted, levelFunctions, game, gameData)) {
        if (!gameSaved) {
            System.out.println("j'essaie de save");
            LevelFunctions.saveGameIfLevelCompleted(gameData, levelCompleted);
            gameSaved = true;
            levelFunctions.setGame(game);
            if (!isLoadScheduled) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                levelFunctions.loadLastSaveAndStartGame();
                            }
                        });
                    }
                }, 3000); // Delay in milliseconds
                isLoadScheduled = true;
            }
        }
        LevelFunctions.displaySuccessScreen(game, levelFunctions);
    } else if (player.isGameOver()) {
      if (!gameOverScreenSetUp) {
          LevelFunctions.setupGameOverScreen(gameOverScreenSetUp);
          if (!isGameOverSaved) {
              LevelFunctions.saveGameAtm(gameData);
              isGameOverSaved = true;
          }
          levelFunctions.setGame(game);
          if (!isLoadScheduled) {
              new Timer().schedule(new TimerTask() {
                  @Override
                  public void run() {
                      Gdx.app.postRunnable(new Runnable() {
                          @Override
                          public void run() {
                              levelFunctions.loadLastSaveAndStartGame();
                          }
                      });
                  }
              }, 3000); // Delay in milliseconds
              isLoadScheduled = true;
          }
      }
} else {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("ColisionLayer");
    TiledMapTileLayer damageLayer = (TiledMapTileLayer) map.getLayers().get("DamageLayer");
    //Movement controllers
    movementController.updateMovement();
    //entityMovement.updateMovement(npc, player.getPosition());
    player.update(Gdx.graphics.getDeltaTime());
    player.savePreviousPosition();
    // player.setHealth(100); //IF U WANT TO CHEAT
    //npc.savePreviousPosition();
    movementController.updatePlayerPosition(player, collisionLayer);
    //entityMovement.updateNpcPosition(npc, player.getPosition());
    System.out.println(player.getPosition().x + " " + player.getPosition().y);
    //Basics IMPORTANTS Functions
    LevelFunctions.handleKeyPresses(items, player);
    LevelFunctions.preventFallOffScreen(player);
    LevelFunctions.updateCamera(camera, player, map);
    gameData = new GameData();
    gameData.setHealth(player.getHealth());
    gameData.setPosition(player.getPosition());
    gameData.setInventory(player.getInventory());
    gameData.setEquippedWeapon(player.getEquippedWeapon());
    gameData.setLevelNumber(1); // for Level0
    LevelFunctions.saveGameIfKeyPressed(gameData);
    System.out.println(player.getPosition());
    if (!minotaur.isDead()) {
    LevelFunctions.handlePlatformInteractions2(platforms, player);
    }
    minotaur.update(Gdx.graphics.getDeltaTime());
    if (!minotaur.isDead()) {
    LevelFunctions.minotaurRushAttack(minotaur, player);
    minotaur.moveLeft(Gdx.graphics.getDeltaTime());
    minotaur.moveRight(Gdx.graphics.getDeltaTime());
    }
    if (Math.abs(player.getPosition().x - 10850) < epsilon && Math.abs(player.getPosition().y - 1343) < epsilon) {
      levelCompleted = true;
  }
    batch.begin();
    //Basics IMPORTANTS Functions , that need visual
    LevelFunctions.renderMap(batch, camera, mapRenderer);
    //LevelFunctions.handlePlatformInteractions(platforms, player, npc);
    LevelFunctions.handleDamageLayer(damageLayer, player);
    LevelFunctions.handleCollisionLayer(collisionLayer, player);
    LevelFunctions.drawHealthBar(batch, player, healthBar, font, camera,shield,player.isArmorEquipped());
    if (!minotaur.isDead()) {
    LevelFunctions.handleDamageforMinotaur(minotaur, player.getBullets(), player);
    }
    // LevelFunctions.drawSeringue(batch, seringue);
    if (!minotaur.isDead()) {
    LevelFunctions.drawPlatforms(batch, platforms);
    }
    LevelFunctions.drawItems(batch, items);

    LevelFunctions.drawPlayerDamage(batch, player);
    LevelFunctions.updateDamageTaken(player);
    LevelFunctions.playerAttackWeapon(player);
    LevelFunctions.drawWeapon(batch, chaos, player);
    LevelFunctions.renderBullet(batch, player.getBullets());
    LevelFunctions.removeBullet(player.getBullets(), camera);
    if (!minotaur.isDisapear()) {
    LevelFunctions.drawMinotaurHealthBar(batch, minotaur, healthBar, font);
    LevelFunctions.drawMinotaur(batch, minotaur);
    }
    // LevelFunctions.drawPlayerDamage(batch, player);
    //Draw entities (Maybe we can do a function for that)
    //batch.draw(npc.getCurrentSprite(), npc.getPosition().x, npc.getPosition().y, spriteWidth, spriteWidth);
    batch.draw(
        messageNpc.getCurrentSprite(),
        messageNpc.getPosition().x,
        messageNpc.getPosition().y,
        spriteWidth,
        spriteWidth
      );
      if (
        !messageNpc.isMessageDisplayed() &&
        player.getPosition().dst(messageNpc.getPosition()) <
        messageDisplayDistance
      ) {
        if (!minotaur.isDead()) {
    font.setColor(Color.RED); // Set the font color to red
    font.draw(batch, messageNpc.getMessage(), messageNpc.getPosition().x + spriteWidth, messageNpc.getPosition().y + 130);
    font.setColor(Color.WHITE); // Reset the font color to white
        } else {
          font.setColor(Color.RED); // Set the font color to red
    font.draw(batch, "Good Job ! Grab the mini-golem\non my left\nto launch the next level !", messageNpc.getPosition().x + spriteWidth, messageNpc.getPosition().y + 130);
    font.setColor(Color.WHITE); // Reset the font color to white
      }
    }
    batch.draw(
        messageNpc2.getCurrentSprite(),
        messageNpc2.getPosition().x,
        messageNpc2.getPosition().y,
        spriteWidth,
        spriteWidth
      );
      if (
        !messageNpc2.isMessageDisplayed() &&
        player.getPosition().dst(messageNpc2.getPosition()) <
        messageDisplayDistance
      ) {
    font.setColor(Color.RED); // Set the font color to red
    font.draw(batch, messageNpc2.getMessage(), messageNpc2.getPosition().x + spriteWidth, messageNpc2.getPosition().y + 130);
    font.setColor(Color.WHITE); // Reset the font color to white
      }
    // End batch
    batch.end();
    LevelFunctions.toggleInventory(inventoryStage, player, camera);

  }
}

  @Override
  public void dispose() {
    batch.dispose();
  }
}
