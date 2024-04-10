package no.ntnu.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.ntnu.game.Button.Button;
import no.ntnu.game.Views.CreateOrJoinRoomScreen;
import no.ntnu.game.Views.MainMenuScreen;
import no.ntnu.game.Views.PlayerLoginScreen;
import no.ntnu.game.Views.ScreenManager;
import no.ntnu.game.firestore.Player;


public class StarKnight extends Game {
	//	MainMenuScreen mainMenuScreen;
	CreateOrJoinRoomScreen createOrJoinRoomScreen;
	private ShapeRenderer shapeRenderer;
	private Button leftArrowButton;
	private Button createRoomButton;
	private Button joinRoomButton;
	private SpriteBatch spriteBatch;
	private static FirebaseInterface _FI;
	private ScreenManager gvm;

	public StarKnight(FirebaseInterface FI) { _FI = FI; }
	// private TreeWithPowerUp tree;
	@Override
	public void create () {
		gvm = new ScreenManager();
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();

		gvm.push(new PlayerLoginScreen(gvm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gvm.update(Gdx.graphics.getDeltaTime()); // delta time is the time diff between one frame rendered and next frame rendered
		gvm.render(spriteBatch);

	}

	@Override
	public void dispose () {
		super.dispose();
		spriteBatch.dispose();
	}

	public static FirebaseInterface getFirebaseInterface() {
		if (_FI != null) {
			return _FI;
		} else {
			throw new NullPointerException("Firebaseinterface is not present");
		}
	}
}