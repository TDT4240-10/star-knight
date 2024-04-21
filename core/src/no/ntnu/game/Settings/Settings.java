package no.ntnu.game.Settings;

public class Settings {
    private static volatile Settings instance = null;
    // https://libgdx.com/wiki/audio/streaming-music
    private float musicVolume;
    // https://libgdx.com/wiki/audio/sound-effects
    private float effectVolume;

    private Settings() {
        // The music starts out at full volume
        musicVolume = 1.0f;
        // The sound effects starts out at full volume
        effectVolume = 1.0f;
    }

    public static Settings getInstance() {
        if (instance == null) {
            synchronized (Settings.class) {
                if (instance == null) {
                    instance = new Settings();
                }
            }
        }
        return instance;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public float getEffectVolume() {
        return effectVolume;
    }

    public void setMusicVolume(float music) {
        if (music >= 0f && music <= 1.0f) {
            this.musicVolume = music;
        }
    }

    public void setEffectVolume(float sound) {
        if (sound >= 0f && sound <= 1.0f) {
            this.effectVolume = sound;
        }
    }
}