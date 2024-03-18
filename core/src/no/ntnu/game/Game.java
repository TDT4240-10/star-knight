package no.ntnu.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import no.ntnu.game.Button.Button;
import no.ntnu.game.Button.ButtonFactory;


public class Game extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;
	private Button leftArrowButton;
	private SpriteBatch spriteBatch;

	FirebaseInterface _FI;

	public Game(FirebaseInterface FI) { _FI = FI; }
	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		// Create a left arrow button using the factory method
		leftArrowButton = ButtonFactory.createLeftArrowButton(1900, 200);

		// TODO: Initialize Database references
		// _FI.SomeFunction();


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Render the left arrow button
		leftArrowButton.render(shapeRenderer ,  spriteBatch);

		shapeRenderer.end();

		// Handle input
		if (leftArrowButton.isPressed(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
			System.out.println("Left arrow button pressed!");
		}

	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();

	}
}
