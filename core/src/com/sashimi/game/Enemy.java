package com.sashimi.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for Enemy Creation and AI
 */
public class Enemy extends Entity {
    protected int moveSpeed = 10;

    Enemy(GameScreen screen, int x, int y, String textureName){
        super(screen,x,y,"Enemies/"+textureName);
        health = 1; // Default health
        bulletVelocity = -7; // Default velocity
        firesBullets = true;
    }

    // The below actually just adds bullets to an external bulletManager (from the game screen)
    // All it really does is add a Bullet with the entity's position
    public void fireBullet (float deltaTime, ArrayList<Bullet> bulletManager) {
        if (health > 0 && firesBullets) {
            fireDelay -= deltaTime;
            if(fireDelay <= 0) {
                Bullet tempBullet = new Bullet(screen, (int)(position.x+position.getHeight()/4), (int)(position.y-position.getHeight()/4), "enemyBubble.png", bulletVelocity);
                bulletManager.add(tempBullet);
                fireDelay += 0.2;
            }
        }
    }

    void render(){
        super.render();
    }
}
