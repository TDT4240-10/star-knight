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
import no.ntnu.game.FirebaseInterface;
import no.ntnu.game.firestore.Player;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class PlayerLoginScreen extends Screen {
    public Player player;
    private Stage stage;
    private TextField usernameField;
    private Skin skin; // libGDX skins provide styling for UI widgets

    private SpriteBatch batch;
//    private ShapeRenderer shapeRenderer;

    private Texture logo;

    BitmapFont font; // Declare the font variable
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private Button loginButton;
    private FirebaseInterface _FI;

    // this is the constructor for the CreateGameScreen class, a user will come to this screen either make a new room or join a room.
    // there will be two buttons, one for creating a room and one for joining a room.

    public PlayerLoginScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // create text field
        usernameField = new TextField("", skin);
        usernameField.setPosition(300, 1050);
        usernameField.setSize(500, 150);

        stage.addActor(usernameField);
        this._FI = _FI;
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
        final float CENTER_USERNAME_X = calculateCenterX("Enter your username!", font);
        loginButton = ButtonFactory.createLoginButton(CENTER_BUTTON_X, 700);

        // Create input listeners for buttons
        ButtonInputListener loginInputListener = new ButtonInputListener(loginButton, gvm, null, usernameField, sb);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add stage first to ensure it receives input first
        inputMultiplexer.addProcessor(loginInputListener);

        Gdx.input.setInputProcessor(inputMultiplexer);

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

        font.draw(sb, "Enter your username!", CENTER_USERNAME_X, 1000);
        sb.end();

        // render both buttons
        loginButton.render(shapeRenderer, sb);
        shapeRenderer.end();

        // draw stage and text field
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt){

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        logo.dispose();
        stage.dispose(); // Dispose of the stage
        System.out.println("Create or Join Room View Disposed");
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
