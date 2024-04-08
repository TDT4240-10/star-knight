package no.ntnu.game.firestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

import no.ntnu.game.FirebaseClass;
import no.ntnu.game.FirebaseInterface;

public class GameRoom extends FirebaseClass {
    private String roomCode;
    private List<Player> players;
    private Date createdAt;
    private String status;

    public GameRoom(Player creatingPlayer) {
        players = new ArrayList<>();
        this.players.add(creatingPlayer);
        this.createdAt = new Date();
        this.status = "lobby";
        this.roomCode = generateRandomCode();
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

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public String getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    @Override
    public String getCollectionName() {
        return "game_rooms";
    }
}
