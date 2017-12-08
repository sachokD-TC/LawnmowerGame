package com.waasche.lawnmower.data;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.waasche.lawnmower.resources.Assets;

public class Lawnmower extends Sprite {

    private FieldPoint lawnmowerPosition;
    private Texture up = Assets.lawnmowerUpRightTexture;
    private Texture down = Assets.lawnmowerDownLeftTexture;
    private Texture left = Assets.lawnmowerUpLeftTexture;
    private Texture right = Assets.lawnmowerDownRightTexture;
    public Lawnmower(Sprite sprite) {
        super(sprite);
    }


    public FieldPoint getLawnmowerPosition() {
        return lawnmowerPosition;
    }

    public void setLawnmowerPosition(FieldPoint lawnmowerPosition) {
        super.setPosition(lawnmowerPosition.getX(), lawnmowerPosition.getY());
        this.lawnmowerPosition = lawnmowerPosition;
    }

    public void upTexture(){
        this.setTexture(up);
    }

    public void downTexture(){
        this.setTexture(down);
    }

    public void leftTexture(){
        this.setTexture(left);
    }

    public void rightTexture(){
        this.setTexture(right);
    }
}
