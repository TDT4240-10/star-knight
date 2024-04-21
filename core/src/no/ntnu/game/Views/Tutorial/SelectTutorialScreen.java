package no.ntnu.game.Views.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import no.ntnu.game.Models.Factory.Button.RectangleButtonFactory;
import no.ntnu.game.Views.GameModeTutorialScreen;
import no.ntnu.game.Views.MainMenuScreen;
import no.ntnu.game.Views.Screen;
import no.ntnu.game.Views.ScreenManager;

/**
 * For users to select which tutorial/ explanation they want to see
 *
 * @author Deen
 */
public class SelectTutorialScreen extends Screen {
    private final Texture LOGO;
    private final ShapeRenderer SHAPE_RENDERER;
    private final Stage STAGE;

    public SelectTutorialScreen(ScreenManager gvm) {
        super(gvm);
        LOGO = new Texture("tutorial.png");
        // Declare the font variable
        BitmapFont font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        SHAPE_RENDERER = new ShapeRenderer();

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        // Indicate that the touch event is handled
        Button gameModeButton = rectButtonFactory.createButton("Game Modes", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new GameModeTutorialScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        gameModeButton.setSize(650, 200);
        gameModeButton.setPosition(centerButtonX(gameModeButton), 800);
        Button controlsButton = rectButtonFactory.createButton("Controls & Game Play", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new Tutorial1aScreen(gvm));
                return true;
            }
        });

        controlsButton.setSize(950, 200); // Set the size of the button
        controlsButton.setPosition(centerButtonX(controlsButton), 550);

        Button exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true;
            }
        });

        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition(centerButtonX(exitButton), 300);

        // Create the stage for the buttons
        STAGE = new Stage();
        STAGE.addActor(gameModeButton);
        STAGE.addActor(controlsButton);
        STAGE.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(STAGE);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

    }

    public float centerButtonX(Button button) {
        return (Gdx.graphics.getWidth() - button.getWidth()) / 2;
    }

    @Override
    public void render(SpriteBatch sb) {
        float logoWidth = LOGO.getWidth();
        float logoHeight = LOGO.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top

        sb.begin();
        sb.draw(LOGO, logoX, logoY);
        sb.end();

        // draw stage and text field
        STAGE.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        STAGE.draw();
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        SHAPE_RENDERER.dispose();
    }

    @Override
    public void create() {

    }
}