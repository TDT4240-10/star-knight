package no.ntnu.game.Views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.game.Controllers.GameController;
import no.ntnu.game.Models.TreeWithPowerUp;

public class GameScreen extends Screen {

    private GameController gameController;

    private TreeWithPowerUp tree;

    public GameScreen(GameScreenManager gvm) {
        super(gvm);

        gameController = new GameController();

        tree = new TreeWithPowerUp();
        tree.init();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        tree.draw(sb);
    }

    @Override
    public void dispose() {

    }
}
