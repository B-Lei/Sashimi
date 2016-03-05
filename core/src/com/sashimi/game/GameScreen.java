package com.sashimi.game;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameScreen implements Screen {
    final Sashimi game;

    private float gameTime = 0;
    private int justOnce = 0;

    protected Texture BG;
    public int BGwidth = 720;
    public int BGheight = 1280;

    protected Player you;

    protected Vector<Enemy> enemies = new Vector<Enemy>();
    ArrayList<Bullet> enemyBulletManager = new ArrayList<Bullet>();
    private float enemySpawnDelay;
    private int numEnemies;

    public EasyButton pauseButton;

    public GameScreen(final Sashimi game) {
        int yourWidth = 30;
        this.game = game;
        BG = new Texture(Gdx.files.internal("BG/BG1.png"));
        you = new Player(this,game.screenWidth/2-yourWidth/2,100,"mrfish1.5x.png");
        System.out.println("Width: " + you.getPosition().getX());
        System.out.println("Height: " + you.getPosition().getY());
        Vector2 spriteDimensions = new Vector2(0,0);
        System.out.println("Center Width: " + you.getPosition().getCenter(spriteDimensions).x);
        System.out.println("Center Height: " + you.getPosition().getCenter(spriteDimensions).y);

        //Set up menu button
        /*pauseButton = new EasyButton("Pause.png");
        pauseButton.setX((game.screenWidth / 2) - (pauseButton.getWidth() / 2));
        pauseButton.setY((game.screenHeight) - (pauseButton.getHeight() * 3 / 2));
        */
    }

    public void updateGameTime (float deltaTime) {
        gameTime += deltaTime;
    }

    public void spawnEnemies (float deltaTime) {
        // Example of a time-spawned enemy
        if (gameTime > 2 && justOnce == 0) {
            Enemy tempEnemy = new Enemy(this, 500, 800, "jelly1.5x.png");
            enemies.add(tempEnemy);
            numEnemies++;
            justOnce = 1;
        }

        // Spawn random enemies up until 20
        if (numEnemies < 20) {
            if (you.health > 0) {
                enemySpawnDelay -= deltaTime;
                if (enemySpawnDelay <= 0) {
                    int randomDeterminant = random(1);
                    String randomEnemy = (1 == randomDeterminant) ? "starfish1.5x.png" : "jelly1.5x.png";
                    RandomEnemy tempEnemy = new RandomEnemy(this, random(720), game.screenHeight, randomEnemy);
                    enemies.add(tempEnemy);
                    enemySpawnDelay += 0.3;
                    numEnemies++;
                }
            }
        }
    }

    public void renderEnemiesAndBullets (float deltaTime) {
        // Renders enemies
        for(Enemy e: enemies) {
            if (e != null) {
                e.render();
                e.fireBullet(deltaTime, enemyBulletManager);
            }
        }
        // Render enemy bullets
        for (int i=0; i<enemyBulletManager.size(); i++) {
            Bullet savedBullet = enemyBulletManager.get(i);
            savedBullet.update();
            if(savedBullet.bulletLocation.x > -30 && savedBullet.bulletLocation.x < game.screenWidth && savedBullet.bulletLocation.y > 0 && savedBullet.bulletLocation.y < game.screenHeight)
                game.batch.draw(savedBullet.texture, savedBullet.bulletLocation.x, savedBullet.bulletLocation.y);
            else {
                enemyBulletManager.remove(i);
                if(enemyBulletManager.size() > 0)
                    i--;
            }
        }
    }

    public void checkForCollisions (float deltaTime) {
        // Handles enemy collisions
        for(int i=0; i<enemies.size(); i++){
            Enemy e = enemies.get(i);
            if(e.isHit(you.getHitbox())){
                you.health--; // UNCOMMENT FOR INVINCIBILITY
                e.health--;
                System.out.println("Your HP: "+ you.health);
                if (e.health <= 0) {
                    System.out.println("Enemy is destroyed");
                    e.dispose();
                    enemies.remove(e);
                    you.setScore(System.currentTimeMillis());
                    numEnemies--;
                }
            }

            //Handles Bullet Collisions
            // From your bullets:
            for(int j=0; j< you.bulletManager.size(); j++){
                if(e.isHit(you.bulletManager.get(j).getPosition())){
                    e.health--;
                    you.bulletManager.get(j).dispose();
                    you.bulletManager.remove(j);
                    j--;
                    if (e.health <= 0) {
                        System.out.println("Enemy is destroyed");
                        e.dispose();
                        enemies.remove(e);
                        you.setScore(System.currentTimeMillis());
                        numEnemies--;
                    }
                }
            }
            // For enemy bullets:
            for(int j=0; j< enemyBulletManager.size(); j++){
                if(you.isHit(enemyBulletManager.get(j).getPosition())){
                    you.health--;
                    System.out.println("Your HP: "+ you.health);
                    enemyBulletManager.get(j).dispose();
                    enemyBulletManager.remove(j);
                    j--;
                }
            }
        }
    }

    public void checkPlayerHealth () {
        // If your health is 0, go back to title screen
        if (you.health <= 0){
            System.out.println("Score: "+you.getScore());
            game.gameOver();
        }
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(BG, 0, 0, BGwidth, BGheight);

        updateGameTime(delta);

        you.render(delta);

        spawnEnemies(delta);
        renderEnemiesAndBullets(delta);

        checkForCollisions(delta);

        checkPlayerHealth();

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
