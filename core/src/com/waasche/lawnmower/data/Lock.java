package com.waasche.lawnmower.data;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.screen.MenuScreen;

public class Lock extends Group {

    private Image image;
    private int levelInd;
    private boolean isOpen;
    private MenuScreen screen;

    public Lock(MenuScreen menuScreen, boolean isOpen, int levelInd) {
        this.isOpen = isOpen;
        this.screen = menuScreen;
        if (isOpen) {
            this.image = new Image(Assets.lockOpen);
        } else {
            this.image = new Image(Assets.lockClosed);
        }
        addActor(this.image);
        this.levelInd = levelInd;
        addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Lock.this.handleClick();
            }
        });
    }

    public void draw(SpriteBatch spriteBatch){
        image.draw(spriteBatch, 1f);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getLevelInd() {
        return levelInd;
    }

    public void lock(){
        image = new Image(Assets.lockClosed);
    }

    public void unlock(){
        image = new Image(Assets.lockOpen);
    }

    public void handleClick() {
        if(isOpen()){

        }
    }

    public void setLevelInd(int levelInd) {
        this.levelInd = levelInd;
    }

    public Image getImage() {
        return image;
    }

}
