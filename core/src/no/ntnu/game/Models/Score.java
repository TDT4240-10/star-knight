package no.ntnu.game.Models;public class Score {
    private int score;

    // Constructor
    public Score() {
        this.score = 0;
    }

    // Method to increment score
    public void incrementScore(int amount) {
        this.score += amount;
    }

    // Getter for score
    public int getScore() {
        return score;
    }
}
