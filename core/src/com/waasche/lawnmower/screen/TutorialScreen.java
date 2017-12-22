package com.waasche.lawnmower.screen;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.waasche.lawnmower.main.MainClass;
import com.waasche.lawnmower.resources.Assets;
import com.waasche.lawnmower.resources.Sounds;
import com.waasche.lawnmower.view.MenuButtonActor;

public class TutorialScreen extends MenuScreen implements Screen {

    private MenuButtonActor buttonBack;
    private MenuButtonActor buttonDone;
    private MenuButtonActor buttonNext;
    private MenuButtonActor buttonPrev;
    private Table content;
    private Image tutorialImage;
    private int tutorialIndex;
    private int tutorialLength;
    private Label tutorialText;

    public TutorialScreen(MainClass mainClass) {
        super(mainClass, 0);
        this.tutorialIndex = 0;
        this.tutorialLength = Assets.spritesTutorial.length;
        for (int i = 0; i < this.tutorialLength; i++) {
            this.skin.add("tutorial" + i, Assets.spritesTutorial[i], Sprite.class);
        }
        this.tutorialImage = new Image();
        this.tutorialText = new Label("", this.skin, "lightSmall");
        this.tutorialText.setWrap(true);
        this.tutorialText.setAlignment(1);
        this.buttonBack = new MenuButtonActor(this, this.skin.getDrawable("buttonBack"), this.SCREEN_UNIT * 10.0f);
        this.buttonDone = new MenuButtonActor(this, this.skin.getDrawable("buttonOk"), this.SCREEN_UNIT * 10.0f);
        this.buttonPrev = new MenuButtonActor(this, this.skin.getDrawable("buttonArrowLeft"), this.SCREEN_UNIT * 7.5f);
        this.buttonNext = new MenuButtonActor(this, this.skin.getDrawable("buttonArrowRight"), this.SCREEN_UNIT * 7.5f);
        this.content = new Table();
        createLayout();
        show();
        showPage();
    }

    private void createLayout() {
        this.content.clearChildren();
        this.container.clearChildren();
        this.content.add(this.buttonPrev).size(this.SCREEN_UNIT * 7.5f, this.SCREEN_UNIT * 7.5f).pad(0.0f, 2.0f * this.SCREEN_UNIT, 0.0f, 0.0f).align(8);
        this.content.add(this.tutorialImage).size(41.0f * this.SCREEN_UNIT, 41.0f * this.SCREEN_UNIT).expandX();
        this.content.add(this.buttonNext).size(this.SCREEN_UNIT * 7.5f, this.SCREEN_UNIT * 7.5f).pad(0.0f, 0.0f, 0.0f, 2.0f * this.SCREEN_UNIT).align(16);
        this.content.row();
        this.content.add(this.tutorialText).size(52.0f * this.SCREEN_UNIT, 15.0f * this.SCREEN_UNIT).padBottom(this.SCREEN_UNIT * 4.0f).align(2).expandY().colspan(3);
        this.content.row();
        if (isLandscape()) {
            this.container.add(this.content).expand().align(2).colspan(3);
        } else {
            this.container.add(this.content).expand().colspan(3);
        }
        this.container.row();
        this.container.add(this.buttonBack).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(0.0f, this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f, 0.0f);
        this.container.add().expandX();
        this.container.add(this.buttonDone).size(this.SCREEN_UNIT * 10.0f, this.SCREEN_UNIT * 10.0f).pad(0.0f, 0.0f, this.SCREEN_UNIT * 4.0f, this.SCREEN_UNIT * 4.0f);
    }

    private void prevPage() {
        if (this.tutorialIndex > 0) {
            this.tutorialIndex--;
            showPage();
        }
    }

    private void nextPage() {
        if (this.tutorialIndex < this.tutorialLength - 1) {
            this.tutorialIndex++;
            showPage();
        }
    }

    private void showPage() {
        float f;
        float f2 = 0.0f;
        this.tutorialImage.setDrawable(this.skin.getDrawable("tutorial" + this.tutorialIndex));
        this.tutorialText.setText(String.format("%s/%s.\n%s", new Object[]{Integer.valueOf(this.tutorialIndex + 1), Integer.valueOf(this.tutorialLength), Assets.strings.get("tutorial" + this.tutorialIndex)}));
        MenuButtonActor menuButtonActor = this.buttonPrev;
        if (this.tutorialIndex > 0) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        menuButtonActor.setColor(1.0f, 1.0f, 1.0f, f);
        menuButtonActor = this.buttonNext;
        if (this.tutorialIndex < this.tutorialLength - 1) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        menuButtonActor.setColor(1.0f, 1.0f, 1.0f, f);
        MenuButtonActor menuButtonActor2 = this.buttonDone;
        if (this.tutorialIndex == this.tutorialLength - 1) {
            f2 = 1.0f;
        }
        menuButtonActor2.setColor(1.0f, 1.0f, 1.0f, f2);
    }


    public void buttonClick(MenuButtonActor button) {
        super.buttonClick(button);
        if (this.buttonBack.equals(button) || this.buttonDone.equals(button)) {
            Sounds.playMenuClick(0.0f);
            navigateBack();
        } else if (this.buttonPrev.equals(button)) {
            Sounds.playButtonClick(0.0f);
            prevPage();
        } else if (this.buttonNext.equals(button)) {
            Sounds.playButtonClick(0.0f);
            nextPage();
        }
    }

    public void navigateBack() {
        mainClass.setCurrentScreen(new HelpScreen(mainClass));
    }
}
