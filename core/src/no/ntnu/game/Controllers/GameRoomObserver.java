package no.ntnu.game.Controllers;

import no.ntnu.game.firestore.GameRoom;

public interface GameRoomObserver {
    void gameStatusChanged(GameRoom gameRoom);
}
