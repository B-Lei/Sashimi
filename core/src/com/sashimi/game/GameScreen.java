package com.sashimi.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    final Sashimi game;

    private Texture fishImage;
    public int fishWidth = 300;
    public int fishHeight = 124;
    private Rectangle fish;
    private OrthographicCamera camera;

    public GameScreen(final Sashimi game) {
        this.game = game;

        fishImage = new Texture(Gdx.files.internal("tempFish.png"));
        fish = new Rectangle(game.screenWidth/2-fishWidth/2, game.screenHeight/2-fishHeight/2, fishWidth, fishHeight);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenWidth, game.screenHeight);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(fishImage, fish.x, fish.y);
        game.batch.end();

        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            // Added some value to y to position fish above finger
            fish.x = touchPos.x - fishWidth / 2;
            fish.y = touchPos.y - fishHeight / 2 + 100;
        }

        if(Gdx.input.isKeyPressed(Keys.LEFT)) fish.x -= 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) fish.x += 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.UP)) fish.y += 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.DOWN)) fish.y -= 800 * Gdx.graphics.getDeltaTime();

        if(fish.x < 0) fish.x = 0;
        if(fish.x > game.screenWidth - fishWidth) fish.x = game.screenWidth-fishWidth;
        if(fish.y < 0) fish.y = 0;
        if(fish.y > game.screenHeight - fishHeight) fish.y = game.screenHeight-fishHeight;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
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
    public void dispose() {
        fishImage.dispose();
    }
}
