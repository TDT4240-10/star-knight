package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import no.ntnu.game.Models.Settings;
import no.ntnu.game.factory.button.RectangleButtonFactory;

/**
 * Settings View class to render settings screen
 *
 * @author Han
 */
public class SettingsScreen extends Screen {
    private Texture logo;
    private Texture musicText;
    private Texture soundText;
    BitmapFont font; // Declare the font variable

    private Button exitButton;

    private ShapeRenderer shapeRenderer;
    // private SpriteBatch spriteBatch;

    private Stage stage;

    private Skin skin;

    private Slider musicSlider;

    private Slider soundSlider;

    private Settings settings;

    public SettingsScreen(ScreenManager gvm) {
        super(gvm);
        logo = new Texture("settings.png");
        musicText = new Texture("music.png");
        soundText = new Texture("sound.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        shapeRenderer = new ShapeRenderer();
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        // spriteBatch = new SpriteBatch();

        stage = new Stage();

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        float sliderKnobHeight = 100.0f;

        musicSlider = new Slider(0.0f, 1.0f, 0.1f, false, skin);

        // Only need to change for one slider since they share the same knob
        musicSlider.getStyle().knob.setMinHeight(sliderKnobHeight);

        soundSlider = new Slider(0.0f, 1.0f, 0.1f, false, skin);

        // Position of sliders
        musicSlider.setPosition(300, 1050);
        soundSlider.setPosition(300, 700);

        // Width of sliders
        musicSlider.setWidth(500);
        soundSlider.setWidth(500);
        settings = Settings.getInstance();

        musicSlider.setValue(settings.getMusic());
        soundSlider.setValue(settings.getSound());

        // The addListeners methods are AI generated code that has been somewhat
        // modified
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.setMusic(musicSlider.getValue());
            }
        });
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.setSound(soundSlider.getValue());
            }
        });

        exitButton = rectButtonFactory.createButton("Exit", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gvm.set(new MainMenuScreen(gvm));
                return true; // Indicate that the touch event is handled
            }
        });
        exitButton.setSize(350, 200); // Set the size of the button
        exitButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);

        stage.addActor(musicSlider);
        stage.addActor(soundSlider);
        stage.addActor(exitButton);

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

        float musicWidth = musicText.getWidth();
        float musicHeight = musicText.getHeight();
        float musicX = (screenWidth - musicWidth) / 2;
        float musicY = (screenHeight) / 2 - musicHeight / 2;
        ;

        float soundWidth = soundText.getWidth();
        float soundHeight = soundText.getHeight();
        float soundX = (screenWidth - soundWidth) / 2;
        float soundY = ((screenHeight) / 2.75f) - soundHeight / 2;
        ;

        sb.begin();
        sb.draw(logo, logoX, logoY);
        sb.draw(musicText, musicX, musicY);
        sb.draw(soundText, soundX, soundY);
        sb.end();

        // draw stage and music slider and sound effects slider
        stage.act();
        stage.draw();
    }

    @Override
    protected void handleInput() {
        // if play button is pressed, go to CreateOrJoinRoomScreen
        // if(playButton.isPressed()){
        // gvm.set(new CreateOrJoinRoomScreen(gvm));
        // }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        stage.dispose(); // Dispose of the stage
    }

    @Override
    public void create() {

    }
}