package no.ntnu.game.Controllers;

import no.ntnu.game.FirebaseInterface;
import no.ntnu.game.StarKnight;
import no.ntnu.game.callback.FirebaseCallback;
import no.ntnu.game.firestore.GameRoom;
import no.ntnu.game.firestore.Player;

public class GameRoomController {


    private static GameRoomController instance;
    private GameRoom room;



    private final FirebaseInterface fi;

    private GameRoomController() {
        fi = StarKnight.getFirebaseInterface();
    }

    ;

    public static GameRoomController getInstance() {
        if (instance == null) {
            instance = new GameRoomController();
        }
        return instance;
    }

    private void createRoomListener() {
        fi.createRoomListener(room, new FirebaseCallback<GameRoom>() {
            @Override
            public void onCallback(GameRoom result) {
                if (result != null) {
                    room = result;
                }
            }
        });
    }

    public void joinGameRoom(Player player, String code, FirebaseCallback<GameRoom> callback) {
        fi.getRoomByCode(code, new FirebaseCallback<GameRoom>() {
            @Override
            public void onCallback(GameRoom result) {
                if (result != null) {
                    result.addPlayer(player);
                    result.setGameStatus(GameRoom.GameStatus.LOBBY);
                    room = result;
                    fi.saveRoom(result, new FirebaseCallback<GameRoom>() {
                        @Override
                        public void onCallback(GameRoom result) {
                            if (result != null) {
                                createRoomListener();
                                callback.onCallback(room);
                            }
                        }
                    });
                }
            }
        });
    }

    public void createGameRoom(Player player, FirebaseCallback<GameRoom> callback) {
        GameRoom newRoom = new GameRoom(player);
        fi.saveRoom(newRoom, new FirebaseCallback<GameRoom>() {
            @Override
            public void onCallback(GameRoom result) {
                room = result;
                createRoomListener();
                callback.onCallback(result);
            }
        });
    }

    public GameRoom getGameRoom() {
        return this.room;
    }

    public void setGameMode(GameRoom.GameMode gameMode) {
        this.room.setGameMode(gameMode);
        fi.saveRoom(room, new FirebaseCallback<GameRoom>() {
            @Override
            public void onCallback(GameRoom result) {

            }
        });
    }

    public void setGameStatus(GameRoom.GameStatus status) {
        this.room.setGameStatus(status);
        fi.saveRoom(room, new FirebaseCallback<GameRoom>() {
            @Override
            public void onCallback(GameRoom result) {

            }
        });
    }

    // Method to get the current game mode
    public GameRoom.GameMode getCurrentGameMode() {
        return this.room.getGameMode();
    }

    // Utility methods for checking the active game mode
    public boolean isFastestKnightMode() {
        return this.room.getGameMode() == GameRoom.GameMode.FASTEST_KNIGHT;
    }

    public boolean isLastKnightMode() {
        return this.room.getGameMode() == GameRoom.GameMode.LAST_KNIGHT;

    }
}
