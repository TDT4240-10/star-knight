package no.ntnu.game.Sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import no.ntnu.game.Settings.Settings;
import no.ntnu.game.firestore.GameRoom;
import no.ntnu.game.firestore.GameRoomObserver;

public class MusicPlayer implements SoundPlayer, GameRoomObserver {
    private static MusicPlayer instance;
    private final Music backgroundMusic;

    private MusicPlayer() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("HinaCC0_011_Fallen_leaves(chosic.com).mp3"));
        backgroundMusic.setVolume(Settings.getInstance().getMusicVolume());
        backgroundMusic.setLooping(true);
    }

    public static MusicPlayer getMusicPlayer() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    @Override
    public void play() {
        backgroundMusic.play();
    }

    @Override
    public void stop() {
        backgroundMusic.stop();
    }

    @Override
    public void gameStatusChanged(GameRoom gameRoom) {
        if (gameRoom.getStatus() == GameRoom.GameStatus.PLAYING) {
            this.play();
        } else
            this.stop();
    }
}
