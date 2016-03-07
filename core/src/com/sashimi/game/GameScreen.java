package com.sashimi.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameScreen implements Screen {
    final Sashimi game;
    Music mainBGM;
    Sound hurt;
    Sound enemyDestroyed;

    protected float gameTime = 0;
    private int justOnce = 0;

    protected Texture BGtexture;
    protected Rectangle BGposition;
    protected Texture BGtexture2;
    protected Rectangle BGposition2;
    protected int scrollSpeed = 4; // must be a factor of screenHeight

    protected Player you;

    protected Vector<Enemy> enemies = new Vector<Enemy>();
    ArrayList<Bullet> enemyBulletManager = new ArrayList<Bullet>();
    private float enemySpawnDelay;
    private int numEnemies;

    private boolean rumbling = false;
    float rumbleTime = 0.2f;
    float cameraX, cameraY;
    float current_time = 0;
    float power = 7;
    float current_power = 0;
    boolean coordToggle = true;
    private float rumbleDelay = 0;

    public EasyButton pauseButton;

    public GameScreen(final Sashimi game) {
        int yourWidth = 30;
        this.game = game;
        mainBGM = Gdx.audio.newMusic(Gdx.files.internal("Music/sashimi.wav"));
        hurt = Gdx.audio.newSound(Gdx.files.internal("Music/hurt.wav"));
        enemyDestroyed = Gdx.audio.newSound(Gdx.files.internal("Music/enemyDestroyed.wav"));

        BGtexture = new Texture(Gdx.files.internal("BG/BG1scrollable.png"));
        BGposition = new Rectangle(0,0,BGtexture.getWidth(),BGtexture.getHeight());
        BGtexture2 = new Texture(Gdx.files.internal("BG/BG1scrollable.png"));
        BGposition2 = new Rectangle(0,game.screenHeight,BGtexture2.getWidth(),BGtexture2.getHeight());

        you = new Player(this,game.screenWidth/2-yourWidth/2,100,"mrfish1.5x.png");
        Vector2 spriteDimensions = new Vector2(0,0);
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
        // Example of a time-spawned enemy - testing purposes
//        if (gameTime > 2 && justOnce == 0) {
//            Enemy tempEnemy = new Enemy(this, 500, 800, "jelly1.5x.png");
//            enemies.add(tempEnemy);
//            numEnemies++;
//            justOnce++;
//        }
//        if (gameTime > 3 && justOnce == 1) {
//            Enemy tempEnemy = new Enemy(this, 200, 1000, "starfish1.5x.png");
//            enemies.add(tempEnemy);
//            numEnemies++;
//            justOnce++;
//        }

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
        for(int i=0; i<enemies.size(); i++){
            Enemy e = enemies.get(i);

            // Handles enemy collisions
            if (!you.invincible) {
                if (e.isHit(you.getHitbox())) {
                    rumbling = true;
                    if (!you.invincible) you.health--; // COMMENT OUT FOR INVINCIBILITY
                    hurt.play();
                    you.invincible = true;
                    if (!e.invincible) e.health--;
                    System.out.println("Collided with Enemy. Your HP: " + you.health);
                    if (e.health <= 0) {
                        //System.out.println("Enemy is destroyed");
                        e.dispose();
                        enemies.remove(e);
                        you.setScore(System.currentTimeMillis());
                        numEnemies--;
                        enemyDestroyed.play();
                    }
                }
            }

            //Handles Bullet Collisions
            // From your bullets:
            for(int j=0; j<you.bulletManager.size(); j++){
                if(e.isHit(you.bulletManager.get(j).getPosition())){
                    if (!e.invincible) e.health--;
                    you.bulletManager.get(j).dispose();
                    you.bulletManager.remove(j);
                    j--;
                    if (e.health <= 0) {
                        //System.out.println("Enemy is destroyed");
                        e.dispose();
                        enemies.remove(e);
                        you.setScore(System.currentTimeMillis());
                        numEnemies--;
                        enemyDestroyed.play();
                    }
                }
            }
            // For enemy bullets:
            for(int j=0; j<enemyBulletManager.size(); j++) {
                if (!you.invincible) {
                    if (you.isHit(enemyBulletManager.get(j).getPosition())) {
                        rumbling = true;
                        you.health--; // COMMENT OUT FOR INVINCIBILITY
                        hurt.play();
                        you.invincible = true;
                        System.out.println("Hit by Bullet. Your HP: " + you.health);
                        enemyBulletManager.get(j).dispose();
                        enemyBulletManager.remove(j);
                        j--;
                    }
                }
            }
        }
    }

    public void checkPlayerHealth () {
        // If your health is 0, go back to title screen
        if (you.health <= 0){
            System.out.println("Score: " + you.getScore());
            game.gameOver();
        }
    }

    public void performRumble(float deltaTime) {
        rumbleDelay -= deltaTime;
        if(current_time <= rumbleTime) {
            if (rumbleDelay <= 0) {
                current_power = power * ((rumbleTime - current_time) / rumbleTime);
                cameraX = ((coordToggle) ? 0.8f-deltaTime : -0.8f-deltaTime) * 2 * current_power;
                cameraY = ((coordToggle) ? -1.3f-deltaTime : 1.3f-deltaTime) * 2 * current_power;

                // Set the camera to new x/y position
                you.camera.translate(-cameraX, -cameraY);
                current_time += deltaTime;
                coordToggle = !coordToggle;
                rumbleDelay += 0.07;
            }
        } else {
            // Reset camera position and values
            you.camera.setToOrtho(false, game.screenWidth, game.screenHeight);
            current_time = 0;
            current_power = 0;
            rumbling = false;
        }
    }

    public void scrollBG() {
        BGposition.y -= scrollSpeed;
        BGposition2.y -= scrollSpeed;
        if (BGposition.y == -game.screenHeight) BGposition.y = game.screenHeight;
        if (BGposition2.y == -game.screenHeight) BGposition2.y = game.screenHeight;
    }

    @Override
    public void render (float delta) {
        //TODO remove
        //game.level1Boss();
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(BGtexture, BGposition.x, BGposition.y, BGposition.getWidth(), BGposition.getHeight());
        game.batch.draw(BGtexture2, BGposition2.x, BGposition2.y, BGposition2.getWidth(), BGposition2.getHeight());
        updateGameTime(delta);
        scrollBG();

        you.render(delta);

        spawnEnemies(delta);
        renderEnemiesAndBullets(delta);

        checkForCollisions(delta);

        checkPlayerHealth();

        if (rumbling) performRumble(delta);

        //Add pause button (temporary, will be improved later)
        //game.batch.draw(pauseButton.getButtonTexture(), pauseButton.getX(), pauseButton.getY());
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music when the screen is shown
        mainBGM.setLooping(true);
        mainBGM.play();
    }

    @Override
    public void hide() {
        mainBGM.stop();
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
        BGtexture.dispose();
        for(Enemy e: enemies){
            e.dispose();
        }
        mainBGM.dispose();
        hurt.dispose();
        enemyDestroyed.dispose();
    }
}
