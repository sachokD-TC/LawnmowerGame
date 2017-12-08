package com.waasche.lawnmower.resources;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.waasche.lawnmower.data.UserSettings;

/**
 * Created by sadm on 12/8/2017.
 */
public class Sounds {
    private static boolean soundPlayed;
    private static Array<DelayedSound> sounds = new Array(false, 5);

    public static void playMenuClick(float delay) {
        play(Assets.soundClick, Assets.pitchBackClick, delay);
    }

    public static void playButtonClick(float delay) {
        play(Assets.soundClick, Assets.pitchBackClick, delay);
    }


    private static void play(Sound sound, float pitch, float delay) {
        if (!UserSettings.isSoundOn()) {
            return;
        }
        if (delay > 0.0f) {
            sounds.add(new DelayedSound(sound, pitch, delay));
        } else if (!soundPlayed) {
            sound.play(1.0f, pitch, 0.0f);
            soundPlayed = true;
        }
    }

    private static class DelayedSound {
        private float delay;
        private float pitch;
        private Sound sound;

        public DelayedSound(Sound sound, float pitch, float delay) {
            this.sound = sound;
            this.pitch = pitch;
            this.delay = delay;
        }

        public boolean update(float delta) {
            this.delay -= delta;
            return this.delay <= 0.0f;
        }

        public Sound getSound() {
            return this.sound;
        }

        public float getPitch() {
            return this.pitch;
        }
    }
}
