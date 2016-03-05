package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BossScreen extends GameScreen {
    final Sashimi game;

    BossScreen(final Sashimi game) {
        super(game);
        this.game = game;
        enemies.add(new Level1Boss(this,500,500));
    }

    @Override
    public void render(float delta){
        game.batch.begin();
        game.batch.draw(BG, 0, 0, game.screenWidth, game.screenHeight);
        you.render(delta);
        for(Enemy e: enemies){
            e.render();
        }
        game.batch.end();
    }

    @Override
    public void dispose(){

    }

}
