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
import com.stroin.game.controllers.MovementController;
import com.stroin.game.data.GameData;
import com.stroin.game.elements.BouleDeFeu;
import com.stroin.game.elements.HorizontalPlatform;
import com.stroin.game.elements.MovablePlatform;
import com.stroin.game.elements.SonicBall;
import com.stroin.game.entity.BabyDrake;
import com.stroin.game.entity.BossDrake;
import com.stroin.game.entity.ClassicNpc;
// import com.stroin.game.entity.EnemyNpc;
import com.stroin.game.entity.EntityNpcMovement;
import com.stroin.game.entity.MessageNpc;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Consumables.Seringue;
import com.stroin.game.items.Equipments.Kevlar;

public class Level2 extends ApplicationAdapter implements Level {

  private MovementController movementController;
  private EntityNpcMovement entityMovement;
  private Player player;
  private ClassicNpc npc;
  private MessageNpc messageNpc;
  private BossDrake bossDrake;
  private BabyDrake babyDrake1;
  private BabyDrake babyDrake2;
  // private EnemyNpc enemyNpc;
  private SpriteBatch batch;
  private TextureRegion[] sprites;
  private Texture healthBar;
  private Texture bossHealthBar;
  private Texture bouleTexture;
  private Texture sonicBallTexture;
  private int spriteWidth = 100;
  private Texture platformTexture;
  private List<HorizontalPlatform> platforms = new ArrayList<>();
  public List<MovablePlatform> platforms2 = new ArrayList<>();
  public List<BouleDeFeu> boules = new ArrayList<>();
  private TiledMap map;
  private OrthogonalTiledMapRenderer mapRenderer;
  private OrthographicCamera camera;
  private Seringue seringue;
  private BitmapFont font;
  private TextureRegion[] weaponsprites;
  float platformSpawnTimer = 0;
  float bouleDeFeuSpawnTimer = 0;
  private float timeSinceLastAttack = 0;
  private float timeSinceLastAttack1 = 0;
  private float timeSinceLastAttack2 = 0;
  private boolean levelCompleted = false;
  private float disapearTimer = 0;
  private boolean gameOverScreenSetUp = false;
  private StroIN game;
  private GameData gameData;
  private boolean isLoadScheduled = false;
  private float messageDisplayDistance = 400;
  private boolean phase1 = false;
  private boolean phase2 = false;
  private boolean cheater = false;
  private float delta;
  private boolean gameSaved = false;
  private boolean isGameOverSaved = false;
  LevelFunctions levelFunctions = new LevelFunctions();
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
    LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics3();

    font = result.font;
    camera = result.camera;
    batch = result.batch;
    map = result.map;
    mapRenderer = result.mapRenderer;
    sprites = result.sprites;
    healthBar = result.healthBar;
    //System.out.println("healthBar" + healthBar);
    bossHealthBar = result.bossHealthBar;
    movementController = result.movementController;
    entityMovement = result.entityMovement;
    player = result.player;
    manager = new AssetManager();
    manager.load("explosion.mp3", Sound.class);
    manager.load("gunShoot.mp3", Sound.class);
    manager.load("????.mp3", Music.class);
    manager.finishLoading();
    Kevlar kevlar = new Kevlar();
    kevlar.setPosition(new Vector2(2305, 0)); // Set the position as per your requirement

    Seringue seringue = new Seringue(player);
    seringue.setPosition(new Vector2(420, 2367));

    items.add(kevlar);
    items.add(seringue);
    npc =
      new ClassicNpc(
        "Npc1",
        100,
        new Vector2(0, 0),
        5,
        sprites,
        weaponsprites,
        5.0f
      );
    messageNpc =
      new MessageNpc(
        "Npc",
        100,
        new Vector2(300, 0),
        5,
        sprites,
        5.0f,
        "Press [F] to SHOOT A SONIC BALL!\nPEW PEW PEW!\nDid you hear that noise up there ? Terrifying !"
      );
    platformTexture = new Texture("PlateformCyber.png");
    bouleTexture = new Texture("boule.png");
    sonicBallTexture = new Texture("SonicBall.png");
    TextureRegion[] bossDrakeSprites = new TextureRegion[5];
    bossDrakeSprites[0] =
      new TextureRegion(new Texture(Gdx.files.internal("BossDrake1.png")));
    bossDrakeSprites[1] =
      new TextureRegion(new Texture(Gdx.files.internal("BossDrake2.png")));
    bossDrakeSprites[2] =
      new TextureRegion(new Texture(Gdx.files.internal("BossDrake3.png")));
    bossDrakeSprites[3] =
      new TextureRegion(new Texture(Gdx.files.internal("BossDrake4.png")));
    bossDrakeSprites[3].flip(true, false);
    bossDrakeSprites[4] =
      new TextureRegion(new Texture(Gdx.files.internal("BossDrake5.png")));
    bossDrakeSprites[4].flip(true, false);
    gameData = new GameData();
    bossDrake =
      new BossDrake(
        "BossDrake",
        750,
        new Vector2(1800, 2750),
        10,
        bossDrakeSprites,
        0
      );
    babyDrake1 =
      new BabyDrake(
        "BabyDrake1",
        100,
        new Vector2(1650, 3060),
        10,
        bossDrakeSprites,
        0
      );
    babyDrake2 =
      new BabyDrake(
        "BabyDrake2",
        100,
        new Vector2(1650, 2720),
        10,
        bossDrakeSprites,
        0
      );

    for (HorizontalPlatform platform : platforms) {
      platform.setImage(platformTexture);
    }
    for (MovablePlatform platform : platforms2) {
      platform.setImage(platformTexture);
    }
    seringue = new Seringue(player);
    seringue.setPosition(new Vector2(420, 2367));
inventoryStage = new Stage();
Gdx.input.setInputProcessor(inventoryStage);
  }

  public void create(Player loadedPlayer) {
    LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics3();

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
    manager = new AssetManager();
    manager.load("explosion.mp3", Sound.class);
    manager.load("gunShoot.mp3", Sound.class);
    manager.load("????.mp3", Music.class);
    manager.finishLoading();
    player = loadedPlayer; // Use the loaded player instead of creating a new one
    npc =
      new ClassicNpc(
        "Npc1",
        100,
        new Vector2(0, 0),
        5,
        sprites,
        weaponsprites,
        5.0f
      );
    messageNpc =
      new MessageNpc(
        "Npc",
        100,
        new Vector2(300, 0),
        5,
        sprites,
        5.0f,
        "Press [F] to SHOOT A SONIC BALL!\nPEW PEW PEW!\nDid you hear that noise up there ? Terrifying !"
      );

    platformTexture = new Texture("PlateformCyber.png");
    bouleTexture = new Texture("boule.png");
    sonicBallTexture = new Texture("SonicBall.png");
    TextureRegion[] bossDrakeSprites = new TextureRegion[5];
    bossDrakeSprites[0] =
      new TextureRegion(new Texture(Gdx.files.internal("BossDrake1.png")));
    bossDrakeSprites[1] =
      new TextureRegion(new Texture(Gdx.files.internal("BossDrake2.png")));
    bossDrakeSprites[2] =
      new TextureRegion(new Texture(Gdx.files.internal("BossDrake3.png")));
    bossDrakeSprites[3] =
      new TextureRegion(new Texture(Gdx.files.internal("BossDrake4.png")));
    bossDrakeSprites[3].flip(true, false);
    bossDrakeSprites[4] =
      new TextureRegion(new Texture(Gdx.files.internal("BossDrake5.png")));
    bossDrakeSprites[4].flip(true, false);
    bossDrake =
    new BossDrake(
        "BossDrake",
        750,
        new Vector2(1800, 2750),
        10,
        bossDrakeSprites,
        0
      );
    babyDrake1 =
      new BabyDrake(
        "BabyDrake1",
        100,
        new Vector2(1650, 3060),
        10,
        bossDrakeSprites,
        0
      );
    babyDrake2 =
      new BabyDrake(
        "BabyDrake2",
        100,
        new Vector2(1650, 2720),
        10,
        bossDrakeSprites,
        0
      );

        
    for (HorizontalPlatform platform : platforms) {
      platform.setImage(platformTexture);
    }
    for (MovablePlatform platform : platforms2) {
      platform.setImage(platformTexture);
    }
    Kevlar kevlar = new Kevlar();
    kevlar.setPosition(new Vector2(2305, 0)); // Set the position as per your requirement

    Seringue seringue = new Seringue(player);
    seringue.setPosition(new Vector2(420, 2367));

    items.add(kevlar);
    items.add(seringue);
    Stage inventoryStage = new Stage();
    Gdx.input.setInputProcessor(inventoryStage);
    gameData = new GameData();
  }

  @Override
  public void render() {
    delta = Gdx.graphics.getDeltaTime();
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
      TiledMapTileLayer plateforme1Layer = (TiledMapTileLayer) map
        .getLayers()
        .get("Plateforme1Layer");
      //TiledMapTileLayer plateforme2Layer = (TiledMapTileLayer) map.getLayers().get("Plateforme2Layer");
      //TiledMapTileLayer damageLayer = (TiledMapTileLayer) map.getLayers().get("DamageLayer");
      //Movement controllers
      platformSpawnTimer += Gdx.graphics.getDeltaTime();
      bouleDeFeuSpawnTimer += Gdx.graphics.getDeltaTime();

      if (platformSpawnTimer > 0.2) { // Spawn a new platform every 0.25 seconds
        LevelFunctions.spawnMovablePlatform(
          150,
          50,
          platforms2,
          platformTexture,
          camera
        );
        platformSpawnTimer = 0;
      }

      if (bouleDeFeuSpawnTimer > 2) { // Spawn a new BouleDeFeu every 1 second
        LevelFunctions.spawnBouleDeFeu(150, 50, boules, bouleTexture, camera);
        bouleDeFeuSpawnTimer = 0;
      }
      if (phase1) {
        bossDrake.update(Gdx.graphics.getDeltaTime());
      }
      if (phase2) {
        babyDrake1.update(Gdx.graphics.getDeltaTime());
        babyDrake2.update(Gdx.graphics.getDeltaTime());
      }
      player.update(Gdx.graphics.getDeltaTime());
      timeSinceLastAttack += Gdx.graphics.getDeltaTime();
      timeSinceLastAttack1 += Gdx.graphics.getDeltaTime();
      timeSinceLastAttack2 += Gdx.graphics.getDeltaTime();
      if (timeSinceLastAttack >= 2.5) {
        bossDrake.attack();
        timeSinceLastAttack = 0;
      }
      if (phase2) {
        if (!babyDrake1.isDisapear()) {
          if (timeSinceLastAttack1 >= 4) {
            babyDrake1.attack();
            timeSinceLastAttack1 = 0;
          }
        }
        if (!babyDrake2.isDisapear()) {
          if (timeSinceLastAttack2 >= 4) {
            babyDrake2.attack();
            timeSinceLastAttack2 = 0;
          }
        }
      }
      gameData.setHealth(player.getHealth());
      gameData.setPosition(player.getPosition());
      gameData.setInventory(player.getInventory());
      gameData.setEquippedWeapon(player.getEquippedWeapon());
      gameData.setLevelNumber(2); // for Level0
      LevelFunctions.saveGameIfKeyPressed(gameData);
      float delta = Gdx.graphics.getDeltaTime();
      LevelFunctions.cheat(player, cheater);
      movementController.updateMovement();
      entityMovement.updateMovement(npc, player.getPosition());
      player.savePreviousPosition();
      npc.savePreviousPosition();
      movementController.updatePlayerPosition(
        player,
        plateforme1Layer,
        platforms2
      );
      entityMovement.updateNpcPosition(npc, player.getPosition());

      //Basics IMPORTANTS Functions
      //System.out.println(player.getPosition().x + " " + player.getPosition().y);
      LevelFunctions.handleKeyPresses(items,player);
      phase1 = LevelFunctions.phase1BossDrake(player, bossDrake);
      phase2 = LevelFunctions.phase2BossDrake(bossDrake);
      LevelFunctions.preventFallOffScreen(player, npc);
      LevelFunctions.updateCamera(camera, player, map);
      LevelFunctions.handleDamageByHitBoxBossDrake(bossDrake, player);
      if (phase1) {
        bossDrake.moveUp(delta);
        bossDrake.moveDown(delta);
      }
      batch.begin();
      //Basics IMPORTANTS Functions , that need visual
      LevelFunctions.renderMap(batch, camera, mapRenderer);
      LevelFunctions.handlePlatformInteractions(platforms2, player, npc);
      LevelFunctions.attackPlayer(player);
      //player.movePush(delta);
      //LevelFunctions.handleDamageLayer(damageLayer, player);
      LevelFunctions.handleDamageLayerForBouleDeFeu(boules, player);
      if (phase1) {
        LevelFunctions.handleDamageLayerForBouleDeFeu2(
          bossDrake.getProjectiles(),
          player
        );
      }
      //if (!bossDrake.getProjectiles().isEmpty()) {
      //LevelFunctions.handleBouleDeFeuCollision(bossDrake.getProjectiles(), plateforme1Layer);
      //}
      if (phase2) {
        if (!babyDrake1.isDisapear()) {
          LevelFunctions.handleDamageLayerForBouleDeFeu2(
            babyDrake1.getProjectiles(),
            player
          );
          LevelFunctions.handleDamageForBossDrake(
            babyDrake1,
            player.getProjectiles()
          );
        }
        if (!babyDrake2.isDisapear()) {
          LevelFunctions.handleDamageForBossDrake(
            babyDrake2,
            player.getProjectiles()
          );
          LevelFunctions.handleDamageLayerForBouleDeFeu2(
            babyDrake2.getProjectiles(),
            player
          );
        }
      }
      LevelFunctions.handleDamageForBossDrake(
        bossDrake,
        player.getProjectiles()
      );
      LevelFunctions.handleCollisionLayer(plateforme1Layer, player, npc);
      //LevelFunctions.handleCollisionLayer(plateforme2Layer, player, npc);
    LevelFunctions.drawHealthBar(batch, player, healthBar, font, camera,shield,player.isArmorEquipped());
      LevelFunctions.drawPlayerDamage(batch, player);
      LevelFunctions.updateDamageTaken(player);
      if (phase1) {
        if (!bossDrake.isDisapear()) {
          LevelFunctions.drawBossHealthBar(
            batch,
            bossDrake,
            bossHealthBar,
            font
          );
        }
      }
    LevelFunctions.drawItems(batch, items);
      LevelFunctions.drawPlatforms(batch, platforms);
      // LevelFunctions.drawPlayerDamage(batch, player);
      //Draw entities (Maybe we can do a function for that)
      List<MovablePlatform> toRemove = new ArrayList<>();
      for (MovablePlatform platform : platforms2) {
        platform.update(Gdx.graphics.getDeltaTime());
        float leftEdgeOfScreen = camera.position.x - camera.viewportWidth / 2;
        if (platform.getX() + platform.getWidth() < leftEdgeOfScreen) {
          toRemove.add(platform);
        } else {
          platform.draw(batch);
        }
      }
      platforms2.removeAll(toRemove);
      toRemove.clear();
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
    font.setColor(Color.YELLOW); // Set the font color to yellow
    font.draw(batch, messageNpc.getMessage(), messageNpc.getPosition().x + spriteWidth, messageNpc.getPosition().y + 130);
    font.setColor(Color.WHITE); // Reset the font color to white

      }
      

      List<BouleDeFeu> toRemove2 = new ArrayList<>();
      for (BouleDeFeu boule : boules) {
        boule.update(Gdx.graphics.getDeltaTime());
        float leftEdgeOfScreen = camera.position.x - camera.viewportWidth / 2;
        if (boule.getX() + boule.getWidth() < leftEdgeOfScreen) {
          toRemove2.add(boule);
        } else {
          boule.draw(batch);
        }
      }

      boules.removeAll(toRemove2);

      TextureRegion currentSprite = bossDrake.getSprites()[bossDrake.getCurrentSpriteIndex()];
      float scale = 2f;
      if (phase1) {
        if (!bossDrake.isDisapear()) {
          batch.draw(
            currentSprite,
            bossDrake.getPosition().x,
            bossDrake.getPosition().y,
            currentSprite.getRegionWidth() * scale, // Scale the width
            currentSprite.getRegionHeight() * scale // Scale the height
          );
        }
      }
      if (phase2) {
        if (!babyDrake1.isDisapear()) {
          TextureRegion currentSprite1 = babyDrake1.getSprites()[babyDrake1.getCurrentSpriteIndex()];
          batch.draw(
            currentSprite1,
            babyDrake1.getPosition().x,
            babyDrake1.getPosition().y,
            currentSprite.getRegionWidth(),
            currentSprite.getRegionHeight()
          );
        }
        if (!babyDrake2.isDisapear()) {
          TextureRegion currentSprite2 = babyDrake2.getSprites()[babyDrake2.getCurrentSpriteIndex()];
          batch.draw(
            currentSprite2,
            babyDrake2.getPosition().x,
            babyDrake2.getPosition().y,
            currentSprite.getRegionWidth(),
            currentSprite.getRegionHeight()
          );
        }
      }
      for (SonicBall sonicBall : player.getProjectiles()) {
        sonicBall.update(Gdx.graphics.getDeltaTime());
        sonicBall.setImage(sonicBallTexture);
        sonicBall.draw(batch);
      }
      if (phase1) {
        if (!bossDrake.isDisapear()) {
          for (BouleDeFeu boule : bossDrake.getProjectiles()) {
            boule.update(Gdx.graphics.getDeltaTime());
            boule.setImage(bouleTexture);
            boule.draw(batch);
          }
        }
      }
      if (phase2) {
        if (!babyDrake1.isDisapear()) {
          for (BouleDeFeu boule : babyDrake1.getProjectiles()) {
            boule.update(Gdx.graphics.getDeltaTime());
            boule.setImage(bouleTexture);
            boule.draw(batch);
          }
        }
        if (!babyDrake2.isDisapear()) {
          for (BouleDeFeu boule : babyDrake2.getProjectiles()) {
            boule.update(Gdx.graphics.getDeltaTime());
            boule.setImage(bouleTexture);
            boule.draw(batch);
          }
        }
      }
      if (bossDrake.isDisapear()) {
        disapearTimer += Gdx.graphics.getDeltaTime();
        if (disapearTimer >= 1) {
          levelCompleted = true;
        }
      }
      System.out.println(player.getPosition());
      batch.end();
    LevelFunctions.toggleInventory(inventoryStage, player, camera);

    }
  }

  @Override
  public void dispose() {
    batch.dispose();
  }
}
