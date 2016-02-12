package com.sashimi.game;

import java.util.Random;

/**
 * Class for Enemy Creation and AI
 */
public class Enemy extends Character{
    private Random random = new Random();
    private int target;

    Enemy(GameScreen screen, int x, int y, String textureName){
        super(screen,x,y,textureName);
        target = 70;
    }


    void moveLeftAndRight(){
        if(position.contains(target,position.getY())){
            target = random.nextInt(screen.game.screenWidth);
        }


        if(position.getX() < target){
            position.setX(position.getX() + screen.moveSpeed);
        }
        else{
            position.setX(position.getX() - screen.moveSpeed);
        }
    }

    @Override
    void render(){
        moveLeftAndRight();
        screen.game.batch.draw(texture, position.x, position.y, position.getWidth(), position.getHeight());
    }


}
