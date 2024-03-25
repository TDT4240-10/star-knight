User
package no.ntnu.game.Models;
/**
 * Powerup class to create a Powerup  for the game
 *
 * @author Jeff
 */
public class PowerUp {
    private String name;
    private int duration; // Duration of the power-up in milliseconds
    private boolean isActive;

    // Constructor
    public PowerUp(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.isActive = false;
    }

    // Getter for name
    public String getName() {
        return name;
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

