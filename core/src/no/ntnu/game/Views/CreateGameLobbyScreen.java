package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.Random;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;
import no.ntnu.game.Controllers.GameModeController;
import no.ntnu.game.firestore.GameRoom;
import no.ntnu.game.firestore.Player;

public class CreateGameLobbyScreen extends Screen {
    public static Color Starknightdown = new Color(105 / 255f, 105 / 255f, 105 / 255f, 1 / 255f);
    public static Color Starknight = new Color(61 / 255f, 63 / 255f, 65 / 255f, 255 / 255f);
    public static Color green = new Color(0 / 255f, 255 / 255f, 0 / 255f, 255 / 255f);
    public static Color red = new Color(255 / 255f, 0 / 255f, 0 / 255f, 255 / 255f);
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
    private GameRoom gameRoom; // game room object
    private final float CENTER_BUTTON_X = 0.5f * Gdx.graphics.getWidth() - 150;

    public CreateGameLobbyScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
        // render last knight standing and fastest knight buttons side by side
        lastKnightButton = ButtonFactory.createLastKnightButton(CENTER_BUTTON_X - 200, 0.27f * Gdx.graphics.getHeight());
        fastestKnightButton = ButtonFactory.createFastestKnightButton(CENTER_BUTTON_X + 200, 0.27f * Gdx.graphics.getHeight());

        // todo deen
        // gameRoom = new GameRoom();  // where to get players??
        roomID = gameRoom.getRoomCode();
    }

    public float calculateCenterX(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float textWidth = layout.width;
        return (Gdx.graphics.getWidth() - textWidth) / 2;
    }


    @Override
    public void render(SpriteBatch sb) {
        final float CENTER_ROOMID_X = calculateCenterX("Room ID: " + roomID, font);
        final float CENTER_PLAYERS_X = calculateCenterX("Players: ", font);
        startGameButton = ButtonFactory.createStartGameButton(CENTER_BUTTON_X, 0.15f * Gdx.graphics.getHeight());
        exitButton = ButtonFactory.createExitButton(CENTER_BUTTON_X, 0.03f * Gdx.graphics.getHeight());

        updateButtonColors();

        // Create input listeners for buttons
        ButtonInputListener startGameInputListener = new ButtonInputListener(startGameButton, gvm, null, null, sb);
        ButtonInputListener exitGameInputListener = new ButtonInputListener(exitButton, gvm, null, null,  sb);
        ButtonInputListener lastKnightListener = new ButtonInputListener(lastKnightButton, gvm, null,  null, sb);
        ButtonInputListener fastestKnightListener = new ButtonInputListener(fastestKnightButton, gvm, null, null, sb);


        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(startGameInputListener);
        inputMultiplexer.addProcessor(exitGameInputListener);
        inputMultiplexer.addProcessor(lastKnightListener);
        inputMultiplexer.addProcessor(fastestKnightListener);

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

        // display room id and player list in the middle
        font.setColor(0, 0, 0, 1);
        font.draw(sb, "Room ID: " + roomID, CENTER_ROOMID_X, 1330);
        font.draw(sb, "Players: ", CENTER_PLAYERS_X, 1230);

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

    private void updateButtonColors() {
        GameModeController gameModeController = GameModeController.getInstance();

        if (gameModeController.isLastKnightMode()) {
            lastKnightButton.setColor(Starknightdown); // Highlight LastKnight button
            fastestKnightButton.setColor(Starknight); // Reset FastestKnight button
            startGameButton.setColor(green); // Highlight StartGame button
        } else if (gameModeController.isFastestKnightMode()) {
            fastestKnightButton.setColor(Starknightdown); // Highlight FastestKnight button
            lastKnightButton.setColor(Starknight); // Reset LastKnight button
            startGameButton.setColor(green); // Highlight StartGame button
        } else {
            // Optional: Reset both buttons if no mode is selected
            fastestKnightButton.setColor(Starknight);
            lastKnightButton.setColor(Starknight);
            startGameButton.setColor(red); // Disable StartGame button
        }
    }

}
