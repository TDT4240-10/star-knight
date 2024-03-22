package no.ntnu.game.Models;

public class KnightModel {
    private int lives;
    private String direction;

    // Constructor
    public KnightModel(int lives) {
        this.lives = lives;
    }

    // Getter and setter methods
    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    // Additional methods for knight behavior
    public void attack() {
        // Implement attack logic
    }

    public void hitByBranch() {
        // Implement damage calculation logic
        lives -= 1;
    }
}

