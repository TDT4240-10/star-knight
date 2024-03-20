package no.ntnu.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;
import no.ntnu.game.Button.ButtonInputListener;
import no.ntnu.game.Views.CreateOrJoinRoomScreen;
import no.ntnu.game.Views.GameViewManager;
import no.ntnu.game.Views.TempMainMenu;


public class StarKnight extends Game {
//	MainMenuScreen mainMenuScreen;
	CreateOrJoinRoomScreen createOrJoinRoomScreen;
	private ShapeRenderer shapeRenderer;
	private Button leftArrowButton;
	private Button createRoomButton;
	private Button joinRoomButton;
	private SpriteBatch spriteBatch;
	private Button playButton;
	FirebaseInterface _FI;
	private GameViewManager gvm;

	public StarKnight(FirebaseInterface FI) { _FI = FI; }

	@Override
	public void create () {
		gvm = new GameViewManager();
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		// Create a left arrow button using the factory method this is where u specify the direction
//		leftArrowButton = ButtonFactory.createLeftArrowButton(200, 200);
//		menubutton = ButtonFactory.createMenuButton(300,700,"Play");

//		joinRoomButton = ButtonFactory.createJoinRoomButton(300, 600);
//		createRoomButton = ButtonFactory.createCreateRoomButton(300, 300);


//		// Create input listeners for buttons
//		ButtonInputListener leftArrowInputListener = new ButtonInputListener(leftArrowButton);
//		ButtonInputListener menuInputListener = new ButtonInputListener(menubutton);
//		// Set input processors
//		InputMultiplexer inputMultiplexer = new InputMultiplexer();
//		inputMultiplexer.addProcessor(leftArrowInputListener);
//		inputMultiplexer.addProcessor(menuInputListener);
//		Gdx.input.setInputProcessor(inputMultiplexer);

		// TODO: Initialize Database references
		// _FI.SomeFunction();
//		mainMenuScreen = new MainMenuScreen(spriteBatch);
//		setScreen(mainMenuScreen);



		gvm.push(new TempMainMenu(gvm));	// push the main menu screen to the stack
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gvm.update(Gdx.graphics.getDeltaTime()); // delta time is the time diff between one frame rendered and next frame rendered
		gvm.render(spriteBatch);
		// Render the left arrow button
//		leftArrowButton.render(shapeRenderer ,  spriteBatch);
		// Render the menu button
//		menubutton.render(shapeRenderer,spriteBatch);
	}

	@Override
	public void dispose () {
		super.dispose();

	}
}