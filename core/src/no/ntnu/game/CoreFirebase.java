package no.ntnu.game;

import no.ntnu.game.firestore.Player;

/**
 * CoreFirebase is a class that will be overridden by the AndroidFirebase class.
 * It should only implement the methods that are common to all platforms,
 * but not contain any logic.
 * 
 * @author Magnus Byrkjeland
 */
public class CoreFirebase implements FirebaseInterface {

    @Override
    public void SomeFunction() {
    }

    @Override
    public String SerializeClass(FirebaseClass object) {
        return null;
    }

    @Override
    public void getPlayer(String username, PlayerCallback callback) {

    }

    @Override
    public void joinRoom(String code, Player player, JoinRoomCallback callback) {

    }

}
