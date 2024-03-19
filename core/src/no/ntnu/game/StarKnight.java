package no.ntnu.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;
import no.ntnu.game.View.MainMenuScreen;
import com.badlogic.gdx.InputMultiplexer;


public class StarKnight extends Game {
	MainMenuScreen mainMenuScreen;
	private ShapeRenderer shapeRenderer;
	private Button leftArrowButton;
	private SpriteBatch spriteBatch;
	private Button menubutton;
	FirebaseInterface _FI;

	public StarKnight(FirebaseInterface FI) { _FI = FI; }
	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		// Create a left arrow button using the factory method this is where u specific the direction
		leftArrowButton = ButtonFactory.createLeftArrowButton(200, 200);
		menubutton = ButtonFactory.createMenuButton(300,1400,"Play");

		// Create input listeners for buttons
		ButtonInputListener leftArrowInputListener = new ButtonInputListener(leftArrowButton);
		ButtonInputListener menuInputListener = new ButtonInputListener(menubutton);
		// Set input processors
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(leftArrowInputListener);
		inputMultiplexer.addProcessor(menuInputListener);
		Gdx.input.setInputProcessor(inputMultiplexer);

		// TODO: Initialize Database references
		// _FI.SomeFunction();
		mainMenuScreen = new MainMenuScreen(spriteBatch);
		setScreen(mainMenuScreen);


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render the left arrow button
		leftArrowButton.render(shapeRenderer ,  spriteBatch);
		// Render the menu button
		menubutton.render(shapeRenderer,spriteBatch);
		shapeRenderer.end();


	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();

	}
}
