package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import no.ntnu.game.Views.Sprites.LoseDeadKnightSprite;
import no.ntnu.game.factory.button.RectangleButtonFactory;

/**
 * End Game Screen View class to render Lose screen
 *
 * @author Han
 */
public class LastKnightEndGameScreen extends Screen {
    private final Texture logo;
    private final BitmapFont font; // Declare the font variable
    private final ShapeRenderer shapeRenderer;
    private final LoseDeadKnightSprite loseDeadKnightSprite;
    private final float knightX, knightY;
    private final int player_score;
    private final Stage stage;

    public LastKnightEndGameScreen(ScreenManager gvm, int player_score) {
        super(gvm);
        logo = new Texture("your_score.png");
        stage = new Stage();
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();

        loseDeadKnightSprite = new LoseDeadKnightSprite();

        knightX = 300;
        knightY = 900;
        this.player_score = player_score;

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

    private float calculateCenterX(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float textWidth = layout.width;
        return (Gdx.graphics.getWidth() - textWidth) / 2;
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
        font.draw(sb, String.valueOf(player_score), calculateCenterX(String.valueOf(player_score), font), logoY - 100);

        sb.end();

        // to ensure the knight is moving at the same speed on all devices
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);

        loseDeadKnightSprite.setPosition(knightX, knightY);
        loseDeadKnightSprite.render(sb);

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
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    @Override
    public void create() {

    }
}