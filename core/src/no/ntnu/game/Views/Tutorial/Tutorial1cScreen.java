package no.ntnu.game.Views.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;


import no.ntnu.game.Views.MainMenuScreen;
import no.ntnu.game.Views.Screen;
import no.ntnu.game.Views.ScreenManager;
import no.ntnu.game.factory.button.RectangleButtonFactory;

/**
 * Tutorial 1c Screen, third screen for controls tutorial, with video
 *
 * @author Deen
 */
public class Tutorial1cScreen extends Screen {
    private VideoPlayer videoPlayer;
    SpriteBatch batch;

    private Button forwardButton;

    private Button backwardButton;
    private Button exitButton;

    private Stage stage;


    public Tutorial1cScreen(ScreenManager gvm) {
        super(gvm);

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        forwardButton = rectButtonFactory.createButton(">>", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new Tutorial1dScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });


        backwardButton = rectButtonFactory.createButton("<<", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new Tutorial1bScreen(gvm));
                return true;
            }
        });

        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true;
            }
        });

        // button size and positions
        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition(centerButtonX(exitButton), 100);

        float forwardButtonX = ((float)0.5*Gdx.graphics.getWidth()) + 300;
        float backwardButtonX = ((float)0.5*Gdx.graphics.getWidth()) - 470 - backwardButton.getWidth();

        forwardButton.setSize(200, 200);
        forwardButton.setPosition(forwardButtonX, 100);

        backwardButton.setSize(200, 200); // Set the size of the button
        backwardButton.setPosition(backwardButtonX, 100);

        // Create the stage for the buttons
        stage = new Stage();
        stage.addActor(forwardButton);
        stage.addActor(backwardButton);
        stage.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

        batch = new SpriteBatch();
        create();
        videoPlayer.setLooping(true);
    }

    public float centerButtonX(Button button) {
        return (Gdx.graphics.getWidth() - button.getWidth()) / 2;
    }


    @Override
    public void render(SpriteBatch sb) {
        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (videoPlayer.isPlaying()) {
            videoPlayer.update();

            batch.begin();
            Texture frame = videoPlayer.getTexture();
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
        stage.dispose();
        batch.dispose();
    }
    @Override
    public void create(){
        videoPlayer = VideoPlayerCreator.createVideoPlayer();

        try {
            videoPlayer.play(Gdx.files.internal("tutorial_videos/move_right_tutorial_720p.webm"));
            Gdx.app.log("Tutorial1cScreen", "Tutorial1cScreen Video loaded and should be playing.");
        } catch (Exception e) {
            Gdx.app.error("Tutorial1cScreen", "Tutorial1cScreen Video file not found or error playing video.", e);
        }
    }
}