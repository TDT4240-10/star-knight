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

import no.ntnu.game.factory.button.RectangleButtonFactory;

//import no.ntnu.game.Button.Button;
//import no.ntnu.game.Button.ButtonFactory;
//import no.ntnu.game.Button.ButtonInputListener;

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
    private Stage stage;


    private ShapeRenderer shapeRenderer;
    //    private SpriteBatch spriteBatch;
    public SingleOrMultiplayerScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
//        spriteBatch = new SpriteBatch();

        // Create buttons
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        singleplayerButton = rectButtonFactory.createButton("Solo", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new SinglePlayerChooseGameModeScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        singleplayerButton.setSize(350, 200); // Set the size of the button
        singleplayerButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 800);

        multiplayerButton = rectButtonFactory.createButton("Online", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.push(new CreateOrJoinRoomScreen(gvm));
                return true;
            }
        });

        multiplayerButton.setSize(350, 200); // Set the size of the button
        multiplayerButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 550);

        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true;
            }
        });

        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);


        // Create the stage for the buttons
        stage = new Stage();
        stage.addActor(singleplayerButton);
        stage.addActor(multiplayerButton);
        stage.addActor(exitButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);// Add stage first to ensure it receives input first

    }

    @Override
    public void render(SpriteBatch sb) {

//        singlePlayerButton = ButtonFactory.createPlayButton(300,1100);
//        multiPlayerButton = ButtonFactory.createTutorialButton(300,800);
//        exitButton = ButtonFactory.createRectSettingsButton(300,500);

//        final float CENTER_BUTTON_X = 0.5f * Gdx.graphics.getWidth() - 150;
//        singleplayerButton = ButtonFactory.createMenuButton(CENTER_BUTTON_X,900, "Solo");
//        multiplayerButton = ButtonFactory.createMenuButton(CENTER_BUTTON_X,600, "Online");
//        exitButton = ButtonFactory.createExitButton(CENTER_BUTTON_X,300);
//
//        // Create input listeners for buttons
//        ButtonInputListener singleplayerInputListener = new ButtonInputListener(singleplayerButton, gvm, null, sb);
//        ButtonInputListener multiplayerInputListener = new ButtonInputListener(multiplayerButton, gvm, null, sb);
//        ButtonInputListener exitInputListener = new ButtonInputListener(exitButton, gvm, null, sb);
//
//
//        // Set input processors
//        InputMultiplexer inputMultiplexer = new InputMultiplexer();
//
//        inputMultiplexer.addProcessor(singleplayerInputListener);
//        inputMultiplexer.addProcessor(multiplayerInputListener);
//        inputMultiplexer.addProcessor(exitInputListener);
//
//        Gdx.input.setInputProcessor(inputMultiplexer);

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
//        singleplayerButton.render(shapeRenderer,sb);
//        multiplayerButton.render(shapeRenderer,sb);
//        exitButton.render(shapeRenderer,sb);

        shapeRenderer.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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