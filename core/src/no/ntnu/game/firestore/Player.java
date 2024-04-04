package no.ntnu.game.firestore;

import no.ntnu.game.FirebaseClass;

public class Player extends FirebaseClass {
    public String username;

    public Integer highScore;


    public Player(String username) {
        this.username = username;
        this.highScore = 0;
    }

    public String getCollectionName() {
        return "players";
    }
}
