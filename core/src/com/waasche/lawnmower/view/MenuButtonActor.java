package com.waasche.lawnmower.view;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.waasche.lawnmower.screen.MenuScreen;

public class MenuButtonActor extends Group {
    private Image image;
    private MenuScreen screen;

    public MenuButtonActor(MenuScreen screen, Drawable drawable, float size) {
        this.image = new Image(drawable);
        this.image.setSize(size, size);
        this.image.setOrigin(size / 2.0f, size / 2.0f);
        addActor(this.image);
        this.screen = screen;
        addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MenuButtonActor.this.animateClick();
                MenuButtonActor.this.handleClick();
            }
        });
    }

    public void changeImage(Drawable drawable) {
        this.image.setDrawable(drawable);
    }

    private void animateClick() {
        this.image.addAction(Actions.sequence(Actions.scaleTo(0.8f, 0.8f, 0.1f, Interpolation.exp5Out), Actions.scaleTo(1.0f, 1.0f, 0.1f, Interpolation.exp5In)));
    }

    private void handleClick() {
        this.screen.buttonClick(this);
    }
}
