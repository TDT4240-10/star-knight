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

public class TempMainMenu extends View {
    private Texture logo;
    BitmapFont font; // Declare the font variable

    private Button menubutton;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    public TempMainMenu(GameViewManager gvm) {
        super(gvm);
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
    }

//    public void create() {
//
//    }


    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        // Create input listeners for buttons
        ButtonInputListener menuInputListener = new ButtonInputListener(menubutton);
        // Set input processors
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(menuInputListener);
        Gdx.input.setInputProcessor(inputMultiplexer);

        menubutton = ButtonFactory.createMenuButton(300,700,"Play");

        sb.begin();

        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top

        sb.draw(logo, logoX, logoY);
        sb.end();

        // Render the menu button
        menubutton.render(shapeRenderer,spriteBatch);
    }

    @Override
    public void dispose() {

    }
}
