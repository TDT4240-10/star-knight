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

import no.ntnu.game.Factory.button.RectangleButtonFactory;
import no.ntnu.game.Views.Sprites.WinRunningKnightSprite;

/**
 * End Game Screen View class to render Win screen
 *
 * @author Deen
 */
public class LastKnightYouWinGameScreen extends Screen {
    private final Texture LOGO;
    private final BitmapFont FONT; // Declare the font variable
    private final Stage STAGE;
    private final ShapeRenderer SHAPE_RENDERER;
    private final WinRunningKnightSprite WIN_RUNNING_KNIGT_SPRITE;
    private float knightX, knightY;
    private float knightSpeed = 300; // Pixels per second
    private final int PLAYER_SCORE;

    public LastKnightYouWinGameScreen(ScreenManager gvm, int player_score) {
        super(gvm);
        LOGO = new Texture("win.png");
        FONT = new BitmapFont(); // Load the font
        FONT.getData().setScale(3); // Set the font scale to 2 for double size
        SHAPE_RENDERER = new ShapeRenderer();

        WIN_RUNNING_KNIGT_SPRITE = new WinRunningKnightSprite();

        knightX = 0;
        knightY = 900;
        this.PLAYER_SCORE = player_score;

        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
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

        STAGE = new Stage();
        STAGE.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(STAGE); // Add stage first to ensure it receives input first
        Gdx.input.setInputProcessor(inputMultiplexer);

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

        FONT.getData().setScale(10f);
        float x = (Gdx.graphics.getWidth() - FONT.getXHeight()) / 2; // Assuming average glyph width
        float y = logoY - 100; // Center vertically
        FONT.draw(sb, String.valueOf(PLAYER_SCORE), x, y);

        sb.end();

        // to ensure the knight is moving at the same speed on all devices
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);

        WIN_RUNNING_KNIGT_SPRITE.setPosition(knightX, knightY);
        WIN_RUNNING_KNIGT_SPRITE.render(sb);
        STAGE.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        STAGE.draw();

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        knightX += knightSpeed * dt;
        if (knightX > Gdx.graphics.getWidth()) {
            knightX = -WIN_RUNNING_KNIGT_SPRITE.getWidth();
        }
    }

    @Override
    public void dispose() {
        SHAPE_RENDERER.dispose();
        WIN_RUNNING_KNIGT_SPRITE.dispose();
    }

    @Override
    public void create() {

    }
}