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
public class TutorialScreen extends Screen {
    private VideoPlayer videoPlayer;
    SpriteBatch batch;
    private Texture logo;
    BitmapFont font; // Declare the font variable

    private Button exitButton;
    private Stage stage;

    private ShapeRenderer shapeRenderer;
    //    private SpriteBatch spriteBatch;
    public TutorialScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("tutorial.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();

        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 100);
//
        stage = new Stage();
        stage.addActor(exitButton);
        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void create () {
    }

    @Override
    public void render(SpriteBatch sb) {
        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top

        sb.begin();
        sb.draw(logo, logoX, logoY);
        sb.end();
//
//        if (videoPlayer != null) {
//            videoPlayer.update();
//            Texture frame = videoPlayer.getTexture();
//            if (frame != null) {
//                batch.begin();
//                // Adjust the drawing coordinates and size as needed
//                batch.draw(frame, 300, 300, Gdx.graphics.getWidth() - 600, Gdx.graphics.getHeight() - 600);
//                batch.end();
//            }
//        }

        stage.act();
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
//        if (videoPlayer != null) {
//            videoPlayer.dispose();
//        }
        if (batch != null) {
            batch.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}