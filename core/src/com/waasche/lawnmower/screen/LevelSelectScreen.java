package com.waasche.lawnmower.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.waasche.lawnmower.data.Level;
import com.waasche.lawnmower.data.LevelTypeMetaData;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.view.LevelActor;
import com.waasche.lawnmower.view.MenuButtonActor;

import java.util.List;

public class LevelSelectScreen extends MenuScreen implements Screen {
    LevelTypeMetaData levelPackMetaData;
    List<Level> levelsMetaData;
    private MenuButtonActor buttonBack;
    private Table levelGrid;
    private MainClass mainClass;

    public LevelSelectScreen(MainClass mainClass, LevelTypeMetaData levelPackMetaData) {
        super(mainClass, levelPackMetaData.getId());
        this.mainClass = mainClass;
        this.levelPackMetaData = levelPackMetaData;
        this.levelsMetaData = Assets.levelsList.get(levelPackMetaData.getId()).getLevels();
        this.skin.add("colorLarge", new LabelStyle(this.skin.getFont("large"), levelPackMetaData.getColor()));
        this.skin.add("colorSmall", new LabelStyle(this.skin.getFont("small"), levelPackMetaData.getColor()));
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Assets.colorButtonLevelIncomplete);
        pixmap.fill();
        this.skin.add("buttonLevelIncomplete", new Texture(pixmap));
        pixmap.setColor(levelPackMetaData.getColor());
        pixmap.fill();
        this.skin.add("buttonLevelComplete", new Texture(pixmap));
        pixmap.dispose();
        this.buttonBack = new MenuButtonActor(this, this.skin.getDrawable("buttonBack"), 10.0f * this.SCREEN_UNIT);
        this.levelGrid = new Table();
    }


    public void buttonClick(MenuButtonActor button) {
        super.buttonClick(button);
        if (this.buttonBack.equals(button)) {
            mainClass.setCurrentScreen(new MainMenuScreen(mainClass) {
            });
        }
    }

    protected void orientationChange() {
        createLayout();
    }

    public void show() {
        createLayout();
        this.container.setFillParent(true);
        this.stage.addActor(this.container);
    }

    private void createLayout() {
        float f;
        this.container.clearChildren();
        fillLevelGrid();
        this.container.add(new Label(Assets.strings.get(this.levelPackMetaData.getTitle()), this.skin, "lightLarge")).height(8.0f * this.SCREEN_UNIT).padTop(2.0f * this.SCREEN_UNIT).expandX();
        this.container.row();
        this.container.add(new Label(Assets.strings.get(this.levelPackMetaData.getSubtitle()), this.skin, "lightSmall")).height(3.0f * this.SCREEN_UNIT).expandX();
        this.container.row();
        Cell add = this.container.add(this.levelGrid);
        if (isLandscape()) {
            f = -5.0f * this.SCREEN_UNIT;
        } else {
            f = 0.0f;
        }
        add.padBottom(f).expand();
        this.container.row();
        this.container.add(this.buttonBack).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(0.0f, this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f, 0.0f).align(8);
    }

    private void fillLevelGrid() {
        int columns = isLandscape() ? 9 : 6;
        for (Level level : levelsMetaData) {
            Actor levelActor = new LevelActor(this.levelPackMetaData, level, this.skin, this.SCREEN_UNIT * 6.0f);
            levelActor.addListener(new LevelClickListener(levelActor, this.levelPackMetaData));
            this.levelGrid.add(levelActor).pad(this.SCREEN_UNIT * 0.75f);
            if ((levelsMetaData.indexOf(level) + 1) % columns == 0) {
                this.levelGrid.row();
            }
        }
    }

    private class LevelClickListener extends ClickListener {
        private LevelActor actor;
        private LevelTypeMetaData levelTypeMetaData;

        public LevelClickListener(Actor actor, LevelTypeMetaData levelTypeMetaData) {
            this.actor = (LevelActor) actor;
            this.levelTypeMetaData = levelTypeMetaData;
        }

        public void clicked(InputEvent event, float x, float y) {
            mainClass.setCurrentScreen(new GameScreen(mainClass, this.levelTypeMetaData, Integer.valueOf(actor.getLevelMetaData().getName())));
        }
    }
}
