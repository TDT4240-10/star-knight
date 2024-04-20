package no.ntnu.game;

import no.ntnu.game.Callback.FirebaseCallback;
import no.ntnu.game.Firestore.GameRoom;
import no.ntnu.game.Firestore.Player;

/**
 * CoreFirebase is a class that will be overridden by the AndroidFirebase class.
 * It should only implement the methods that are common to all platforms,
 * but not contain any logic.
 * 
 * @author Magnus Byrkjeland
 */
public class CoreFirebase implements FirebaseInterface {

    @Override
    public void savePlayer(Player player) {

    }

    @Override
    public void getPlayer(String username, FirebaseCallback<Player> callback) {

    }

    @Override
    public void getRoomByCode(String code, FirebaseCallback<GameRoom> callback) {

    }

    @Override
    public void saveRoom(GameRoom room, FirebaseCallback<GameRoom> callback) {

    }

    @Override
    public void createRoomListener(GameRoom room, FirebaseCallback<GameRoom> callback) {

    }
}
