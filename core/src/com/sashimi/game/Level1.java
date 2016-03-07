package com.sashimi.game;

import static com.badlogic.gdx.math.MathUtils.random;

public class Level1 extends GameScreen {
    public Level1 (final Sashimi game) {
        super(game);
    }

    @Override
    public void spawnEnemies(float deltaTime) {
        // Spawn random enemies up until 20
        if (numEnemies < 20) {
            if (you.health > 0) {
                enemySpawnDelay -= deltaTime;
                if (enemySpawnDelay <= 0) {
                    int randomDeterminant = random(1);
                    String randomEnemy = (1 == randomDeterminant) ? "starfish1.5x.png" : "jelly1.5x.png";
                    RandomEnemy tempEnemy = new RandomEnemy(this, random(720), game.screenHeight, randomEnemy);
                    enemies.add(tempEnemy);
                    enemySpawnDelay += 0.3;
                    numEnemies++;
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        //TODO remove
        //game.level1Boss();
        super.render(delta);
    }
}
