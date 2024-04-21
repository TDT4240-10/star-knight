package no.ntnu.game.Sound;

import com.badlogic.gdx.Gdx;

import no.ntnu.game.Settings.Settings;
import no.ntnu.game.StarKnight;

import com.badlogic.gdx.audio.Sound;

public class ChopSoundEffectPlayer implements SoundPlayer {
    private final Sound CHOP_SOUND_EFFECT;
    private final Settings SETTINGS;

    public ChopSoundEffectPlayer() {
        CHOP_SOUND_EFFECT = Gdx.audio.newSound(Gdx.files.internal("audio_cut.mp3"));
        SETTINGS = StarKnight.getSettings();
    }

    @Override
    public void play() {
        CHOP_SOUND_EFFECT.play(SETTINGS.getEffectVolume() * 0.5f);
    }

    @Override
    public void stop() {
        return;
    }
}
