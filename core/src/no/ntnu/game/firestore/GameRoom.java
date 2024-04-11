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
        CREATED, LOBBY, PLAYING, COMPLETE
    }
    private String roomCode;

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
        this.currentGameMode = mode;
    }

    public void setGameStatus(GameStatus status) {
        this.status = status;
    }

    public GameState getCreatingPlayerState() {
        return creatingPlayerState;
    }

    public void incrementCreatingPlayerScore(int amount) {
        this.creatingPlayerState.incrementScore(amount);
    }

    public void incrementJoiningPlayerScore(int amount) {
        this.joiningPlayerState.incrementScore(amount);
    }

    public void decrementCreatingPlayerScore(int amount) {
        this.creatingPlayerState.decrementScore(amount);
    }

    public void decrementJoiningPlayerScore(int amount) {
        this.joiningPlayerState.decrementScore(amount);
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
}
