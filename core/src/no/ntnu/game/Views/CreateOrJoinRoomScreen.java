package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.Controllers.PlayerController;
import no.ntnu.game.Factory.Button.RectangleButtonFactory;
import no.ntnu.game.Factory.Textfield.TextFieldFactory;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Create or join room screen, linked with firestore to allow user to create or
 * join a room
 *
 * @author Deen
 */

public class CreateOrJoinRoomScreen extends Screen {
    private final Stage STAGE;
    private final TextField ROOM_ID;
    private final Texture LOGO;
    private final BitmapFont FONT; // Declare the font variable
    private final ShapeRenderer SHAPE_RENDERER;
    private final PlayerController PLAYER_CONTROLLER;
    private final GameRoomController GAME_ROOM_CONTROLLER;

    public CreateOrJoinRoomScreen(ScreenManager gvm) {
        super(gvm);

        PLAYER_CONTROLLER = PlayerController.getPlayerController();
        GAME_ROOM_CONTROLLER = GameRoomController.getInstance();

        LOGO = new Texture("starknight_logo.png");
        FONT = new BitmapFont(); // Load the font
        FONT.getData().setScale(3); // Set the font scale to 2 for double size
        SHAPE_RENDERER = new ShapeRenderer();
        RectangleButtonFactory rectButtonFactory = new RectangleButtonFactory();
        TextFieldFactory textFieldFactory = new TextFieldFactory();

        ROOM_ID = textFieldFactory.createTextfield("");
        ROOM_ID.setSize(400, 200);
        ROOM_ID.setPosition((float) (Gdx.graphics.getWidth() / 2) - 200, 1050);

        // Indicate that the touch event is handled
        Button joinRoomButton = rectButtonFactory.createButton("Join", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GAME_ROOM_CONTROLLER.joinGameRoom(PLAYER_CONTROLLER.getPlayer(), ROOM_ID.getText().toUpperCase(),
                        result -> {
                            if (result != null) {
                                Gdx.app.postRunnable(() -> gvm.set(new CreateGameLobbyScreen(gvm)));
                            }
                        });
                return true; // Indicate that the touch event is handled
            }
        });

        joinRoomButton.setSize(350, 200); // Set the size of the button
        joinRoomButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 550);
        Button createRoomButton = rectButtonFactory.createButton("Create", new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GAME_ROOM_CONTROLLER.createOnlineRoom(PLAYER_CONTROLLER.getPlayer(), result -> {
                    if (result != null) {
                        Gdx.app.postRunnable(() -> gvm.set(new CreateGameLobbyScreen(gvm)));
                    }

                });
                return true;
            }
        });

        createRoomButton.setSize(350, 200); // Set the size of the button
        createRoomButton.setPosition((float) Gdx.graphics.getWidth() / 2 - 175, 300);

        STAGE = new Stage();
        STAGE.addActor(ROOM_ID);
        STAGE.addActor(joinRoomButton);
        STAGE.addActor(createRoomButton);

        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(STAGE); // Add stage first to ensure it receives input first
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(SpriteBatch sb) {
        // to center the words
        GlyphLayout layout = new GlyphLayout();
        layout.setText(FONT, "Enter a 4 letters unique room ID!");
        float textWidth = layout.width;
        final float CENTER_WORDS_X = (Gdx.graphics.getWidth() - textWidth) / 2;

        sb.begin();
        float logoWidth = LOGO.getWidth();
        float logoHeight = LOGO.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top
        sb.draw(LOGO, logoX, logoY);

        FONT.draw(sb, "Enter a 4 letters unique room ID!", CENTER_WORDS_X, 1000);
        sb.end();

        // draw stage and text field
        STAGE.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
        LOGO.dispose();
        STAGE.dispose();
    }

    @Override
    public void create() {
    }
}
