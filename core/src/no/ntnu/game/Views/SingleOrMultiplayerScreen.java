package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;

/**
 * Main Menu View class to render main menu screen
 *
 * @author Han
 */
public class SingleOrMultiplayerScreen extends Screen {
    private Texture logo;
    BitmapFont font; // Declare the font variable

    private Button singleplayerButton;

    private Button multiplayerButton;
    private Button exitButton;

    private ShapeRenderer shapeRenderer;
    //    private SpriteBatch spriteBatch;
    public SingleOrMultiplayerScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
//        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render(SpriteBatch sb) {

//        singlePlayerButton = ButtonFactory.createPlayButton(300,1100);
//        multiPlayerButton = ButtonFactory.createTutorialButton(300,800);
//        exitButton = ButtonFactory.createRectSettingsButton(300,500);

        final float CENTER_BUTTON_X = 0.5f * Gdx.graphics.getWidth() - 150;
        singleplayerButton = ButtonFactory.createMenuButton(CENTER_BUTTON_X,900, "Solo");
        multiplayerButton = ButtonFactory.createMenuButton(CENTER_BUTTON_X,600, "Online");
        exitButton = ButtonFactory.createExitButton(CENTER_BUTTON_X,300);

        // Create input listeners for buttons
        ButtonInputListener singleplayerInputListener = new ButtonInputListener(singleplayerButton, gvm, null, sb);
        ButtonInputListener multiplayerInputListener = new ButtonInputListener(multiplayerButton, gvm, null, sb);
        ButtonInputListener exitInputListener = new ButtonInputListener(exitButton, gvm, null, sb);


        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(singleplayerInputListener);
        inputMultiplexer.addProcessor(multiplayerInputListener);
        inputMultiplexer.addProcessor(exitInputListener);

        Gdx.input.setInputProcessor(inputMultiplexer);

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
        sb.end();

        // Render the menu button
        singleplayerButton.render(shapeRenderer,sb);
        multiplayerButton.render(shapeRenderer,sb);
        exitButton.render(shapeRenderer,sb);

        shapeRenderer.end();
    }

    @Override
    protected void handleInput() {
        // if play button is pressed, go to CreateOrJoinRoomScreen
//        if(playButton.isPressed()){
//            gvm.set(new CreateOrJoinRoomScreen(gvm));
//        }
    }

    @Override
    public void update(float dt) {

    }
    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}