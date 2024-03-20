package no.ntnu.game.Models;

public class Settings {
    private static volatile Settings instance = null;

    // TODO music and sound effects
    // TODO add player information

    // Assumes we are going to be using the Sound class provided by LibGDX
    // https://libgdx.com/wiki/audio/sound-effects
    private float volume;

    private Settings() {
        // The volume starts out at full volume
        volume = 1.0f;
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

    public void setVolume(float volume) {
        if(volume >= 0f && volume <= 1.0f) {
            this.volume = volume;
        }
    }
}
