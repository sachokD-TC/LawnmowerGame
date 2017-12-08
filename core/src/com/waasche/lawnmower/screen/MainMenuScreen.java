package com.waasche.lawnmower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.waasche.lawnmower.data.GameProgress;
import com.waasche.lawnmower.data.Lock;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.view.MenuButtonActor;


public class MainMenuScreen extends MenuScreen implements Screen {

    public MainMenuScreen(MainClass mainClass) {
        super(mainClass, 0);
        this.show();
        this.stage.addActor(this.container);
    }

    @Override
    public void buttonClick(MenuButtonActor button) {
        //mainClass.setCurrentScreen(new LevelPicker(mainClass, button.getLevelInd()));
    }

    public void prepareLocks() {
        for (int i = 0; i != 5; i++) {
            Lock lock = new Lock(this, GameProgress.isLevelTypeCompleted("" + (i + 3)), 3 + i);
            locks.add(lock);
            this.container.add(lock).left();
        }
    }

    public void navigateBack() {
        Gdx.app.exit();
    }

}
