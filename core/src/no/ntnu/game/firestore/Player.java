package no.ntnu.game.firestore;

import java.util.UUID;

public class Player extends FirebaseClass {

    private String username;
    private Integer highScore;
    private Integer fastestTime;

    public Player() {};

    public Player(String username) {
        this.username = username;
        this.highScore = 0;
        this.setDocumentId(UUID.randomUUID().toString());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getHighScore() {
        return highScore;
    }

    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }

    public Integer getFastestTime() {
        return this.fastestTime;
    }

    public void setFastestTime(Integer time) {
        this.fastestTime = time;
    }
}
