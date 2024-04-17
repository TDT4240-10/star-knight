package no.ntnu.game.Views;

import com.badlogic.gdx.Game;
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

import no.ntnu.game.Controllers.GameController;
import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.Controllers.KnightController;
import no.ntnu.game.Models.PowerUp;
import no.ntnu.game.Models.PowerUpFactory;
import no.ntnu.game.Models.Timer;
import no.ntnu.game.Models.TreeWithPowerUp;
import no.ntnu.game.Views.Sprites.ChoppingKnightSprite;
import no.ntnu.game.Views.Sprites.DeadKnightSprite;
import no.ntnu.game.Views.Sprites.IdleKnightSprite;
import no.ntnu.game.factory.button.CircleButtonFactory;
import no.ntnu.game.factory.button.RectangleButtonFactory;
import no.ntnu.game.firestore.GameRoom;

/**
 * Game Screen View class to render StarKnight game
 *
 * @author Han
 */
public class FastestKnightGameScreen extends Screen {
//    private float elapsedTime = 0;

    private GameRoomController gameRoomController;

    private Texture powerUpTextLogo;

    private final Button leftButton;

    private final Button rightButton;
    private final Button exitButton;
    private int player_score;


    private TreeWithPowerUp treeWithPowerUp;
    private ChoppingKnightSprite choppingKnightSprite;
    private IdleKnightSprite idleKnightSprite;
    private DeadKnightSprite deadKnightSprite;
    private KnightController knightController;

    private ShapeRenderer shapeRenderer;

//    private TimeLimitBar timeLimitBar;

    private float temp = 0;

    private float timeLimit = 30f;

    private float initialTime = 30f;

    private PowerUp life1;
    private PowerUp life2;
    private PowerUp life3;

//    private Score score;
    private final Stage stage;
    private final BitmapFont font;
    private Timer timer;

    private Texture animationTexture;
    private TextureRegion[] animationFrames;
    private float frameDuration = 0.1f; // Adjust this value to change animation speed
    private float stateTime = 0f;

    public FastestKnightGameScreen(ScreenManager gvm) {
        super(gvm);
        gameRoomController = GameRoomController.getInstance();
        timer = new Timer();
        font = new BitmapFont(); // Assuming you have a font for rendering text
        // Load the background image
        animationTexture = new Texture("background.png");
        // Calculate the width of each frame
        int frameCount = 4; // Assuming 4 frames horizontally
        int frameWidth = animationTexture.getWidth() / frameCount;
        // Split the texture into individual frames
        TextureRegion[][] tmp = TextureRegion.split(animationTexture, frameWidth, animationTexture.getHeight());
        animationFrames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            animationFrames[i] = tmp[0][i];
        }
        powerUpTextLogo = new Texture("power_ups.png");

        stage = new Stage();


        treeWithPowerUp = new TreeWithPowerUp();
        treeWithPowerUp.init();

        shapeRenderer = new ShapeRenderer();

        choppingKnightSprite = new ChoppingKnightSprite();
        idleKnightSprite = new IdleKnightSprite();
        deadKnightSprite = new DeadKnightSprite();

        knightController = new KnightController("fastest_knight", -80, 500, treeWithPowerUp, null, timeLimit);

        knightController.setIdlePosition(-80, 500);
        knightController.setChoppingPosition(-99999, -99999);
        knightController.setDeadPosition(-99999, -99999);

        life1 = PowerUpFactory.createLivesPowerUp();
        life2 = PowerUpFactory.createLivesPowerUp();
        life3 = PowerUpFactory.createLivesPowerUp();

        float x_offset = 80;
        float y_offset = 100;

        // Create buttons
        CircleButtonFactory circleButtonFactory = new CircleButtonFactory();
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();

        leftButton = circleButtonFactory.createButton("<", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Objects.equals(knightController.getDirection(), "right")) {
                    // Run chopping animation
                    knightController.moveLeft();
                }
                else {
                    knightController.stayLeft();
                }
                return true; // Indicate that the touch event is handled
            }
        });
        leftButton.setPosition(x_offset, x_offset + y_offset);
        leftButton.setSize(200,200);

        // Create buttons
        float rightButtonX = Gdx.graphics.getWidth() - 200 - x_offset;

        rightButton = circleButtonFactory.createButton(">", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Objects.equals(knightController.getDirection(), "left")) {
                    // Run chopping animation
                    knightController.moveRight();
                }
                else {
                    knightController.stayRight();
                }
                return true; // Indicate that the touch event is handled
            }
        });
        rightButton.setPosition(rightButtonX, x_offset + y_offset);
        rightButton.setSize(200,200);

        // Create buttons
        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                knightController.stopMusic();
                gvm.set(new MainMenuScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        float exitButtonX = Gdx.graphics.getWidth() - 300 - x_offset; // Adjust the offset as needed
        float exitButtonY = Gdx.graphics.getHeight() - 200 - x_offset; // Adjust the offset as needed
        exitButton.setPosition(exitButtonX, exitButtonY);
        exitButton.setSize(300,250);

        // Create the stage for the buttons
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

        // Adding left and right keystrokes to move the Knight
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.LEFT) {
                    if (Objects.equals(knightController.getDirection(), "right")) {
                        knightController.moveLeft();
                    } else {
                        knightController.stayLeft();
                    }
                } else if (keycode == Input.Keys.RIGHT) {
                    if (Objects.equals(knightController.getDirection(), "left")) {
                        knightController.moveRight();
                    } else {
                        knightController.stayRight();
                    }
                }
                return true; // Indicate that the key event is handled
            }
        });

    }

    @Override
    protected void handleInput() {
        // when touched either buttons, start timer
        if (Gdx.input.justTouched()) {
            timer.start();
        }
    }

    @Override
    public void update(float dt) {
        // Update the elapsed time
        timer.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        // Update the animation state time
        stateTime += Gdx.graphics.getDeltaTime();

        // Get the current frame index based on the state time and frame duration
        int frameIndex = (int) (stateTime / frameDuration) % animationFrames.length;

        // Draw the current frame
        sb.begin();
        sb.draw(animationFrames[frameIndex], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();
        treeWithPowerUp.draw(sb);

//        timeLimitBar.render(shapeRenderer);

        knightController.renderIdleKnight(sb);
        knightController.renderChoppingKnight(sb);
        knightController.renderDeadKnight(sb);

        knightController.renderLife1(sb);
        knightController.renderLife2(sb);
        knightController.renderLife3(sb);

        player_score = knightController.getScore();
        
        if (player_score < 0) {
            player_score = 0;
        }
//        player_score = 1234;

        if (player_score == 0) {
            // stop timer
            timer.stop();
            // TODO: Change from casting to some other method
            // TODO: Updates even when loosing
            knightController.setScore((int) timer.getElapsedTime());
            gameRoomController.gameOver();
            knightController.stopMusic();
            gvm.set(new FastestKnightWinGameScreen(gvm, timer.getElapsedTime()));
            return;
        }
        if (Objects.equals(knightController.update(Gdx.graphics.getDeltaTime()), "lose")){
            // stop timer
            timer.stop();
            gameRoomController.gameOver();
            gvm.set(new FastestKnightLoseGameScreen(gvm, timer.getElapsedTime()));
            return;
        }

        // You you haven't won, but the game is complete, you have lost the game
        if(gameRoomController.getGameStatus().equals(GameRoom.GameStatus.COMPLETE)) {
            timer.stop();
            gameRoomController.gameOver();
            gvm.set(new FastestKnightLoseGameScreen(gvm, timer.getElapsedTime()));
            return;
        }

        shapeRenderer.end();

        sb.begin();
        sb.draw(powerUpTextLogo, 30, 80);

        float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2 - 80; // Assuming average glyph width
        float y = Gdx.graphics.getHeight() - 500; // Center vertically
        float timer_y = Gdx.graphics.getHeight() - 300; // Center vertically
        font.getData().setScale(4f);
        font.draw(sb, "Trees to cut: " + player_score, x, y);
        font.draw(sb, "Time: " + formatTime(timer.getElapsedTime()), x, timer_y);
        // print out the time that user has taken
        // implement a count up timer
        sb.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    // method to format time in HH:MM:SS format
    @SuppressWarnings("DefaultLocale")
    private String formatTime(float time) {
        int hours = (int) (time / 3600);
        int minutes = (int) ((time % 3600) / 60);
        int seconds = (int) (time % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        animationTexture.dispose();

    }

    @Override
    public void create(){

    }
}
