package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.Dialog;

import javafx.stage.Stage;

public class InfoScreen implements Screen {

    final Sashimi game;

    private Texture waterImage;
    public int waterWidth = 720;
    public int waterHeight = 1400;
    private Rectangle water;
    public EasyButton menuButton;
    public BitmapFont infoFont;


    OrthographicCamera camera;

    public InfoScreen(final Sashimi game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenWidth, game.screenHeight);

        //Set up water image as background
        waterImage = new Texture(Gdx.files.internal("waterImage.png"));

        //Set up menu button
        menuButton = new EasyButton("Main Menu.png");
        menuButton.setX((game.screenWidth / 2) - (menuButton.getWidth() / 2));
        menuButton.setY((game.screenHeight / 6));

        //Set up font for info screen
        infoFont = new BitmapFont();
        infoFont.setColor(Color.NAVY);
        infoFont.getData().setScale(2,2);

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //Define instructions to be displayed
        CharSequence instructions = "Goal: Swim to the top of the ocean while\navoiding the enemies." +
                                    "\n\nHold down finger to move from side to side,\nand tap to shoot bubbles." +
                                    "\n\nAdd better instructions here...";

        game.batch.begin();

        //Add water background
        game.batch.draw(waterImage, 0, -50, waterWidth, waterHeight);

        //Add text for info screen
        infoFont.draw(game.batch, instructions, Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()*2-50);

        //Add main menu button
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
        waterImage.dispose();
        infoFont.dispose();
    }

}