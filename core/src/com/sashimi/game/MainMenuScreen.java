package com.sashimi.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class MainMenuScreen implements Screen {

    final Sashimi game;
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture buttonTex;
    Rectangle button;

    public MainMenuScreen(final Sashimi game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.screenWidth, game.screenHeight);
        batch = new SpriteBatch();


        //Set up texture
        buttonTex = new Texture(Gdx.files.internal("Raw_Images/Play Button.png"));
        int buttonHeight = buttonTex.getHeight();
        int buttonWidth = buttonTex.getWidth();
        button = new Rectangle((game.screenWidth/2)-(buttonWidth/2), (game.screenHeight/2), buttonWidth, buttonHeight);
        //System.out.println("screenHeight: "+game.screenHeight);
        //System.out.println("screenWidth: "+game.screenWidth);





    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        game.batch.begin();
        game.batch.draw(buttonTex, button.x, button.y);
        game.batch.end();


        if(Gdx.input.isTouched()){
            int x = Gdx.input.getX();
            int y = game.screenHeight - (Gdx.input.getY() * 2);
            System.out.println("Touched: "+x+","+y);
            System.out.println("Rectangle"+button.getX()+","+button.getY());

            boolean fitsInWidth=false;
            if(x >= button.getX() && x<= button.getX()+button.getWidth()){
                fitsInWidth = true;
            }
            boolean fitsInHeight = false;
            if(y>=button.getY() && y<= button.getHeight() + button.getY()){
                fitsInHeight = true;
            }

            System.out.println(button.contains(x,y));
            if(fitsInHeight && fitsInWidth){
                game.level1();
            }
        }
    }



    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
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
    public void dispose(){
        batch.dispose();
        buttonTex.dispose();
    }
}
