package no.ntnu.game.firestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import no.ntnu.game.Controllers.GameRoomController;

public class GameRoom extends FirebaseClass {
    public enum GameMode {
        NONE, FASTEST_KNIGHT, LAST_KNIGHT
    }

    public enum GameStatus {
        CREATED, LOBBY, STARTING, PLAYING, COMPLETE
    }
    private String roomCode;
    private Date gameStartTime;

    private GameMode currentGameMode;
    private Player creatingPlayer;
    private Player joiningPlayer;

    private GameState creatingPlayerState;
    private GameState joiningPlayerState;
    private Date createdAt;
    private GameStatus status;



    public GameRoom() {};

    public GameRoom(Player creatingPlayer) {
        this.createdAt = new Date();
        this.creatingPlayer = creatingPlayer;
        this.status = GameStatus.CREATED;
        this.roomCode = generateRandomCode();
        this.setDocumentId(UUID.randomUUID().toString());
        creatingPlayerState = new GameState();
        joiningPlayerState = new GameState();
    }

    public static String generateRandomCode() {
        // Define alphabet
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Initialize random generator
        Random random = new Random();

        // Initialize StringBuilder to construct the random code
        StringBuilder sb = new StringBuilder();

        // Generate 4 random letters and append to StringBuilder
        for (int i = 0; i < 4; i++) {
            char randomChar = alphabet.charAt(random.nextInt(alphabet.length()));
            sb.append(randomChar);
        }

        // Convert StringBuilder to String and return
        return sb.toString();
    }

    public void addJoiningPlayer(Player player) {
        this.joiningPlayer = player;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public Player getCreatingPlayer() {
        return this.creatingPlayer;
    }

    public Player getJoiningPlayer() {
        return this.joiningPlayer;
    }

    public GameMode getGameMode() {
        return this.currentGameMode;
    }

    public void setGameMode(GameMode mode) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            System.out.println(element.getClassName() + "." + element.getMethodName() + "()");
        }
        if (mode == null) {
            return;
        }
        if (mode.equals(GameMode.FASTEST_KNIGHT)) {
            this.joiningPlayerState.setScore(30);
            this.creatingPlayerState.setScore(30);
        } else {
            this.creatingPlayerState.setScore(9999);
            this.joiningPlayerState.setScore(9999);
        }
        this.currentGameMode = mode;
    }

    public void setGameStatus(GameStatus status) {
        this.status = status;
    }

    public GameState getCreatingPlayerState() {
        return creatingPlayerState;
    }

    public void setCreatingPlayerState(GameState creatingPlayerState) {
        this.creatingPlayerState = creatingPlayerState;
    }

    public GameState getJoiningPlayerState() {
        return joiningPlayerState;
    }

    public void setJoiningPlayerState(GameState joiningPlayerState) {
        this.joiningPlayerState = joiningPlayerState;
    }

    public Date getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(Date gameStartTime) {
        this.gameStartTime = gameStartTime;
    }
}
