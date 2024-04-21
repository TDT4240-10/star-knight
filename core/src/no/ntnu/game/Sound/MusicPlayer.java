package no.ntnu.game.Sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import no.ntnu.game.Controllers.GameRoomObserver;
import no.ntnu.game.Settings.Settings;
import no.ntnu.game.firestore.GameRoom;

public class MusicPlayer implements SoundPlayer, GameRoomObserver {
    private static MusicPlayer instance;
    private final Music BACKGROUND_MUSIC;

    private MusicPlayer() {
        BACKGROUND_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("HinaCC0_011_Fallen_leaves(chosic.com).mp3"));
        BACKGROUND_MUSIC.setVolume(Settings.getInstance().getMusicVolume());
        BACKGROUND_MUSIC.setLooping(true);
    }

    public static MusicPlayer getMusicPlayer() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    @Override
    public void play() {
        BACKGROUND_MUSIC.play();
    }

    @Override
    public void stop() {
        BACKGROUND_MUSIC.stop();
    }

    @Override
    public void gameStatusChanged(GameRoom gameRoom) {
        if (gameRoom.getStatus() == GameRoom.GameStatus.PLAYING) {
            this.play();
        } else
            this.stop();
    }
}
