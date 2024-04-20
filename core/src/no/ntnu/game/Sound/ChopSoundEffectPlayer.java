package no.ntnu.game.Sound;

import com.badlogic.gdx.Gdx;

import no.ntnu.game.Settings.Settings;
import no.ntnu.game.StarKnight;

import com.badlogic.gdx.audio.Sound;

public class ChopSoundEffectPlayer implements SoundPlayer {
    private final Sound chopSoundEffect;
    private final Settings settings;

    public ChopSoundEffectPlayer() {
        chopSoundEffect = Gdx.audio.newSound(Gdx.files.internal("audio_cut.mp3"));
        settings = StarKnight.getSettings();
    }

    @Override
    public void play() {
        chopSoundEffect.play(settings.getEffectVolume() * 0.5f);
    }

    @Override
    public void stop() {
        return;
    }
}
