package com.sashimi.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;

/**
 * Generic class for rendering buttons outside of Actor
 */
//This is just a simple class to provide quick generic button generation
//For The contains function, MAKE SURE TO PASS RAW X AND Y along with SCREENHEIGHT
    //contains shifts for the coordinate axes
public class EasyButton extends Rectangle{
    private Texture buttonTexture;

    EasyButton(String fileName){
        //Initialize Basic Rectangle
        super();

        //Load In Texture, along with width and height
        buttonTexture = new Texture(Gdx.files.internal("Buttons/"+fileName));
        this.setHeight(buttonTexture.getHeight());
        this.setWidth(buttonTexture.getWidth());
    }

    void setButtonTexture(Texture texture){
        this.buttonTexture = texture;
    }

    Texture getButtonTexture(){
        return buttonTexture;
    }

    //Converts a raw xIn and yIn from Gdx.input to spritebatch coordinate point
    boolean contains(int x, int y, int screenHeight){
        x*=2;
        y = screenHeight - (y * 2);
        return (contains(x,y));
    }

    void dispose(){
        buttonTexture.dispose();
    }

}
