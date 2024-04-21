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

import no.ntnu.game.Models.Factory.Button.RectangleButtonFactory;
import no.ntnu.game.Views.MainMenuScreen;
import no.ntnu.game.Views.Screen;
import no.ntnu.game.Views.ScreenManager;

/**
 * Tutorial 1a Screen, first screen for controls tutorial
 *
 * @author Deen
 */
public class Tutorial1aScreen extends Screen {
    private final Texture LOGO;
    private final Texture TEXT;
    private final BitmapFont FONT;
    private final Stage STAGE;

    public Tutorial1aScreen(ScreenManager gvm) {
        super(gvm);
        LOGO = new Texture("goal.png");
        TEXT = new Texture("goal_text.png");
        FONT = new BitmapFont(); // Load the font
        FONT.getData().setScale(3); // Set the font scale to 2 for double size

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        // Indicate that the touch event is handled
        Button forwardButton = rectButtonFactory.createButton(">>", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new Tutorial1bScreen(gvm));
                return true; // Indicate that the touch event is handled
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

        float forwardButtonX = ((float) 0.5 * Gdx.graphics.getWidth()) + 300;

        forwardButton.setSize(200, 200);
        forwardButton.setPosition(forwardButtonX, 100);

        // Create the stage for the buttons
        STAGE = new Stage();
        STAGE.addActor(forwardButton);
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

        // render text
        float textWidth = TEXT.getWidth();
        float textHeight = TEXT.getHeight();
        float textX = (screenWidth - textWidth) / 2;
        float textY = (screenHeight - textHeight) / 2;

        sb.begin();
        sb.draw(LOGO, logoX, logoY);
        sb.draw(TEXT, textX, textY);
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
        LOGO.dispose();
        TEXT.dispose();
        FONT.dispose();
        STAGE.dispose();
    }

    @Override
    public void create() {

    }
}