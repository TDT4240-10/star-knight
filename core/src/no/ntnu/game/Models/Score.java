package no.ntnu.game.Models;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.game.Controllers.GameRoomController;

public class Score {
    private int score;
//    private int treesToChop;
    private BitmapFont font;
    public GameRoomController gameRoomController;

    // Constructor
    public Score() {
        gameRoomController = GameRoomController.getInstance();

        font = new BitmapFont(); // Assuming you have a font for rendering text

        // Initialize score to 0 if game mode is last knight standing
        if (gameRoomController.isLastKnightMode()) {
            score = 0;
        }

        // Initialize score to 0 if game mode is fastest knight
        else if (gameRoomController.isFastestKnightMode()) {
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
        //System.out.println("LOLOLOL This is your score: " + score);
        return score;
    }

    public void render(SpriteBatch batch) {
        // if game mode is last knight standing, render score
        if (gameRoomController.isLastKnightMode()) {
            batch.begin();
            // Calculate the position to center the text on the screen
            float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2; // Assuming average glyph width
            float y = Gdx.graphics.getHeight() - 500; // Center vertically
            font.getData().setScale(4f);
            font.draw(batch, "Score: " + getScore(), x, y);
            batch.end();
        }
        // if game mode is fastest knight
        else if (gameRoomController.isFastestKnightMode()) {
            batch.begin();
            // Calculate the position to center the text on the screen
            float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2; // Assuming average glyph width
            float y = Gdx.graphics.getHeight() - 500; // Center vertically
            font.getData().setScale(4f);
            font.draw(batch, "Score: " + getScore(), x, y);
            batch.end();
        }
    }
}
