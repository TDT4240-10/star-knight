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
/**
 * Tree class to create a tree with branch for the game
 * Should use the list of trees to remove the tree one by one when it chops **
 *
 * @author Jeff
 */
public class Tree {
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    public List<TreePart> trees;
    private String[] treesPossibility = {"none", "left", "right"};
    private Color trunkColor = new Color(0.647f, 0.455f, 0.208f, 1); // Brown color 1

    private Color TreeColor1 = new Color(0.765f, 0.556f, 0.208f, 1); //tree color 1

    private Color TreeColor2 = new Color(0.8f, 0.556f, 0.208f, 1); //tree color 2

    private int stemWidth = 300;
    private int stemHeight = 100;
    private int starterTree = 10;// change this to affect how many trees we start with
    private int width = 300;
    private int minYPosition = 300; // adjust this for space for character

    //here creates the tree object which includes stage that is to render the tree.
    public Tree() {
        float simulatorWidth = Gdx.graphics.getWidth();
        float simulatorHeight = Gdx.graphics.getHeight();
        stage = new Stage(new FitViewport(simulatorWidth, simulatorHeight));
        shapeRenderer = new ShapeRenderer();
        trees = new ArrayList<>();
    }

    //so it always initialise a basic number of tree and the color is either 1 or 2
    public void init() {
        for (int i = 1; i <= starterTree; i++) {
            // the string is where we decide to have branch on left or right.
            String newTrunk = treesPossibility[MathUtils.random(2)];
            Color color = (i % 2 == 0) ?TreeColor1 : TreeColor2;
            trees.add(new TreePart(newTrunk, color));
        }
    }

    // this function is used in the controller for the game to create new tree each time we chop one.
    public void createNewTrunk() {
        String newTrunk = trees.get(trees.size() - 1).value.equals("left") ? "right" : "left";
        Color color = (trees.get(trees.size() - 1).color.equals(trunkColor)) ? TreeColor1 : TreeColor2;
        trees.add(new TreePart(newTrunk, color));
    }

    //this is where how the trees are drawn.
    public void draw(SpriteBatch batch) {

        batch.begin();
        float centerX = stage.getWidth() / 2 - width / 2;
        int height = 250;
        float centerY = stage.getHeight() / 2 - (trees.size() * height) / 2; // Adjusted to center vertically

        for (int i = 0; i < trees.size(); i++) {
            TreePart treePart = trees.get(i);

            // Only draw the tree if its y position is above the minYPosition
            if (centerY + i * height > minYPosition) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                shapeRenderer.setColor(treePart.color);
                shapeRenderer.rect(centerX, centerY + i * height, width, height);

                if (treePart.value.equals("left")) {
                    treePart.setPos(centerX - stemWidth,centerY + i * height + height / 2);
                    shapeRenderer.rect(centerX - stemWidth, centerY + i * height + height / 2, stemWidth, stemHeight);
                } else if (treePart.value.equals("right")) {
                    treePart.setPos(centerX + width,centerY + i * height + height / 2);
                    shapeRenderer.rect(centerX + width, centerY + i * height + height / 2, stemWidth, stemHeight);
                }

                shapeRenderer.end();
            }
        }

        batch.end();
    }
}
