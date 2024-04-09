package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Objects;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;
import no.ntnu.game.Controllers.GameController;
import no.ntnu.game.Controllers.KnightController;
import no.ntnu.game.Models.PowerUp;
import no.ntnu.game.Models.PowerUpFactory;
import no.ntnu.game.Models.Score;
import no.ntnu.game.Models.TimeLimitBar;
import no.ntnu.game.Models.TreeWithPowerUp;

/**
 * Game Screen View class to render StarKnight game
 *
 * @author Han
 */
public class GameScreen extends Screen {

    private GameController gameController;

    private Texture powerUpTextLogo;

    private Button leftButton;

    private Button rightButton;
    private Button exitButton;


    private TreeWithPowerUp treeWithPowerUp;
    private ChoppingKnightSprite choppingKnightSprite;
    private IdleKnightSprite idleKnightSprite;
    private DeadKnightSprite deadKnightSprite;
    private KnightController knightController;

    private ShapeRenderer shapeRenderer;

    private TimeLimitBar timeLimitBar;

    private float temp = 0;

    private float timeLimit = 4f;

    private float initialTime = 4f;

    private PowerUp life1;
    private PowerUp life2;
    private PowerUp life3;

    private Score score;


    public GameScreen(ScreenManager gvm) {
        super(gvm);
        powerUpTextLogo = new Texture("power_ups.png");

        gameController = new GameController();

        timeLimitBar = new TimeLimitBar(initialTime, timeLimit, 300f, 20f, (Gdx.graphics.getWidth() - 300f) / 2, Gdx.graphics.getHeight() - 50f);

        treeWithPowerUp = new TreeWithPowerUp();
        treeWithPowerUp.init();

        shapeRenderer = new ShapeRenderer();

        choppingKnightSprite = new ChoppingKnightSprite();
        idleKnightSprite = new IdleKnightSprite();
        deadKnightSprite = new DeadKnightSprite();

        knightController = new KnightController(-80, 500, treeWithPowerUp, timeLimitBar, timeLimit);

        knightController.setIdlePosition(-80, 500);
        knightController.setChoppingPosition(-99999, -99999);
        knightController.setDeadPosition(-99999, -99999);

        life1 = PowerUpFactory.createLivesPowerUp();
        life2 = PowerUpFactory.createLivesPowerUp();
        life3 = PowerUpFactory.createLivesPowerUp();

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        // Update the time limit
        timeLimitBar.updateTime(dt);
        if (timeLimitBar.isTimeUp()) {
            gvm.set(new YouLoseGameScreen(gvm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        // Adjust offsets as needed
        float x_offset = 80;
        float y_offset = 100;

        // Calculate the XY-coordinates for the right button
        float rightButtonX = Gdx.graphics.getWidth() - 2 * 150 - x_offset;
        leftButton = ButtonFactory.createLeftArrowButton(x_offset + 150, x_offset + 150 + y_offset);
        rightButton = ButtonFactory.createRightArrowButton(rightButtonX + 150, x_offset + 150 + y_offset);

        // Calculate the XY-coordinates for the exit button
        float exitButtonX = Gdx.graphics.getWidth() - 300 - x_offset; // Adjust the offset as needed
        float exitButtonY = Gdx.graphics.getHeight() - 200 - x_offset; // Adjust the offset as needed
        exitButton = ButtonFactory.createExitButton(exitButtonX, exitButtonY);

        // Create input listeners for buttons
        ButtonInputListener exitInputListener = new ButtonInputListener(exitButton, gvm, knightController, sb);
        ButtonInputListener leftInputListener = new ButtonInputListener(leftButton, gvm, knightController, sb);
        ButtonInputListener rightInputListener = new ButtonInputListener(rightButton, gvm, knightController, sb);
        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(exitInputListener);
        inputMultiplexer.addProcessor(leftInputListener);
        inputMultiplexer.addProcessor(rightInputListener);

        Gdx.input.setInputProcessor(inputMultiplexer);

        treeWithPowerUp.draw(sb);

        exitButton.render(shapeRenderer,sb);
        leftButton.render(shapeRenderer,sb);
        rightButton.render(shapeRenderer,sb);

        timeLimitBar.render(shapeRenderer);

        knightController.renderIdleKnight(sb);
        knightController.renderChoppingKnight(sb);
        knightController.renderDeadKnight(sb);

        knightController.renderLife1(sb);
        knightController.renderLife2(sb);
        knightController.renderLife3(sb);

        knightController.renderScore(sb);

        if (Objects.equals(knightController.update(Gdx.graphics.getDeltaTime()), "lose")) {
            gvm.set(new YouWinGameScreen(gvm));
//            gvm.set(new YouLoseGameScreen(gvm));
        };

        shapeRenderer.end();

        sb.begin();
        sb.draw(powerUpTextLogo, 30, 80);
        sb.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
