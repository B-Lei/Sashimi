package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class InfoScreen implements Screen {

    final Sashimi game;

    OrthographicCamera camera;

    public InfoScreen(final Sashimi game) {
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
        //Add text for info screen
        game.batch.end();

        //for now if user touches screen, go back to MainScreen
        if(Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
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