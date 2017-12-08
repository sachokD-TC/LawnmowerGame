package com.waasche.lawnmower.services;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.waasche.lawnmower.data.Lock;
import com.waasche.lawnmower.resources.Assets;

import java.util.List;

public class MenuDrawingService {

    public static void drawLockers(List<Lock> locks, Table container, float screenSizeX, float screenSizeY){
        for (int i = 0; i != locks.size(); i++) {
            Lock lock = locks.get(i);
            lock.setPosition((screenSizeX / 2 - 250) + i * 100, screenSizeY / 2f);
            Label label = new Label("" + lock.getLevelInd(), Assets.textStyle);
            label.setPosition(locks.get(i).getX() + locks.get(i).getWidth() / 2, screenSizeY / 2f + lock.getHeight());
            label.setSize(64, 64);
            container.add(lock);
        }
    }

    public static void clearScreen(){
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
    }
}
