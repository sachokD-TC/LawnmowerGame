package com.waasche.lawnmower.resources;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.I18NBundle;
import com.waasche.lawnmower.data.Lawnmower;
import com.waasche.lawnmower.data.Level;
import com.waasche.lawnmower.data.LevelsList;

import java.util.HashMap;
import java.util.Map;

public class Assets {

    public static final float SCALE_X = 1f;
    public static final float SCALE_Y = 1f;
    public static final float LOCK_WIDTH = 100f;
    public static final float LOCK_HEIGHT = 100f;
    public static final float ANDROID_WIDTH = Gdx.graphics.getWidth();
    public static final float ANDROID_HEIGHT = Gdx.graphics.getHeight();
    public static Lawnmower lawnmowerUpRight;
    public static Color colorBackground;
    public static Color colorButtonLevelIncomplete;
    public static final Texture lawnmowerUpRightTexture = new Texture("actor/up-right.png");
    public static final Texture lawnmowerUpLeftTexture =  new Texture("actor/up-left.png");
    public static final Texture lawnmowerDownRightTexture = new Texture("actor/down-right.png");
    public static final Texture lawnmowerDownLeftTexture = new Texture("actor/down-left.png");
    public static Sprite lockClosed;
    public static Sprite lockOpen;
    public static Sprite grassFull;
    public static Sprite grassEmpty;
    public static Sprite wallSprite;
    public static Map<Integer, LevelsList> levelsList = new HashMap<Integer, LevelsList>();
    public static Color wallColor;
    public static Label.LabelStyle textStyle;
    public static Color colorText;
    public static BitmapFont fontLarge;
    public static BitmapFont fontMedium;
    public static BitmapFont fontSmall;
    private static float fontScale;
    public static I18NBundle strings;
    public static Sprite spriteIconStar;
    public static Sprite spriteIconStarFill;
    public static Sprite spriteTitle;
    public static Sprite spriteButtonBack;


    public static void load() {
        lawnmowerUpRight = createLawnmoverSprite("actor/up-right.png");
        colorBackground = createColor(26, 26, 26);
        grassFull = new Sprite(new Texture(Gdx.files.internal("data/grass-full.png")));
        grassEmpty = new Sprite(new Texture(Gdx.files.internal("data/grass-empty.png")));
        wallSprite = new Sprite(new Texture(Gdx.files.internal("data/wall.png")));
        spriteTitle = createSprite("menu/title.png");
        lockOpen = createSprite("menu/lock_open.png");
        lockClosed = createSprite("menu/lock_closed.png");
        lockClosed.setSize(LOCK_WIDTH, LOCK_HEIGHT);
        levelsList.put(4, new Level("levels/levels4.json").getLevelsList());
        levelsList.put(5, new Level("levels/levels5.json").getLevelsList());
        levelsList.put(6, new Level("levels/levels6.json").getLevelsList());
        wallColor = new Color(1, 0, 0, 1);
        textStyle = new Label.LabelStyle();
        textStyle.font = new BitmapFont();
        fontMedium = new BitmapFont();
        fontScale = 0.0f;
        strings = I18NBundle.createBundle(com.waasche.lawnmower.resources.AssetLoader.getInternalFileHandler("strings/strings"));
        colorText = createColor(255, 255, 255);
        spriteIconStar = createSprite("menu/icon_star.png");
        spriteIconStarFill = createSprite("menu/icon_star.png");
        spriteButtonBack = createSprite("menu/back_button.png");
        colorButtonLevelIncomplete = createColor(50, 50, 50);
    }

    public static Sprite createSprite(String path) {
        Sprite sprite = new Sprite(new Texture(Gdx.files.internal(path)));
        return sprite;
    }

    public static Lawnmower createLawnmoverSprite(String path) {
        Sprite sprite = createSprite(path);
        sprite.setScale(SCALE_X, SCALE_Y);
        Lawnmower lawnmower = new Lawnmower(sprite);
        return lawnmower;
    }

    public static void updateFont(float scale) {
        if (scale != fontScale) {
            FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(AssetLoader.getInternalFileHandler("roboto.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            fontParameter.characters += "�����?��?�����?��?";
            fontParameter.size = Math.round(28.0f * scale);
            fontLarge = fontGenerator.generateFont(fontParameter);
            fontParameter.size = Math.round(20.0f * scale);
            fontMedium = fontGenerator.generateFont(fontParameter);
            fontParameter.size = Math.round(12.0f * scale);
            fontSmall = fontGenerator.generateFont(fontParameter);
            fontGenerator.dispose();
            fontScale = scale;
        }
    }

    public static Color createColor(int r, int g, int b) {
        return createColor(r, g, b, 1.0f);
    }

    public static Color createColor(int r, int g, int b, float alpha) {
        return new Color(((float) r) / 255.0f, ((float) g) / 255.0f, ((float) b) / 255.0f, alpha);
    }

}