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
    public EasyButton menuButton;

    public VictoryScreen(final Sashimi game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenWidth, game.screenHeight);

        //Set up menu button
        menuButton = new EasyButton("Play Button.png");
        menuButton.setX((game.screenWidth / 2) - (menuButton.getWidth() / 2));
        menuButton.setY((game.screenHeight / 6));

        //Set up text alerting player they won
        victoryMessage = "You Win!!!!!!";
        victoryFont = new BitmapFont();
        victoryFont.setColor(Color.GOLD);
    }


    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        //Adds text alerting player they won
        victoryFont.draw(game.batch, victoryMessage, 300, 700);

        //Adds main menu button
        game.batch.draw(menuButton.getButtonTexture(), menuButton.getX(), menuButton.getY());

        game.batch.end();

        //Check if user touches the screen
        if(Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            //Checks if the menu button is touched
            if(menuButton.contains(x,y,game.screenHeight)){
                game.mainMenu();
            }

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
        menuButton.dispose();
        victoryFont.dispose();
    }
}


