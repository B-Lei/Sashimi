package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

//import javafx.scene.paint.Color;

public class PauseScreen implements Screen{

    final Sashimi game;

    OrthographicCamera camera;
    private String pause;
    public BitmapFont pauseFont;

    public PauseScreen(final Sashimi game){
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
        //Adds text that says "PAUSE"
        pause = "PAUSE";
        pauseFont = new BitmapFont();
        pauseFont.setColor(Color.TEAL);

        pauseFont.draw(game.batch, pause, 300, 700);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }


@Override
    public void show() {

    }
@Override
    public void hide() {

    }
@Override
    public void resume() {

    }
@Override
    public void pause() {

    }
@Override
    public void resize(int width, int height) {

    }
@Override
    public void dispose() {
    pauseFont.dispose();
    }
}
