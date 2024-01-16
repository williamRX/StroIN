package com.stroin.game.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.stroin.game.Level.Level3;
import com.stroin.game.controllers.EnemyController;
import com.stroin.game.elements.EnemyBall;

public class Enemy extends EnemyNpc {

    private Rectangle agroRectangle;
    public EnemyController enemyController;
    private Vector2 originalPosition;
    private List<EnemyBall> balls;
    private float attackTimer = 0;
    private static final float ATTACK_DELAY = 0.5f;
    List<Enemy> enemies = new ArrayList<>();
    private TextureRegion sprite;
    public State state = State.NORMAL;
    private int explosionCounter = 0;
    
    
    public enum State {
        NORMAL,
        DEAD,
        EXPLODING
    }

    public Enemy(String name, int health, Vector2 position, int speed, TextureRegion[] sprites, float velocityY) {
        super(name, health, position, speed, sprites, velocityY);
        this.enemyController = new EnemyController();
        this.agroRectangle = new Rectangle(position.x - 500, position.y - 500, 1000, 1000);
        this.originalPosition = new Vector2(position.x, position.y);
        this.balls = new ArrayList<EnemyBall>();
        this.sprites = sprites;
        this.sprite = new TextureRegion(new Texture("eggMan.png"));

    }

    public boolean isWithinAgroRange(Vector2 playerPosition) {
        return this.agroRectangle.contains(playerPosition.x, playerPosition.y);
    }

    public void moveLeft(float speed) {
        this.position.x -= speed;
    }

    public void moveRight(float speed) {
        this.position.x += speed;
    }

    public Vector2 getOriginalPosition() {
        return this.originalPosition;
    }

    public void updatePos(Enemy enemy, Player player) {
        if(this.state == State.NORMAL){
            this.enemyController.updateEnemyMovement(enemy, player);
        }
    }

    public boolean canAttack() {
        return attackTimer >= ATTACK_DELAY;
    }
    public void resetAttackTimer() {
        attackTimer = 0;
    }

public void attack(Player player) {
    if (this.canAttack() && this.state == State.NORMAL) {
        float speedX = -50; // Default speed to the left

        // If the player is to the right of the enemy, change the speed to the right
        if (player.getPosition().x > this.position.x) {
            speedX = 50;
        }

        int enemyMiddleY = (int) this.position.y + this.height / 8;

        EnemyBall ball = new EnemyBall(
            "enemyBall", 
            (int) this.position.x, 
            enemyMiddleY, 
            10, // width
            10, // height
            speedX, 
            0 // speedY
        );
        this.balls.add(ball);
        this.resetAttackTimer();
    }
}
    public List<EnemyBall> getBalls() {
        return balls;
    }

    public void update(float deltaTime) {
        attackTimer += deltaTime;

    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }
    @Override
public TextureRegion getCurrentSprite() {
    return sprite;
}

    public void setSprite(TextureRegion textureRegion) {
        this.sprite = textureRegion;
    }
public void setState(State state) {
    this.state = state;
}
public State getState() {
    if(this.state == State.DEAD) {
        return State.DEAD;
    }else{
    return this.state;
    }
}
public void updateEnemyState(List<Enemy> enemies) {
    switch (state) {
        case NORMAL:
            if(this.health ==0) {
                state = State.EXPLODING;
                this.setSprite(new TextureRegion(new Texture("explosion.png")));
                Sound explosionSound = Level3.manager.get("explosion.mp3", Sound.class);
                explosionSound.play();
            }
            else{

                break;
            }
        case EXPLODING:
            explosionCounter++;
            if (explosionCounter > 60) {
                state = State.DEAD;
            }
            break;
        case DEAD:
            break;
    }
}
@Override
public void takeDamage(int damage) {
    this.health -= damage;
}
}
