package no.ntnu.game.Models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.Objects;

/**
 * This is the tree that can have power-ups
 *
 * @author Jeff
 */

public class TreeWithPowerUp extends Tree {
    float centerX, centerY;
    private int powerUpFrequency;
    private int countTowardsNextPowerUp;
    private final String[] TREES_POSSIBILITY = { "none", "left", "right" };
    private final Color[] COLOR_POSSIBILITY = { TreeColor1, TreeColor2 };

    public TreeWithPowerUp() {
        super();
    }

    // Override init method to include powerups
    @Override
    public void init() {
        super.init(); // Call the init method of the superclass

        powerUpFrequency = 10;
        countTowardsNextPowerUp = 0;

        // Add powerups to certain trees
        for (int i = 0; i < trees.size(); i++) {
            if (i % powerUpFrequency == 0) { // For example, add powerup to every 2nd tree so change this to change
                                             // frequency
                TreePart treePart = trees.get(i);
                if (!Objects.equals(treePart.value, "none")) {
                    treePart.setPowerup(PowerUpFactory.createPowerUp());
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch); // Call the draw method of the superclass

        batch.begin();
        for (int i = 0; i < trees.size(); i++) {
            TreePart treePart = trees.get(i);
            centerX = treePart.x + 100;
            centerY = treePart.y;
            if (treePart.powerup != null) {
                // Draw the textureRegion at position (x, y)
                batch.draw(treePart.powerup.textureRegion, centerX, centerY,
                        treePart.powerup.textureRegion.getRegionWidth() * 6,
                        treePart.powerup.textureRegion.getRegionHeight() * 6);
            }

        }
        batch.end();
    }

    @Override
    public void createNewTrunk() {
        if (powerUpFrequency != countTowardsNextPowerUp) {
            super.createNewTrunk();
            countTowardsNextPowerUp += 1;
        } else {
            // If top-most branch is left or right, new branch added should be none
            String newTrunk;
            if (Objects.equals(trees.get(trees.size() - 1).getValue(), "left") ||
                    Objects.equals(trees.get(trees.size() - 1).getValue(), "right")) {
                newTrunk = "none";

            } else {
                newTrunk = TREES_POSSIBILITY[MathUtils.random(2)];
            }
            Color color = COLOR_POSSIBILITY[MathUtils.random(1)];

            TreePart treePart = new TreePart(newTrunk, color);
            if (!Objects.equals(newTrunk, "none")) {
                treePart.setPowerup(PowerUpFactory.createPowerUp());
            }
            trees.add(treePart);
            countTowardsNextPowerUp = 0;
        }

    }
}
