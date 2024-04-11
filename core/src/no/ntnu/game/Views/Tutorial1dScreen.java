package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;


import no.ntnu.game.Controllers.PlayerController;
import no.ntnu.game.factory.button.CircleButtonFactory;
import no.ntnu.game.factory.button.RectangleButtonFactory;

/**
 * Tutorial 1d Screen, fourth screen for controls tutorial
 *
 * @author Deen
 */
public class Tutorial1dScreen extends Screen {
    private Texture powerUp;
    BitmapFont font; // Declare the font variable

    private Button backwardButton;
    private Button exitButton;

    private ShapeRenderer shapeRenderer;
    private Stage stage;


    public Tutorial1dScreen(ScreenManager gvm) {
        super(gvm);
        powerUp = new Texture("power_up_tutorial.png");

        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();

        backwardButton = rectButtonFactory.createButton("<<", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new Tutorial1cScreen(gvm));
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

        float backwardButtonX = ((float)0.5*Gdx.graphics.getWidth()) - 470 - backwardButton.getWidth();

        backwardButton.setSize(200, 200);
        backwardButton.setPosition(backwardButtonX, 100);

        // Create the stage for the buttons
        stage = new Stage();
        stage.addActor(backwardButton);
        stage.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

    }


    public float centerButtonX(Button button) {
        return (Gdx.graphics.getWidth() - button.getWidth()) / 2;
    }


    @Override
    public void render(SpriteBatch sb) {
        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float powerUpWidth = powerUp.getWidth();
        float powerUpHeight = powerUp.getHeight();
        float powerUpX = (screenWidth - powerUpWidth) / 2;
        //1/4 from the top
        float powerUpY = (screenHeight - powerUpHeight) * 3 / 4;

        sb.begin();
        sb.draw(powerUp, powerUpX, powerUpY);
        sb.end();

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
        powerUp.dispose();
        font.dispose();
        stage.dispose();
    }
    @Override
    public void create(){

    }
}