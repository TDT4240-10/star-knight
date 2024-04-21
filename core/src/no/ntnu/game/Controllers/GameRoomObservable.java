package no.ntnu.game.Controllers;

import no.ntnu.game.firestore.GameRoom.GameStatus;

public interface GameRoomObservable {

    void setGameStatus(GameStatus gameStatus);

    GameStatus getGameStatus();

    void addObserver(GameRoomObserver observer);

    void removeObserver(GameRoomObserver observer);
}
