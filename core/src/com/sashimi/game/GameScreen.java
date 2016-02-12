package com.sashimi.game;

import java.util.Iterator;
import java.util.Vector;

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
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameScreen implements Screen {
    final Sashimi game;

    private Texture waterImage;
    public int waterWidth = 720;
    public int waterHeight = 1280;
    private Rectangle water;
    private Vector<Enemy> enemies = new Vector<Enemy>();


    private Enemy enemy;
    private Texture fishImage;
    public int fishWidth = 150;
    public int fishHeight = 200;
    private Rectangle fish;
    private OrthographicCamera camera;

    //Used by multiple classes to determine how fast a character can move
    final int moveSpeed;


    public EasyButton pauseButton;

    public GameScreen(final Sashimi game) {
        this.game = game;

        waterImage = new Texture(Gdx.files.internal("waterImage.png"));

        fishImage = new Texture(Gdx.files.internal("actorFish.png"));
        fish = new Rectangle(game.screenWidth/2-fishWidth/2, game.screenHeight/2-fishHeight/2, fishWidth, fishHeight);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenWidth, game.screenHeight);

        // Enemy Generation
        enemy = new Enemy(this,0,0,"tempFish.png");
        Enemy enemy2 = new Enemy(this,0,0,"tempFish.png");
        enemies.add(enemy);
        enemies.add(enemy2);
        enemies.elementAt(1).setY(100);

        moveSpeed = 10;


        //Set up menu button
        /*pauseButton = new EasyButton("Pause.png");
        pauseButton.setX((game.screenWidth / 2) - (pauseButton.getWidth() / 2));
        pauseButton.setY((game.screenHeight) - (pauseButton.getHeight() * 3 / 2));
        */

    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(waterImage, 0, 0, waterWidth, waterHeight);
        game.batch.draw(fishImage, fish.x, fish.y, fishWidth, fishHeight);
        for(Enemy e: enemies) {
            if (e != null)
                e.render();
        }

        //Add pause button (temporary, will be improved later)
        //game.batch.draw(pauseButton.getButtonTexture(), pauseButton.getX(), pauseButton.getY());
        game.batch.end();

        if(Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);


            //Checks if the menu button is touched
            /*if(pauseButton.contains(x, y, game.screenHeight)){
                game.pauseScreen();
            }*/

            // Added some value to y to position fish above finger
            if(fish.x < touchPos.x) {
                fish.setX(fish.x + moveSpeed);
            }
            else if(fish.x > touchPos.x+10){
                fish.setX(fish.x - moveSpeed);
            }

            if(fish.y < touchPos.y){
                fish.setY(fish.y + moveSpeed);
            }
            else if (fish.y > touchPos.y+10){
                fish.setY(fish.y - moveSpeed);
            }
            /*
            fish.x = touchPos.x - fishWidth / 2;
            fish.y = touchPos.y - fishHeight / 2 + 100;
            */
        }

        if(Gdx.input.isKeyPressed(Keys.LEFT)) fish.x -= 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) fish.x += 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.UP)) fish.y += 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.DOWN)) fish.y -= 800 * Gdx.graphics.getDeltaTime();


        for(Enemy e: enemies){
            if(e.isHit(fish)){
                System.out.println("Enemy is hit");
                e.dispose();
                enemies.remove(e);
            }
        }

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
        pauseButton.dispose();
        fishImage.dispose();
        waterImage.dispose();
        for(Enemy e: enemies){
            e.dispose();
        }
    }
}
