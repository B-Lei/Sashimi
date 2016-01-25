package com.sashimi.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

// The below is based on the tutorial found in the following:
// https://github.com/libgdx/libgdx/wiki/A-simple-game
public class Sashimi extends ApplicationAdapter {
    private Texture fishImage;
    private Rectangle fish;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    // create method is where we create all of our images, sounds, and other media items
	@Override
	public void create () {
        fishImage = new Texture(Gdx.files.internal("tempFish.png"));
        // ctor: (x, y, width height) - all type float
        // create image at appWidth/2 - imageWidth/2 and the same with height to get center
        fish = new Rectangle(720/2 - 300/2, 1280/2 - 124/2, 300, 124);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 1280);
        batch = new SpriteBatch();
	}

    // render method is where we draw the media elements of the game (that we created earlier)
    // method is called every 0.025 seconds by the game loop
    // Here, we can move images, update animations, and do other screen rendering tasks
	@Override
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(fishImage, fish.x, fish.y);
        batch.end();

        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            // Added some value to y to position fish above finger
            fish.x = touchPos.x - 300 / 2;
            fish.y = touchPos.y - 124 / 2 + 100;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) fish.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) fish.x += 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) fish.y += 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) fish.y -= 200 * Gdx.graphics.getDeltaTime();

        if(fish.x < 0) fish.x = 0;
        if(fish.x > 720 - 300) fish.x = 720-300;
        if(fish.y < 0) fish.y = 0;
        if(fish.y > 1280 - 124) fish.y = 1280-124;
	}

    @Override
    public void dispose() {
        fishImage.dispose();
        batch.dispose();
    }

    // TODO: Testing TODO
}
