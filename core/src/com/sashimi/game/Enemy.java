package com.sashimi.game;

import java.util.Random;

/**
 * Class for Enemy Creation and AI
 */
public class Enemy extends Entity {
    private Random random = new Random();
    private int targetX;
    private int targetY;

    Enemy(GameScreen screen, int x, int y, String textureName){
        super(screen,x,y,"Enemies/"+textureName);
        targetX = 70;
        targetY = 70;
    }


    void moveLeftAndRight(){
        if(position.contains(targetX,position.getY())){
            targetX = random.nextInt(screen.game.screenWidth);
        }


        if(position.getX() < targetX){
            position.setX(position.getX() + screen.moveSpeed);
        }
        else{
            position.setX(position.getX() - screen.moveSpeed);
        }
    }

    void moveUpAndDown(){
        if(position.contains(position.getX(),targetY)){
            targetY = random.nextInt(screen.game.screenWidth);
        }

        if(position.getY() < targetY){
            position.setY(position.getY() + screen.moveSpeed);
        }
        else{
            position.setY(position.getY() - screen.moveSpeed);
        }
    }

    @Override
    void render(){
        moveLeftAndRight();
        moveUpAndDown();
        screen.game.batch.draw(texture, position.x, position.y, position.getWidth(), position.getHeight());
    }


}
