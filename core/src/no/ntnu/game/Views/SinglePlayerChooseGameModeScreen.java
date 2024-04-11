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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.Random;

import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.factory.button.RectangleButtonFactory;
import no.ntnu.game.firestore.GameRoom;

public class SinglePlayerChooseGameModeScreen extends Screen {
    private Stage stage;
    private TextField textField;
    private Skin skin; // libGDX skins provide styling for UI widgets

    private SpriteBatch batch;
//    private ShapeRenderer shapeRenderer;

    private Texture logo;

    BitmapFont font; // Declare the font variable
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private Button lastKnightButton;
    private Button fastestKnightButton;
    private Button exitButton;
    private GameRoomController gameRoomController = GameRoomController.getInstance();

    // TODO link the room id with backend
    private String roomID; // room id for the game lobby

    public SinglePlayerChooseGameModeScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
        generateRoomID();

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();

        lastKnightButton = rectButtonFactory.createButton("Last Knight", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // set game mode to last knight
                gameRoomController.setGameMode(GameRoom.GameMode.LAST_KNIGHT);
                gvm.push(new LastKnightGameScreen(gvm));
                return true;
            }
        });

        lastKnightButton.setSize(600, 200); // Set the size of the button
        lastKnightButton.setPosition( (float) Gdx.graphics.getWidth() / 2 - 300, 550);

        fastestKnightButton = rectButtonFactory.createButton("Fastest Knight", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // set game mode to fastest knight
                gameRoomController.setGameMode(GameRoom.GameMode.FASTEST_KNIGHT);
                gvm.set(new FastestKnightGameScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        fastestKnightButton.setSize(600, 200); // Set the size of the button
        fastestKnightButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 300, 800);

        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true;
            }
        });

        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);


        // Create the stage for the buttons
        stage = new Stage();
        stage.addActor(fastestKnightButton);
        stage.addActor(lastKnightButton);
        stage.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

    }

    // Method to generate a random 6-digit room ID
    private void generateRoomID() {
        Random random = new Random();
        int randomID = 100000 + random.nextInt(900000); // Random number between 100000 and 999999
        roomID = String.valueOf(randomID);
    }

    @Override
    public void render(SpriteBatch sb) {
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

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        shapeRenderer.end();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void create(){

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        logo.dispose();
        System.out.println("Game Lobby View Disposed");

    }
}
