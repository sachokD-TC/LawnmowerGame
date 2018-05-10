package com.waasche.lawnmower.resources;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.waasche.lawnmower.data.Lawnmower;
import com.waasche.lawnmower.data.Level;
import com.waasche.lawnmower.data.LevelsList;

import java.util.HashMap;
import java.util.Map;

public class Assets {

    public static final float SCALE_X = 1f;
    public static final float SCALE_Y = 1f;
    public static final float SCREEN_UNIT = (((float) Math.min(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight())) / 60.0f);
    public static final float ANDROID_WIDTH = Gdx.graphics.getWidth();
    public static final float ANDROID_HEIGHT = Gdx.graphics.getHeight();
    public static final long DELAY_TIME_IN_SECONDS = 1;
    public static Lawnmower lawnmowerUpRight;
    public static Color colorBackground;
    public static Color colorButtonLevelIncomplete;
    public static final Texture lawnmowerUpRightTexture = new Texture("actor/up-left.png");
    public static final Texture lawnmowerUpLeftTexture =  new Texture("actor/up-right.png");
    public static final Texture lawnmowerDownRightTexture = new Texture("actor/down-right.png");
    public static final Texture lawnmowerDownLeftTexture = new Texture("actor/down-left.png");
    public static Sprite grassFull;
    public static Sprite grassEmpty;
    public static Sprite wallSprite;
    public static Map<Integer, LevelsList> levelsList = new HashMap<Integer, LevelsList>();
    public static Label.LabelStyle textStyle;
    public static Color colorText;
    public static BitmapFont fontLarge;
    public static BitmapFont fontMedium;
    public static BitmapFont fontSmall;
    private static float fontScale;
    public static I18NBundle strings;
    public static Sprite spriteButtonOk;
    public static Sprite spriteTitle;
    public static Sprite spriteStart;
    public static Sprite spriteButtonBack;
    public static Sprite imageBackground;
    public static Sound soundClick;
    public static float pitchBackClick = 1f;
    public static Sprite spriteButtonHelp;
    public static Sprite spriteButtonSoundOn;
    public static float pitchLwSound = 1f;
    public static Sprite spriteButtonSoundOff;
    public static Music startScreenMusic;
    public static Music levelSelectScreenMusic;
    public static Music backgroundMusic;
    public static Sound soundStopLwm;
    public static Sound soundCrash;
    public static Sound soundLevelFinished;
    public static final int VIBRATE_MSEC = 500;
    public static Sprite spriteLogoWaasche;
    public static Sprite spriteLogoLibGdx;
    public static Sprite spriteButtonTutorial;
    public static Sprite spriteButtonRate;
    public static Sprite spriteButtonArrowLeft;
    public static Sprite spriteButtonArrowRight;
    public static Sprite spriteButtonArrowUp;
    public static Sprite spriteButtonArrowDown;
    public static Sprite[] spritesTutorial;
    public static Label.LabelStyle labelStyle;
    public static Label.LabelStyle smallLabelStyle;
    public static Label.LabelStyle mediumLableStyle;
    public static Sprite levelTextRectangle;
    public static Sprite attemptTextRectangle;
    public static Image grassBackgroundImage;
    public static Drawable backButtonDrawable;
    public static Drawable gradientRectangle;
    public static Sprite spriteDonateButton;



    public static void load() {
        lawnmowerUpRight = createLawnmoverSprite("actor/up-right.png");
        colorBackground = createColor(26, 26, 26);
        grassFull = new Sprite(new Texture(Gdx.files.internal("data/grass-full.png")));
        grassEmpty = new Sprite(new Texture(Gdx.files.internal("data/grass-empty.png")));
        wallSprite = new Sprite(new Texture(Gdx.files.internal("data/wall.png")));
        spriteTitle = createSprite("menu/title.png");
        spriteStart = createSprite("menu/start.png");
        levelTextRectangle = createSprite("data/levelrect.png");
        levelTextRectangle.scale(0.13f* Assets.SCREEN_UNIT);
        attemptTextRectangle = createSprite("data/levelrect.png");
        attemptTextRectangle.scale(0.16f* Assets.SCREEN_UNIT);
        grassBackgroundImage = new Image(new Texture(AssetLoader.getInternalPath("data/background.png")));
        spriteLogoWaasche = createSprite("menu/Waasche.png");
        spriteLogoLibGdx = createSprite("menu/logo_libgdx.jpg");
        spriteButtonRate = createSprite("menu/rate.png");
        spriteButtonTutorial = createSprite("menu/tutorial.png");
        spriteButtonArrowLeft = createSprite("menu/buttonArrowLeft.png");
        spriteButtonArrowRight = createSprite("menu/buttonArrowRight.png");
        spriteButtonArrowDown = createSprite("menu/buttonArrowRight.png");
        spriteButtonArrowDown.rotate(-45);
        spriteButtonArrowUp = createSprite("menu/buttonArrowRight.png");
        spriteButtonArrowUp.rotate(45);
        spritesTutorial = new Sprite[3];
        spritesTutorial[0] = createSprite("menu/tutorial0.png");
        spritesTutorial[1] = createSprite("menu/tutorial1.png");
        spritesTutorial[2] = createSprite("menu/tutorial2.png");
        spriteDonateButton = createSprite("menu/donate.png");
        levelsList.put(4, new Level("levels/levels4.json").getLevelsList());
        levelsList.put(5, new Level("levels/levels5.json").getLevelsList());
        levelsList.put(6, new Level("levels/levels6.json").getLevelsList());
        levelsList.put(7, new Level("levels/levels7.json").getLevelsList());
        levelsList.put(8, new Level("levels/levels8.json").getLevelsList());
        levelsList.put(9, new Level("levels/levels9.json").getLevelsList());
        levelsList.put(10, new Level("levels/levels10.json").getLevelsList());
        textStyle = new Label.LabelStyle();
        textStyle.font = new BitmapFont();
        fontMedium = new BitmapFont();
        fontScale = 0.0f;
        labelStyle = new Label.LabelStyle();
        smallLabelStyle = new Label.LabelStyle();
        mediumLableStyle = new Label.LabelStyle();
        strings = I18NBundle.createBundle(com.waasche.lawnmower.resources.AssetLoader.getInternalFileHandler("strings/strings"));
        colorText = createColor(255, 255, 255);
        spriteButtonOk = createSprite("menu/ok.png");
        spriteButtonBack = createSprite("menu/back_button.png");
        spriteButtonBack.flip(true, false);
        backButtonDrawable =  new TextureRegionDrawable(new TextureRegion(spriteButtonBack.getTexture()));
        gradientRectangle = new TextureRegionDrawable(new TextureRegion(new Texture(AssetLoader.getInternalPath("menu/gradient.png"))));
        spriteButtonHelp = createSprite("menu/help_button.png");
        spriteButtonSoundOn = createSprite("menu/sound_on.png");
        spriteButtonSoundOff = createSprite("menu/sound_off.png");
        imageBackground = createSprite("menu/level_bg.png");
        colorButtonLevelIncomplete = createColor(25, 50, 50);
        soundClick = Gdx.audio.newSound(AssetLoader.getInternalFileHandler("sounds/click.mp3"));
        soundStopLwm = Gdx.audio.newSound(AssetLoader.getInternalFileHandler("sounds/stopLwm.mp3"));
        soundCrash = Gdx.audio.newSound(AssetLoader.getInternalFileHandler("sounds/wrongMove.mp3"));
        soundLevelFinished = Gdx.audio.newSound(AssetLoader.getInternalFileHandler("sounds/levelFinished.mp3"));
        startScreenMusic = Gdx.audio.newMusic(AssetLoader.getInternalFileHandler("sounds/startScreenMusic.mp3"));
        levelSelectScreenMusic = Gdx.audio.newMusic(AssetLoader.getInternalFileHandler("sounds/levelSelectMusic.mp3"));
        backgroundMusic = Gdx.audio.newMusic(AssetLoader.getInternalFileHandler("sounds/backgroundMusic.mp3"));
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
