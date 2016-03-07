package com.sashimi.game;

public class Starfish extends RandomEnemy {
    public Starfish(GameScreen screen, int x, int y) {
        super(screen,x,y,"starfish1.5x.png");
        moveSpeed = 7;
        bulletVelocity = -9;
    }
}
