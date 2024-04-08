package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;
import no.ntnu.game.Controllers.SettingsController;

/**
 * Settings View class to render settings screen
 *
 * @author Han
 */
public class SettingsScreen extends Screen {
    private Texture logo;
    BitmapFont font; // Declare the font variable

    private Button exitButton;

    private ShapeRenderer shapeRenderer;
    //    private SpriteBatch spriteBatch;

    private Stage stage;

    private Skin skin;

    private Slider musicSlider;

    private Slider soundSlider;

    private SettingsController settingsController;

    public SettingsScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("settings.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
//        spriteBatch = new SpriteBatch();

        stage = new Stage();

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        float sliderKnobHeight = 100.0f;

        musicSlider = new Slider(0.0f, 1.0f, 0.1f, false, skin);

        // Only need to change for one slider since they share the same knob
        musicSlider.getStyle().knob.setMinHeight(sliderKnobHeight);

        soundSlider = new Slider(0.0f, 1.0f, 0.1f, false, skin);

        // Position of sliders
        musicSlider.setPosition(300, 1050);
        soundSlider.setPosition(300, 850);

        // Width of sliders
        musicSlider.setWidth(500);
        soundSlider.setWidth(500);
        settingsController = new SettingsController();

        musicSlider.setValue(settingsController.getMusic());
        soundSlider.setValue(settingsController.getSound());

        // The addListeners methods are AI generated code that has been somewhat modified
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settingsController.setMusic(musicSlider.getValue());
            }
        });
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settingsController.setSound(soundSlider.getValue());
            }
        });

        stage.addActor(musicSlider);
        stage.addActor(soundSlider);
    }

    @Override
    public void render(SpriteBatch sb) {
        final float CENTER_BUTTON_X = 0.5f * Gdx.graphics.getWidth() - 150;
        exitButton = ButtonFactory.createExitButton(CENTER_BUTTON_X,900);

        // Create input listeners for buttons
        ButtonInputListener exitInputListener = new ButtonInputListener(exitButton, gvm, null, sb);
        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add stage first to ensure it receives input first

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
        exitButton.render(shapeRenderer,sb);

        shapeRenderer.end();

        // draw stage and music slider and sound effects slider
        stage.act();
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
        stage.dispose(); // Dispose of the stage
    }
}