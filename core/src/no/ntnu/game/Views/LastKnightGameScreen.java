package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.sun.tools.javac.Main;

import java.util.Objects;
import no.ntnu.game.Controllers.GameController;
import no.ntnu.game.Controllers.KnightController;
import no.ntnu.game.Models.PowerUp;
import no.ntnu.game.Models.PowerUpFactory;
import no.ntnu.game.Models.Score;
import no.ntnu.game.Models.TimeLimitBar;
import no.ntnu.game.Models.TreeWithPowerUp;
import no.ntnu.game.factory.button.RectangleButtonFactory;

/**
 * Game Screen View class to render StarKnight game
 *
 * @author Han
 */
public class LastKnightGameScreen extends Screen {

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

    private float timeLimit = 6f;

    private float initialTime = 6f;

    private PowerUp life1;
    private PowerUp life2;
    private PowerUp life3;

    private Score score;

    private Stage stage;


    public LastKnightGameScreen(ScreenManager gvm) {
        super(gvm);
        powerUpTextLogo = new Texture("power_ups.png");
        stage = new Stage();

        gameController = new GameController();

        timeLimitBar = new TimeLimitBar(initialTime, timeLimit, 300f, 20f, (Gdx.graphics.getWidth() - 300f) / 2, Gdx.graphics.getHeight() - 50f);

        treeWithPowerUp = new TreeWithPowerUp();
        treeWithPowerUp.init();

        shapeRenderer = new ShapeRenderer();

        choppingKnightSprite = new ChoppingKnightSprite();
        idleKnightSprite = new IdleKnightSprite();
        deadKnightSprite = new DeadKnightSprite();

        knightController = new KnightController("last_knight", -80, 500, treeWithPowerUp, timeLimitBar, timeLimit);

        knightController.setIdlePosition(-80, 500);
        knightController.setChoppingPosition(-99999, -99999);
        knightController.setDeadPosition(-99999, -99999);

        life1 = PowerUpFactory.createLivesPowerUp();
        life2 = PowerUpFactory.createLivesPowerUp();
        life3 = PowerUpFactory.createLivesPowerUp();

        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();

        leftButton = rectButtonFactory.createButton("<", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Objects.equals(knightController.getDirection(), "right")) {
                    // Run chopping animation
                    knightController.moveLeft();
                }
                else {
                    knightController.stayLeft();
                }
                return true;
            };
        });
        leftButton.setSize(100, 100);
        leftButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 100, 300);
        rightButton = rectButtonFactory.createButton(">", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Objects.equals(knightController.getDirection(), "left")) {
                    // Run chopping animation
                    knightController.moveRight();
                }
                else {
                    knightController.stayRight();
                }

                return true;
            };
        });

        rightButton.setSize(100, 100);
        rightButton.setPosition((float) Gdx.graphics.getWidth() / 2 + 50, 300);

        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true;
            };
        });

        exitButton.setSize(100, 100);
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 50, 100);

        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add stage first to ensure it receives input first
        Gdx.input.setInputProcessor(inputMultiplexer);
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


        treeWithPowerUp.draw(sb);

        timeLimitBar.render(shapeRenderer);

        knightController.renderIdleKnight(sb);
        knightController.renderChoppingKnight(sb);
        knightController.renderDeadKnight(sb);

        knightController.renderLife1(sb);
        knightController.renderLife2(sb);
        knightController.renderLife3(sb);

        knightController.renderScore(sb);

        if (Objects.equals(knightController.update(Gdx.graphics.getDeltaTime()), "lose")) {
            gvm.set(new YouLoseGameScreen(gvm));
        };

        shapeRenderer.end();

        sb.begin();
        sb.draw(powerUpTextLogo, 30, 80);
        sb.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
