package no.ntnu.game.Views.Tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;


import no.ntnu.game.Views.MainMenuScreen;
import no.ntnu.game.Views.Screen;
import no.ntnu.game.Views.ScreenManager;
import no.ntnu.game.factory.button.RectangleButtonFactory;

/**
 * Tutorial 1a Screen, first screen for controls tutorial
 *
 * @author Deen
 */
public class Tutorial1aScreen extends Screen {
    private Texture logo;
    private Texture text;
    BitmapFont font; // Declare the font variable

    private Button forwardButton;

    private Button exitButton;

    private ShapeRenderer shapeRenderer;
    private Stage stage;


    public Tutorial1aScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("goal.png");
        text = new Texture("goal_text.jpg");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        forwardButton = rectButtonFactory.createButton(">>", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new Tutorial1bScreen(gvm));
                return true; // Indicate that the touch event is handled
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

        forwardButton.setSize(200, 200);
        forwardButton.setPosition(forwardButtonX, 100);

        // Create the stage for the buttons
        stage = new Stage();
        stage.addActor(forwardButton);
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

        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top

        // render text
        float textWidth = text.getWidth();
        float textHeight = text.getHeight();
        float textX = (screenWidth - textWidth) / 2;
        float textY = (screenHeight - textHeight) / 2;


        sb.begin();
        sb.draw(logo, logoX, logoY);
        sb.draw(text, textX, textY);
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
        logo.dispose();
        text.dispose();
        font.dispose();
        stage.dispose();
    }
    @Override
    public void create(){

    }
}