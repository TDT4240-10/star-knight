package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;
import no.ntnu.game.Models.PlayerModel;
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
    private Button rectSettingsButton;

    private ShapeRenderer shapeRenderer;
    //    private SpriteBatch spriteBatch;
//    private Player player;
    private String username;
    public MainMenuScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
        username = PlayerModel.getPlayer().getUsername();
    }

    public float calculateCenterX(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float textWidth = layout.width;
        return (Gdx.graphics.getWidth() - textWidth) / 2;
    }


    @Override
    public void render(SpriteBatch sb) {
        final float CENTER_BUTTON_X = 0.5f * Gdx.graphics.getWidth() - 150;
        final float CENTER_WELCOME_X = calculateCenterX("Welcome!", font);
        final float CENTER_USERNAME_X = calculateCenterX(username, font);

        playButton = ButtonFactory.createPlayButton(300,1100);
        tutorialButton = ButtonFactory.createTutorialButton(300,800);
        rectSettingsButton = ButtonFactory.createRectSettingsButton(300,500);

        playButton = ButtonFactory.createPlayButton(CENTER_BUTTON_X,900);
        tutorialButton = ButtonFactory.createTutorialButton(CENTER_BUTTON_X,600);
        rectSettingsButton = ButtonFactory.createRectSettingsButton(CENTER_BUTTON_X,300);

        // Create input listeners for buttons
        ButtonInputListener menuInputListener = new ButtonInputListener(playButton, gvm, null, null, sb);
        ButtonInputListener tutorialInputListener = new ButtonInputListener(tutorialButton, gvm, null, null, sb);
        ButtonInputListener settingsInputListener = new ButtonInputListener(rectSettingsButton, gvm, null, null,  sb);


        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(menuInputListener);
        inputMultiplexer.addProcessor(tutorialInputListener);
        inputMultiplexer.addProcessor(settingsInputListener);

        Gdx.input.setInputProcessor(inputMultiplexer);

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
        font.draw(sb, username, CENTER_USERNAME_X, 1200);
        font.draw(sb, "Welcome!", CENTER_WELCOME_X, 1200);
        sb.end();

        // Render the menu button
        playButton.render(shapeRenderer,sb);
        tutorialButton.render(shapeRenderer,sb);
        rectSettingsButton.render(shapeRenderer,sb);

        shapeRenderer.end();
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
}