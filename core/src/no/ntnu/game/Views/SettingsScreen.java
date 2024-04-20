package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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

import no.ntnu.game.Settings.Settings;
import no.ntnu.game.StarKnight;
import no.ntnu.game.Factory.button.RectangleButtonFactory;

/**
 * Settings View class to render settings screen
 *
 * @author Han
 */
public class SettingsScreen extends Screen {
    private final Texture LOGO;
    private final Texture MUSIC_TEXT;
    private final Texture SOUND_TEXT;

    private final ShapeRenderer SHAPE_RENDERER;
    private final Stage STAGE;
    private final Slider MUSIC_SLIDER;
    private final Slider SOUND_SLIDER;
    private final Settings SETTINGS;

    public SettingsScreen(ScreenManager gvm) {
        super(gvm);
        LOGO = new Texture("settings.png");
        MUSIC_TEXT = new Texture("music.png");
        SOUND_TEXT = new Texture("sound.png");
        // Declare the font variable
        BitmapFont font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
        SHAPE_RENDERER = new ShapeRenderer();
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();

        STAGE = new Stage();

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        float sliderKnobHeight = 100.0f;

        MUSIC_SLIDER = new Slider(0.0f, 1.0f, 0.1f, false, skin);

        // Only need to change for one slider since they share the same knob
        MUSIC_SLIDER.getStyle().knob.setMinHeight(sliderKnobHeight);

        SOUND_SLIDER = new Slider(0.0f, 1.0f, 0.1f, false, skin);

        // Position of sliders
        MUSIC_SLIDER.setPosition(300, 1050);
        SOUND_SLIDER.setPosition(300, 700);

        // Width of sliders
        MUSIC_SLIDER.setWidth(500);
        SOUND_SLIDER.setWidth(500);
        SETTINGS = StarKnight.getSettings();

        MUSIC_SLIDER.setValue(SETTINGS.getMusicVolume());
        SOUND_SLIDER.setValue(SETTINGS.getEffectVolume());

        // The addListeners methods are AI generated code that has been somewhat
        // modified
        MUSIC_SLIDER.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SETTINGS.setMusicVolume(MUSIC_SLIDER.getValue());
            }
        });
        SOUND_SLIDER.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SETTINGS.setEffectVolume(SOUND_SLIDER.getValue());
            }
        });

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

        STAGE.addActor(MUSIC_SLIDER);
        STAGE.addActor(SOUND_SLIDER);
        STAGE.addActor(exitButton);

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

        float musicWidth = MUSIC_TEXT.getWidth();
        float musicHeight = MUSIC_TEXT.getHeight();
        float musicX = (screenWidth - musicWidth) / 2;
        float musicY = (screenHeight) / 2 - musicHeight / 2;

        float soundWidth = SOUND_TEXT.getWidth();
        float soundHeight = SOUND_TEXT.getHeight();
        float soundX = (screenWidth - soundWidth) / 2;
        float soundY = ((screenHeight) / 2.75f) - soundHeight / 2;

        sb.begin();
        sb.draw(LOGO, logoX, logoY);
        sb.draw(MUSIC_TEXT, musicX, musicY);
        sb.draw(SOUND_TEXT, soundX, soundY);
        sb.end();

        // draw stage and music slider and sound effects slider
        STAGE.act();
        STAGE.draw();
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        SHAPE_RENDERER.dispose();
        STAGE.dispose(); // Dispose of the stage
    }

    @Override
    public void create() {

    }
}