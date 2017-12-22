package com.waasche.lawnmower.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.waasche.lawnmower.controller.MenuInputProcessor;
import com.waasche.lawnmower.data.LevelTypeMetaData;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.services.LevelService;
import com.waasche.lawnmower.services.MenuDrawingService;
import com.waasche.lawnmower.view.MenuButtonActor;

import java.util.List;

public abstract class MenuScreen implements Screen {

    protected final float SCREEN_UNIT = (((float) Math.min(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight())) / 60.0f);
    protected List<LevelTypeMetaData> levelTypeMetaDataList = LevelService.listLevelPacks();
    public MainClass mainClass = null;
    protected Table container;
    protected Stage stage;
    protected Skin skin;
    public int levelInd;




    public MenuScreen(MainClass mainClass, int levelInd) {
        Assets.updateFont(this.SCREEN_UNIT / 5.0f);
        this.mainClass = mainClass;
        this.levelInd = levelInd;
        this.container = new Table();
        this.stage = new Stage(new ScreenViewport());
        this.container.clearChildren();
        this.skin = new Skin();
        this.skin.add("large", Assets.fontLarge);
        this.skin.add("medium", Assets.fontMedium);
        this.skin.add("small", Assets.fontSmall);
        this.skin.add("lightMedium", new Label.LabelStyle(this.skin.getFont("medium"), Assets.colorText));
        this.skin.add("lightLarge", new Label.LabelStyle(this.skin.getFont("large"), Assets.colorText));
        this.skin.add("lightMedium", new Label.LabelStyle(this.skin.getFont("medium"), Assets.colorText));
        this.skin.add("lightSmall", new Label.LabelStyle(this.skin.getFont("small"), Assets.colorText));
        this.skin.add("darkLarge", new Label.LabelStyle(this.skin.getFont("large"), Assets.colorBackground));
        this.skin.add("darkSmall", new Label.LabelStyle(this.skin.getFont("small"), Assets.colorBackground));
        this.skin.add("buttonBack", Assets.spriteButtonBack, Sprite.class);
        this.skin.add("buttonRate", Assets.spriteButtonRate, Sprite.class);
        this.skin.add("buttonTutorial", Assets.spriteButtonTutorial, Sprite.class);
        this.skin.add("buttonHelp", Assets.spriteButtonHelp, Sprite.class);
        this.skin.add("buttonOk", Assets.spriteButtonOk, Sprite.class);
        this.skin.add("buttonArrowLeft", Assets.spriteButtonArrowLeft, Sprite.class);
        this.skin.add("buttonArrowRight", Assets.spriteButtonArrowRight, Sprite.class);
        this.skin.add("title", Assets.spriteTitle, Sprite.class);
        this.skin.add("start", Assets.spriteStart, Sprite.class);
        this.skin.add("buttonSoundOn", Assets.spriteButtonSoundOn, Sprite.class);
        this.skin.add("buttonSoundOff", Assets.spriteButtonSoundOff, Sprite.class);
        Assets.updateFont(this.SCREEN_UNIT / 5.0f);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new MenuInputProcessor(this, mainClass));
        inputMultiplexer.addProcessor(this.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
    }


    @Override
    public void show()
    {
        this.stage.draw();
    }

    @Override
    public void render(float delta) {
        MenuDrawingService.clearScreen();
        this.container.setFillParent(true);
        this.stage.addActor(this.container);
        stage.act();
        stage.draw();
    }

    public void buttonClick(MenuButtonActor button){
    }


    protected boolean isLandscape() {
        return isLandscape((float) Gdx.app.getGraphics().getWidth(), (float) Gdx.app.getGraphics().getHeight());
    }

    protected boolean isLandscape(float width, float height) {
        return width > height;
    }

    @Override
    public void resize(int width, int height) {
        boolean prevLandscape = isLandscape(this.stage.getWidth(), this.stage.getHeight());
        this.stage.getViewport().update(width, height, true);
        if (prevLandscape != isLandscape()) {
            orientationChange();
        }
    }

    protected void orientationChange() {
    }

    @Override
    public void pause() {

    }


    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.skin.remove("large", BitmapFont.class);
        this.skin.remove("medium", BitmapFont.class);
        this.skin.remove("small", BitmapFont.class);
        this.skin.dispose();
    }
}
