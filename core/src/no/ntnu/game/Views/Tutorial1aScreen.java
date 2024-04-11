package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import java.io.FileNotFoundException;

import no.ntnu.game.factory.button.RectangleButtonFactory;


/**
 * Tutorial View class to render tutorial screen
 *
 * @author Deen
 */
public class Tutorial1aScreen extends Screen {
    private VideoPlayer videoPlayer;
    SpriteBatch batch;
    private Texture logo;
//    BitmapFont font; // Declare the font variable

//    private Button exitButton;
    private Stage stage;

//    private ShapeRenderer shapeRenderer;
    //    private SpriteBatch spriteBatch;
    public Tutorial1aScreen(ScreenManager gvm) {
        super(gvm);

        batch = new SpriteBatch();
        create();
    }



    @Override
    public void render(SpriteBatch sb) {
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
    }


    @Override
    public void create() {
        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        videoPlayer.setOnCompletionListener(new VideoPlayer.CompletionListener() {
            @Override
            public void onCompletionListener(FileHandle file) {
                // Optionally loop the video here or handle completion
//                videoPlayer.play(Gdx.files.internal("tutorial_videos/tut_1.webm"));
            }
        });

        try {
            videoPlayer.play(Gdx.files.internal("tutorial_videos/tut_1.webm"));
            Gdx.app.log("Tutorial1aScreen", "Video loaded and should be playing.");
        } catch (Exception e) {
            Gdx.app.error("Tutorial1aScreen", "Video file not found or error playing video.", e);
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
            videoPlayer.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
    }

}