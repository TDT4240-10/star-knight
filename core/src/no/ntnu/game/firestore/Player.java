package no.ntnu.game.firestore;

import no.ntnu.game.FirebaseClass;

public class Player extends FirebaseClass {
    public String username;

    public Integer highScore;

    public Player() {};

    public Player(String username) {
        this.username = username;
        this.highScore = 0;
    }

    public String getCollectionName() {
        return "players";
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public void setHighScore(Integer highScore) {
//        this.highScore = highScore;
//    }
}
