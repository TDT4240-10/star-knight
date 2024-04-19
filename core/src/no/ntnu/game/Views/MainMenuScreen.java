package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import no.ntnu.game.Views.Tutorial.SelectTutorialScreen;
import no.ntnu.game.factory.button.RectangleButtonFactory;

/**
 * Main Menu View class to render main menu screen
 *
 * @author Han
 */
public class MainMenuScreen extends Screen {
    private final Texture LOGO;
    BitmapFont font; // Declare the font variable
    private final ShapeRenderer SHAPE_RENDERER;
    private final Stage STAGE;
    private final PlayerController PLAYER_CONTROLLER;

    public MainMenuScreen(ScreenManager gvm) {
        super(gvm);
        PLAYER_CONTROLLER = PlayerController.getPlayerController();
        LOGO = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        SHAPE_RENDERER = new ShapeRenderer();

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        Button playButton = rectButtonFactory.createButton("Play", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new SingleOrMultiplayerScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        playButton.setSize(350, 200); // Set the size of the button
        playButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 800);

        Button tutorialButton = rectButtonFactory.createButton("Tutorial", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new SelectTutorialScreen(gvm));
                return true;
            }
        });
        tutorialButton.setSize(350, 200); // Set the size of the button
        tutorialButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 550);

        Button settingsButton = rectButtonFactory.createButton("Settings", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new SettingsScreen(gvm));
                return true;
            }
        });
        settingsButton.setSize(350, 200); // Set the size of the button
        settingsButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);

        // Create the stage for the buttons
        STAGE = new Stage();
        STAGE.addActor(playButton);
        STAGE.addActor(tutorialButton);
        STAGE.addActor(settingsButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(STAGE);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first
    }

    private float calculateCenterX(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float textWidth = layout.width;
        return (Gdx.graphics.getWidth() - textWidth) / 2;
    }

    @Override
    public void render(SpriteBatch sb) {
        final String HIGHSCORE_STRING = "Your highscore: " + PLAYER_CONTROLLER.getPlayer().getHighScore().toString();
        final String FASTEST_STRING = "Your fastest time: "
                + (Float.isInfinite(PLAYER_CONTROLLER.getPlayer().getFastestTime()) ? "0"
                        : PLAYER_CONTROLLER.getPlayer().getFastestTime().toString());
        final String WELCOME_STRING = "Welcome " + PLAYER_CONTROLLER.getPlayer().getUsername() + "!";
        final float CENTER_WELCOME_X = calculateCenterX(WELCOME_STRING, font);
        final float CENTER_USER_HIGHSCORE_X = calculateCenterX(HIGHSCORE_STRING, font);
        final float CENTER_USER_FASTEST_X = calculateCenterX(FASTEST_STRING, font);

        float logoWidth = LOGO.getWidth();
        float logoHeight = LOGO.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top

        sb.begin();
        sb.draw(LOGO, logoX, logoY);
        font.draw(sb, FASTEST_STRING, CENTER_USER_FASTEST_X, 1150);
        font.draw(sb, HIGHSCORE_STRING, CENTER_USER_HIGHSCORE_X, 1200);
        font.draw(sb, WELCOME_STRING, CENTER_WELCOME_X, 1250);
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