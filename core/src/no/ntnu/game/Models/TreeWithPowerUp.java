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
    float centerX, centerY;
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
            if (i % 2 == 0) { // For example, add powerup to every 2nd tree so change this to change frequency
                TreePart treePart = trees.get(i);
                treePart.setPowerup(PowerUpFactory.createPowerUp());
            }
        }
    }

    @Override
    public void draw() {
        super.draw(); // Call the draw method of the superclass
        int height = 250;

        batch.begin();
        //TODO to draw the powerups ono the trees.
        float minYPosition = 300; // adjust this with the tree to have no error

        for (int i = 0; i < trees.size(); i++) {
            TreePart treePart = trees.get(i);
            centerX = treePart.x + 100;
            centerY = treePart.y;
            if (centerY > minYPosition) {
                if (treePart.powerup != null) {
                    // Draw the textureRegion at position (x, y)
                    batch.draw(treePart.powerup.textureRegion, centerX, centerY, treePart.powerup.textureRegion.getRegionWidth() * 6, treePart.powerup.textureRegion.getRegionHeight() * 6);
                }
            }
        }
        batch.end();
    }
}
