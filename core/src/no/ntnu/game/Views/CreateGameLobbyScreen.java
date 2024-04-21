package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.Models.Factory.Button.RectangleButtonFactory;
import no.ntnu.game.firestore.GameRoom;

public class CreateGameLobbyScreen extends Screen {
    public static final Color WHITE = new Color(255 / 255f, 255 / 255f, 255 / 255f, 255 / 255f);
    private final Texture LOGO;
    private final BitmapFont FONT; // Declare the font variable
    private final ShapeRenderer SHAPE_RENDERER;
    private final GameRoomController GAME_ROOM_CONTROLLER;
    private final Stage STAGE;

    public CreateGameLobbyScreen(ScreenManager gvm) {
        super(gvm);
        GAME_ROOM_CONTROLLER = GameRoomController.getInstance();
        LOGO = new Texture("starknight_logo.png");
        FONT = new BitmapFont(); // Load the font
        FONT.getData().setScale(3); // Set the font scale to 2 for double size
        SHAPE_RENDERER = new ShapeRenderer();
        STAGE = new Stage();
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();

        // Indicate that the touch event is handled
        Button lastKnightButton = rectButtonFactory.createButton("Last knight", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GAME_ROOM_CONTROLLER.setGameMode(GameRoom.GameMode.LAST_KNIGHT);
                return true; // Indicate that the touch event is handled
            }
        });
        lastKnightButton.setColor(WHITE);
        lastKnightButton.setSize(400, 200);
        lastKnightButton.setPosition((float) Gdx.graphics.getWidth() / 2 + 50, 800);
        // Indicate that the touch event is handled
        Button fastestKnightButton = rectButtonFactory.createButton("Fast knight", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GAME_ROOM_CONTROLLER.setGameMode(GameRoom.GameMode.FASTEST_KNIGHT);
                return true; // Indicate that the touch event is handled

            }
        });
        fastestKnightButton.setColor(WHITE);
        fastestKnightButton.setSize(400, 200);
        fastestKnightButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 450, 800);

        Button startGameButton = rectButtonFactory.createButton("Start game", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (GAME_ROOM_CONTROLLER.getCurrentGameMode() == null)
                    return true;
                if (GAME_ROOM_CONTROLLER.getGameRoom().getJoiningPlayer() == null)
                    return true;
                GAME_ROOM_CONTROLLER.startGame();
                return true;
            }
        });

        startGameButton.setSize(350, 200);
        startGameButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 550);

        // Indicate that the touch event is handled
        Button exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);

        STAGE.addActor(fastestKnightButton);
        STAGE.addActor(lastKnightButton);
        STAGE.addActor(startGameButton);
        STAGE.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(STAGE); // Add stage first to ensure it receives input first
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void setGameScreen() {
        GameRoom.GameMode currentMode = GAME_ROOM_CONTROLLER.getCurrentGameMode();
        if (currentMode == null) {
            return;
        }
        GAME_ROOM_CONTROLLER.setGameStatusPlaying();
        if (currentMode.equals(GameRoom.GameMode.FASTEST_KNIGHT)) {
            gvm.set(new FastestKnightGameScreen(gvm));
        } else {
            gvm.set(new LastKnightGameScreen(gvm));
        }
    }

    public float calculateCenterX(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float textWidth = layout.width;
        return (Gdx.graphics.getWidth() - textWidth) / 2;
    }

    private String getUsernames() {
        String creatingPlayerUsername = GAME_ROOM_CONTROLLER.getGameRoom().getCreatingPlayer() != null
                ? GAME_ROOM_CONTROLLER.getGameRoom().getCreatingPlayer().getUsername()
                : "";
        String joiningPlayerUsername = GAME_ROOM_CONTROLLER.getGameRoom().getJoiningPlayer() != null
                ? GAME_ROOM_CONTROLLER.getGameRoom().getJoiningPlayer().getUsername()
                : "";

        return creatingPlayerUsername + ", " + joiningPlayerUsername;
    }

    @Override
    public void render(SpriteBatch sb) {
        String usernames = getUsernames();
        if (GAME_ROOM_CONTROLLER.getGameStatus().equals(GameRoom.GameStatus.STARTING)) {
            if (GAME_ROOM_CONTROLLER.getGameStartCountdown() == 0) {
                setGameScreen();
            }
        }

        // Since clocks can be slightly off, the other client might start the game
        // before the countdown is complete on current device.
        // If that happens we can just start the game on this device.
        if (GAME_ROOM_CONTROLLER.getGameStatus().equals(GameRoom.GameStatus.PLAYING)) {
            setGameScreen();
        }
        String roomCode = GAME_ROOM_CONTROLLER.getGameRoom().getRoomCode();
        GameRoom.GameMode gameMode = GAME_ROOM_CONTROLLER.getCurrentGameMode();
        final float CENTER_ROOM_ID_X = calculateCenterX("Room ID: " + roomCode, FONT);
        final float CENTER_PLAYERS_X = calculateCenterX("Players: " + usernames, FONT);
        final float CENTER_GAME_MODE = calculateCenterX("Game mode: " + gameMode, FONT);

        // display logo
        sb.begin();

        // draw logo
        float logoWidth = LOGO.getWidth();
        float logoHeight = LOGO.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top
        sb.draw(LOGO, logoX, logoY);

        // display room id and player list in the middle
        FONT.setColor(WHITE);
        FONT.draw(sb, "Room ID: " + roomCode, CENTER_ROOM_ID_X, 1360);
        FONT.draw(sb, "Players: " + usernames, CENTER_PLAYERS_X, 1260);
        FONT.draw(sb, "Game mode: " + gameMode, CENTER_GAME_MODE, 1160);
        if (GAME_ROOM_CONTROLLER.getGameStatus().equals(GameRoom.GameStatus.STARTING)) {
            final float CENTER_COUNTDOWN = calculateCenterX(
                    "Game starting in: " + GAME_ROOM_CONTROLLER.getGameStartCountdown(), FONT);
            FONT.draw(sb, "Game starting in: " + GAME_ROOM_CONTROLLER.getGameStartCountdown(), CENTER_COUNTDOWN, 1060);
        }
        sb.end();

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
    public void create() {

    }

    @Override
    public void dispose() {
        SHAPE_RENDERER.dispose();
        LOGO.dispose();
    }

}
