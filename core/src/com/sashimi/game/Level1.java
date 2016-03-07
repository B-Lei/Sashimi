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
                    Enemy randomEnemy = (1 == randomDeterminant) ? new Jellyfish(this, random(720), game.screenHeight) : new Starfish(this, random(720), game.screenHeight);
                    enemies.add(randomEnemy);
                    enemySpawnDelay += 0.3;
                    numEnemies++;
                }
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
