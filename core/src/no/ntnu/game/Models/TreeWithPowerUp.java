package no.ntnu.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Null;

/**
 * This is the tree that can have power-ups
 *
 * @author Jeff
 */

public class TreeWithPowerUp extends Tree {
    private PowerUp Powerup;
    private PowerUpFactory createPowerUp;
    private SpriteBatch batch;
    private int width = 300;
    float simulatorWidth = Gdx.graphics.getWidth();
    float simulatorHeight = Gdx.graphics.getHeight();
    
    public TreeWithPowerUp(SpriteBatch batch) {
        super(batch);
        this.batch = batch; // Initialize the SpriteBatch
    }

    // Override init method to include powerups
    @Override
    public void init() {
        super.init(); // Call the init method of the superclass

        // Add powerups to certain trees
        for (int i = 0; i < trees.size(); i++) {
            if (i % 3 == 0) { // For example, add powerup to every third tree
                TreePart treePart = trees.get(i);
                treePart.setPowerup(createPowerUp.createPowerUp());
            }
        }
    }

    @Override
    public void draw() {
        super.draw(); // Call the draw method of the superclass
        float centerX = simulatorWidth / 2 - width / 2;
        int height = 250;
        float centerY = simulatorHeight / 2 - (trees.size() * height) / 2; // Adjusted to center vertically

        //TODO to draw the powerups ono the trees.
        for (int i = 0; i < trees.size(); i++) {
            TreePart treePart = trees.get(i);

            if (treePart.powerup != null){
                // Draw the textureRegion at position (x, y)
                batch.draw(treePart.powerup.textureRegion, centerX, centerY);
            }

        }
        batch.end();
    }
}
