package com.sashimi.game;

public class Jellyfish extends Enemy {
    public Jellyfish(GameScreen screen, int x, int y) {
        super(screen,x,y,"jelly1.5x.png");
        moveSpeed = 5;
        bulletVelocity = -6;
    }
}
