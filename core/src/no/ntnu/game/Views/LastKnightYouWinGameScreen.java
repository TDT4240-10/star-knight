package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
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
 * End Game Screen View class to render Win screen
 *
 * @author Deen
 */
public class LastKnightYouWinGameScreen extends Screen {
    private Texture logo;
    BitmapFont font; // Declare the font variable

    private Button exitButton;
    private Stage stage;

    private ShapeRenderer shapeRenderer;
    private WinRunningKnightSprite winRunningKnightSprite;
    private float knightX, knightY;
    private float knightSpeed = 300; // Pixels per second
    //    private SpriteBatch spriteBatch;
    private int player_score;
    public LastKnightYouWinGameScreen(ScreenManager gvm, int player_score) {
        super(gvm);
        logo = new Texture("win.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();

        winRunningKnightSprite = new WinRunningKnightSprite();

        knightX = 0;
        knightY = 900;
        this.player_score = player_score;

        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);

        stage = new Stage();
        stage.addActor(exitButton);


        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add stage first to ensure it receives input first
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render(SpriteBatch sb) {

        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top

        sb.begin();
        sb.draw(logo, logoX, logoY);

        font.getData().setScale(10f);
        float x = (Gdx.graphics.getWidth() - font.getXHeight()) / 2; // Assuming average glyph width
        float y = logoY - 100; // Center vertically
        font.draw(sb, String.valueOf(player_score), x, y);

        sb.end();

        // to ensure the knight is moving at the same speed on all devices
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);

        winRunningKnightSprite.setPosition(knightX, knightY);
        winRunningKnightSprite.render(sb);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        knightX += knightSpeed * dt;
        if (knightX > Gdx.graphics.getWidth()) {
            knightX = -winRunningKnightSprite.getWidth();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        winRunningKnightSprite.dispose();
    }

    @Override
    public void create(){

    }
}