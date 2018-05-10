package com.waasche.lawnmower.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.waasche.lawnmower.data.GameProgress;
import com.waasche.lawnmower.data.Level;
import com.waasche.lawnmower.data.LevelTypeMetaData;
import com.waasche.lawnmower.resources.Assets;

public class LevelActor extends Group {

    private final LevelTypeMetaData levelPackMetaData;
    private final Level levelMetaData;
    private float size;
    private Skin skin;

    public LevelActor(LevelTypeMetaData levelPackMetaData, Level metaData, Skin skin, float size) {
        this.levelPackMetaData = levelPackMetaData;
        this.levelMetaData = metaData;
        this.skin = skin;
        this.size = size;
        boolean completed = GameProgress.isCompleted("" + levelPackMetaData.getId(), metaData.getName());
        Image image = new Image(skin.getDrawable(completed ? "buttonLevelComplete" : "buttonLevelIncomplete"));
        image.setSize(size, size);
        addActor(image);
        Label label = new Label(metaData.getName(), skin, "lightMedium");
        label.setColor(completed ? Assets.colorBackground : levelPackMetaData.getColor());
        label.setBounds(0.0f, 0.0f, size, size);
        label.setAlignment(Align.left);
        addActor(label);
        if(completed){
            Label labelSmall = new Label("" + GameProgress.getFirstSuccess("" + levelPackMetaData.getId(), "" + metaData.getName()), skin, "lightSmall");
            labelSmall.setBounds(0.0f, 0.0f, size, size);
            labelSmall.setAlignment(Align.top);
            addActor(labelSmall);
        }
        setSize(size, size);
    }

    public Level getLevelMetaData() {
        return this.levelMetaData;
    }
}
