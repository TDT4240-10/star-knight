package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;
/**
 * Join Game Lobby View class to render join game lobby screen
 *
 * @author Deen
 */
public class JoinGameLobbyScreen extends Screen {
    private Stage stage;
    private TextField textField;
    private Skin skin; // libGDX skins provide styling for UI widgets

    private SpriteBatch batch;
//    private ShapeRenderer shapeRenderer;

    private Texture logo;
    private Texture waitingForHostToStart;

    BitmapFont font; // Declare the font variable
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private Button exitButton;
    private String joinRoomID;

    public JoinGameLobbyScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
        waitingForHostToStart = new Texture("waiting_for_host_to_start.png");

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
        final float CENTER_ROOMID_X = calculateCenterX("Room ID: " + joinRoomID, font);
        final float CENTER_PLAYERS_X = calculateCenterX("Players: ", font);
        exitButton = ButtonFactory.createExitButton(CENTER_BUTTON_X, 300);

        // Create input listeners for buttons
        ButtonInputListener exitInputListener = new ButtonInputListener(exitButton, gvm, null, null, sb);
        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(exitInputListener);

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
        // todo get room id and player list from server
        font.draw(sb, "Room ID: " + joinRoomID, CENTER_ROOMID_X, 1330);
        font.draw(sb, "Players: ", CENTER_PLAYERS_X, 1230);

        sb.end();

        // render waiting for host to start
        sb.begin();
        float waitingForHostToStartWidth = waitingForHostToStart.getWidth();
        float waitingForHostToStartHeight = waitingForHostToStart.getHeight();

        // Calculate the scale factor to fit the texture to the screen
        float scale = Math.min(screenWidth / waitingForHostToStartWidth, screenHeight / waitingForHostToStartHeight);

        // Calculate the new width and height of the texture
        float newWidth = waitingForHostToStartWidth * scale * 0.95f;
        float newHeight = waitingForHostToStartHeight * scale * 0.95f;

        // Calculate the new position to center the texture on the screen
        float waitingForHostToStartX = (screenWidth - newWidth) / 2;
        float waitingForHostToStartY = (screenHeight - newHeight) * 0.27f;

        // Draw the texture with the new size and position
        sb.draw(waitingForHostToStart, waitingForHostToStartX, waitingForHostToStartY, newWidth, newHeight);
        sb.end();

        // render exit button
        exitButton.render(shapeRenderer, sb);
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

    // set roomID
    public void setJoinRoomID(String roomID) {
        this.joinRoomID = roomID;
    }
}
