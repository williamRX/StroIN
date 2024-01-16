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
import com.stroin.game.controllers.MovementController;
import com.stroin.game.data.GameData;
import com.stroin.game.elements.HorizontalPlatform;
import com.stroin.game.entity.Blaster;
import com.stroin.game.entity.ClassicNpc;
import com.stroin.game.entity.Enemy;
import com.stroin.game.entity.EntityNpcMovement;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Items;
import com.stroin.game.items.Consumables.Seringue;
import com.stroin.game.items.Equipments.Kevlar;
import com.stroin.game.items.Weapons.Chaos;
import com.stroin.game.entity.Enemy;
public class Level3 extends ApplicationAdapter implements Level {

  private MovementController movementController;
  private EntityNpcMovement entityMovement;
  private Player player;
  private ClassicNpc npc;
  private SpriteBatch batch;
  private TextureRegion[] sprites;
  private Texture healthBar;
  private Texture blasterBallTexture;
  private int spriteWidth = 100;
  private Texture platformTexture;
  private List<HorizontalPlatform> platforms = new ArrayList<>();
  private TiledMap map;
  private OrthogonalTiledMapRenderer mapRenderer;
  private OrthographicCamera camera;
  private Seringue seringue;
  private Seringue seringue1;
  private Seringue seringue2;
  private Seringue seringue3;
  private boolean isLoadScheduled = false;
  private BitmapFont font;
  private TextureRegion[] weaponsprites;
  private boolean gameOverScreenSetUp = false;
  Stage stage;
  private Blaster blaster;
  private boolean phase2 = false;
List<Enemy> enemies = new ArrayList<>();
  private GameData gameData;
  private boolean levelCompleted = false;
  private boolean gameSaved = false;
  private boolean isGameOverSaved = false;
  private LevelFunctions levelFunctions = new LevelFunctions();
    private StroIN game;
  private Stage inventoryStage;  
  private boolean alreadyInterphased = false;
  private Enemy enemy;
  public static AssetManager manager;
  private float disapearTimer = 0;
List<Object> items = new ArrayList<>();
    Texture shield = new Texture("shield.png");
    
    Vector2[] positions = new Vector2[] {
      new Vector2(1430, 128),
      new Vector2(3850, 896),
      new Vector2(5145, 544),
      new Vector2(9630, 1280),
      new Vector2(10750, 640),
      new Vector2(13445, 864),
  };
    public void createEntities() {

      // Create enemies
      for (int i = 0; i < positions.length; i++) {
          Enemy enemy = new Enemy(null, 50, positions[i], 4, sprites, disapearTimer/* parameters for creating an enemy */);
          enemies.add(enemy);
      }
    }




    @Override
    public void passGame(StroIN game) {
        this.game = game;
    }
    public void getGame(StroIN game) {
        this.game = game;
    }
  @Override
  public void create() {
    LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics4();


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
    player = result.player;
    manager = new AssetManager();
    manager.load("explosion.mp3", Sound.class);
    manager.load("gunShoot.mp3", Sound.class);
    manager.load("????.mp3", Music.class);
    manager.finishLoading();
Music backgroundMusic = manager.get("????.mp3", Music.class);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    npc = new ClassicNpc("Npc1", 100, new Vector2(0, 0), 5, sprites,weaponsprites, 5.0f);
    platformTexture = new Texture("PlateformCyber.png");
    blasterBallTexture = new Texture("BallBlaster.png");
    for (HorizontalPlatform platform : platforms) {
      platform.setImage(platformTexture);
    }
    gameData = new GameData();
    Kevlar kevlar = new Kevlar();
    kevlar.setPosition(new Vector2(4810, 224)); // Set the position as per your requirement
    items.add(kevlar);

    Seringue seringue = new Seringue(player);
    seringue.setPosition(new Vector2(1065, 1472));
    items.add(seringue);

    Seringue seringue1 = new Seringue(player);
    seringue1.setPosition(new Vector2(6750, 512));
    items.add(seringue1);

    Seringue seringue2 = new Seringue(player);
    seringue2.setPosition(new Vector2(10790, 640));
    items.add(seringue2);

    Seringue seringue3 = new Seringue(player);
    seringue3.setPosition(new Vector2(13990, 224));
    items.add(seringue3);
            Chaos chaos = new Chaos();
        result.player.setEquippedWeapon(chaos);

    TextureRegion[] BlasterSprites = new TextureRegion[30];

    for (int i = 0; i < 15; i++) {
      BlasterSprites[i] = new TextureRegion(new Texture(Gdx.files.internal("Blaster" + (i + 1) + ".png")));
  }

    for (int i = 0; i < 15; i++) {
      BlasterSprites[i + 15] = new TextureRegion(BlasterSprites[i]);
      BlasterSprites[i + 15].flip(true, false);
  }
  blaster = new Blaster("Blaster", 500, new Vector2(14500, 122), 5, BlasterSprites, 5.0f);

inventoryStage = new Stage();
Gdx.input.setInputProcessor(inventoryStage);
createEntities();
  }
  public void create(Player loadedPlayer) {
  LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics4();

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
  npc = new ClassicNpc("Npc1", 100, new Vector2(0, 0), 5, sprites, weaponsprites, 5.0f);
  platformTexture = new Texture("PlateformCyber.png");
  gameData = new GameData();


     Kevlar kevlar = new Kevlar();
    kevlar.setPosition(new Vector2(4810, 224)); // Set the position as per your requirement
    items.add(kevlar);
    
  Seringue seringue = new Seringue(player);
    seringue.setPosition(new Vector2(1065, 1472));
    items.add(seringue);

    Seringue seringue1 = new Seringue(player);
    seringue1.setPosition(new Vector2(6750, 512));
    items.add(seringue1);

    Seringue seringue2 = new Seringue(player);
    seringue2.setPosition(new Vector2(10790, 640));
    items.add(seringue2);

    Seringue seringue3 = new Seringue(player);
    seringue3.setPosition(new Vector2(13990, 224));
        Chaos chaos = new Chaos();
        chaos.setPosition(new Vector2(200, 100));
        result.player.setEquippedWeapon(chaos);

        Stage inventoryStage = new Stage();
        Gdx.input.setInputProcessor(inventoryStage);
        player.setEquippedWeapon(chaos);

    TextureRegion[] BlasterSprites = new TextureRegion[30];

    for (int i = 0; i < 15; i++) {
      BlasterSprites[i] = new TextureRegion(new Texture(Gdx.files.internal("Blaster" + (i + 1) + ".png")));
  }

    for (int i = 0; i < 15; i++) {
      BlasterSprites[i + 15] = new TextureRegion(BlasterSprites[i]);
      BlasterSprites[i + 15].flip(true, false);
  }
   blaster = new Blaster("Blaster", 500, new Vector2(14500, 122), 5, BlasterSprites, 5.0f);
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
        LevelFunctions.displaySuccessScreen2(game, levelFunctions);
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
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("CollisionLayer");
    TiledMapTileLayer damageLayer = (TiledMapTileLayer) map.getLayers().get("DamageLayer");
    if (LevelFunctions.interphase(blaster, alreadyInterphased)) {
      alreadyInterphased = true;
    }
    //Movement controllers
    movementController.updateMovement();
    entityMovement.updateMovement(npc, player.getPosition());
    player.update(Gdx.graphics.getDeltaTime());

    phase2 = LevelFunctions.phase2Blaster(blaster);

    player.savePreviousPosition();
    npc.savePreviousPosition();
    movementController.updatePlayerPosition(player, collisionLayer);
    entityMovement.updateNpcPosition(npc, player.getPosition());
    LevelFunctions.updatePosEnemies(enemies, player);
    LevelFunctions.updateEnemies(enemies, Gdx.graphics.getDeltaTime());
    //Basics IMPORTANTS Functions
    gameData.setHealth(player.getHealth());
    gameData.setPosition(player.getPosition());
    gameData.setInventory(player.getInventory());
    gameData.setEquippedWeapon(player.getEquippedWeapon());
    gameData.setLevelNumber(3); // for Level0
    LevelFunctions.saveGameIfKeyPressed(gameData);
    LevelFunctions.handleKeyPresses(items, player);
    LevelFunctions.handleDamageforBlaster(blaster, player.getBullets(), player);
    LevelFunctions.handleDamageLayer(damageLayer, player);
    LevelFunctions.preventFallOffScreen(player, npc);
    LevelFunctions.updateCamera(camera, player, map);
    LevelFunctions.removeDeadEnemies(enemies);
    LevelFunctions.updateEnemies(enemies);
    //Mecanics Blaster Boss
    if (!alreadyInterphased && !phase2) {
    blaster.moveLeft(Gdx.graphics.getDeltaTime());
    blaster.moveRight(Gdx.graphics.getDeltaTime());
    LevelFunctions.BlasterReaction(blaster, player);
    }
    //player.setHealth(100);
    if (phase2) {
      blaster.flyLeft(Gdx.graphics.getDeltaTime());
      blaster.flyRight(Gdx.graphics.getDeltaTime());
      if (!blaster.isDead()) {
      LevelFunctions.BlasterReactionPhase2(blaster, player);
      }
    }
    if (!blaster.isDead()) {
    blaster.attack(Gdx.graphics.getDeltaTime());
    blaster.attackShort(Gdx.graphics.getDeltaTime());
    }
    blaster.transition(Gdx.graphics.getDeltaTime());
    
    batch.begin();
    blaster.update(Gdx.graphics.getDeltaTime());
    System.out.println(blaster.getState());
    //Basics IMPORTANTS Functions , that need visual
    LevelFunctions.renderMap(batch, camera, mapRenderer);
    //LevelFunctions.handlePlatformInteractions(platforms, player, npc);
    
    //System.out.println(player.getPosition().x + " " + player.getPosition().y);
    LevelFunctions.handleCollisionLayer(collisionLayer, player, npc);
    LevelFunctions.drawHealthBar(batch, player, healthBar, font, camera,shield,player.isArmorEquipped());
    if (!blaster.isDisapear()) {
    LevelFunctions.drawBlasterHealthBar(batch, blaster, healthBar, font);
    }
    LevelFunctions.drawItems(batch, items);
    LevelFunctions.drawPlatforms(batch, platforms);
    LevelFunctions.drawPlayerDamage(batch, player);
    if (!blaster.isDisapear()) {
    LevelFunctions.drawBlaster(batch, blaster);
    LevelFunctions.drawBlasterBall(batch, blaster.getProjectiles(), blasterBallTexture);
    }
    LevelFunctions.updateDamageTaken(player);
    LevelFunctions.playerAttackWeapon(player);
    System.out.println(player.getPosition());
    //LevelFunctions.handleDamageForEnemy(enemy,player.getBullets());
    LevelFunctions.handleDamageForEnemies(enemies,player.getBullets());
    LevelFunctions.handleDamageForBlasterBall(player, blaster.getProjectiles());
    LevelFunctions.renderBullet(batch, player.getBullets());
    LevelFunctions.removeBullet(player.getBullets(), camera, enemies);
    LevelFunctions.renderEnemyBalls(batch,camera, enemies);
    LevelFunctions.handleDamageForPlayer(player, enemies);
for (Enemy enemy : enemies) {
        batch.draw(enemy.getCurrentSprite(), enemy.getPosition().x, enemy.getPosition().y, spriteWidth, spriteWidth);
    }
    batch.end();
    LevelFunctions.toggleInventory(inventoryStage, player, camera);
    if (blaster.isDisapear()) {
      disapearTimer += Gdx.graphics.getDeltaTime();
      if (disapearTimer >= 1) {
        levelCompleted = true;
      }
    }

  }
  }

  @Override
  public void dispose() {
    batch.dispose();
  }
}
