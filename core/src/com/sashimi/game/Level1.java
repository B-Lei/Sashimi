package com.sashimi.game;

import static com.badlogic.gdx.math.MathUtils.random;

public class Level1 extends GameScreen {
    private int wave1enemyCount = 0;
    private int wave2enemyCount = 0;

    public Level1 (final Sashimi game) {
        super(game);
    }

    @Override
    public void spawnEnemies(float deltaTime) {
        // First line of enemies
        if (secondsElapsed > 2 && wave1enemyCount < 10) {
            // Spawns two enemies at a time, multiple times, to form a row
            enemySpawnDelay -= deltaTime;
            if (enemySpawnDelay <= 0) {

                Enemy tempEnemy = new Jellyfish(this, -50, 1200);
                enemies.add(tempEnemy);
                wave1enemyCount++;

                tempEnemy = new Jellyfish(this, -50, 1100);
                enemies.add(tempEnemy);
                wave1enemyCount++;

                numEnemies += 2;
                enemySpawnDelay += 0.3;
            }
        }
    }

    @Override
    public void render(float delta) {
        //TODO remove
        game.level1Boss();
        super.render(delta);
    }
}
