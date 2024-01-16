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
import com.stroin.game.entity.ClassicNpc;
import com.stroin.game.entity.EntityNpcMovement;
import com.stroin.game.entity.MessageNpc;
import com.stroin.game.entity.Player;
import com.stroin.game.items.Consumables.Seringue;
import com.stroin.game.items.Equipments.Kevlar;
import com.stroin.game.items.Weapons.Chaos;

public class Level0 extends ApplicationAdapter implements Level {

    private MovementController movementController;
    private EntityNpcMovement entityMovement;
    private Player player;
    private ClassicNpc npc;
    private MessageNpc messageNpc1;
    private MessageNpc messageNpc2;
    private MessageNpc messageNpc3;
    private MessageNpc messageNpc4;
    private MessageNpc messageNpc5;
    private MessageNpc messageNpc6;
    private MessageNpc messageNpc7;
    private SpriteBatch batch;
    private TextureRegion[] sprites;
    private Texture healthBar;
    private Texture healthBarNpc;
    private Texture bouleTexture;
    private int spriteWidth = 100;
    private Texture platformTexture;
    private List<HorizontalPlatform> platforms = new ArrayList<>();
    private List<BouleDeFeu> balls = new ArrayList<>();
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Seringue seringue;
    private Seringue seringue1;
    private Seringue seringue2;
    private Seringue seringue3;
    private BitmapFont font;
    private TextureRegion[] weaponsprites;
    private boolean gameOverScreenSetUp = false;
    //private Stage stage;
    private Stage inventoryStage;  
    //private Stage dialogStage;
    //private Skin skin;
    private GameData gameData;
    private Chaos chaos;
    private float timeAccumulator = 0;
    private StroIN game;
    private float epsilon = 30f;
    private List<MessageNpc> messageNpcs = new ArrayList<>();
    private int messageDisplayDistance = 600;
    private LevelFunctions levelFunctions = new LevelFunctions();
    private boolean levelCompleted = false;
    private boolean gameSaved = false;
    private boolean isGameOverSaved = false;
    private boolean isLoadScheduled = false;
    public static AssetManager manager;
    Texture shield = new Texture("shield.png");
    List<Object> items = new ArrayList<>();


    //private Window.WindowStyle windowStyle;
    //private Label.LabelStyle labelStyle;
    //private TextButton.TextButtonStyle textButtonStyle;


    @Override
    public void passGame(StroIN game) {
        this.game = game;
    }
    public void getGame(StroIN game) {
        this.game = game;
    }
    public void create() {
        LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics();

        font = result.font;
        camera = result.camera;
        batch = result.batch;
        map = result.map;
        mapRenderer = result.mapRenderer;
        sprites = result.sprites;
        weaponsprites = result.weaponsprites;
        healthBar = result.healthBar;
        healthBarNpc = new Texture(Gdx.files.internal("barreNpc.png"));
        movementController = result.movementController;
        entityMovement = result.entityMovement;
        player = result.player;
        manager = new AssetManager();
        manager.load("explosion.mp3", Sound.class);
        manager.load("gunShoot.mp3", Sound.class);
        manager.load("????.mp3", Music.class);
        manager.finishLoading();
        npc = new ClassicNpc("Npc1", 100, new Vector2(200, 0), 5, sprites, weaponsprites, 5.0f);
        platformTexture = new Texture("PlateformCyber.png");
        bouleTexture = new Texture("boule_2.png");
        gameData = new GameData();
        //dialogStage = new Stage(new ScreenViewport());
        //windowStyle = new Window.WindowStyle(font, Color.BLACK, null);
        //labelStyle = new Label.LabelStyle(font, Color.BLACK);
        //textButtonStyle = new TextButton.TextButtonStyle();
        //textButtonStyle.font = font;
        //skin = new Skin();
        //skin.add("default", windowStyle, Window.WindowStyle.class);
        //skin.add("default", labelStyle, Label.LabelStyle.class);
        //skin.add("default", textButtonStyle, TextButton.TextButtonStyle.class);
    for (HorizontalPlatform platform : platforms) {
            platform.setImage(platformTexture);
        }

        Seringue seringue = new Seringue(player);
        seringue.setPosition(new Vector2(7345, 75));
        items.add(seringue);

        Seringue seringue1 = new Seringue(player);
        seringue1.setPosition(new Vector2(7395, 75));
        items.add(seringue1);

        Seringue seringue2 = new Seringue(player);
        seringue2.setPosition(new Vector2(7345, 125));
        items.add(seringue2);

        Seringue seringue3 = new Seringue(player);
        seringue3.setPosition(new Vector2(7395, 125));
        items.add(seringue3);

        Kevlar kevlar = new Kevlar();
        kevlar.setPosition(new Vector2(7370, 319));
        items.add(kevlar);
        chaos = new Chaos();
        chaos.setPosition(new Vector2(7900, 65));

        inventoryStage = new Stage();
        Gdx.input.setInputProcessor(inventoryStage);
        //TextureRegion[] npcSprites = sprites; // Replace with the actual sprites for the MessageNpc
        messageNpc1 = new MessageNpc("Npc1", 100, new Vector2(3380, 159), 5, sprites, 5.0f, "Press [ARROW UP] to JUMP!\n HOP HOP HOP!");
        messageNpc2 = new MessageNpc("Npc2", 100, new Vector2(1150, 222), 5, sprites, 5.0f, "Press [SHIFT] for SPRINT!\n GO GO GO!");
        messageNpc3 = new MessageNpc("Npc3", 100, new Vector2(6120, 0), 5, sprites, 5.0f, "Beware of the spikes and fireballs !");
        messageNpc4 = new MessageNpc("Npc4", 100, new Vector2(8638, 223), 5, sprites, 5.0f, "Try shooting on me with the [R] key !");
        messageNpc5 = new MessageNpc("Npc5", 100, new Vector2(7205, 0), 5, sprites, 5.0f, "Take syringes with [E] key\n and use it with [Q] key\n to restore your health.\nPress [Spacebar] \nto open your inventory.");
        messageNpc6 = new MessageNpc("Npc6", 100, new Vector2(7925, 1151), 5, sprites, 5.0f, "Good Job !\n You can now go to the next level !\n Grab the baby licorn !");
        messageNpc7 = new MessageNpc("Npc6", 100, new Vector2(7225, 287), 5, sprites, 5.0f, "Press [E] to take the kevlar\nto protect yourself\nfrom some damage.");
        // Add the MessageNpc objects to a list
        messageNpcs.add(messageNpc1);
        messageNpcs.add(messageNpc2);
        messageNpcs.add(messageNpc3);
        messageNpcs.add(messageNpc4);
        messageNpcs.add(messageNpc5);
                }

public void create(Player loadedPlayer) {
  LevelFunctions.InitializationResult result = LevelFunctions.initializeBasics();

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
  bouleTexture = new Texture("boule_2.png");
    manager = new AssetManager();
    manager.load("explosion.mp3", Sound.class);
    manager.load("gunShoot.mp3", Sound.class);
    manager.load("????.mp3", Music.class);
    manager.finishLoading();
  gameData = new GameData();
        List<Object> items = new ArrayList<>();

        Seringue seringue = new Seringue(player);
        seringue.setPosition(new Vector2(7345, 75));
        items.add(seringue);

        Seringue seringue1 = new Seringue(player);
        seringue1.setPosition(new Vector2(7395, 75));
        items.add(seringue1);

        Seringue seringue2 = new Seringue(player);
        seringue2.setPosition(new Vector2(7345, 125));
        items.add(seringue2);

        Seringue seringue3 = new Seringue(player);
        seringue3.setPosition(new Vector2(7395, 125));
        items.add(seringue3);

        Kevlar kevlar = new Kevlar();
        kevlar.setPosition(new Vector2(7370, 319));
        items.add(kevlar);
        chaos = new Chaos();
        chaos.setPosition(new Vector2(7900, 65));
                

        inventoryStage = new Stage();
        Gdx.input.setInputProcessor(inventoryStage);
        
        messageNpc1 = new MessageNpc("Npc1", 100, new Vector2(3380, 159), 5, sprites, 5.0f, "Press [ARROW UP] to JUMP!\n HOP HOP HOP!");
        messageNpc2 = new MessageNpc("Npc2", 100, new Vector2(1150, 222), 5, sprites, 5.0f, "Press [SHIFT] for SPRINT!\n GO GO GO!");
        messageNpc3 = new MessageNpc("Npc3", 100, new Vector2(6120, 0), 5, sprites, 5.0f, "Beware of the spikes and fireballs !");
        messageNpc4 = new MessageNpc("Npc4", 100, new Vector2(8638, 223), 5, sprites, 5.0f, "Try shooting on me with the [R] key !");
        messageNpc5 = new MessageNpc("Npc5", 100, new Vector2(7205, 0), 5, sprites, 5.0f, "Take syringes with [E] key\n and use it with [Q] key\n to restore your health.\nPress [Spacebar] to open your inventory.");
        messageNpc6 = new MessageNpc("Npc6", 100, new Vector2(7925, 1151), 5, sprites, 5.0f, "Good Job !\n You can now go to the next level !\n Grab the baby licorn !");
        messageNpc7 = new MessageNpc("Npc6", 100, new Vector2(7225, 287), 5, sprites, 5.0f, "You can take the kevlar\nto protect yourself from some damage.");
        // Add the MessageNpc objects to a list
        messageNpcs.add(messageNpc1);
        messageNpcs.add(messageNpc2);
        messageNpcs.add(messageNpc3);
        messageNpcs.add(messageNpc4);
        messageNpcs.add(messageNpc5);
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
    }     else if (player.isGameOver()) {
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

    if (Math.abs(player.getPosition().x - 7666) < epsilon && Math.abs(player.getPosition().y - 1183) < epsilon) {
        levelCompleted = true;
    } else {
        gameOverScreenSetUp = false;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("ColisionLayer");
        TiledMapTileLayer damageLayer = (TiledMapTileLayer) map.getLayers().get("DamageLayer");

        // Movement controllers, update positions, etc.
        movementController.updateMovement();
        entityMovement.updateMovement(npc, player.getPosition());
        player.update(Gdx.graphics.getDeltaTime());
        player.savePreviousPosition();
        npc.savePreviousPosition();
        
        movementController.updatePlayerPosition(player, collisionLayer);
        entityMovement.updateNpcPosition(npc, player.getPosition());
        // Basics IMPORTANT Functions
        LevelFunctions.handleKeyPresses(items,player);
        LevelFunctions.preventFallOffScreen(player, npc);
        LevelFunctions.updateCamera(camera, player, map);
        LevelFunctions.handleDamageLayerForBouleDeFeu(balls, player);
        System.out.println(player.getPosition().x + " " + player.getPosition().y);
        gameData = new GameData();
        gameData.setHealth(player.getHealth());
        gameData.setPosition(player.getPosition());
        gameData.setInventory(player.getInventory());
        gameData.setEquippedWeapon(player.getEquippedWeapon());
        gameData.setLevelNumber(0); // for Level0
        LevelFunctions.saveGameIfKeyPressed(gameData);
        timeAccumulator += Gdx.graphics.getDeltaTime();
    if (timeAccumulator >= 2) {
        LevelFunctions.createBouleDeFeu(balls);
        timeAccumulator = 0;
    }

        batch.begin();
        // Basics IMPORTANT Functions, that need visual
        LevelFunctions.renderMap(batch, camera, mapRenderer);
        //LevelFunctions.handlePlatformInteractions(platforms, player, npc);
        LevelFunctions.handleDamageLayer(damageLayer, player);
        LevelFunctions.handleCollisionLayer(collisionLayer, player, npc, messageNpc1, messageNpc2);
    LevelFunctions.drawHealthBar(batch, player, healthBar, font, camera,shield,player.isArmorEquipped());
    System.out.println(player.isArmorEquipped());
    LevelFunctions.drawItems(batch, items);

        LevelFunctions.drawPlatforms(batch, platforms);
        LevelFunctions.drawPlayerDamage(batch, player);
        LevelFunctions.updateDamageTaken(player);
        LevelFunctions.playerAttackWeapon(player);
        LevelFunctions.drawWeapon(batch, chaos,player);
        LevelFunctions.handleDamageForEnnemy(messageNpc4,player.getBullets());
        LevelFunctions.renderBullet(batch, player.getBullets());
        // LevelFunctions.removeBullet(player.getBullets(), camera, null);
        if (player.getPosition().x > 9112) {
            LevelFunctions.unequipWeapon(player);
        }
        for (BouleDeFeu boule : balls) {
            boule.update(Gdx.graphics.getDeltaTime());
            boule.setImage(bouleTexture);
            boule.draw(batch);
          }
        
        // Draw entities (Maybe we can do a function for that)
        batch.draw(npc.getCurrentSprite(), npc.getPosition().x, npc.getPosition().y, spriteWidth, spriteWidth);
for (MessageNpc messageNpc : messageNpcs) {
    if (!messageNpc.isMessageDisplayed() && player.getPosition().dst(messageNpc.getPosition()) < messageDisplayDistance) {
       font.setColor(Color.YELLOW); 
        font.draw(batch, messageNpc.getMessage(), messageNpc.getPosition().x - 2*spriteWidth, messageNpc.getPosition().y + 130);
        font.setColor(Color.WHITE); 
    }
            batch.draw(sprites[8], messageNpc.getPosition().x, messageNpc.getPosition().y, spriteWidth, spriteWidth);
        }
        if (!messageNpc6.isMessageDisplayed() && player.getPosition().dst(messageNpc6.getPosition()) < messageDisplayDistance){
            font.setColor(Color.YELLOW); 
            font.draw(batch, messageNpc6.getMessage(), messageNpc6.getPosition().x + spriteWidth, messageNpc6.getPosition().y + 130);
            font.setColor(Color.WHITE); 
        }
        batch.draw(messageNpc6.getCurrentSprite(), messageNpc6.getPosition().x, messageNpc6.getPosition().y, spriteWidth, spriteWidth);
        LevelFunctions.drawNpcHealthBar(batch, messageNpc4, healthBarNpc, font);
        if (messageNpc4.isDead()) {
            font.setColor(Color.YELLOW); 
            font.draw(batch, "You killed me ! Good Job !\n Continue to the right !", messageNpc4.getPosition().x - 2*spriteWidth, messageNpc4.getPosition().y + 110);
            font.setColor(Color.WHITE); 
        }
        if (player.getPosition().dst(npc.getPosition()) < messageDisplayDistance /2) {
            font.setColor(Color.YELLOW); 
            font.draw(batch, "       Hello ! I'm a NPC !\n Press [ARROW RIGHT] and [ARROW LEFT]\n to move your character", npc.getPosition().x - 2*spriteWidth+100, npc.getPosition().y + 160);
            font.setColor(Color.WHITE); 
        }
        if (!messageNpc7.isMessageDisplayed() && player.getPosition().dst(messageNpc7.getPosition()) < messageDisplayDistance) {
            font.setColor(Color.YELLOW); 
        font.draw(batch, messageNpc7.getMessage(), messageNpc7.getPosition().x - 2*spriteWidth, messageNpc7.getPosition().y + 130);
        font.setColor(Color.WHITE); 

    }
            batch.draw(sprites[8], messageNpc7.getPosition().x, messageNpc7.getPosition().y, spriteWidth, spriteWidth);
        
        // End batch
        batch.end();
        LevelFunctions.toggleInventory(inventoryStage, player, camera);
    }
    }
}

      @Override
      public void dispose() {
        batch.dispose();
        inventoryStage.dispose();
      }
    }

