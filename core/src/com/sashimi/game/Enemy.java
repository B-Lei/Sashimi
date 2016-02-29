package com.sashimi.game;

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
        health = 2;
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

    @Override
    void render(){
        moveLeftAndRight();
        moveUpAndDown();
        screen.game.batch.draw(texture, position.x, position.y, position.getWidth(), position.getHeight());
    }


}
