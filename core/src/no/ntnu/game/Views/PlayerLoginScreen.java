package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.game.Controllers.PlayerController;
import no.ntnu.game.Factory.button.RectangleButtonFactory;
import no.ntnu.game.Factory.textfield.TextFieldFactory;
import no.ntnu.game.Firestore.Player;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Log in screen, linked with firestore to load user's data
 *
 * @author Deen
 */

public class PlayerLoginScreen extends Screen {
    public Player player;
    private final Stage STAGE;
    private final TextField USERNAME_FIELD;

    private final Texture LOGO;

    private final BitmapFont FONT; // Declare the font variable
    private final ShapeRenderer SHAPE_RENDERER;

    private final PlayerController PLAYER_CONTROLLER;

    public PlayerLoginScreen(ScreenManager gvm) {
        super(gvm);
        PLAYER_CONTROLLER = PlayerController.getPlayerController();
        LOGO = new Texture("starknight_logo.png");
        FONT = new BitmapFont(); // Load the font
        FONT.getData().setScale(3); // Set the font scale to 2 for double size
        SHAPE_RENDERER = new ShapeRenderer();
        RectangleButtonFactory rectangleButtonFactory = new RectangleButtonFactory();
        TextFieldFactory textFieldFactory = new TextFieldFactory();
        Button loginButton = rectangleButtonFactory.createButton("Login", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String username = USERNAME_FIELD.getText();
                if (!username.isEmpty()) {
                    PLAYER_CONTROLLER.signInPlayer(username,
                            player -> Gdx.app.postRunnable(() -> gvm.set(new MainMenuScreen(gvm))));
                }
                return true;
            }
        });
        loginButton.setSize(350, 200); // Set the size of the button
        loginButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 700);

        // create text field
        USERNAME_FIELD = textFieldFactory.createTextfield("");
        USERNAME_FIELD.setPosition((float) Gdx.graphics.getWidth() / 2 - 250, 1050);
        USERNAME_FIELD.setSize(500, 150);

        STAGE = new Stage();
        STAGE.addActor(USERNAME_FIELD);
        STAGE.addActor(loginButton);
    }

    public float calculateCenterX(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float textWidth = layout.width;
        return (Gdx.graphics.getWidth() - textWidth) / 2;
    }

    @Override
    public void render(SpriteBatch sb) {
        final float CENTER_USERNAME_X = calculateCenterX("Enter your username!", FONT);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(STAGE); // Add stage first to ensure it receives input first

        Gdx.input.setInputProcessor(inputMultiplexer);

        sb.begin();
        // draw logo
        float logoWidth = LOGO.getWidth();
        float logoHeight = LOGO.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top
        sb.draw(LOGO, logoX, logoY);

        FONT.draw(sb, "Enter your username!", CENTER_USERNAME_X, 1000);
        sb.end();

        // render both buttons
        SHAPE_RENDERER.end();

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
        LOGO.dispose();
        STAGE.dispose(); // Dispose of the stage
    }

    @Override
    public void create() {

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
