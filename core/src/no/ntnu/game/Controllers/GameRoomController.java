package no.ntnu.game.Controllers;

import java.util.Calendar;
import java.util.Date;

import no.ntnu.game.FirebaseInterface;
import no.ntnu.game.StarKnight;
import no.ntnu.game.callback.FirebaseCallback;
import no.ntnu.game.firestore.GameRoom;
import no.ntnu.game.firestore.Player;

public class GameRoomController {
    private static GameRoomController instance;
    private GameRoom room;

    public enum Actor {
        JOINING, CREATING
    }

    public enum RoomType {
        SOLO, ONLINE
    }

    private RoomType roomType;
    private Actor roomActor;



    private final FirebaseInterface fi;

    private GameRoomController() {
        fi = StarKnight.getFirebaseInterface();
    };

    public Actor getRoomActor() {
        return this.roomActor;
    }

    public RoomType getRoomType() {
        return this.roomType;
    }

    public void setRoomType(RoomType type) {
        this.roomType = type;
    }

    public static GameRoomController getInstance() {
        if (instance == null) {
            instance = new GameRoomController();
        }
        return instance;
    }

    private void stateChanged() {
         // if (roomType.equals(RoomType.SOLO)) return;
        fi.saveRoom(room, new FirebaseCallback<GameRoom>() {
            @Override
            public void onCallback(GameRoom result) {
                System.out.println("Updated room");
            }
        });
    }

    public long getGameStartCountdown() {
        if (this.room.getGameStartTime() != null) {
            long diffMilli = this.room.getGameStartTime().getTime() - new Date().getTime();
            return diffMilli / 1000;
        }
        return 0;
    }

    public void startGame() {
        this.room.setGameStatus(GameRoom.GameStatus.STARTING);

        // Set the start time of the game 10 seconds from now.
        Calendar gameStartTime = Calendar.getInstance();
        gameStartTime.add(Calendar.SECOND, 10);
        this.room.setGameStartTime(gameStartTime.getTime());

        stateChanged();
    }

    public void createSoloRoom(Player player) {
        createGameRoom(player, new FirebaseCallback<GameRoom>() {
            @Override
            public void onCallback(GameRoom result) {
                roomType = RoomType.SOLO;
            }
        });
    }

    public void createOnlineRoom(Player player, FirebaseCallback<GameRoom> callback) {
        roomType = RoomType.ONLINE;
        createGameRoom(player, new FirebaseCallback<GameRoom>() {
            @Override
            public void onCallback(GameRoom result) {
                if (result == null) { return; }
                createRoomListener();
                callback.onCallback(result);
            }
        });
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
        roomType = RoomType.ONLINE;
        fi.getRoomByCode(code, new FirebaseCallback<GameRoom>() {
            @Override
            public void onCallback(GameRoom result) {
                if (result != null) {
                    result.addJoiningPlayer(player);
                    roomActor = Actor.JOINING;
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
                if (result == null) {
                    callback.onCallback(null);
                    return;
                }
                room = result;
                roomActor = Actor.CREATING;
                callback.onCallback(result);
            }
        });
    }

    public void createSoloRoom() {
        this.room = new GameRoom();
        this.roomType = RoomType.SOLO;
    }

    public GameRoom getGameRoom() {
        return this.room;
    }

    public void setGameMode(GameRoom.GameMode gameMode) {
        if (this.room == null) return;
        this.room.setGameMode(gameMode);
        stateChanged();
    }

    public void resetGameMode() {
        if (this.room != null) {
            this.room.setGameMode(GameRoom.GameMode.NONE);
        }
    }

    public void setGameStatus(GameRoom.GameStatus status) {
        this.room.setGameStatus(status);
        stateChanged();
    }

    public GameRoom.GameStatus getGameStatus() {
        return this.room.getStatus();
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
    public void incrementCreatingPlayerScore(int amount) {
        this.room.getCreatingPlayerState().incrementScore(amount);
    }

    public void incrementJoiningPlayerScore(int amount) {
        this.room.getJoiningPlayerState().incrementScore(amount);
    }

    public void setGameStatusPlaying() {
        this.room.setGameStatus(GameRoom.GameStatus.PLAYING);
    }

    public void decrementCreatingPlayerScore(int amount) {
        this.room.getCreatingPlayerState().decrementScore(amount);
    }

    public void decrementJoiningPlayerScore(int amount) {
        this.room.getJoiningPlayerState().decrementScore(amount);
    }

    public void setCreatingPlayerScore(int amount) {
        this.room.getCreatingPlayerState().setScore(amount);
    }

    public void setJoiningPlayerScore(int amount) {
        this.room.getCreatingPlayerState().setScore(amount);
    }

    public void updateHighScores() {
        if (room.getGameMode().equals(GameRoom.GameMode.LAST_KNIGHT)) {
            if (room.getCreatingPlayerState().getScore() > room.getCreatingPlayer().getHighScore()) {
                room.getCreatingPlayer().setHighScore(room.getCreatingPlayerState().getScore());
                fi.savePlayer(room.getCreatingPlayer());
            }
            if (roomType.equals(RoomType.ONLINE)) {
                if (room.getJoiningPlayerState().getScore() > room.getJoiningPlayer().getHighScore()) {
                    room.getJoiningPlayer().setHighScore(room.getJoiningPlayerState().getScore());
                    fi.savePlayer(room.getJoiningPlayer());
                }
            }
        }
        if (room.getGameMode().equals(GameRoom.GameMode.FASTEST_KNIGHT)){
            if (room.getCreatingPlayerState().getScore() < room.getCreatingPlayer().getFastestTime()) {
                room.getCreatingPlayer().setFastestTime(room.getCreatingPlayerState().getScore());
                fi.savePlayer(room.getCreatingPlayer());
            }
            if (roomType.equals(RoomType.ONLINE)) {
                if (room.getJoiningPlayerState().getScore() < room.getJoiningPlayer().getFastestTime()) {
                    room.getJoiningPlayer().setFastestTime(room.getJoiningPlayerState().getScore());
                    fi.savePlayer(room.getJoiningPlayer());
                }
            }
        }
    }

    public void gameOver() {
        this.room.setGameStatus(GameRoom.GameStatus.COMPLETE);
        stateChanged();
        updateHighScores();
    }

}
