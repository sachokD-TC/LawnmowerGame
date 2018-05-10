package com.waasche.lawnmower.data;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameProgress {

    public static Preferences progress;

    public static void load() {
        progress = Gdx.app.getPreferences("GameProgress");
        if (Gdx.app.getType().equals(Application.ApplicationType.Desktop)) {
            progress.clear();
        }
    }

    public static boolean isCompleted(String levelPackId, String levelId) {
        return progress.getBoolean(levelPackId + '.' + levelId, false);
    }

    public static boolean isLevelTypeCompleted(String levelType) {
        return progress.getBoolean(levelType, false);
    }

    public static void setCompleted(String levelPackId, String levelId, boolean completed) {
        progress.putBoolean(levelPackId + '.' + levelId, completed);
        progress.flush();
    }

    public static int getFirstSuccess(String levelPackId, String levelId){
        return progress.getInteger("fistSuccessAttempt" + levelPackId + "." + levelId);
    }

    public static void setFirstSuccess(String levelPackId, String levelId){
        if(getFirstSuccess(levelPackId,levelId) == 0) {
            progress.putInteger("fistSuccessAttempt" + levelPackId + "." + levelId, getAttempt(levelPackId, levelId));
            progress.flush();
        }
    }

    public static int getAttempt(String levelPackId, String levelId){
        return progress.getInteger("attempt" + levelId + "." + levelId);
    }

    public static void setAttempt(String levelPackId, String levelId){
        int attempt = getAttempt(levelPackId, levelId) + 1;
        progress.putInteger("attempt" + levelId + "." + levelId, attempt);
        progress.flush();
    }

    public static void setLevelTypeCompleted(String levelPackId, boolean completed) {
        progress.putBoolean(levelPackId, completed);
        progress.flush();
    }

}
