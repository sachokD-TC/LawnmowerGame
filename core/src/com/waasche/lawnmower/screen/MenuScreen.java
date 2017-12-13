package com.waasche.lawnmower.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.waasche.lawnmower.controller.MenuInputProcessor;
import com.waasche.lawnmower.data.LevelTypeMetaData;
import com.waasche.lawnmower.data.Lock;
import com.waasche.lawnmower.data.SessionData;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.resources.Sounds;
import com.waasche.lawnmower.services.LevelService;
import com.waasche.lawnmower.services.MenuDrawingService;
import com.waasche.lawnmower.view.LevelTypeActor;
import com.waasche.lawnmower.view.MenuButtonActor;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuScreen implements Screen {

    protected final float SCREEN_UNIT = (((float) Math.min(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight())) / 60.0f);
    private final ScrollPane.ScrollPaneStyle scrollStyle;
    private final ScrollPane scroll;
    private List<LevelTypeMetaData> levelTypeMetaDataList = LevelService.listLevelPacks();
    public MainClass mainClass = null;
    private Table list;
    public List<Lock> locks = new ArrayList<Lock>();
    protected Table container;
    protected Stage stage;
    protected Skin skin;
    public int levelInd;



    public MenuScreen(MainClass mainClass, int levelInd) {
        this.locks.clear();
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
        this.skin.add("buttonHelp", Assets.spriteButtonHelp, Sprite.class);
        this.skin.add("iconStar", Assets.spriteIconStar, Sprite.class);
        this.skin.add("title", Assets.spriteTitle, Sprite.class);
        this.skin.add("start", Assets.spriteStart, Sprite.class);
        this.skin.add("buttonSoundOn", Assets.spriteButtonSoundOn, Sprite.class);
        this.skin.add("buttonSoundOff", Assets.spriteButtonSoundOff, Sprite.class);
        Assets.updateFont(this.SCREEN_UNIT / 5.0f);
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        for (LevelTypeMetaData levelTypeMetaData : levelTypeMetaDataList) {
            pixmap.setColor(levelTypeMetaData.getColor());
            pixmap.fill();
            this.skin.add("buttonLevelPack_" + levelTypeMetaData.getId(), new Texture(pixmap));
        }
        pixmap.dispose();
        pixmap = new Pixmap((int) this.SCREEN_UNIT, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Assets.colorText);
        pixmap.fill();
        this.skin.add("scrollKnob", new Texture(pixmap));
        pixmap.dispose();
        this.list = new Table();
        this.scrollStyle = new ScrollPane.ScrollPaneStyle();
        this.scrollStyle.vScrollKnob = this.skin.getDrawable("scrollKnob");
        this.scroll = new ScrollPane(this.list, this.scrollStyle);
        this.scroll.setFadeScrollBars(false);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new MenuInputProcessor(this, mainClass));
        inputMultiplexer.addProcessor(this.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    @Override
    public void show()
    {
        this.stage.draw();
        createLayout();
    }

    @Override
    public void render(float delta) {
        MenuDrawingService.clearScreen();
        this.container.setFillParent(true);
        this.stage.addActor(this.container);
        this.scroll.layout();
        this.scroll.setScrollY(SessionData.getLevelPackSelectScrollPosition());
        this.scroll.updateVisualScroll();
        stage.act();
        stage.draw();
    }

    public List<Lock> getLocks() {
        return locks;
    }

    public void buttonClick(MenuButtonActor button){
    }


    public void createLayout() {
        this.list.clearChildren();
        this.container.clearChildren();
        for (int i=0; i!=levelTypeMetaDataList.size(); i++) {
            float f;
            Actor levelPackActor = new LevelTypeActor(levelTypeMetaDataList.get(i), this.skin, this.SCREEN_UNIT * 13.5f);
            levelPackActor.addListener(new LevelPackClickListener(levelPackActor));
            Cell width = this.list.add(levelPackActor).width(calcListWidth());
            float f2 = this.SCREEN_UNIT * 2.0f;
            if (i < this.levelTypeMetaDataList.size() - 1) {
                f = this.SCREEN_UNIT * 1.5f;
            } else {
                f = 0.0f;
            }
            width.pad(0.0f, f2, f, this.SCREEN_UNIT * 2.0f);
            this.list.row();
        }
        this.list.setWidth(calcListWidth());
        this.scroll.setScrollingDisabled(true, false);
        this.container.add(new Label(Assets.strings.get("levelPackHint"), this.skin, "lightMedium")).height(this.SCREEN_UNIT * 10.0f).padBottom(this.SCREEN_UNIT * 2.0f).expandX();
        this.container.row();
        this.container.add(this.scroll).padBottom(isLandscape() ? -9.0f * this.SCREEN_UNIT : 0.0f).expand();
        this.container.row();
        //this.container.add(this.buttonBack).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f, 0.0f).align(8);
    }

    protected boolean isLandscape() {
        return isLandscape((float) Gdx.app.getGraphics().getWidth(), (float) Gdx.app.getGraphics().getHeight());
    }

    private float calcListWidth() {
        return Math.min(((float) Gdx.app.getGraphics().getWidth()) - (this.SCREEN_UNIT * 8.0f), this.SCREEN_UNIT * 60.0f);
    }

    protected boolean isLandscape(float width, float height) {
        return width > height;
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

    private class LevelPackClickListener extends ClickListener {
        private LevelTypeActor actor;

        public LevelPackClickListener(Actor actor) {
            this.actor = (LevelTypeActor) actor;
        }

        public void clicked(InputEvent event, float x, float y) {
            SessionData.setLevelPackSelectScrollPosition(MenuScreen.this.scroll.getScrollY());
            Sounds.playMenuClick(0.0f);
            mainClass.setCurrentScreen(new LevelSelectScreen(mainClass, actor.getMetaData()));
            mainClass.showCurrentScreen();
        }
    }
}
