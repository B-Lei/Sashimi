package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Player extends Entity {
    final int moveSpeed = 10;
    private OrthographicCamera camera;
    private float fireDelay;
    public int health = 5;
    public Bullet bullet;

    ArrayList<Bullet> bulletManager = new ArrayList<Bullet>();

    public Player(GameScreen screen, int x, int y, String textureName) {
        super(screen, x, y, textureName);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screen.game.screenWidth, screen.game.screenHeight);
    }

    public void update() {
        screen.game.batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            // Added some value to y to position fish above finger
            if(position.x < touchPos.x) {
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
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) position.x -= 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) position.x += 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) position.y += 800 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) position.y -= 800 * Gdx.graphics.getDeltaTime();
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
            screen.game.batch.draw(bullet.texture, bullet.bulletLocation.x, bullet.bulletLocation.y);
            counter++;
        }

        screen.game.batch.draw(texture, position.x, position.y, position.getWidth(), position.getHeight());
    }
}
