package no.ntnu.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Tree class to create a tree with branch for the game
 * Should use the list of trees to remove the tree one by one when it chops **
 *
 * @author Jeff
 */
public class Tree {
    private final ShapeRenderer SHAPE_RENDERER;
    private final Stage STAGE;
    public List<TreePart> trees;
    protected final String[] TREES_POSSIBILITY = { "none", "left", "right" };
    protected Color trunkColor = new Color(0.647f, 0.455f, 0.208f, 1); // Brown color 1

    protected Color TreeColor1 = new Color(0.745f, 0.556f, 0.208f, 1); // tree color 1

    protected Color TreeColor2 = new Color(0.8f, 0.556f, 0.208f, 1); // tree color 2

    protected final Color[] COLOR_POSSIBILITY = { TreeColor1, TreeColor2 };

    private String newTrunk;

    private final int STEM_WIDTH = 300;
    private final int STEM_HEIGHT = 100;
    private final int STARTER_TREE = 10;// change this to affect how many trees we start with
    private final int WIDTH = 300;
    private final int MIN_Y_POSISION = 200; // adjust this for space for character

    // here creates the tree object which includes stage that is to render the tree.
    public Tree() {
        float simulatorWidth = Gdx.graphics.getWidth();
        float simulatorHeight = Gdx.graphics.getHeight();
        STAGE = new Stage(new FitViewport(simulatorWidth, simulatorHeight));
        SHAPE_RENDERER = new ShapeRenderer();
        trees = new ArrayList<>();
    }

    // so it always initialise a basic number of tree and the color is either 1 or 2
    public void init() {
        trees.add(new TreePart("none", TreeColor1));
        trees.add(new TreePart("none", TreeColor1));
        trees.add(new TreePart("none", TreeColor1));
        for (int i = 0; i <= STARTER_TREE - 3; i++) {
            if (Objects.equals(trees.get(trees.size() - 1).getValue(), "left") ||
                    Objects.equals(trees.get(trees.size() - 1).getValue(), "right")) {
                newTrunk = "none";

            } else {
                newTrunk = TREES_POSSIBILITY[MathUtils.random(2)];
            }
            // the string is where we decide to have branch on left or right.
            Color color = (i % 2 == 0) ? TreeColor1 : TreeColor2;
            trees.add(new TreePart(newTrunk, color));

        }
    }

    public void chop() {
        trees.remove(0);
    }

    // this function is used in the controller for the game to create new tree each
    // time we chop one.
    public void createNewTrunk() {

        if (Objects.equals(trees.get(trees.size() - 1).getValue(), "left") ||
                Objects.equals(trees.get(trees.size() - 1).getValue(), "right")) {
            newTrunk = "none";

        } else {
            newTrunk = TREES_POSSIBILITY[MathUtils.random(2)];
        }
        Color color = COLOR_POSSIBILITY[MathUtils.random(1)];

        trees.add(new TreePart(newTrunk, color));
    }

    // this is where how the trees are drawn.
    public void draw(SpriteBatch batch) {

        batch.begin();
        float centerX = STAGE.getWidth() / 2 - WIDTH / 2;
        int height = 250;
        float centerY = MIN_Y_POSISION; // Adjusted to center vertically

        for (int i = 0; i < trees.size(); i++) {
            TreePart treePart = trees.get(i);

            // Only draw the tree if its y position is above the minYPosition
            // Translate the ShapeRenderer to start from a fixed y-coordinate

            SHAPE_RENDERER.begin(ShapeRenderer.ShapeType.Filled);
            SHAPE_RENDERER.identity();

            SHAPE_RENDERER.translate(0, MIN_Y_POSISION, 0); // Adjust the value as per your needs

            SHAPE_RENDERER.setColor(treePart.color);
            SHAPE_RENDERER.rect(centerX, centerY + i * height, WIDTH, height);

            if (treePart.value.equals("left")) {
                treePart.setPos(centerX - STEM_WIDTH, centerY + STEM_WIDTH / 2 + i * height + height / 2);
                SHAPE_RENDERER.rect(centerX - STEM_WIDTH, centerY + i * height + height / 2, STEM_WIDTH, STEM_HEIGHT);
            } else if (treePart.value.equals("right")) {
                treePart.setPos(centerX + WIDTH, centerY + STEM_WIDTH / 2 + i * height + height / 2);
                SHAPE_RENDERER.rect(centerX + WIDTH, centerY + i * height + height / 2, STEM_WIDTH, STEM_HEIGHT);
            }

            SHAPE_RENDERER.end();
        }

        batch.end();
    }
}
