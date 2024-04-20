package no.ntnu.game;

import no.ntnu.game.callback.FirebaseCallback;
import no.ntnu.game.firestore.GameRoom;
import no.ntnu.game.firestore.Player;

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
