package com.sashimi.game;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameScreen implements Screen {
    final Sashimi game;

    protected Texture BG;
    protected int BGwidth = 720;
    protected int BGheight = 1280;
    private Rectangle water;

    protected Vector<Enemy> enemies = new Vector<Enemy>();
    protected float enemySpawnDelay;
    protected int numEnemies;

    protected Player you;
    protected int yourWidth = 30;
    protected int yourHeight = 50;

    public EasyButton pauseButton;

    public GameScreen(final Sashimi game) {
        this.game = game;
        BG = new Texture(Gdx.files.internal("BG/BG1.png"));
        you = new Player(this,game.screenWidth/2-yourWidth/2,100,"mrfish1.5x.png");

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

        you.render(delta);

        // Spawn random enemies up until 20
        if (numEnemies < 20) {
            if (you.health > 0) {
                enemySpawnDelay -= delta;
                if (enemySpawnDelay <= 0) {
                    int randomDeterminant = random(1);
                    String randomEnemy = (1 == randomDeterminant) ? "starfish1.5x.png" : "jelly1.5x.png";
                    Enemy tempEnemy = new Enemy(this, random(720), game.screenHeight, randomEnemy);
                    enemies.add(tempEnemy);
                    enemySpawnDelay += 0.5;
                    numEnemies++;
                }
            }
        }

        // Render enemies
        for(Enemy e: enemies) {
            if (e != null)
                e.render();
        }

        // Handles enemy collisions - why doesn't bullet destroy the enemy?
        for(int i=0; i<enemies.size(); i++){
            Enemy e = enemies.get(i);
            //If you are hit by an enemy
            if(e.isHit(you.getPosition())){
                //System.out.println("You Were Hit By an Enemy");
                e.dispose();
                enemies.remove(e);
                numEnemies--;
                you.health--; // UNCOMMENT FOR INVINCIBILITY
                System.out.println("Your HP: "+ you.health);
            }

            //Handles Bullet Collisions
            for(int j=0; j<you.bulletManager.size(); j++){
                if(e.isHit(you.bulletManager.get(j).getPosition())){
                    System.out.println("Enemy is destroyed");
                    e.dispose();
                    enemies.remove(e);
                    numEnemies--;
                    you.bulletManager.get(j).dispose();
                    you.bulletManager.remove(j);
                    j--;
                }
            }
        }

        // If your health is 0, go back to title screen
        if (you.health <= 0) game.gameOver();

        //Add pause button (temporary, will be improved later)
        //game.batch.draw(pauseButton.getButtonTexture(), pauseButton.getX(), pauseButton.getY());
        game.batch.end();


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
//        pauseButton.dispose();
        you.dispose();
        BG.dispose();
        for(Enemy e: enemies){
            e.dispose();
        }
    }
}
