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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.List;

import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.factory.button.RectangleButtonFactory;
import no.ntnu.game.firestore.GameRoom;
import no.ntnu.game.firestore.Player;

public class CreateGameLobbyScreen extends Screen {
    public static Color Starknightdown = new Color(105 / 255f, 105 / 255f, 105 / 255f, 1 / 255f);
    public static Color Starknight = new Color(61 / 255f, 63 / 255f, 65 / 255f, 255 / 255f);
    public static Color green = new Color(0 / 255f, 255 / 255f, 0 / 255f, 255 / 255f);
    public static Color red = new Color(255 / 255f, 0 / 255f, 0 / 255f, 255 / 255f);


    private Texture logo;

    BitmapFont font; // Declare the font variable
    private ShapeRenderer shapeRenderer;

    private Button startGameButton;
    private Button lastKnightButton;
    private Button fastestKnightButton;
    private Button exitButton;
    private GameRoomController gameRoomController;
    private Stage stage;

    public CreateGameLobbyScreen(ScreenManager gvm) {
        super(gvm);
        gameRoomController = GameRoomController.getInstance();
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
        stage = new Stage();
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();

        lastKnightButton = rectButtonFactory.createButton("Last knight", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameRoomController.setGameMode(GameRoom.GameMode.LAST_KNIGHT);
                return true; // Indicate that the touch event is handled

            }
        });

        lastKnightButton.setSize(400, 200);
        lastKnightButton.setPosition((float) Gdx.graphics.getWidth() / 2 + 50, 800);
        fastestKnightButton = rectButtonFactory.createButton("Fast knight", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameRoomController.setGameMode(GameRoom.GameMode.FASTEST_KNIGHT);
                return true; // Indicate that the touch event is handled

            }
        });

        fastestKnightButton.setSize(400, 200);
        fastestKnightButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 450, 800);

        startGameButton = rectButtonFactory.createButton("Start game", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameRoom.GameMode currentMode = gameRoomController.getCurrentGameMode();
                if (currentMode == null) {
                    return false;
                }

                if (currentMode.equals(GameRoom.GameMode.FASTEST_KNIGHT)) {
                    gvm.set(new FastestKnightGameScreen(gvm));
                } else { gvm.set(new LastKnightGameScreen(gvm));
                }
                gameRoomController.setGameStatus(GameRoom.GameStatus.PLAYING);
                return true;
            }
        });

        startGameButton.setSize(350, 200);
        startGameButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 550);

        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });

        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);

        stage.addActor(fastestKnightButton);
        stage.addActor(lastKnightButton);
        stage.addActor(startGameButton);
        stage.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add stage first to ensure it receives input first
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public float calculateCenterX(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float textWidth = layout.width;
        return (Gdx.graphics.getWidth() - textWidth) / 2;
    }

    private String createUsernamesString() {
        List<Player> players = gameRoomController.getGameRoom().getPlayers();
        StringBuilder usernameBuilder = new StringBuilder();
        for (Player player : players) {
            if (usernameBuilder.length() > 0) {
                usernameBuilder.append(", ");
            }
            usernameBuilder.append(player.getUsername());
        }

        return usernameBuilder.toString();
    }

    @Override
    public void render(SpriteBatch sb) {
        String usernames = createUsernamesString();
        String roomCode = gameRoomController.getGameRoom().getRoomCode();
        GameRoom.GameMode gameMode = gameRoomController.getCurrentGameMode();
        final float CENTER_ROOMID_X = calculateCenterX("Room ID: " + roomCode, font);
        final float CENTER_PLAYERS_X = calculateCenterX("Players: " + usernames, font);
        final float CENTER_GAME_MODE = calculateCenterX("Game mode: " + gameMode, font);

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
        font.draw(sb, "Room ID: " + roomCode, CENTER_ROOMID_X, 1330);
        font.draw(sb, "Players: " + usernames, CENTER_PLAYERS_X, 1230);
        font.draw(sb, "Game mode: " + gameMode, CENTER_GAME_MODE, 1130);

        sb.end();


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
        System.out.println("Game Lobby View Disposed");

    }

    private void updateButtonColors() {
        GameRoomController gameModeController = GameRoomController.getInstance();

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
