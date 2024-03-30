package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;
import no.ntnu.game.Controllers.GameController;
import no.ntnu.game.Models.TreeWithPowerUp;

public class GameScreen extends Screen {

    private GameController gameController;

    private Button leftButton;

    private Button rightButton;

    private TreeWithPowerUp tree;

    private ShapeRenderer shapeRenderer;

    private float temp = 0;

    public GameScreen(GameScreenManager gvm) {
        super(gvm);

        gameController = new GameController();

        tree = new TreeWithPowerUp();
        tree.init();

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

        temp += dt;
        if(temp > 1) {

            tree.chop();
            tree.createNewTrunk();
            temp = 0;
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        leftButton = ButtonFactory.createLeftArrowButton(200,300);
        rightButton = ButtonFactory.createRightArrowButton(800,300);

        // Create input listeners for buttons
        ButtonInputListener leftInputListener = new ButtonInputListener(leftButton, gvm);
        ButtonInputListener rightInputListener = new ButtonInputListener(rightButton, gvm);
        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(leftInputListener);
        inputMultiplexer.addProcessor(rightInputListener);

        Gdx.input.setInputProcessor(inputMultiplexer);

        tree.draw(sb);

        leftButton.render(shapeRenderer,sb);
        rightButton.render(shapeRenderer,sb);

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
