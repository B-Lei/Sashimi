package com.sashimi.game;

import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;

public class Level1 extends GameScreen {
    int spawnedBoss = 0;

    public Level1 (final Sashimi game) {
        super(game);
    }

    @Override
    public void spawnEnemies(float deltaTime) {
        if (secondsElapsed > 2 && secondsElapsed < 6) {spawnJellyRow(deltaTime, 1200, 1);}
        if (secondsElapsed > 3 && secondsElapsed < 7) {spawnJellyRow(deltaTime, 1100, 2);}
        if (secondsElapsed > 6 && secondsElapsed < 10) {spawnJellySideColumns(deltaTime, 100);}
        if (secondsElapsed > 10 && secondsElapsed < 20) {spawnRandomEnemies(deltaTime);}
        if (secondsElapsed > 17 && secondsElapsed < 22) {spawnJellySideColumns(deltaTime, 100);}
        if (secondsElapsed > 22 && secondsElapsed < 24) {spawnJellySideColumns(deltaTime, 200);}
        if (secondsElapsed > 24 && secondsElapsed < 27) {spawnJellyRow(deltaTime, 900, 1);}
        if (secondsElapsed > 28 && secondsElapsed < 31) {spawnJellyRow(deltaTime, 800, 1);}
        if (secondsElapsed > 31 && justOnce == 0) {
            enemies.add(new Level1Boss(this, game.screenWidth/2, 1100));
            numEnemies++;
            justOnce++;
            spawnedBoss = 1;
        }
        if (spawnedBoss == 1 && enemies.size() == 0) {
            game.victory(you.getScore(), secondsElapsed);
        }
    }

    @Override
    public void render(float delta) {
        //TODO remove
        //game.level1Boss();
        super.render(delta);
    }
}
