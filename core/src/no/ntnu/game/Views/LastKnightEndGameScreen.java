package no.ntnu.game.Views;

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

import no.ntnu.game.factory.button.RectangleButtonFactory;

//import no.ntnu.game.Button.Button;
//import no.ntnu.game.Button.ButtonFactory;
//import no.ntnu.game.Button.ButtonInputListener;

/**
 * End Game Screen View class to render Lose screen
 *
 * @author Han
 */
public class LastKnightEndGameScreen extends Screen {
    private Texture logo;
    BitmapFont font; // Declare the font variable

    private Button exitButton;

    private ShapeRenderer shapeRenderer;
    private LoseDeadKnightSprite loseDeadKnightSprite;
    private float knightX, knightY;
    private float knightSpeed = 300; // Pixels per second
    private int player_score;
    private Stage stage;
    //    private SpriteBatch spriteBatch;
    public LastKnightEndGameScreen(ScreenManager gvm, int player_score) {
        super(gvm);
        logo = new Texture("your_score.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();

        loseDeadKnightSprite = new LoseDeadKnightSprite();

        knightX = 300;
        knightY = 900;
        this.player_score = player_score;

        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);

        stage = new Stage();
        stage.addActor(exitButton);


        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add stage first to ensure it receives input first
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(SpriteBatch sb) {
        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top

//        exitButton = ButtonFactory.createExitButton(screenWidth/2 - 150,screenHeight/2 - 100);
//        final float CENTER_BUTTON_X = 0.5f * Gdx.graphics.getWidth() - 150;
//        exitButton = ButtonFactory.createExitButton(CENTER_BUTTON_X,600);
//
//        // Create input listeners for buttons
//        ButtonInputListener exitInputListener = new ButtonInputListener(exitButton, gvm, null, sb);
//        // Set input processors
//        InputMultiplexer inputMultiplexer = new InputMultiplexer();
//
//        inputMultiplexer.addProcessor(exitInputListener);
//
//        Gdx.input.setInputProcessor(inputMultiplexer);

        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(logo, logoX, logoY);


        // Calculate the position to center the text on the screen
        font.getData().setScale(10f);
        float x = (Gdx.graphics.getWidth() - font.getXHeight()) / 2; // Assuming average glyph width
        float y = logoY - 100; // Center vertically
        font.draw(sb, String.valueOf(player_score), x, y);

        sb.end();

        // to ensure the knight is moving at the same speed on all devices
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);

        loseDeadKnightSprite.setPosition(knightX, knightY);
        loseDeadKnightSprite.render(sb);
//        sb.end();

        // Render the menu button
//        exitButton.render(shapeRenderer,sb);
        shapeRenderer.end();

        // draw stage and text field
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
//        knightX += knightSpeed * dt;
//        if (knightX > Gdx.graphics.getWidth()) {
//            knightX = -runningKnightSprite.getWidth();
//        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
//        runningKnightSprite.dispose();
    }
}