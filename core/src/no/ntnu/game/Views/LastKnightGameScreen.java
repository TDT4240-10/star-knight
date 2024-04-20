package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.Objects;

import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.Controllers.KnightController;
import no.ntnu.game.Factory.button.CircleButtonFactory;
import no.ntnu.game.Factory.button.RectangleButtonFactory;
import no.ntnu.game.Firestore.GameRoom;
import no.ntnu.game.Models.TimeLimitBar;
import no.ntnu.game.Models.TreeWithPowerUp;

/**
 * Game Screen View class to render StarKnight game
 *
 * @author Han
 */
public class LastKnightGameScreen extends Screen {
    private final Texture POWER_UP_TEXT_LOGO;
    private boolean gameStart = false;
    private final TreeWithPowerUp TREE_WITH_POWER_UP;
    private final KnightController KNIGHT_CONTROLLER;
    private final ShapeRenderer SHAPE_RENDERER;
    private final TimeLimitBar TIME_LIMIT_BAR;
    private int score;
    private final BitmapFont FONT;
    private final Stage STAGE;
    private final Texture ANIMATION_TEXTURE;
    private final TextureRegion[] ANIMATION_FRAMES;
    private final float FRAME_DURATION = 0.1f; // Adjust this value to change animation speed
    private float stateTime = 0f;
    private final GameRoomController GAME_ROOM_CONTROLLER;

    public LastKnightGameScreen(ScreenManager gvm) {
        super(gvm);
        FONT = new BitmapFont(); // Assuming you have a font for rendering text
        // Load the background image
        ANIMATION_TEXTURE = new Texture("background.png");
        // Calculate the width of each frame
        int frameCount = 4; // Assuming 4 frames horizontally
        int frameWidth = ANIMATION_TEXTURE.getWidth() / frameCount;
        // Split the texture into individual frames
        TextureRegion[][] tmp = TextureRegion.split(ANIMATION_TEXTURE, frameWidth, ANIMATION_TEXTURE.getHeight());
        ANIMATION_FRAMES = new TextureRegion[frameCount];
        System.arraycopy(tmp[0], 0, ANIMATION_FRAMES, 0, frameCount);
        POWER_UP_TEXT_LOGO = new Texture("power_ups.png");

        GAME_ROOM_CONTROLLER = GameRoomController.getInstance();

        float timeLimit = 3f;
        float initialTime = 3f;
        TIME_LIMIT_BAR = new TimeLimitBar(initialTime, timeLimit, 300f, 20f, (Gdx.graphics.getWidth() - 300f) / 2,
                Gdx.graphics.getHeight() - 50f);

        TREE_WITH_POWER_UP = new TreeWithPowerUp();
        TREE_WITH_POWER_UP.init();

        SHAPE_RENDERER = new ShapeRenderer();

        KNIGHT_CONTROLLER = new KnightController("last_knight", -80, 500, TREE_WITH_POWER_UP, TIME_LIMIT_BAR,
                timeLimit);

        KNIGHT_CONTROLLER.setIdlePosition(-80, 500);
        KNIGHT_CONTROLLER.setChoppingPosition(-99999, -99999);
        KNIGHT_CONTROLLER.setDeadPosition(-99999, -99999);

        float x_offset = 80;
        float y_offset = 100;

        // Create buttons
        CircleButtonFactory circleButtonFactory = new CircleButtonFactory();
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();

        Button leftButton = circleButtonFactory.createButton("<", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameStart = true;

                if (Objects.equals(KNIGHT_CONTROLLER.getDirection(), "right")) {
                    // Run chopping animation
                    KNIGHT_CONTROLLER.moveLeft();
                } else {
                    KNIGHT_CONTROLLER.stayLeft();
                }
                return true; // Indicate that the touch event is handled
            }
        });
        leftButton.setPosition(x_offset, x_offset + y_offset);
        leftButton.setSize(200, 200);

        // Create buttons
        Button rightButton = circleButtonFactory.createButton(">", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Objects.equals(KNIGHT_CONTROLLER.getDirection(), "left")) {
                    gameStart = true;

                    // Run chopping animation
                    KNIGHT_CONTROLLER.moveRight();
                } else {
                    KNIGHT_CONTROLLER.stayRight();
                }
                return true; // Indicate that the touch event is handled
            }
        });
        float rightButtonX = Gdx.graphics.getWidth() - 200 - x_offset;
        rightButton.setPosition(rightButtonX, x_offset + y_offset);
        rightButton.setSize(200, 200);

        // Create buttons
        // Indicate that the touch event is handled
        Button exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GAME_ROOM_CONTROLLER.gameOver(false);
                gvm.set(new MainMenuScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        float exitButtonX = Gdx.graphics.getWidth() - 300 - x_offset; // Adjust the offset as needed
        float exitButtonY = Gdx.graphics.getHeight() - 200 - x_offset; // Adjust the offset as needed
        exitButton.setPosition(exitButtonX, exitButtonY);
        exitButton.setSize(300, 250);

        // Create the stage for the buttons
        STAGE = new Stage();
        STAGE.addActor(leftButton);
        STAGE.addActor(rightButton);
        STAGE.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(STAGE);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

        // Adding left and right keystrokes to move the Knight
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                gameStart = true;

                if (keycode == Input.Keys.LEFT) {
                    if (Objects.equals(KNIGHT_CONTROLLER.getDirection(), "right")) {
                        KNIGHT_CONTROLLER.moveLeft();
                    } else {
                        KNIGHT_CONTROLLER.stayLeft();
                    }
                } else if (keycode == Input.Keys.RIGHT) {
                    if (Objects.equals(KNIGHT_CONTROLLER.getDirection(), "left")) {
                        KNIGHT_CONTROLLER.moveRight();
                    } else {
                        KNIGHT_CONTROLLER.stayRight();
                    }
                }
                return true; // Indicate that the key event is handled
            }
        });
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        // Update the time limit
        if (gameStart) {
            TIME_LIMIT_BAR.updateTime(dt);
            if (TIME_LIMIT_BAR.isTimeUp()) {
                GAME_ROOM_CONTROLLER.gameOver();
                gvm.set(new LastKnightEndGameScreen(gvm, score));
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        // Update the animation state time
        stateTime += Gdx.graphics.getDeltaTime();

        // Get the current frame index based on the state time and frame duration
        int frameIndex = (int) (stateTime / FRAME_DURATION) % ANIMATION_FRAMES.length;

        // Draw the current frame
        sb.begin();
        sb.draw(ANIMATION_FRAMES[frameIndex], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();
        TREE_WITH_POWER_UP.draw(sb);

        TIME_LIMIT_BAR.render(SHAPE_RENDERER);
        KNIGHT_CONTROLLER.renderBulletTimer(SHAPE_RENDERER);

        KNIGHT_CONTROLLER.renderIdleKnight(sb);
        KNIGHT_CONTROLLER.renderChoppingKnight(sb);
        KNIGHT_CONTROLLER.renderDeadKnight(sb);

        KNIGHT_CONTROLLER.renderLife1(sb);
        KNIGHT_CONTROLLER.renderLife2(sb);
        KNIGHT_CONTROLLER.renderLife3(sb);

        score = KNIGHT_CONTROLLER.getScore();

        if (Objects.equals(KNIGHT_CONTROLLER.update(Gdx.graphics.getDeltaTime()), "lose")) {
            GAME_ROOM_CONTROLLER.gameOver();
            gvm.set(new LastKnightEndGameScreen(gvm, score));
            return;
        }
        if (GAME_ROOM_CONTROLLER.getGameStatus().equals(GameRoom.GameStatus.COMPLETE)) {
            GAME_ROOM_CONTROLLER.gameOver();
            gvm.set(new LastKnightYouWinGameScreen(gvm, score));
            return;
        }

        SHAPE_RENDERER.end();

        sb.begin();
        sb.draw(POWER_UP_TEXT_LOGO, 30, 80);
        FONT.getData().setScale(4f);

        // Calculate the position to center the text on the screen
        float x = (Gdx.graphics.getWidth() - FONT.getXHeight() * 7) / 2; // Assuming average glyph width
        float y = Gdx.graphics.getHeight() - 500; // Center vertically
        FONT.getData().setScale(4f);
        FONT.draw(sb, "Score: " + score, x, y);

        sb.end();

        STAGE.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        STAGE.draw();
    }

    @Override
    public void dispose() {
        SHAPE_RENDERER.dispose();
        ANIMATION_TEXTURE.dispose();
    }

    @Override
    public void create() {
    }
}
