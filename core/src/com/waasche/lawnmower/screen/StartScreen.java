package com.waasche.lawnmower.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.waasche.lawnmower.data.UserSettings;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Sounds;
import com.waasche.lawnmower.view.MenuButtonActor;

/**
 * Created by sadm on 12/8/2017.
 */
public class StartScreen  extends MenuScreen implements Screen {
    private MenuButtonActor buttonHelp = new MenuButtonActor(this, this.skin.getDrawable("buttonHelp"), this.SCREEN_UNIT * 10.0f);
    private Actor buttonPlay;
    private MenuButtonActor buttonSettings;
    private MenuButtonActor buttonSound;
    private Image imageTitle = new Image(this.skin.getDrawable("title"));

    public StartScreen(final MainClass mainClass) {
        super(mainClass, -1);
        this.buttonSound = new MenuButtonActor(this, this.skin.getDrawable(UserSettings.isSoundOn() ? "buttonSoundOn" : "buttonSoundOff"), this.SCREEN_UNIT * 10.0f);
        this.buttonPlay = new Image(this.skin.getDrawable("start"));
        this.buttonPlay.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sounds.playButtonClick(0.0f);
                mainClass.setCurrentScreen(new MainMenuScreen(mainClass));
            }
        });
    }

    public void buttonClick(MenuButtonActor button) {
        super.buttonClick(button);
        if (this.buttonHelp.equals(button)) {
            Sounds.playButtonClick(0.0f);
        } else if (this.buttonSound.equals(button)) {
            UserSettings.toggleSound();
            this.buttonSound.changeImage(this.skin.getDrawable(UserSettings.isSoundOn() ? "buttonSoundOn" : "buttonSoundOff"));
            Sounds.playButtonClick(0.0f);
        }
    }

    @Override
    public void createLayout() {
        this.container.clearChildren();
        float titleWidth = 36.0f * this.SCREEN_UNIT;
        float titleHeight = (this.imageTitle.getHeight() * titleWidth) / this.imageTitle.getWidth();
        if (isLandscape()) {
            this.container.add(this.imageTitle).size(titleWidth, titleHeight).colspan(2).align(8).pad(this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f, 0.0f, 0.0f).expandX();
            this.container.add(this.buttonSound).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(this.SCREEN_UNIT * 4.0f, 0.0f, 0.0f, this.SCREEN_UNIT * 4.0f);
            this.container.row();
            this.container.add(this.buttonPlay).colspan(3).expand();
            this.container.row();
            this.container.add(this.buttonHelp).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(0.0f, this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f, 0.0f);
            this.container.add().expandX();
            this.container.add(this.buttonSettings).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(0.0f, 0.0f, this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f);
            return;
        }
        this.container.add(this.imageTitle).size(titleWidth, titleHeight).colspan(3).padTop(this.SCREEN_UNIT * 4.0f).expandX();
        this.container.row();
        this.container.add(this.buttonPlay).size(titleWidth, titleHeight).colspan(3).padTop(this.SCREEN_UNIT * 4.0f).expandX();
        this.container.row();
        this.container.add(this.buttonHelp).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(0.0f, this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f, 0.0f);
        this.container.add(this.buttonSound).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(0.0f, 0.0f, this.SCREEN_UNIT * 4.0f, 0.0f).expandX();
        this.container.add(this.buttonSettings).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(0.0f, 0.0f, this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f);
    }
}
