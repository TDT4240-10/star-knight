package no.ntnu.game.Views.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import no.ntnu.game.Factory.Button.RectangleButtonFactory;
import no.ntnu.game.Views.MainMenuScreen;
import no.ntnu.game.Views.Screen;
import no.ntnu.game.Views.ScreenManager;

/**
 * Tutorial 1d Screen, fourth screen for controls tutorial
 *
 * @author Deen
 */
public class Tutorial1dScreen extends Screen {
    private final Texture POWER_UP;
    private final BitmapFont FONT; // Declare the font variable
    private final Stage STAGE;

    public Tutorial1dScreen(ScreenManager gvm) {
        super(gvm);
        POWER_UP = new Texture("power_up_tutorial.png");

        FONT = new BitmapFont(); // Load the font
        FONT.getData().setScale(3); // Set the font scale to 2 for double size

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        Button backwardButton = rectButtonFactory.createButton("<<", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new Tutorial1cScreen(gvm));
                return true;
            }
        });

        Button exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true;
            }
        });

        // button size and positions
        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition(centerButtonX(exitButton), 100);

        float backwardButtonX = ((float) 0.5 * Gdx.graphics.getWidth()) - 470 - backwardButton.getWidth();

        backwardButton.setSize(200, 200);
        backwardButton.setPosition(backwardButtonX, 100);

        // Create the stage for the buttons
        STAGE = new Stage();
        STAGE.addActor(backwardButton);
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
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float powerUpWidth = POWER_UP.getWidth();
        float powerUpHeight = POWER_UP.getHeight();
        float powerUpX = (screenWidth - powerUpWidth) / 2;
        // 1/4 from the top
        float powerUpY = (screenHeight - powerUpHeight) * 3 / 4;

        sb.begin();
        sb.draw(POWER_UP, powerUpX, powerUpY);
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
        POWER_UP.dispose();
        FONT.dispose();
        STAGE.dispose();
    }

    @Override
    public void create() {

    }
}