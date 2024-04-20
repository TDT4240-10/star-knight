package no.ntnu.game;

import no.ntnu.game.Callback.FirebaseCallback;
import no.ntnu.game.Firestore.GameRoom;
import no.ntnu.game.Firestore.Player;

/**
 * Interface for Firebase Database Interaction
 *
 * @author Magnus Byrkjeland
 */
public interface FirebaseInterface {

    void savePlayer(Player player);

    void getPlayer(String username, FirebaseCallback<Player> callback);

    void getRoomByCode(String code, FirebaseCallback<GameRoom> callback);

    void saveRoom(GameRoom room, FirebaseCallback<GameRoom> callback);

    void createRoomListener(GameRoom room, FirebaseCallback<GameRoom> callback);

}
