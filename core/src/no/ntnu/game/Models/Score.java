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
    public Score() {
        gameModeController = GameModeController.getInstance();

        font = new BitmapFont(); // Assuming you have a font for rendering text

        // Initialize score to 0 if game mode is last knight standing
        if (gameModeController.isLastKnightMode()) {
            score = 0;
        }

        // Initialize score to 0 if game mode is fastest knight
        if (gameModeController.isFastestKnightMode()) {
            score = 30;
        }

    }

    // Method to increment score
    public void incrementScore(int amount) {
        this.score += amount;
    }

    // Method to decrement score
//    public void decrementScore(int amount) {
//        this.score -= amount;
//    }

    // Getter for score
    public int getScore() {
        return score;
    }

    // getter for treesToChop
//    public int getTreesToChop() {
//        return treesToChop;
//    }

    public void render(SpriteBatch batch) {
        // if game mode is last knight standing, render score
//        if (gameModeController.isLastKnightMode()) {
//            batch.begin();
//            // Calculate the position to center the text on the screen
//            float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2; // Assuming average glyph width
//            float y = Gdx.graphics.getHeight() - 500; // Center vertically
//            font.getData().setScale(4f);
////            font.draw(batch, "Score: " + getScore(), x, y);
//            batch.end();
//        }
//        // if game mode is fastest knight
//        else if (gameModeController.isFastestKnightMode()) {
//            batch.begin();
//            // Calculate the position to center the text on the screen
//            float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2; // Assuming average glyph width
//            float y = Gdx.graphics.getHeight() - 500; // Center vertically
//            font.getData().setScale(4f);
////            font.draw(batch, "Score: " + getScore(), x, y);
//            batch.end();
//        }
    }
}
