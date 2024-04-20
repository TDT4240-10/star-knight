package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.Factory.Button.RectangleButtonFactory;
import no.ntnu.game.firestore.GameRoom;

/**
 * SinglePlayerChooseGameModeScreen is a screen where the player can choose
 * between two game modes
 * for single player mode.
 */
public class SinglePlayerChooseGameModeScreen extends Screen {
    private final Stage STAGE;
    private final Texture LOGO;
    private final ShapeRenderer SHAPE_RENDERER;
    private final GameRoomController GAME_ROOM_CONTROLLER;

    public SinglePlayerChooseGameModeScreen(ScreenManager gvm) {
        super(gvm);
        GAME_ROOM_CONTROLLER = GameRoomController.getInstance();
        LOGO = new Texture("starknight_logo.png");
        // Declare the font variable
        BitmapFont font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        SHAPE_RENDERER = new ShapeRenderer();

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();

        // set game mode to last knight
        Button lastKnightButton = rectButtonFactory.createButton("Last Knight", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // set game mode to last knight
                GAME_ROOM_CONTROLLER.setGameMode(GameRoom.GameMode.LAST_KNIGHT);
                GAME_ROOM_CONTROLLER.setGameStatusPlaying();
                gvm.push(new LastKnightGameScreen(gvm));
                return true;
            }
        });
        lastKnightButton.setSize(600, 200); // Set the size of the button
        lastKnightButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 300, 550);

        // set game mode to fastest knight
        Button fastestKnightButton = rectButtonFactory.createButton("Fastest Knight", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // set game mode to fastest knight
                GAME_ROOM_CONTROLLER.setGameMode(GameRoom.GameMode.FASTEST_KNIGHT);
                GAME_ROOM_CONTROLLER.setGameStatusPlaying();
                gvm.set(new FastestKnightGameScreen(gvm));
                return true;
            }
        });
        fastestKnightButton.setSize(600, 200); // Set the size of the button
        fastestKnightButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 300, 800);

        Button exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true;
            }
        });
        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);

        // Create the stage for the buttons
        STAGE = new Stage();
        STAGE.addActor(fastestKnightButton);
        STAGE.addActor(lastKnightButton);
        STAGE.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(STAGE);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        float logoWidth = LOGO.getWidth();
        float logoHeight = LOGO.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top
        sb.draw(LOGO, logoX, logoY);
        sb.end();

        STAGE.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        STAGE.draw();

        SHAPE_RENDERER.end();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void create() {

    }

    @Override
    public void dispose() {
        SHAPE_RENDERER.dispose();
        LOGO.dispose();
    }
}
