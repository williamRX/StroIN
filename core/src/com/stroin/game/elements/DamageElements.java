package com.stroin.game.elements;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.stroin.game.entity.Player;

public class DamageElements {
    protected TiledMapTileLayer damageLayer;

    public DamageElements(TiledMap map, String damageLayerName) {
        // Get the DamageLayer
        this.damageLayer = (TiledMapTileLayer) map.getLayers().get(damageLayerName);
    }

    public void checkCollisionDamage(Player player) {
    // Get the character's bounding rectangle
    Rectangle characterBounds = player.getBounds();

    // Check for collisions with the damage layer
    for (int y = 0; y < damageLayer.getHeight(); y++) {
        for (int x = 0; x < damageLayer.getWidth(); x++) {
            TiledMapTileLayer.Cell cell = damageLayer.getCell(x, y);
            if (cell != null) {
                Rectangle tileBounds = new Rectangle(x * damageLayer.getTileWidth(), y * damageLayer.getTileHeight(), damageLayer.getTileWidth(), damageLayer.getTileHeight());
                if (characterBounds.overlaps(tileBounds)) {
                    // Decrease the character's health
                    player.takeDamage(5);
                }
            }
        }
    }
}

}