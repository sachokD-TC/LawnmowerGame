package com.waasche.lawnmower.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.resources.Sounds;
import com.waasche.lawnmower.view.MenuButtonActor;

public class HelpScreen extends MenuScreen implements Screen {

    private MenuButtonActor buttonBack = new MenuButtonActor(this, this.skin.getDrawable("buttonBack"), this.SCREEN_UNIT * 10.0f);
    private MenuButtonActor buttonRate = new MenuButtonActor(this, this.skin.getDrawable("buttonRate"), this.SCREEN_UNIT * 10.0f);
    private MenuButtonActor buttonTutorial = new MenuButtonActor(this, this.skin.getDrawable("buttonTutorial"), this.SCREEN_UNIT * 10.0f);
    private Image logoWaasche;
    private Image logoLibGdx;
    private MainClass mainClass;

    public HelpScreen(MainClass mainClass) {
        super(mainClass, 0);
        this.mainClass = mainClass;
        this.skin.add("logoCrabs", Assets.spriteLogoWaasche, Sprite.class);
        this.skin.add("logoLibGdx", Assets.spriteLogoLibGdx, Sprite.class);
        logoWaasche = new Image(this.skin.getDrawable("logoCrabs"));
        logoLibGdx = new Image(this.skin.getDrawable("logoLibGdx"));
        show();
    }



    public void buttonClick(MenuButtonActor button) {
        super.buttonClick(button);
        if (this.buttonBack.equals(button)) {
            Sounds.playTickSound(0.0f);
            navigateBack();
        } else if (this.buttonTutorial.equals(button)) {
            Sounds.playButtonClick(0.0f);
            mainClass.setCurrentScreen(new TutorialScreen(mainClass));
        } else if (this.buttonRate.equals(button)) {
            Sounds.playButtonClick(0.0f);
            Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.waasche.lawnmower");
        }
    }

    public void show() {
        this.logoWaasche.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sounds.playButtonClick(0.0f);
                Gdx.net.openURI("http://waasche.com/");
            }
        });
        this.logoLibGdx.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sounds.playButtonClick(0.0f);
                Gdx.net.openURI("http://libgdx.badlogicgames.com/");
            }
        });
        createLayout();
        this.container.setFillParent(true);
        this.stage.addActor(this.container);
    }

    public void navigateBack() {
        mainClass.setCurrentScreen(new StartScreen(mainClass));
    }

    private void createLayout() {
        int columns;
        float f;
        this.container.clearChildren();
        if (isLandscape()) {
            columns = 4;
            this.container.add(this.buttonTutorial).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f, 0.0f, 0.0f).align(8);
            this.container.add(new Label(Assets.strings.get("help"), this.skin, "lightSmall")).pad(this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 2.0f, 0.0f, 0.0f).align(8).expandX();
            this.container.add(new Label(Assets.strings.get("rate"), this.skin, "lightSmall")).pad(this.SCREEN_UNIT * 4.0f, 0.0f, 0.0f, this.SCREEN_UNIT * 2.0f).align(16).expandX();
            this.container.add(this.buttonRate).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(this.SCREEN_UNIT * 4.0f, 0.0f, 0.0f, this.SCREEN_UNIT * 4.0f).align(16);
            this.container.row();
        } else {
            columns = 3;
            this.container.add(this.buttonTutorial).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f, 0.0f, 0.0f).align(8);
            this.container.add().size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(this.SCREEN_UNIT * 4.0f, 0.0f, 0.0f, this.SCREEN_UNIT * 4.0f);
            this.container.row();
            this.container.add().size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(this.SCREEN_UNIT * 2.0f, this.SCREEN_UNIT * 4.0f, 0.0f, 0.0f);
            this.container.add(this.buttonRate).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(this.SCREEN_UNIT * 2.0f, 0.0f, 0.0f, this.SCREEN_UNIT * 4.0f).align(16);
            this.container.row();
        }
        float logoWidth = 36.0f * this.SCREEN_UNIT;
        Cell align = this.container.add(this.logoWaasche).size(logoWidth, (this.logoWaasche.getHeight() * logoWidth) / this.logoWaasche.getWidth()).align(4);
        if (isLandscape()) {
            f = 6.0f * this.SCREEN_UNIT;
        } else {
            f = 0.0f;
        }
        align.pad(f, 0.0f, this.SCREEN_UNIT * 2.0f, 0.0f).colspan(columns).expand();
        this.container.row();
        this.container.add(this.logoLibGdx).size(logoWidth, (this.logoLibGdx.getHeight() * logoWidth) / this.logoLibGdx.getWidth()).align(2).padTop(this.SCREEN_UNIT * 2.0f).colspan(columns).expand();
        this.container.row();
        this.container.add(this.buttonBack).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(0.0f, this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f, 0.0f).align(8).colspan(columns);
    }
}
