package com.waasche.lawnmower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.waasche.lawnmower.data.LevelTypeMetaData;
import com.waasche.lawnmower.data.SessionData;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.resources.Sounds;
import com.waasche.lawnmower.view.LevelTypeActor;
import com.waasche.lawnmower.view.MenuButtonActor;

import java.util.Iterator;


public class MainMenuScreen extends MenuScreen implements Screen {

    private Table list;
    private final ScrollPane.ScrollPaneStyle scrollStyle;
    protected final ScrollPane scroll;

    public MainMenuScreen(MainClass mainClass) {
        super(mainClass, 0);
        this.list = new Table();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        for (LevelTypeMetaData levelTypeMetaData : levelTypeMetaDataList) {
            pixmap.setColor(levelTypeMetaData.getColor());
            pixmap.fill();
            this.skin.add("buttonLevelPack_" + levelTypeMetaData.getId(), new Texture(pixmap));
        }
        this.skin.add("blackLine", new Texture(pixmap));
        pixmap.dispose();
        pixmap = new Pixmap((int) Assets.SCREEN_UNIT, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Assets.colorText);
        pixmap.fill();
        this.skin.add("scrollKnob", new Texture(pixmap));
        pixmap.dispose();
        this.scrollStyle = new ScrollPane.ScrollPaneStyle();
        this.scrollStyle.vScrollKnob = this.skin.getDrawable("scrollKnob");
        this.scroll = new ScrollPane(this.list, this.scrollStyle);
        this.scroll.setFadeScrollBars(false);
        this.show();
    }

    public void show() {
        createLayout();
        this.container.setFillParent(true);
        this.stage.addActor(this.container);
        this.scroll.layout();
        this.scroll.setScrollY(SessionData.getLevelPackSelectScrollPosition());
        this.scroll.updateVisualScroll();

    }

    @Override
    public void buttonClick(MenuButtonActor button) {
        //mainClass.setCurrentScreen(new LevelPicker(mainClass, button.getLevelInd()));
    }

    protected void orientationChange() {
        super.orientationChange();
    }


    public void createLayout() {
        this.list.clearChildren();
        this.container.clearChildren();
        for (int i = 0; i != levelTypeMetaDataList.size(); i++) {
            float f;
            Actor levelPackActor = new LevelTypeActor(levelTypeMetaDataList.get(i), this.skin, Assets.SCREEN_UNIT * 13.5f);
            levelPackActor.addListener(new LevelPackClickListener(levelPackActor));
            Cell width = this.list.add(levelPackActor).width(calcListWidth());
            if(i == levelTypeMetaDataList.size()-1){
                width.pad(0.0f, Assets.SCREEN_UNIT * 2.0f, Assets.SCREEN_UNIT * 5.5f, Assets.SCREEN_UNIT * 2.0f);
            } else {
                width.pad(0.0f, Assets.SCREEN_UNIT * 2.0f, Assets.SCREEN_UNIT * 1.5f, Assets.SCREEN_UNIT * 2.0f);
            }
            this.list.row();
        }
        this.list.row();
        this.list.setWidth(calcListWidth());
        this.scroll.setScrollingDisabled(true, false);
        this.container.add(new Label(Assets.strings.get("levelPackHint"), this.skin, "lightMedium")).height(Assets.SCREEN_UNIT * 10.0f).padBottom(Assets.SCREEN_UNIT * 2.0f).expandX();
        this.container.row();
        this.container.add(this.scroll).padBottom(isLandscape() ? -9.0f * Assets.SCREEN_UNIT : 0.0f).expand();
    }

    private float calcListWidth() {
        return Math.min(((float) Gdx.app.getGraphics().getWidth()) - (Assets.SCREEN_UNIT * 8.0f), Assets.SCREEN_UNIT * 60.0f);
    }


    public void navigateBack() {
        mainClass.setCurrentScreen(new StartScreen(mainClass));
        mainClass.showCurrentScreen();
    }


    public void resize(int width, int height) {
        super.resize(width, height);
        Iterator it = this.list.getCells().iterator();
        while (it.hasNext()) {
            ((Cell) it.next()).width(calcListWidth());
        }
        this.list.setWidth(calcListWidth());
        this.list.layout();
    }

    private class LevelPackClickListener extends ClickListener {
        private LevelTypeActor actor;

        public LevelPackClickListener(Actor actor) {
            this.actor = (LevelTypeActor) actor;
        }

        public void clicked(InputEvent event, float x, float y) {
            Sounds.playMenuClick(0.0f);
            mainClass.setCurrentScreen(new LevelSelectScreen(mainClass, actor.getMetaData()));
            mainClass.showCurrentScreen();
        }
    }

}
