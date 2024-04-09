package no.ntnu.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {
    private int score;
    private BitmapFont font;

    // Constructor
    public Score() {
        this.score = 0;
        font = new BitmapFont(); // Assuming you have a font for rendering text
    }

    // Method to increment score
    public void incrementScore(int amount) {
        this.score += amount;
    }

    // Getter for score
    public int getScore() {
        return score;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        // Calculate the position to center the text on the screen
        float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2; // Assuming average glyph width
        float y = Gdx.graphics.getHeight() - 500; // Center vertically
        font.getData().setScale(4f);
        font.draw(batch, "Score: " + getScore(), x, y);
        batch.end();
    }
}
