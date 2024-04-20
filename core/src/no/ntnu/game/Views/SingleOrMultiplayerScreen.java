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
import no.ntnu.game.Controllers.PlayerController;
import no.ntnu.game.Factory.button.RectangleButtonFactory;

/**
 * Main Menu View class to render main menu screen
 *
 * @author Han
 */
public class SingleOrMultiplayerScreen extends Screen {
    private final Texture LOGO;

    private final Stage STAGE;

    public final GameRoomController GAME_ROOM_CONTROLLER;
    private final PlayerController PLAYER_CONTROLLER;

    private final ShapeRenderer SHAPE_RENDERER;

    public SingleOrMultiplayerScreen(ScreenManager gvm) {
        super(gvm);
        GAME_ROOM_CONTROLLER = GameRoomController.getInstance();
        PLAYER_CONTROLLER = PlayerController.getPlayerController();
        LOGO = new Texture("starknight_logo.png");
        STAGE = new Stage();
        // Declare the font variable
        BitmapFont font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        SHAPE_RENDERER = new ShapeRenderer();

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        // Indicate that the touch event is handled
        Button singleplayerButton = rectButtonFactory.createButton("Solo", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GAME_ROOM_CONTROLLER.resetGameMode();
                GAME_ROOM_CONTROLLER.createSoloRoom(PLAYER_CONTROLLER.getPlayer());
                gvm.set(new SinglePlayerChooseGameModeScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        singleplayerButton.setSize(350, 200); // Set the size of the button
        singleplayerButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 800);

        Button multiplayerButton = rectButtonFactory.createButton("Online", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GAME_ROOM_CONTROLLER.resetGameMode();
                gvm.push(new CreateOrJoinRoomScreen(gvm));
                return true;
            }
        });

        multiplayerButton.setSize(350, 200); // Set the size of the button
        multiplayerButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 550);

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
        STAGE.addActor(singleplayerButton);
        STAGE.addActor(multiplayerButton);
        STAGE.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(STAGE);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first
    }

    @Override
    public void render(SpriteBatch sb) {
        float logoWidth = LOGO.getWidth();
        float logoHeight = LOGO.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top
        sb.begin();
        sb.draw(LOGO, logoX, logoY);
        sb.end();

        SHAPE_RENDERER.end();

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