package com.waasche.lawnmower.screen;


import com.badlogic.gdx.Screen;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.view.MenuButtonActor;

public class LevelPicker extends MenuScreen implements Screen {

    public LevelPicker(MainClass mainClass, int levelInd) {
        super(mainClass, levelInd);
        this.stage.addActor(this.container);
    }

    @Override
    public void show() {

    }


    @Override
    public void buttonClick(MenuButtonActor button) {
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
