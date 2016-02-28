package com.sashimi.game;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity {
    public Vector2 bulletLocation = new Vector2(0,0);
    private Vector2 bulletVelocity = new Vector2(0,0);

    public Bullet(GameScreen screen, int x, int y, String textureName) {
        super(screen, x, y, "Bullets/"+textureName);
        bulletLocation = new Vector2(x,y);
        bulletVelocity = new Vector2(0,20);
    }

    public void update() {
        bulletLocation.x += bulletVelocity.x;
        bulletLocation.y += bulletVelocity.y;
        position.setPosition(bulletLocation);
    }
}
