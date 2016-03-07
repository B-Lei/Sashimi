package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BossScreen extends GameScreen {
    final Sashimi game;

    BossScreen(final Sashimi game) {
        super(game);
        this.game = game;
        enemies.add(new Level1Boss(this,500,1000));
    }

    @Override
    public void render(float delta){
        game.batch.begin();
        game.batch.draw(BGtexture, BGposition.x, BGposition.y, BGposition.getWidth(), BGposition.getHeight());
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
