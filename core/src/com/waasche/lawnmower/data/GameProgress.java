package com.waasche.lawnmower.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameProgress {

    private static Preferences progress;

    public static void load() {
        progress = Gdx.app.getPreferences("GameProgress");
        progress.clear();
    }

    public static boolean isCompleted(String levelPackId, String levelId) {
        return progress.getBoolean(levelPackId + '.' + levelId, false);
    }

    public static boolean isLevelTypeCompleted(String levelType){
        return progress.getBoolean(levelType, false);
    }

    public static void setCompleted(String levelPackId, String levelId, boolean completed) {
        progress.putBoolean(levelPackId + '.' + levelId, completed);
        progress.flush();
    }

    public static void setLevelTypeCompleted(String levelPackId, boolean completed) {
        progress.putBoolean(levelPackId, completed);
        progress.flush();
    }

}
