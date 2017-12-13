package com.waasche.lawnmower.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Screen;
import com.waasche.lawnmower.data.GameProgress;
import com.waasche.lawnmower.data.UserSettings;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.screen.StartScreen;


public class MainClass extends ApplicationAdapter {

    private Screen currentScreen;

    @Override
    public void create(){
        Assets.load();
        GameProgress.load();
        UserSettings.load();
        GameProgress.setLevelTypeCompleted("4", true);
        GameProgress.setCompleted("4", "1", true);
        currentScreen = new StartScreen(this);
        showCurrentScreen();
    }

    @Override
    public void render(){
        currentScreen.render(2f);
    }

    public void showCurrentScreen(){
        currentScreen.show();
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }
}