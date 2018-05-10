package com.waasche.lawnmower.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.resources.Sounds;
import com.waasche.lawnmower.view.MenuButtonActor;

public class HelpScreen extends MenuScreen implements Screen {

    private MenuButtonActor buttonBack = new MenuButtonActor(this, this.skin.getDrawable("buttonBack"), Assets.SCREEN_UNIT * 10.0f);
    private Image logoWaasche;
    private Image logoLibGdx;
    private Image donateButton;
    private Image backgroundImage = Assets.grassBackgroundImage;
    private MainClass mainClass;

    public HelpScreen(MainClass mainClass) {
        super(mainClass, 0);
        this.mainClass = mainClass;
        this.container.background(backgroundImage.getDrawable());
        this.skin.add("logoCrabs", Assets.spriteLogoWaasche, Sprite.class);
        this.skin.add("logoLibGdx", Assets.spriteLogoLibGdx, Sprite.class);
        this.skin.add("donate", Assets.spriteDonateButton, Sprite.class);
        logoWaasche = new Image(this.skin.getDrawable("logoCrabs"));
        logoLibGdx = new Image(this.skin.getDrawable("logoLibGdx"));
        donateButton = new Image(this.skin.getDrawable("donate"));
        show();
    }



    public void buttonClick(MenuButtonActor button) {
        super.buttonClick(button);
        if (this.buttonBack.equals(button)) {
            Sounds.playButtonClick(0.0f);
            navigateBack();
        }
    }

    public void show() {
        this.logoWaasche.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sounds.playButtonClick(0.0f);
                Gdx.net.openURI("http://www.waasche.com/");
            }
        });
        this.logoLibGdx.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sounds.playButtonClick(0.0f);
                Gdx.net.openURI("http://libgdx.badlogicgames.com/");
            }
        });
        this.donateButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Sounds.playButtonClick(0.0f);
                Gdx.net.openURI("https://easypay.ua/en/setc2c/95140043");
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
        float f;
        this.container.clearChildren();
        float logoWidth = 25.0f * Assets.SCREEN_UNIT;
        Cell align = this.container.add(this.logoWaasche).size(logoWidth, (this.logoWaasche.getHeight() * logoWidth) / this.logoWaasche.getWidth()).align(4);
        if (isLandscape()) {
            f = 6.0f * Assets.SCREEN_UNIT;
        } else {
            f = 0.0f;
        }
        align.pad(f, 0.0f, Assets.SCREEN_UNIT * 2.0f, 0.0f).colspan(1).expand();
        this.container.row();
        this.container.add(this.logoLibGdx).size(logoWidth, (this.logoLibGdx.getHeight() * logoWidth) / this.logoLibGdx.getWidth()).align(2).padTop(Assets.SCREEN_UNIT * 2.0f).colspan(1).expand();
        this.container.row();
        this.container.add(this.buttonBack).size(Assets.SCREEN_UNIT * 10.0f, Assets.SCREEN_UNIT * 10.0f).pad(0.0f, Assets.SCREEN_UNIT * 4.0f, Assets.SCREEN_UNIT * 4.0f, 0.0f).align(8);
        this.container.add(this.donateButton).size(Assets.SCREEN_UNIT*40f, Assets.SCREEN_UNIT*10f).padLeft(-Assets.SCREEN_UNIT*110f).padTop(Assets.SCREEN_UNIT*8f);
    }
}
