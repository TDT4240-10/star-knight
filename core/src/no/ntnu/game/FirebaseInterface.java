package no.ntnu.game;

import no.ntnu.game.firestore.Player;

/**
 * Interface for Firebase Database Interaction
 *
 * @author Magnus Byrkjeland
 */
public interface FirebaseInterface {

    // TODO: Remove Test Function
    void SomeFunction();

    String SerializeClass(FirebaseClass object);

    void getPlayer(String username, PlayerCallback callback);

    void joinRoom(String code, Player player, JoinRoomCallback callback);



}

