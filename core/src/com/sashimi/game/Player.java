package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Player extends Entity {
    private OrthographicCamera camera;
    private float fireDelay;
    public Bullet bullet;
    private int moveSpeed = 10;
    public double startTime = 0;
    public double totalPlayTime = 0;

    ArrayList<Bullet> bulletManager = new ArrayList<Bullet>();

    public Player(GameScreen screen, int x, int y, String textureName) {
        super(screen, x, y, "Players/"+textureName);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screen.game.screenWidth, screen.game.screenHeight);
        health = 10;
    }

    public void update() {
        screen.game.batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

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

            // OLD IMPLEMENTATION
            // Added some value to y to position fish above finger
            position.x = touchPos.x - position.getWidth() / 2;
            position.y = touchPos.y - position.getHeight() / 2 + 150;
        }
        // Keboard input
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) position.x -= 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) position.x += 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) position.y += 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) position.y -= 800 * Gdx.graphics.getDeltaTime();

        // Keeps you in the game's boundaries
        if(position.x < 0) position.x = 0;
        if(position.x > screen.game.screenWidth - position.getWidth()) position.x = screen.game.screenWidth-position.getWidth();
        if(position.y < 0) position.y = 0;
        if(position.y > screen.game.screenHeight - position.getHeight()) position.y = screen.game.screenHeight-position.getHeight();
    }

    void render(float deltaTime){
        update();
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

}
