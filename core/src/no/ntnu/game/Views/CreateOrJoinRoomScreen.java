package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;

public class CreateOrJoinRoomScreen extends View{
    private SpriteBatch batch;
//    private ShapeRenderer shapeRenderer;

    private Texture logo;

    BitmapFont font; // Declare the font variable
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private Button createRoomButton;
    private Button joinRoomButton;

    // this is the constructor for the CreateGameScreen class, a user will come to this screen either make a new room or join a room.
    // there will be two buttons, one for creating a room and one for joining a room.

    public CreateOrJoinRoomScreen(GameViewManager gvm) {
        super(gvm);
        shapeRenderer = new ShapeRenderer();//
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size

        // create and join room buttons
        createRoomButton = ButtonFactory.createCreateRoomButton(300, 8000);
        joinRoomButton = ButtonFactory.createJoinRoomButton(300, 500);
    }



    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw logo
        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top
        sb.draw(logo, logoX, logoY);

        // render both buttons
        createRoomButton.render(shapeRenderer, spriteBatch);
        joinRoomButton.render(shapeRenderer, spriteBatch);
        shapeRenderer.end();

        sb.end();
    }

    @Override
    protected void handleInput() {
        // if create room button is clicked, go to lobby with auto generated id

        // else, enter a game room id and join a room. if id is not valid, stay on page
    }

    @Override
    public void update(float dt){
        handleInput();
    }

    @Override
    public void dispose() {
//        background.dispose();
        logo.dispose();
        System.out.println("Create or Join Room View Disposed");
    }


}
