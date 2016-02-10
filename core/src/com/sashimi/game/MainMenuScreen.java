package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {

    final Sashimi game;
    OrthographicCamera camera;
    SpriteBatch batch;
    EasyButton play;
    EasyButton info;

    public MainMenuScreen(final Sashimi game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenWidth, game.screenHeight);
        batch = new SpriteBatch();

        //Sets up a play button
        play = new EasyButton("Play Button.png");
        play.setX((game.screenWidth/2)-(play.getWidth()/2));
        play.setY((game.screenHeight/2));

        //Sets up an info button
        info = new EasyButton("Info.png");
        info.setX((game.screenWidth/2)-(info.getWidth()/2));
        info.setY((game.screenHeight/2) - (2*info.getHeight()));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //Render Play Button
        game.batch.begin();
        game.batch.draw(play.getButtonTexture(), play.getX(), play.getY());
        game.batch.draw(info.getButtonTexture(),info.getX(), info.getY());
        game.batch.end();


        //In the event that screen is touched
        if(Gdx.input.isTouched()){
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            //Checks if the play button is touched
            if(play.contains(x,y,game.screenHeight)){
                game.level1();
            }

            //Checks if info button is touched
            if(info.contains(x,y,game.screenHeight)){
                game.infoScreen();
            }
        }
    }



    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose(){
        batch.dispose();
        play.dispose();
        info.dispose();
    }
}
