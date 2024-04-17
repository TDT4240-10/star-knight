package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.game.Controllers.PlayerController;
import no.ntnu.game.factory.button.RectangleButtonFactory;
import no.ntnu.game.factory.textfield.TextFieldFactory;
import no.ntnu.game.firestore.Player;

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
    private final Stage stage;
    private final TextField usernameField;

    private final Texture logo;

    private final BitmapFont font; // Declare the font variable
    private final ShapeRenderer shapeRenderer;

    private final PlayerController playerController;

    public PlayerLoginScreen(ScreenManager gvm) {
        super(gvm);
        playerController = PlayerController.getPlayerController();
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
        RectangleButtonFactory rectangleButtonFactory = new RectangleButtonFactory();
        TextFieldFactory textFieldFactory = new TextFieldFactory();
        Button loginButton = rectangleButtonFactory.createButton("Login", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String username = usernameField.getText();
                if (!username.isEmpty()) {
                    playerController.signInPlayer(username,
                            player -> Gdx.app.postRunnable(() -> gvm.set(new MainMenuScreen(gvm))));
                }
                return true;
            }
        });
        loginButton.setSize(350, 200); // Set the size of the button
        loginButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 700);

        // create text field
        usernameField = textFieldFactory.createTextfield("");
        usernameField.setPosition((float) Gdx.graphics.getWidth() / 2 - 250, 1050);
        usernameField.setSize(500, 150);

        stage = new Stage();
        stage.addActor(usernameField);
        stage.addActor(loginButton);
    }

    public float calculateCenterX(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float textWidth = layout.width;
        return (Gdx.graphics.getWidth() - textWidth) / 2;
    }

    @Override
    public void render(SpriteBatch sb) {
        final float CENTER_USERNAME_X = calculateCenterX("Enter your username!", font);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add stage first to ensure it receives input first

        Gdx.input.setInputProcessor(inputMultiplexer);

        sb.begin();
        // draw logo
        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top
        sb.draw(logo, logoX, logoY);

        font.draw(sb, "Enter your username!", CENTER_USERNAME_X, 1000);
        sb.end();

        // render both buttons
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

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        logo.dispose();
        stage.dispose(); // Dispose of the stage
        System.out.println("Create or Join Room View Disposed");
    }

    @Override
    public void create() {

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
