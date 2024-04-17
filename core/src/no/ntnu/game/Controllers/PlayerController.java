package no.ntnu.game.Controllers;

import no.ntnu.game.FirebaseInterface;
import no.ntnu.game.callback.FirebaseCallback;
import no.ntnu.game.StarKnight;
import no.ntnu.game.firestore.Player;

/**
 * PlayerController is a singleton class for logic related to the Player model
 * that is synced to Firebase.
 * 
 * @author Casper Salminen Andreassen
 */

public class PlayerController {
    private static PlayerController instance;

    private Player player;
    private final FirebaseInterface _FI;

    private PlayerController() {
        _FI = StarKnight.getFirebaseInterface();
    }

    public static PlayerController getPlayerController() {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void signInPlayer(String username, FirebaseCallback<Player> playerCallback) {
        _FI.getPlayer(username, maybePlayer -> {
            if (maybePlayer == null) {
                Player newPlayer = new Player(username);
                _FI.savePlayer(newPlayer);
                player = newPlayer;
            } else {
                player = maybePlayer;
            }
            playerCallback.onCallback(player);
        });
    }
}
