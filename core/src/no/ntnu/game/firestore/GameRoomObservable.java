package no.ntnu.game.Firestore;

import no.ntnu.game.Firestore.GameRoom.GameStatus;

public interface GameRoomObservable {

    void setGameStatus(GameStatus gameStatus);

    GameStatus getGameStatus();

    void addObserver(GameRoomObserver observer);

    void removeObserver(GameRoomObserver observer);
}
