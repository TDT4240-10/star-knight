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

import no.ntnu.game.Views.Sprites.WinRunningKnightSprite;
import no.ntnu.game.factory.button.RectangleButtonFactory;

/**
 * End Game Screen View class to render Lose screen
 *
 * @author Han
 */
public class FastestKnightWinGameScreen extends Screen {
    private final Texture logo;
    private final BitmapFont font; // Declare the font variable
    private final ShapeRenderer shapeRenderer;
    private final WinRunningKnightSprite winRunningKnightSprite;
    private final float time_elapsed;
    private final Stage stage;
    private final float knightSpeed = 400; // Pixels per second
    private int KNIGHT_X = 0;
    private final int KNIGHT_Y = 900;

    public FastestKnightWinGameScreen(ScreenManager gvm, float time_elapsed) {
        super(gvm);
        logo = new Texture("you_cleared.png");
        stage = new Stage();
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();

        winRunningKnightSprite = new WinRunningKnightSprite();
        this.time_elapsed = time_elapsed;

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

        stage.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add stage first to ensure it receives input first
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(SpriteBatch sb) {
        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top

        sb.begin();
        sb.draw(logo, logoX, logoY);

        // Calculate the position to center the text on the screen
        font.getData().setScale(10f);
        // center x
        float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2; // Assuming average glyph width
        float y = logoY - 100; // Center vertically
        font.draw(sb, String.valueOf(formatTime(time_elapsed)), x, y);

        sb.end();

        // to ensure the knight is moving at the same speed on all devices
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);

        winRunningKnightSprite.setPosition(KNIGHT_X, KNIGHT_Y);
        winRunningKnightSprite.render(sb);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        shapeRenderer.end();

        // draw stage and text field
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        KNIGHT_X += knightSpeed * dt;
        if (KNIGHT_X > Gdx.graphics.getWidth()) {
            KNIGHT_X = -winRunningKnightSprite.getWidth();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        winRunningKnightSprite.dispose();
    }

    @Override
    public void create() {

    }

    @SuppressWarnings("DefaultLocale")
    private String formatTime(float time) {
        final int hours = (int) (time / 3600);
        final int minutes = (int) ((time % 3600) / 60);
        final int seconds = (int) (time % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}