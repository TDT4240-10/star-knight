package no.ntnu.game.Models;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.game.Controllers.GameModeController;


public class Score {
    private int score;
//    private int treesToChop;
    private BitmapFont font;
    public GameModeController gameModeController;

    // Constructor
    public Score(GameModeController gameModeController) {
//        gameModeController = GameModeController.getInstance();

        font = new BitmapFont(); // Assuming you have a font for rendering text

        // Initialize score to 0 if game mode is last knight standing and 30 if game mode is fastest knight
        if (gameModeController.isLastKnightMode()) {
            score = 0;
            // print out score set to 0
            System.out.println("Score has been set to: " + score);
        } else if (gameModeController.isFastestKnightMode()) {
            score = 30;
            // print out score set to 30
            System.out.println("Score has been set to: " + score);
        }

    }

    // Method to increment score
    public void incrementScore(int amount) {
        this.score += amount;
    }

    // Method to decrement score
    public void decrementScore(int amount) {
        this.score -= amount;
    }

    // Getter for score
    public int getScore() {
        // print this is your score
        System.out.println("LOLOLOL This is your score: " + score);
        return score;
    }

    public void render(SpriteBatch batch) {
    }
}
