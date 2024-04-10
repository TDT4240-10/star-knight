package no.ntnu.game.Models;

public class Settings {
    private static volatile Settings instance = null;

    // TODO add player information

    // https://libgdx.com/wiki/audio/streaming-music
    private float music;
    // https://libgdx.com/wiki/audio/sound-effects
    private float sound;

    private Settings() {
        // The music starts out at full volume
        music = 1.0f;
        // The sound effects starts out at full volume
        sound = 1.0f;
    }

    public static Settings getInstance() {
        if (instance == null) {
            synchronized(Settings.class) {
                if (instance == null) {
                    instance = new Settings();
                }
            }
        }
        return instance;
    }

    public float getMusic() {
        return music;
    }

    public float getSound() {
        return sound;
    }

    public void setMusic(float music) {
        if(music >= 0f && music <= 1.0f) {
            this.music = music;
        }
    }

    public void setSound(float sound) {
        if(sound >= 0f && sound <= 1.0f) {
            this.sound = sound;
        }
    }
}