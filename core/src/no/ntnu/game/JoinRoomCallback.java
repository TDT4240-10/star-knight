package no.ntnu.game;

import no.ntnu.game.firestore.GameRoom;

public interface JoinRoomCallback {
    void onCallback(GameRoom room);
}
