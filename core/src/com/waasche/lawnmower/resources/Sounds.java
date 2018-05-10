package com.waasche.lawnmower.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.waasche.lawnmower.data.UserSettings;

/**
 * Created by sadm on 12/8/2017.
 */
public class Sounds {


    public static Music music;

    public static Sound sound;

    public static float volume = 100;

    public static void playButtonClick(float delay) {
        play(Assets.soundClick, Assets.pitchBackClick, delay);
    }

    public static void playBackgroundMusic(float delay) {
        playMusic(Assets.backgroundMusic);
    }

    public static void playStartScreenMusic() {
        playMusic(Assets.startScreenMusic);
    }

    public static void playSelectLevelMusic() {
        playMusic(Assets.levelSelectScreenMusic);
    }

    public static void playStopSound(float delay) {
        play(Assets.soundStopLwm, Assets.pitchLwSound, delay);
    }

    public static void playLevelFinished(float delay){
        play(Assets.soundLevelFinished, Assets.pitchLwSound, delay);
    }

    public static void playCrashSound(float delay) {
        if (UserSettings.isSoundOn()) {
            play(Assets.soundCrash, Assets.pitchLwSound, delay);
        } else {
            Gdx.input.vibrate(Assets.VIBRATE_MSEC);
        }
    }

    public static void increaseVolume(){
        volume+=10;
    }

    public static void decreaseVolume(){
        volume-=10;
    }

    private static void play(Sound sound, float pitch, float delay) {
        Sounds.sound = sound;
        if (!UserSettings.isSoundOn()) {
            return;
        }
        Sounds.sound.play(1.0f, pitch, 0.0f);
    }

    private static void playMusic(Music music) {
        if(Sounds.music != null && !music.equals(Sounds.music)){
            stopMusic();
        }
        if (UserSettings.isSoundOn()) {
            Sounds.music = music;
            Sounds.music.play();
            Sounds.music.setLooping(true);
        }
        music.setVolume(volume);
    }

    public static void stopMusic() {
        if (Sounds.music != null && Sounds.music.isPlaying()) {
            Sounds.music.stop();
        }
    }

    public static void toggleMusic() {
        if(music == null){
            playBackgroundMusic(0);
        }
        if (UserSettings.isSoundOn()) {
            music.play();
        } else {
            music.stop();
        }
    }

    public static void continueMusic(){
        if (music != null && UserSettings.isSoundOn()) {
            music.play();
        }
    }
}
