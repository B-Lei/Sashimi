package com.sashimi.game;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {
    final Sashimi game;

    private Texture BG;
    public int BGwidth = 720;
    public int BGheight = 1280;
    private Rectangle water;

    private Vector<Enemy> enemies = new Vector<Enemy>();
    private Enemy enemy;
    private Character you;
    private int yourWidth = 30;
    private int yourHeight = 50;

    //Used by multiple classes to determine how fast a character can move
    final int moveSpeed;


    public EasyButton pauseButton;

    public GameScreen(final Sashimi game) {
        this.game = game;

        BG = new Texture(Gdx.files.internal("BG1.png"));
        you = new Player(this,game.screenWidth/2-yourWidth,game.screenHeight/2-yourHeight,"mrfish.png");

        // Enemy Generation
        enemy = new Enemy(this,420,game.screenHeight,"starfish.png");
        Enemy enemy2 = new Enemy(this,0,game.screenHeight,"jelly.png");
        Enemy enemy3 = new Enemy(this,123,game.screenHeight,"jelly.png");
        enemies.add(enemy);
        enemies.add(enemy2);
        enemies.add(enemy3);
        // Commented out so that enemies spawn from the top
        //enemies.elementAt(1).setY(100);

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

        game.batch.begin();
        game.batch.draw(BG, 0, 0, BGwidth, BGheight);

        you.render();

        for(Enemy e: enemies) {
            if (e != null)
                e.render();
        }

        //Add pause button (temporary, will be improved later)
        //game.batch.draw(pauseButton.getButtonTexture(), pauseButton.getX(), pauseButton.getY());
        game.batch.end();

        for(int i=0; i<enemies.size(); i++){
            Enemy e = enemies.get(i);
            if(e.isHit(you.getPosition())){
                System.out.println("Enemy is hit");
                e.dispose();
                enemies.remove(e);
            }
        }

        // Keeps you in the game's boundaries
        if(you.getPosition().x < 0) you.setX(0);
        if(you.getPosition().x > game.screenWidth - yourWidth) you.getPosition().x = game.screenWidth-yourHeight;
        if(you.getPosition().y < 0) you.setY(0);
        if(you.getPosition().y > game.screenHeight - yourHeight) you.setY(game.screenHeight-yourHeight);
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
        you.dispose();
        BG.dispose();
        for(Enemy e: enemies){
            e.dispose();
        }
    }
}
