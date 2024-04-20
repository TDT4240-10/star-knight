package no.ntnu.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.game.Settings.Settings;
import no.ntnu.game.Views.PlayerLoginScreen;
import no.ntnu.game.Views.ScreenManager;

public class StarKnight extends Game {
	private SpriteBatch spriteBatch;
	private static FirebaseInterface _FI;
	private static Settings SETTINGS;
	private ScreenManager gvm;

	public StarKnight(FirebaseInterface FI) {
		_FI = FI;
		SETTINGS = Settings.getInstance();
	}

	@Override
	public void create() {
		gvm = new ScreenManager();
		spriteBatch = new SpriteBatch();

		gvm.push(new PlayerLoginScreen(gvm));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// delta time is the time diff between one frame rendered and next frame
		// rendered
		gvm.update(Gdx.graphics.getDeltaTime());
		gvm.render(spriteBatch);
	}

	@Override
	public void dispose() {
		super.dispose();
		spriteBatch.dispose();
	}

	public static FirebaseInterface getFirebaseInterface() throws NullPointerException {
		if (_FI != null) {
			return _FI;
		} else {
			throw new NullPointerException("Firebaseinterface is not present");
		}
	}

	public static Settings getSettings() throws NullPointerException {
		if (SETTINGS != null) {
			return SETTINGS;
		} else {
			throw new NullPointerException("Settings instance is not present");
		}
	}
}