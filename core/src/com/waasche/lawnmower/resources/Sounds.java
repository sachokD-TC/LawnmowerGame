package com.waasche.lawnmower.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.waasche.lawnmower.data.UserSettings;

/**
 * Created by sadm on 12/8/2017.
 */
public class Sounds {
    private static boolean soundPlayed;
    private static Array<Long> sounds = new Array(false, 5);

    public static void playMenuClick(float delay) {
        play(Assets.soundClick, Assets.pitchBackClick, delay);
    }

    public static void playButtonClick(float delay) {
        play(Assets.soundClick, Assets.pitchBackClick, delay);
    }

    public static void lwStarts(float delay) {
        play(Assets.soundLwMove, Assets.pitchLwSound, delay);
    }

    public static void playTickSound(float delay){
        play(Assets.soundTick, Assets.pitchLwSound, delay);
    }

    public static void playCrashSound(float delay){
        if(UserSettings.isSoundOn()) {
            play(Assets.soundCrash, Assets.pitchLwSound, delay);
        } else {
            Gdx.input.vibrate(Assets.VIBRATE_MSEC);
        }
    }

    private static void play(Sound sound, float pitch, float delay) {
        if (!UserSettings.isSoundOn()) {
            return;
        }
        sound.play(1.0f, pitch, 0.0f);
    }


    public static void stopSounds() {
        if(soundPlayed) {
            Sounds.stopSounds();
        }
    }



}
