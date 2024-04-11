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
    };

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
}
