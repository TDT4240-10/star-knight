package no.ntnu.game.Models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class Tree extends Actor {
    private Array<TreeItem> trees = new Array<>();
    private int startX;
    private int startY;
    private int width = 100;
    private int height = 150;
    private int stemWidth = 100;
    private int stemHeight = 30;
    private int starterTree = 5;
    private Random random = new Random();
    private ShapeRenderer shapeRenderer;

    public Tree(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        setBounds(startX, startY, width, height); // Set the bounds of the actor
        shapeRenderer = new ShapeRenderer();
        init();
    }

    private void init() {
        for (int i = 1; i <= starterTree; i++) {
            String newTrunk = "";
            int color = (i % 2 == 0) ? Color.rgb888(204, 142, 53) : Color.rgb888(161, 116, 56);
            switch (randomNumber()) {
                case 0:
                    newTrunk = "";
                    break;
                case 1:
                    newTrunk = "left";
                    break;
                case 2:
                    newTrunk = "right";
                    break;
            }
            trees.add(new TreeItem(newTrunk, color));
        }
    }

    private int randomNumber() {
        return random.nextInt(2);
    }

    private class TreeItem {
        String value;
        int color;

        TreeItem(String value, int color) {
            this.value = value;
            this.color = color;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end(); // End batch to switch to shape rendering
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw tree (rectangle)
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());

        // Draw stem (rectangle)
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.rect(getX() + (getWidth() - stemWidth) / 2, getY(), stemWidth, stemHeight);

        shapeRenderer.end();
        batch.begin(); // Begin batch again for other drawing
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }
}