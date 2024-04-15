package no.ntnu.game.firestore;

public class GameState {

    private int score;

    public void incrementScore(int amount) { score += amount; }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void decrementScore(int amount) {
        this.score = score - amount;
    }
}
