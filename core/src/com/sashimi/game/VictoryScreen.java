package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class VictoryScreen implements Screen{
    final Sashimi game;

    OrthographicCamera camera;
    private String victoryMessage;
    public BitmapFont victoryFont;

    public VictoryScreen(final Sashimi game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenWidth, game.screenHeight);
    }


    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        //Adds text alerting player they won
        victoryMessage = "You Win!!!!!!";
        victoryFont = new BitmapFont();
        victoryFont.setColor(Color.GOLD);
        victoryFont.getData().scale(3);
        victoryFont.draw(game.batch, victoryMessage, 260, 700);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new MainMenuScreen(game));
            this.dispose();
        }
    }

    public void show() {

    }

    public void hide() {

    }

    public void resume() {

    }

    public void pause() {

    }

    public void resize(int width, int height) {

    }

    public void dispose() {

    }
}


