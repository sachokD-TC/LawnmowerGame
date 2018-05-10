package com.waasche.lawnmower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.waasche.lawnmower.controller.MenuInputProcessor;
import com.waasche.lawnmower.data.UserSettings;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.resources.Sounds;
import com.waasche.lawnmower.view.MenuButtonActor;

/**
 * Created by sadm on 12/8/2017.
 */
public class StartScreen  extends MenuScreen implements Screen {
    private MenuButtonActor buttonHelp = new MenuButtonActor(this, this.skin.getDrawable("buttonHelp"), Assets.SCREEN_UNIT * 10.0f);
    private MenuButtonActor buttonRate = new MenuButtonActor(this, this.skin.getDrawable("buttonRate"), Assets.SCREEN_UNIT * 10.0f);
    private MenuButtonActor buttonTutorial = new MenuButtonActor(this, this.skin.getDrawable("buttonTutorial"), Assets.SCREEN_UNIT * 10.0f);
    private MenuButtonActor buttonPlay =  new MenuButtonActor(this, this.skin.getDrawable("playButton"), Assets.SCREEN_UNIT * 50.0f);;
    private MenuButtonActor buttonSound;
    private Image title = new Image(this.skin.getDrawable("title"));
    protected InputMultiplexer inputMultiplexer = new InputMultiplexer();

    public StartScreen(final MainClass mainClass) {
        super(mainClass, -1);
        Sounds.playStartScreenMusic();
        this.container.background(title.getDrawable());
        this.buttonSound = new MenuButtonActor(this, this.skin.getDrawable(UserSettings.isSoundOn() ? "buttonSoundOn" : "buttonSoundOff"), Assets.SCREEN_UNIT * 10.0f);
        this.buttonPlay.setRotation(-135);
        this.buttonPlay.setOrigin(31*Assets.SCREEN_UNIT,17*Assets.SCREEN_UNIT);
        this.buttonPlay.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                pressButton(new MainMenuScreen(mainClass));
            }
        });
        this.buttonHelp.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                pressButton(new HelpScreen(mainClass));
            }
        });
        this.buttonTutorial.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                pressButton(new TutorialScreen(mainClass));
            }
        });
        this.buttonRate.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sounds.playButtonClick(0.0f);
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.waasche.lawnmower");
            }
        });

        createLayout();
        inputMultiplexer.addProcessor(new MenuInputProcessor(this, mainClass));
        inputMultiplexer.addProcessor(this.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
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

    private void pressButton(MenuScreen menuScreen) {
        Sounds.playButtonClick(0.0f);
        mainClass.setCurrentScreen(menuScreen);
    }

    public void createLayout() {
        this.container.clearChildren();
        float titleWidth = 80f * Assets.SCREEN_UNIT;
        float titleHeight = 70f * Assets.SCREEN_UNIT;
        if (isLandscape()) {
            this.container.add(buttonPlay).size(titleWidth/1.6f, titleHeight/1.6f).colspan(2).align(Align.center).pad(Assets.SCREEN_UNIT * 4.0f, Assets.SCREEN_UNIT * 4.0f, 0.0f, 0.0f);
            this.container.row();
            this.container.add(this.buttonHelp).size(Assets.SCREEN_UNIT * 1.0f, Assets.SCREEN_UNIT * 10.0f);
            this.container.add(this.buttonSound).size(Assets.SCREEN_UNIT * 15f, Assets.SCREEN_UNIT * 10.0f);
            this.container.add(this.buttonRate).size(Assets.SCREEN_UNIT * 20.0f, Assets.SCREEN_UNIT * 10.0f);
            this.container.add(this.buttonTutorial).size(Assets.SCREEN_UNIT * 20.0f, Assets.SCREEN_UNIT * 10.0f);
            return;
        }
        this.container.add(this.title).size(titleWidth, titleHeight).colspan(3).padTop(Assets.SCREEN_UNIT * 4.0f).expandX();
        this.container.row();
        this.container.add(this.title).size(titleWidth, titleHeight).colspan(3).padTop(Assets.SCREEN_UNIT * 4.0f).expandX();
        this.container.row();
        this.container.add(this.buttonHelp).size(Assets.SCREEN_UNIT * 10.0f, Assets.SCREEN_UNIT * 10.0f).pad(0.0f, Assets.SCREEN_UNIT * 4.0f, Assets.SCREEN_UNIT * 4.0f, 0.0f);
        this.container.add(this.buttonSound).size(Assets.SCREEN_UNIT * 10.0f, Assets.SCREEN_UNIT * 10.0f).pad(0.0f, 0.0f, Assets.SCREEN_UNIT * 4.0f, 0.0f).expandX();
    }
}
