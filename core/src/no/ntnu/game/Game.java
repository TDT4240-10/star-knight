package no.ntnu.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import no.ntnu.game.firestore.GameRoom;
import no.ntnu.game.firestore.Player;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	FirebaseInterface _FI;

	public Game(FirebaseInterface FI) { _FI = FI; }
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		Player player = new Player("HOHOHO");
		GameRoom room = new GameRoom(player);
		_FI.joinRoom("PNMJ", player, new JoinRoomCallback() {
			@Override
			public void onCallback(GameRoom room) {
				_FI.SerializeClass(room);
				System.out.println("Joined room");
			}
		});

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
