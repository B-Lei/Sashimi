package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BossScreen extends GameScreen {
    final Sashimi game;



    BossScreen(final Sashimi game) {
        super(game);
        this.game = game;

        //Render Static Images
        game.batch.begin();
        game.batch.draw(BG, 0, 0, game.screenWidth, game.screenHeight);
        game.batch.end();
    }

    @Override
    public void render(float delta){

    }

    @Override
    public void dispose(){

    }

}
