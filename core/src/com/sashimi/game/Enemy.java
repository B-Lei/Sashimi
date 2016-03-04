package com.sashimi.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for Enemy Creation and AI
 */
public class Enemy extends Entity {
    private Random random = new Random();
    protected int targetX;
    protected int targetY;
    protected int moveSpeed = 7;

    Enemy(GameScreen screen, int x, int y, String textureName){
        super(screen,x,y,"Enemies/"+textureName);
        targetX = 70;
        targetY = 70;
        health = 1; // Default health
        firesBullets = true;
        bulletVelocity = -10;
    }

    void moveLeftAndRight(){
        if(position.contains(targetX,position.getY())){
            targetX = random.nextInt(screen.game.screenWidth);
        }
        if(position.getX() < targetX){
            position.setX(position.getX() + moveSpeed);
        }
        else{
            position.setX(position.getX() - moveSpeed);
        }
    }

    void moveUpAndDown(){
        if(position.contains(position.getX(),targetY)){
            targetY = random.nextInt(screen.game.screenWidth);
        }

        if(position.getY() < targetY){
            position.setY(position.getY() + moveSpeed);
        }
        else{
            position.setY(position.getY() - moveSpeed);
        }
    }

    // The below actually just adds bullets to an external bulletManager (from the game screen)
    // All it really does is add a Bullet with the entity's position
    public void fireBullet (float deltaTime, ArrayList<Bullet> bulletManager) {
        if (health > 0 && firesBullets) {
            fireDelay -= deltaTime;
            if(fireDelay <= 0) {
                Bullet tempBullet = new Bullet(screen, (int) (position.x + texture.getWidth()/4), (int) (position.y + texture.getHeight() / 2), "enemyBubble.png", bulletVelocity);
                bulletManager.add(tempBullet);
                fireDelay += 0.6;
            }
        }
    }

    void render(float deltaTime){
        moveLeftAndRight();
        moveUpAndDown();
        super.render(deltaTime);
    }
}
