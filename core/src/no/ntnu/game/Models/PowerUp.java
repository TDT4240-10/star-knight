
package no.ntnu.game.Models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Powerup class to create a Powerup for the game
 *
 * @author Jeff
 */
public class PowerUp {
    private String name;
    private int duration; // Duration of the power-up in milliseconds
    private boolean isActive;

    private float x;
    private float y;
    private float powerUpWidth;
    private float powerUpHeight;
    public TextureRegion textureRegion;

    // Constructor
    public PowerUp(String name, int duration, TextureRegion textureRegion) {
        this.name = name;
        this.duration = duration;
        this.isActive = false;
        this.textureRegion = textureRegion;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // To render on game screen when knight gets power ups
    public void render(SpriteBatch batch) {
        // Draw the sprite onto the batch
        batch.begin();
        powerUpWidth = textureRegion.getRegionWidth() * 6;
        powerUpHeight = textureRegion.getRegionHeight() * 6;
        batch.draw(textureRegion, x, y, powerUpWidth, powerUpHeight);
        batch.end();
    }

    // Getter for duration
    public int getDuration() {
        return duration;
    }

    // Method to activate the power-up
    public void activate() {
        isActive = true;
        // Implement any logic related to activating the power-up
    }

    // Method to deactivate the power-up
    public void deactivate() {
        isActive = false;
        // Implement any logic related to deactivating the power-up
    }

    // Getter for isActive
    public boolean isActive() {
        return isActive;
    }
}
