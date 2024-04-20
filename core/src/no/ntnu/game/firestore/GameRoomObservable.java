package no.ntnu.game.firestore;

import no.ntnu.game.firestore.GameRoom.GameStatus;

public interface GameRoomObservable {

    void setGameStatus(GameStatus gameStatus);

    GameStatus getGameStatus();

    void addObserver(GameRoomObserver observer);

    void removeObserver(GameRoomObserver observer);
}
