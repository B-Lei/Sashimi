package com.sashimi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class Sashimi extends Game {
    private TextureAtlas atlas;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    //Called when Application is Created
	@Override
	public void create () {
        //Initialize Atlas
        atlas = new TextureAtlas();

        //Initialize Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 1280);

        //Initialize SpriteBatch
        batch = new SpriteBatch();
        //this.screen =
	}

    // render method is where we draw the media elements of the game (that we created earlier)
    // method is called every 0.025 seconds by the game loop
    // Here, we can move images, update animations, and do other screen rendering tasks
    //May Include Game Logic Updates
	@Override
	public void render () {
        super.render();

	}

    //Called when Application is Destroyed
    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }

    //Called When Home Button is Pressed or Incoming Call Received
    //Good Place to Save Game State
    @Override
    public void pause() {

    }

    //Called When game is re-sized and game is not paused
    //Also Called Once after Create() Method
    //Parameters are width and height the Screen has Been resized to
    @Override
    public void resize(int width, int height){

    }

    public void resume(){

    }



}
