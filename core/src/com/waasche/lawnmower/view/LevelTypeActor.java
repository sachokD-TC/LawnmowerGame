package com.waasche.lawnmower.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.waasche.lawnmower.data.LevelTypeMetaData;
import com.waasche.lawnmower.resources.Assets;

public class LevelTypeActor extends Table {
    private LevelTypeMetaData metaData;
    private float size;
    private Skin skin;

    public LevelTypeActor(LevelTypeMetaData metaData, Skin skin, float size) {
        this.metaData = metaData;
        this.skin = skin;
        this.size = size;
        this.skin.add("darkLarge", new Label.LabelStyle(this.skin.getFont("large"), Assets.colorBackground));
        setBackground(skin.getDrawable("buttonLevelPack_" + metaData.getId()));
        add(new Label(Assets.strings.get(metaData.getTitle()), skin, "darkLarge")).expandX().left().pad(size / 20.0f, size / 10.0f, 0.0f, 0.0f);
        add(createIconStar()).size(size / 1.8f, size / 1.8f).pad(size / 10.0f, 0.0f, 0.0f, size / 10.0f);
        row();
        if(metaData.getSubtitle() != null) {
            add(new Label(Assets.strings.get(metaData.getSubtitle()), skin, "darkSmall")).expandX().left().pad(0.0f, size / 10.0f, size / 8.0f, 0.0f);
        }
        add(new Label(metaData.getCompletedLevels() + "/" + metaData.getTotalLevels(), skin, "darkSmall")).pad(0.0f, 0.0f, size / 10.0f, size / 10.0f);
        row();
        Table progressTable = new Table();
        Button button = new Button(Assets.gradientRectangle);
        button.setTransform(true);
        float fullSizeOfProgressBar = 60*Assets.SCREEN_UNIT;
        float progressSize = fullSizeOfProgressBar*getMetaData().getCompletedLevels()/getMetaData().getTotalLevels();
        progressTable.add(button).width(progressSize).padLeft(8.842f*Assets.SCREEN_UNIT - fullSizeOfProgressBar+progressSize);
        add(progressTable).width(this.getWidth()).fillX();


        pack();
    }

    private Actor createIconStar() {
        Actor iconStarBase = new Image(this.skin.getDrawable("buttonOk"));
        iconStarBase.setSize(this.size / 1.8f, this.size / 1.8f);
        if (!this.metaData.isCompleted()) {
            return iconStarBase;
        }
        Group iconStar = new Group();
        iconStar.addActor(iconStarBase);
        Image iconStarFill = new Image(Assets.spriteButtonRate.getTexture());
        iconStarFill.setSize(this.size / 1.8f, this.size / 1.8f);
        iconStar.addActor(iconStarFill);
        return iconStar;
    }

    public LevelTypeMetaData getMetaData() {
        return this.metaData;
    }
}
