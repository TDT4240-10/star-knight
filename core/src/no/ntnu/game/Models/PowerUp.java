
package no.ntnu.game.Models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Powerup class to create a Powerup for the game
 *
 * @author Jeff
 */
public class PowerUp {
    private final String NAME;
    private final int DURATION; // Duration of the power-up in milliseconds
    private float x;
    private float y;
    private final float POWERUP_WIDTH;
    private final float POWERUP_HEIGHT;
    private final TextureRegion TEXTURE_REGION;

    // Constructor
    public PowerUp(String name, int duration, TextureRegion textureRegion) {
        this.NAME = name;
        this.DURATION = duration;
        this.TEXTURE_REGION = textureRegion;
        this.POWERUP_WIDTH = textureRegion.getRegionWidth() * 6;
        this.POWERUP_HEIGHT = textureRegion.getRegionHeight() * 6;
    }

    // Getter for name
    public String getNAME() {
        return NAME;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // To render on game screen when knight gets power ups
    public void render(SpriteBatch batch) {
        // Draw the sprite onto the batch
        batch.begin();
        batch.draw(TEXTURE_REGION, x, y, POWERUP_WIDTH, POWERUP_HEIGHT);
        batch.end();
    }

    // Getter for duration
    public int getDuration() {
        return DURATION;
    }

    public TextureRegion getTextureRegion() {
        return this.TEXTURE_REGION;
    }

}
