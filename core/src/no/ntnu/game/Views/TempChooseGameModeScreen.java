package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.Random;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;

public class TempChooseGameModeScreen extends Screen {
    private Stage stage;
    private TextField textField;
    private Skin skin; // libGDX skins provide styling for UI widgets

    private SpriteBatch batch;
//    private ShapeRenderer shapeRenderer;

    private Texture logo;

    BitmapFont font; // Declare the font variable
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private Button startGameButton;
    private Button lastKnightButton;
    private Button fastestKnightButton;
    private Button exitButton;

    // TODO link the room id with backend
    private String roomID; // room id for the game lobby

    public TempChooseGameModeScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
        generateRoomID();
    }

    // Method to generate a random 6-digit room ID
    private void generateRoomID() {
        Random random = new Random();
        int randomID = 100000 + random.nextInt(900000); // Random number between 100000 and 999999
        roomID = String.valueOf(randomID);
    }

    @Override
    public void render(SpriteBatch sb) {
        final float CENTER_BUTTON_X = 0.5f * Gdx.graphics.getWidth() - 150;
        startGameButton = ButtonFactory.createStartGameButton(CENTER_BUTTON_X, 0.15f * Gdx.graphics.getHeight());
        exitButton = ButtonFactory.createExitButton(CENTER_BUTTON_X, 0.03f * Gdx.graphics.getHeight());

        // render last knight standing and fastest knight buttons side by side
        lastKnightButton = ButtonFactory.createSingleplayerLastKnightButton(CENTER_BUTTON_X - 200, 0.27f * Gdx.graphics.getHeight());
        fastestKnightButton = ButtonFactory.createSingleplayerFastestKnightButton(CENTER_BUTTON_X + 200, 0.27f * Gdx.graphics.getHeight());

        // Create input listeners for buttons
        ButtonInputListener startGameInputListener = new ButtonInputListener(startGameButton, gvm, null, null, sb);
        ButtonInputListener exitGameInputListener = new ButtonInputListener(exitButton, gvm, null, null, sb);

        ButtonInputListener fastestKnightInputListener = new ButtonInputListener(fastestKnightButton, gvm, null, null, sb);
        ButtonInputListener lastKnightInputListener = new ButtonInputListener(lastKnightButton, gvm, null, null, sb);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(startGameInputListener);
        inputMultiplexer.addProcessor(exitGameInputListener);
        inputMultiplexer.addProcessor(fastestKnightInputListener);
        inputMultiplexer.addProcessor(lastKnightInputListener);

        Gdx.input.setInputProcessor(inputMultiplexer);

        // display logo
        sb.begin();
        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // draw logo
        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top
        sb.draw(logo, logoX, logoY);

        sb.end();

        // render last knight standing and fastest knight buttons side by side
        lastKnightButton.render(shapeRenderer, sb);
        fastestKnightButton.render(shapeRenderer, sb);

        // render start game button
        startGameButton.render(shapeRenderer, sb);

        // render exit button
        exitButton.render(shapeRenderer, sb);

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
        logo.dispose();
        System.out.println("Game Lobby View Disposed");

    }
}
