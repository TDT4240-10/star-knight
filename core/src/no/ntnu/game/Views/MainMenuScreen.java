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
import no.ntnu.game.Models.PlayerModel;
import no.ntnu.game.factory.button.RectangleButtonFactory;
import no.ntnu.game.firestore.Player;

/**
 * Main Menu View class to render main menu screen
 *
 * @author Han
 */
public class MainMenuScreen extends Screen {
    private Texture logo;
    BitmapFont font; // Declare the font variable

    private Button playButton;

    private Button tutorialButton;
    private Button settingsButton;

    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private PlayerController playerController;


    public MainMenuScreen(ScreenManager gvm) {
        super(gvm);
        playerController = PlayerController.getPlayerController();
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();


        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        playButton = rectButtonFactory.createButton("Play", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new SingleOrMultiplayerScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        playButton.setSize(350, 200); // Set the size of the button
        playButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 800);

        tutorialButton = rectButtonFactory.createButton("Tutorial", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new SelectTutorialScreen(gvm));
                return true;
            }
        });

        tutorialButton.setSize(350, 200); // Set the size of the button
        tutorialButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 550);

        settingsButton = rectButtonFactory.createButton("Settings", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new SettingsScreen(gvm));
                return true;
            }
        });

        settingsButton.setSize(350, 200); // Set the size of the button
        settingsButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);


        // Create the stage for the buttons
        stage = new Stage();
        stage.addActor(playButton);
        stage.addActor(tutorialButton);
        stage.addActor(settingsButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

    }

    public float calculateCenterX(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float textWidth = layout.width;
        return (Gdx.graphics.getWidth() - textWidth) / 2;
    }


    @Override
    public void render(SpriteBatch sb) {
        final float CENTER_WELCOME_X = calculateCenterX("Welcome!", font);
        final float CENTER_USERNAME_X = calculateCenterX(playerController.getPlayer().getUsername(), font);


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
        font.draw(sb, playerController.getPlayer().getUsername(), CENTER_USERNAME_X, 1200);
        font.draw(sb, "Welcome!", CENTER_WELCOME_X, 1250);
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
        shapeRenderer.dispose();
    }
    @Override
    public void create(){

    }
}