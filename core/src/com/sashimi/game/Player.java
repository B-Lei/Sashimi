package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Player extends Entity {
    private OrthographicCamera camera;
    private float fireDelay;
    public Bullet bullet;
    private int moveSpeed = 1500;
    private Vector2 velocity;
    public double prevHitTime = 0;
    private int score;
    public int enemiesHit;


    ArrayList<Bullet> bulletManager = new ArrayList<Bullet>();

    public Player(GameScreen screen, int x, int y, String textureName) {
        super(screen, x, y, "Players/" + textureName);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screen.game.screenWidth, screen.game.screenHeight);
        velocity = new Vector2(0,0);
        health = 5;
    }

    public void update(float deltaTime) {
        screen.game.batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            // NEW IMPLEMENTATION
            /*if(position.x < touchPos.x) {
                position.x += moveSpeed;
            }
            else if(position.x > touchPos.x+10) {
                position.x -= moveSpeed;
            }

            if(position.y < touchPos.y){
                position.y += moveSpeed;
            }
            else if (position.y > touchPos.y+10){
                position.y -= moveSpeed;
            }*/

            // Find the direction and length to move in
            velocity.x = (touchPos.x - position.x);
            velocity.y = (touchPos.y - position.y);
            // if (Math.abs(velocity.x) >= 10 || Math.abs(velocity.y) >= 10) { // Forcibly removes jitter

                // normalizes the above vector distance to obtain hypotenuse of 1, then multiplies by speed scalar
                velocity = velocity.nor().scl(moveSpeed);

                // Set add that direction and length to current position
                position.x += (velocity.x) * deltaTime;
                position.y += (velocity.y) * deltaTime;
            //}
        }
        // Keboard input
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) position.x -= 900 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) position.x += 900 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) position.y += 900 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) position.y -= 900 * Gdx.graphics.getDeltaTime();

        // Keeps you in the game's boundaries
        if(position.x < 0) position.x = 0;
        if(position.x > screen.game.screenWidth - position.getWidth()) position.x = screen.game.screenWidth-position.getWidth();
        if(position.y < 0) position.y = 0;
        if(position.y > screen.game.screenHeight - position.getHeight()) position.y = screen.game.screenHeight-position.getHeight();
    }

    void render(float deltaTime){
        update(deltaTime);
        camera.update();

        if (health > 0) {
            fireDelay -= deltaTime;
            if(fireDelay <= 0) {
                Bullet tempBullet = new Bullet(screen, (int) (position.x), (int) (position.y + texture.getHeight() / 2), "bubble.png");
                bulletManager.add(tempBullet);
                fireDelay += 0.2;
            }
        }

        int counter = 0;
        while(counter < bulletManager.size()) {
            bullet = bulletManager.get(counter);
            bullet.update();
            // Saves memory by removing bullets that are out of bounds
            //Note Changed to -30 for overlap of rectangle and boundary
            if(bullet.bulletLocation.x > -30 && bullet.bulletLocation.x < screen.game.screenWidth && bullet.bulletLocation.y > 0 && bullet.bulletLocation.y < screen.game.screenHeight)
                screen.game.batch.draw(bullet.texture, bullet.bulletLocation.x, bullet.bulletLocation.y);
            else {
                bulletManager.remove(counter);
                if(bulletManager.size() > 0)
                    counter--;
            }
            counter++;
        }

        screen.game.batch.draw(texture, position.x, position.y, position.getWidth(), position.getHeight());
    }

    public void setScore(double currentTime){

        double deltaTime = currentTime - prevHitTime;
        score = score + (int)(1000.0/(deltaTime));
        prevHitTime = currentTime;
    }

    public int getScore(){
        return score;
    }

}
