package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Player extends Entity {
    protected OrthographicCamera camera;
    ArrayList<Bullet> bulletManager = new ArrayList<Bullet>();
    public Bullet bullet;
    private int touchMoveSpeed = 1500;
    private int keyMoveSpeed = 900;
    private Vector2 velocity;
    public double prevHitTime = 0;
    private int score;
    public int enemiesHit;

    public Player(GameScreen screen, int x, int y, String textureName) {
        super(screen, x, y, "Players/" + textureName);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screen.game.screenWidth, screen.game.screenHeight);
        velocity = new Vector2(0,0);
        health = 5;
        bulletVelocity = 20;
        firesBullets = true;
    }

    public void update() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            // Find the direction and length to move in
            velocity.x = (touchPos.x - position.x) - position.getWidth() / 2;
            velocity.y = (touchPos.y - position.y) - position.getHeight() + 175;

            // normalizes the above vector distance to obtain hypotenuse of 1, then multiplies by speed scalar
            velocity = velocity.nor().scl(touchMoveSpeed);

            // Set add that direction and length to current position
            position.x += (velocity.x) * Gdx.graphics.getDeltaTime();
            position.y += (velocity.y) * Gdx.graphics.getDeltaTime();
        }

        // Keboard input
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) position.x -= keyMoveSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) position.x += keyMoveSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) position.y += keyMoveSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) position.y -= keyMoveSpeed * Gdx.graphics.getDeltaTime();

        // Keeps you in the game's boundaries
        if(position.x < 0) position.x = 0;
        if(position.x > screen.game.screenWidth - position.getWidth()) position.x = screen.game.screenWidth-position.getWidth();
        if(position.y < 0) position.y = 0;
        if(position.y > screen.game.screenHeight - position.getHeight()) position.y = screen.game.screenHeight-position.getHeight();
    }

    public void setScore(double currentTime){

        double deltaTime = currentTime - prevHitTime;
        score = score + (int)(1000.0/(deltaTime));
        prevHitTime = currentTime;
    }

    public int getScore(){
        return score;
    }

    public void fireBullet (float deltaTime) {
        if (health > 0 && firesBullets) {
            fireDelay -= deltaTime;
            if(fireDelay <= 0) {
                Bullet tempBullet = new Bullet(screen, (int) (position.x + texture.getWidth()/4), (int) (position.y + texture.getHeight() / 2), "bubble.png", bulletVelocity);
                bulletManager.add(tempBullet);
                fireDelay += 0.2;
            }
        }
    }

    void render(float deltaTime){
        camera.update();
        screen.game.batch.setProjectionMatrix(camera.combined);
        update();
        super.render(deltaTime);
        // Cool, makes the camera follow the fish
        // camera.position.set(position.x, position.y, 0);

        fireBullet(deltaTime);
        // Renders bullets
        for (int i=0; i<bulletManager.size(); i++) {
            bullet = bulletManager.get(i);
            bullet.update();
            // Saves memory by removing bullets that are out of bounds
            //Note Changed to -30 for overlap of rectangle and boundary
            if(bullet.bulletLocation.x > -30 && bullet.bulletLocation.x < screen.game.screenWidth && bullet.bulletLocation.y > 0 && bullet.bulletLocation.y < screen.game.screenHeight)
                screen.game.batch.draw(bullet.texture, bullet.bulletLocation.x, bullet.bulletLocation.y);
            else {
                bulletManager.remove(i);
                if(bulletManager.size() > 0)
                    i--;
            }
        }
    }
}
