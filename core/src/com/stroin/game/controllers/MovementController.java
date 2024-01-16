package com.stroin.game.controllers;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.stroin.game.elements.MovablePlatform;
import com.stroin.game.entity.Player;


public class MovementController {

  private float gravity = 0.98f;
  private float jumpVelocity;
  private float maxJumpDuration = 0.5f;
  private float jumpDuration;
  private boolean isMovingRight;
  private boolean isMovingLeft;
  private boolean isJumping;
  private boolean isRunning;
  private int frameCounter = 0;
  private String lastDirection = "right";
  private int currentSpriteIndex = 0;

  public void updateMovement() {
    isMovingRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    isMovingLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT);
    isRunning = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      isJumping = true;
    } else {
      isJumping = false;
    }
  }

  public void jump(Player player, TiledMapTileLayer collisionLayer) {
    float jumpVelocity = 15f; // Adjust this value to change the jump height

    if (isJumping && (player.getPosition().y == 0 || isOnBloc(player, collisionLayer))) {
        jumpDuration = maxJumpDuration;
        this.jumpVelocity = jumpVelocity;
    }
    if (!isJumping) {
        jumpDuration = 0;
    }
    if (jumpDuration > 0) {
        player.setVelocityY(this.jumpVelocity);
        jumpDuration -= Gdx.graphics.getDeltaTime();
        this.jumpVelocity *= 0.98f;
    }
    player.setVelocityY(player.getVelocity() - gravity);
    player.getPosition().y += player.getVelocity();
    System.out.println("Player is jumping");
}

public void jump(Player player, TiledMapTileLayer collisionLayer, List<MovablePlatform> platforms2) {
    float jumpVelocity = 15f; // Adjust this value to change the jump height

    if (isJumping && (player.getPosition().y == 0 || isOnBloc(player, collisionLayer) || isOnPlatform(player, platforms2))) {
        jumpDuration = maxJumpDuration;
        this.jumpVelocity = jumpVelocity;
    }
    if (!isJumping) {
        jumpDuration = 0;
    }
    if (jumpDuration > 0) {
        player.setVelocityY(this.jumpVelocity);
        jumpDuration -= Gdx.graphics.getDeltaTime();
        this.jumpVelocity *= 0.98f;
    }
    player.setVelocityY(player.getVelocity() - gravity);
    player.getPosition().y += player.getVelocity();
    System.out.println("Player is jumping");
}

private boolean isOnPlatform(Player player, List<MovablePlatform> platforms) {
    for (MovablePlatform platform : platforms) {
        float platformTop = platform.getY() + platform.getBounds().height;
        float platformBottom = platform.getY();
        float platformLeft = platform.getX();
        float platformRight = platform.getX() + platform.getBounds().width;

        float playerBottom = player.getPosition().y;
        float playerLeft = player.getPosition().x;
        float playerRight = playerLeft + player.getBounds().width;

        if (playerBottom <= platformTop && playerBottom >= platformBottom && playerRight >= platformLeft && playerLeft <= platformRight) {
            return true;
        }
    }
    return false;
}
private boolean isOnBloc(Player player, TiledMapTileLayer collisionLayer) {
    for (int y = 0; y < collisionLayer.getHeight(); y++) {
        for (int x = 0; x < collisionLayer.getWidth(); x++) {
            TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
            if (cell != null) {
                float tileTop = y * collisionLayer.getTileHeight() + collisionLayer.getTileHeight();
                float tileBottom = y * collisionLayer.getTileHeight();
                float tileLeft = x * collisionLayer.getTileWidth();
                float tileRight = x * collisionLayer.getTileWidth() + collisionLayer.getTileWidth();

                float playerBottom = player.getPosition().y;
                float playerLeft = player.getPosition().x;
                float playerRight = playerLeft + player.getBounds().width;

                if (playerBottom <= tileTop && playerBottom >= tileBottom && playerRight >= tileLeft && playerLeft <= tileRight) {
                    return true;
                }
            }
        }
    }
    return false;
}
public void updatePlayerPosition(Player player, TiledMapTileLayer collisionLayer) {
    player.setJumping(isJumping);
    player.setRunning(isRunning);
    player.setMovingRight(isMovingRight);
    player.setMovingLeft(isMovingLeft);
    jump(player, collisionLayer);
    
    float speed = run(player.isRunning(), player.isMovingRight(), player.isMovingLeft());
    player.getPosition().x += speed;
}

public void updatePlayerPosition(Player player, TiledMapTileLayer collisionLayer, List<MovablePlatform> platforms2) {
    player.setJumping(isJumping);
    player.setRunning(isRunning);
    player.setMovingRight(isMovingRight);
    player.setMovingLeft(isMovingLeft);
    jump(player, collisionLayer, platforms2);
    
    float speed = run(player.isRunning(), player.isMovingRight(), player.isMovingLeft());
    player.getPosition().x += speed;
}

public void move(boolean isMovingRight, boolean isMovingLeft, Player player, float speed) {
    if (isMovingRight) {
        player.getPosition().x += speed;
        System.out.println("Player is moving right");
    } else if (isMovingLeft) {
        player.getPosition().x -= speed;
        System.out.println("Player is moving left");
    }
}
public TextureRegion getCurrentSprite(Player player, TextureRegion[] sprites) {
    frameCounter++;

    if (player.isJumping()) {
        if (player.isMovingRight()) {
            return sprites[6];
        } else if (player.isMovingLeft()){
            return sprites[14];
        } else {
            return sprites[1];
        }
    }
    if (player.isMovingRight()) {
        lastDirection = "right";
        if ((player.isRunning() && frameCounter % 2 == 0) || (!player.isRunning() && frameCounter % 6 == 0)) {
            currentSpriteIndex = (currentSpriteIndex + 1) % 3;
        }
        int spriteIndex = currentSpriteIndex == 2 ? 4 : currentSpriteIndex * 3;
        return sprites[spriteIndex];
    } else if (player.isMovingLeft()) {
        lastDirection = "left";
        if ((player.isRunning() && frameCounter % 2 == 0) || (!player.isRunning() && frameCounter % 6 == 0)) {
            currentSpriteIndex = (currentSpriteIndex + 1) % 3;
        }
        int spriteIndex = currentSpriteIndex == 2 ? 12 : 8 + currentSpriteIndex * 3;
        return sprites[spriteIndex];
    }

    if (lastDirection.equals("right")) {
        return sprites[0];
    } else {
        return sprites[8];
    }
}

public float run(boolean isRunning, boolean isMovingRight, boolean isMovingLeft) {
    float speed = isRunning ? 10 : 5;
    if (isMovingRight) {
        System.out.println("Player is running right");
        return speed;
    } else if (isMovingLeft) {
        System.out.println("Player is running left");
        return -speed;
    }
    return 0;
}

public String getLastDirection() {
    return lastDirection;
}

public boolean isMovingRight() {
    return isMovingRight;
}
public boolean isMovingLeft() {
    return isMovingLeft;
}

}
