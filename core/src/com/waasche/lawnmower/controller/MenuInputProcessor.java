package com.waasche.lawnmower.controller;

import com.badlogic.gdx.InputProcessor;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.screen.MenuScreen;


public class MenuInputProcessor implements InputProcessor {

    private MenuScreen screen;
    private MainClass mainClass;


    public MenuInputProcessor(MenuScreen screen, MainClass mainClass) {
        this.screen = screen;
        this.mainClass = mainClass;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
