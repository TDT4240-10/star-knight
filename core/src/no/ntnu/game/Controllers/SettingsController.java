package no.ntnu.game.Controllers;

import no.ntnu.game.Models.Settings;

public class SettingsController {
    private Settings settings;

    public SettingsController() {
        settings = Settings.getInstance();
    }

    public float getMusic() {
        return settings.getMusic();
    }

    public float getSound() {
        return settings.getSound();
    }

    public void setMusic(float music) {
        settings.setMusic(music);
    }

    public void setSound(float sound) {
        settings.setSound(sound);
    }
}
