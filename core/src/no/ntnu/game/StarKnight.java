package no.ntnu.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.game.view.MainMenuScreen;

import com.badlogic.gdx.Game;

public class StarKnight extends Game {
	MainMenuScreen mainMenuScreen;

	SpriteBatch batch;
	Texture img;
	FirebaseInterface _FI;

	public StarKnight(FirebaseInterface FI) { _FI = FI; }
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		mainMenuScreen = new MainMenuScreen(batch);
		setScreen(mainMenuScreen);


		// TODO: Initialize Database references
		// _FI.SomeFunction();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
