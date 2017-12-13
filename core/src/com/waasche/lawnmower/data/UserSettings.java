package com.waasche.lawnmower.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by sadm on 12/8/2017.
 */
public class UserSettings {

    private static Preferences preferences;
    private static String SOUND = "Sound";

    public static void load() {
        preferences = Gdx.app.getPreferences("UserSettings");
        if (!preferences.contains(SOUND)) {
            preferences.putBoolean(SOUND, true);
        }
        preferences.flush();
    }

    public static boolean isSoundOn() {
        return preferences.getBoolean(SOUND, true);
    }

    public static void toggleSound() {
        preferences.putBoolean(SOUND, !isSoundOn());
        preferences.flush();
    }
}
