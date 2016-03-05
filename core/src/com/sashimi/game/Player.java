package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Player extends Entity {
    protected OrthographicCamera camera;
    ArrayList<Bullet> bulletManager = new ArrayList<Bullet>();
    public Bullet bullet;
    final protected Rectangle hitbox;
    final Texture hitboxTexture;
    private int touchMoveSpeed = 1500;
    private int keyMoveSpeed = 700;
    private Vector2 velocity;
    public double prevHitTime = 0;
    private int score;
    public int enemiesHit;

    Player(GameScreen screen, int x, int y, String textureName) {
        super(screen, x, y, "Players/"+textureName);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screen.game.screenWidth, screen.game.screenHeight);
        hitboxTexture = new Texture(Gdx.files.internal("Players/hitbox.png"));
        hitbox = new Rectangle(x+position.getWidth()/2,y+position.getHeight()/2,hitboxTexture.getWidth(),hitboxTexture.getHeight());
        velocity = new Vector2(0,0);
        health = 5;
        bulletVelocity = 20;
        firesBullets = true;
    }

    public Rectangle getHitbox() { return hitbox; }

    @Override
    boolean isHit(Rectangle other){
        if(hitbox.contains(other.getX(),other.getY())){
            return true;
        }
        else if(hitbox.contains(other.getX(),(other.getY() + other.getHeight()))){
            return true;
        }
        else if(hitbox.contains(other.getX() + other.getWidth(),other.getY() )){
            return true;
        }
        else if(hitbox.contains(other.getX() + other.getWidth(),other.getY()+other.getHeight())){
            return true;
        }
        return false;
    }

    public void setScore(double currentTime){

        double deltaTime = currentTime - prevHitTime;
        score = score + (int)(1000.0/(deltaTime));
        prevHitTime = currentTime;
    }

    public int getScore(){
        return score;
    }

    // WIP
    public void invincible(float deltaTime) {
        float deltaTimer = deltaTime;
        while (deltaTimer - 5 < 0) {
            return;
        }
    }

    public void fireBullet (float deltaTime) {
        if (health > 0 && firesBullets) {
            fireDelay -= deltaTime;
            if(fireDelay <= 0) {
                Bullet tempBullet = new Bullet(screen, (int)(hitbox.x-hitbox.getWidth()/2), (int)(hitbox.y-hitbox.getHeight()+position.getHeight()/2), "bubble.png", bulletVelocity);
                bulletManager.add(tempBullet);
                Vector2 newVelocity = new Vector2(7,19);
                tempBullet = new Bullet(screen, (int)(hitbox.x-hitbox.getWidth()/2), (int)(hitbox.y-hitbox.getHeight()+position.getHeight()/2), "bubble.png", newVelocity);
                bulletManager.add(tempBullet);
                newVelocity = new Vector2(-7,19);
                tempBullet = new Bullet(screen, (int)(hitbox.x-hitbox.getWidth()/2), (int)(hitbox.y-hitbox.getHeight()+position.getHeight()/2), "bubble.png", newVelocity);
                bulletManager.add(tempBullet);
                fireDelay += 0.2;
            }
        }
    }

    public void fireBullet2 (float deltaTime) {
        if (health > 0 && firesBullets) {
            fireDelay -= deltaTime;
            if(fireDelay <= 0) {
                Bullet tempBullet = new Bullet(screen, (int)(hitbox.x-hitbox.getWidth()/2), (int)(hitbox.y-hitbox.getHeight()+position.getHeight()/2), "bubble.png", bulletVelocity);
                bulletManager.add(tempBullet);
                fireDelay += 0.1;
            }
        }
    }

    public void renderBullets () {
        // Renders bullets
        for (int i=0; i<bulletManager.size(); i++) {
            bullet = bulletManager.get(i);
            bullet.update();
            // Saves memory by removing bullets that are out of bounds
            // Note: Changed to -30 for overlap of rectangle and boundary
            if(bullet.bulletLocation.x > -30 && bullet.bulletLocation.x < screen.game.screenWidth && bullet.bulletLocation.y > 0 && bullet.bulletLocation.y < screen.game.screenHeight)
                screen.game.batch.draw(bullet.texture, bullet.bulletLocation.x, bullet.bulletLocation.y);
            else {
                bulletManager.remove(i);
                if(bulletManager.size() > 0)
                    i--;
            }
        }
    }

    public void update() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            // Find the direction and length to move in
            velocity.set(((touchPos.x - position.x) - position.getWidth() / 2), ((touchPos.y - position.y) - position.getHeight() + 200));

            // normalizes the above vector distance to obtain hypotenuse of 1, then multiplies by speed scalar
            velocity = velocity.nor().scl(touchMoveSpeed);

            // Set add that direction and length to current position
            position.x += (velocity.x) * Gdx.graphics.getDeltaTime();
            position.y += (velocity.y) * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
            keyMoveSpeed = 400;
        else
            keyMoveSpeed = 700;

        // Keyboard input
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) position.x -= keyMoveSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) position.x += keyMoveSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) position.y += keyMoveSpeed * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) position.y -= keyMoveSpeed * Gdx.graphics.getDeltaTime();

        // Keeps you in the game's boundaries
        if(position.x < 0) position.x = 0;
        if(position.x > screen.game.screenWidth - position.getWidth()) position.x = screen.game.screenWidth-position.getWidth();
        if(position.y < 0) position.y = 0;
        if(position.y > screen.game.screenHeight - position.getHeight()) position.y = screen.game.screenHeight-position.getHeight();

        // Updates hitbox position
        hitbox.x = position.x + position.getWidth()/2 - hitbox.getWidth()/2;
        hitbox.y = position.y + position.getHeight()/2 - hitbox.getHeight()/2;
    }

    void render(float deltaTime){
        camera.update();
        // Cool, makes the camera follow the fish
        // camera.position.set(position.x, position.y, 0);
        screen.game.batch.setProjectionMatrix(camera.combined);
        update();
        super.render();

        screen.game.batch.draw(hitboxTexture, hitbox.x, hitbox.y, hitbox.getWidth(), hitbox.getHeight());

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            fireBullet2(deltaTime);
        else
            fireBullet(deltaTime);

        renderBullets();
    }
}
