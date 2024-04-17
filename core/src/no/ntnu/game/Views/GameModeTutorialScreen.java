package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import java.io.FileNotFoundException;

import no.ntnu.game.factory.button.RectangleButtonFactory;

/**
 * Game Mode Tutorial Screen, tutorial for the different game modes with video
 *
 * @author Deen
 */
public class GameModeTutorialScreen extends Screen {
    private VideoPlayer videoPlayer;
    SpriteBatch batch;

    private Stage stage;

    private Button exitButton;
    private Texture frame;

    public GameModeTutorialScreen(ScreenManager gvm) {
        super(gvm);

        // create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                videoPlayer.stop();
                gvm.set(new MainMenuScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 50);

        stage = new Stage();
        stage.addActor(exitButton);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add stage first to ensure it receives input first
        Gdx.input.setInputProcessor(inputMultiplexer);

        // for video player
        batch = new SpriteBatch();
        create();
        videoPlayer.setLooping(true);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (videoPlayer.isPlaying()) {
            videoPlayer.update();

            batch.begin();
            frame = videoPlayer.getTexture();
            if (frame != null) {
                batch.draw(frame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }
            batch.end();
        }

        // draw stage and text field
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void create() {
        videoPlayer = VideoPlayerCreator.createVideoPlayer();

        try {
            videoPlayer.play(Gdx.files.internal("tutorial_videos/game_mode_tutorial_720p.webm"));
            Gdx.app.log("GameModeTutorialScreen", "GameModeTutorialScreen Video loaded and should be playing.");
        } catch (Exception e) {
            Gdx.app.error("GameModeTutorialScreen",
                    "GameModeTutorialScreen Video file not found or error playing video.", e);
        }
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        if (videoPlayer != null) {
            videoPlayer.stop(); // Stop the video player
            videoPlayer.dispose(); // Dispose of the video player
            videoPlayer = null; // Nullify the reference

        }
        frame.dispose();
        batch.dispose();
    }

}