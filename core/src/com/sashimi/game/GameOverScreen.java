package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameOverScreen implements Screen {

    final Sashimi game;
    OrthographicCamera camera;
    SpriteBatch batch;
    //Texture buttonTex;
    EasyButton playButton;
    EasyButton mainMenu;

    public GameOverScreen(final Sashimi game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenWidth, game.screenHeight);
        batch = new SpriteBatch();

        //Sets up a play button
        playButton = new EasyButton("Play Button.png");
        playButton.setX((game.screenWidth/2)-(playButton.getWidth()/2));
        playButton.setY((game.screenHeight/2));

        //Sets up info screen button
        mainMenu = new EasyButton("Main Menu.png");
        mainMenu.setX((game.screenWidth / 2) - (mainMenu.getWidth() / 2));
        mainMenu.setY((game.screenHeight / 2) - (mainMenu.getHeight() * 3 / 2));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //Render Play Button
        game.batch.begin();
        game.batch.draw(playButton.getButtonTexture(), playButton.getX(), playButton.getY());
        game.batch.draw(mainMenu.getButtonTexture(), mainMenu.getX(), mainMenu.getY());
        game.batch.end();

        //In the event that screen is touched
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            //Checks if the play button is touched
            if(playButton.contains((int)touchPos.x,(int)touchPos.y,game.screenHeight)){
                game.level1();
            }
            //Check if the info button is touched
            else if(mainMenu.contains((int)touchPos.x,(int)touchPos.y,game.screenHeight)){
                game.mainMenu();
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
        playButton.dispose();
        mainMenu.dispose();
    }
}
