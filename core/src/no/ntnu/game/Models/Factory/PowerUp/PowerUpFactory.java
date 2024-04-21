
package no.ntnu.game.Models.Factory.PowerUp;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import no.ntnu.game.Models.PowerUp;

import java.util.Random;

/**
 * PowerupFactory class to create different Powerup for the game
 *
 * @author Jeff
 */
public class PowerUpFactory implements AbstractPowerUpFactory {
    private Texture powerUpTexture; // Texture containing all power-up sprites
    // Load the power-up texture

    public void loadTextures() {
        powerUpTexture = new Texture("powerups.png");
    }

    public PowerUpFactory() {
        loadTextures();
    }

    // A power will be created randomly
    @Override
    public PowerUp createPowerUp() {
        Random random = new Random();
        int randomIndex = random.nextInt(3); // Adjust the range based on the number of power-up types

        switch (randomIndex) {
            case 0:
                return createLivesPowerUp();
            case 1:
                return createDoublePoints();
            case 2:
                return createBullet();
            // Add cases for other types of PowerUps as needed
            default:
                return null; // Default to speed power-up
        }
    }

    // Method to create a Extra lives PowerUp
    public PowerUp createLivesPowerUp() {
        return new PowerUp("heart", 2000, this.getTextureRegionForType("heart")); // Example duration: 5000 milliseconds
    }

    public PowerUp createDoublePoints() {
        return new PowerUp("double", 5000, getTextureRegionForType("double")); // Example duration: 10000 milliseconds
    }

    public PowerUp createBullet() {
        return new PowerUp("bullet", 5000, getTextureRegionForType("bullet")); // Example duration: 10000 milliseconds
    }

    // Method to get the texture region for a specific type of power-up
    private TextureRegion getTextureRegionForType(String type) {
        int index = getIndexForType(type); // Get the index of the sprite for this type
        int rows = 4;
        int spriteWidth = 32;
        int spriteHeight = 32;
        int row = index / rows;
        int col = index % rows;
        return new TextureRegion(this.powerUpTexture, col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
    }

    // Method to get the index of a power-up type
    private static int getIndexForType(String type) {
        // Example logic to map power-up types to indices
        switch (type.toLowerCase()) {
            case "heart":
                return 1;
            case "bullet":
                return 2;
            case "double":
                return 14;

            // Add cases for other types...
            default:
                return 0; // Default to coin
        }
    }
}
