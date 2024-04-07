package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Objects;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;
import no.ntnu.game.Controllers.GameController;
import no.ntnu.game.Controllers.KnightController;
import no.ntnu.game.Models.TreeWithPowerUp;

/**
 * Game Screen View class to render StarKnight game
 *
 * @author Han
 */
public class GameScreen extends Screen {

    private GameController gameController;

    private Button leftButton;

    private Button rightButton;
    private Button exitButton;


    private TreeWithPowerUp treeWithPowerUp;
    private ChoppingKnightSprite choppingKnightSprite;
    private IdleKnightSprite idleKnightSprite;
    private DeadKnightSprite deadKnightSprite;
    private KnightController knightController;

    private ShapeRenderer shapeRenderer;

    private float temp = 0;

    public GameScreen(ScreenManager gvm) {
        super(gvm);

        gameController = new GameController();

        treeWithPowerUp = new TreeWithPowerUp();
        treeWithPowerUp.init();

        shapeRenderer = new ShapeRenderer();

        choppingKnightSprite = new ChoppingKnightSprite();
        idleKnightSprite = new IdleKnightSprite();
        deadKnightSprite = new DeadKnightSprite();

        knightController = new KnightController(-80, 500, treeWithPowerUp);

        knightController.setIdlePosition(-80, 500);
        knightController.setChoppingPosition(-99999, -99999);
        knightController.setDeadPosition(-99999, -99999);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

//        temp += dt;
//        if(temp > 1) {
//
//            tree.chop();
//            tree.createNewTrunk();
//            temp = 0;
//        }
    }

    @Override
    public void render(SpriteBatch sb) {
        exitButton = ButtonFactory.createExitButton(500,1950);
        leftButton = ButtonFactory.createLeftArrowButton(200,300);
        rightButton = ButtonFactory.createRightArrowButton(800,300);

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


        knightController.renderIdleKnight(sb);
        knightController.renderChoppingKnight(sb);
        knightController.renderDeadKnight(sb);

        if (Objects.equals(knightController.update(Gdx.graphics.getDeltaTime()), "lose")) {
            gvm.set(new EndGameScreen(gvm));
        };

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
