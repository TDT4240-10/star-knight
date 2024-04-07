package no.ntnu.game.Controllers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;

import no.ntnu.game.Models.KnightModel;
import no.ntnu.game.Models.Tree;
import no.ntnu.game.Models.TreePart;
import no.ntnu.game.Models.TreeWithPowerUp;
import no.ntnu.game.Views.ChoppingKnightSprite;
import no.ntnu.game.Views.DeadKnightSprite;
import no.ntnu.game.Views.IdleKnightSprite;
import no.ntnu.game.Views.JoinGameLobbyScreen;
import no.ntnu.game.Views.RunningKnightSprite;

/**
 * KnightController class is the main controller class to handle knight sprites, tree and collision logic.
 *
 * @author Han
 */
public class KnightController {
    private KnightModel knight;
    private ChoppingKnightSprite choppingKnightSprite;
    private IdleKnightSprite idleKnightSprite;
    private DeadKnightSprite deadKnightSprite;
    private int idleX;
    private int idleY;
    private TreeWithPowerUp tree;

    private int phoneWidth;
    private float animationDuration = 0.5f; // Example duration in seconds

    private float deathAnimationDuration = 0.8f;
    private boolean choppingAnimationActive = false;
    private boolean deathAnimationActive = false;
    private float elapsedTime = 0;


    // Constructor with idle knight sprite X, Y coordinates and tree model attributes
    public KnightController(int idleX, int idleY, TreeWithPowerUp tree) {
        knight = new KnightModel(1);
        choppingKnightSprite = new ChoppingKnightSprite();
        idleKnightSprite = new IdleKnightSprite();
        deadKnightSprite = new DeadKnightSprite();

        knight.setDirection("left");
        this.idleX = idleX;
        this.idleY = idleY;
        this.tree = tree;
    }

    // Methods to interact with the knight

    // moveRight() is used when knight's direction is left and right button is clicked
    public void moveRight() {
        knight.setDirection("right");
        phoneWidth = Gdx.graphics.getWidth();
        idleX = phoneWidth / 2 + idleX;

        TreePart lowestTreePart = tree.trees.get(2);
        if (Objects.equals(lowestTreePart.getValue(), knight.getDirection())) {
            idleKnightSprite.setPosition(-99999, -99999);
            deadKnightSprite.setPosition(idleX, idleY);
            deadKnightSprite.flipDirection();
            deathAnimationActive = true;
            elapsedTime = 0;
        }
        else {
            // switch knight direction and run chopping animation

            idleKnightSprite.setPosition(-99999,-99999);
            idleKnightSprite.flipDirection();

            choppingKnightSprite.setPosition(idleX, idleY);
            choppingKnightSprite.flipDirection();

            deadKnightSprite.flipDirection();

            choppingAnimationActive = true;
            elapsedTime = 0;
        }

    }

    // stayRight() is used when knight's direction is right and right button is clicked
    public void stayRight() {
        knight.setDirection("right");
        TreePart lowestTreePart = tree.trees.get(2);
        if (Objects.equals(lowestTreePart.getValue(), knight.getDirection())) {
            idleKnightSprite.setPosition(-99999, -99999);
            deadKnightSprite.setPosition(idleX, idleY);
            deathAnimationActive = true;
            elapsedTime = 0;
        }
        else {
            // switch knight direction and run chopping animation

            idleKnightSprite.setPosition(-99999, -99999);

            choppingKnightSprite.setPosition(idleX, idleY);

            choppingAnimationActive = true;
            elapsedTime = 0;
        }
    }

    // moveLeft() is used when knight's direction is right and left button is clicked
    public void moveLeft() {
        knight.setDirection("left");
        idleX = idleX - (phoneWidth / 2);

        TreePart lowestTreePart = tree.trees.get(2);
        System.out.println("move left, : " + lowestTreePart.getValue());

        if (Objects.equals(lowestTreePart.getValue(), knight.getDirection())) {
            idleKnightSprite.setPosition(-99999, -99999);
            deadKnightSprite.setPosition(idleX, idleY);
            deadKnightSprite.flipDirection();
            deathAnimationActive = true;
            elapsedTime = 0;
        }
        else {
            // switch knight direction and run chopping animation

            idleKnightSprite.setPosition(-99999, -99999);
            idleKnightSprite.flipDirection();

            choppingKnightSprite.setPosition(idleX, idleY);
            choppingKnightSprite.flipDirection();

            deadKnightSprite.flipDirection();

            choppingAnimationActive = true;
            elapsedTime = 0;
        }
    }

    // stayLeft() is used when knight's direction is left and left button is clicked
    public void stayLeft() {
        knight.setDirection("left");
        TreePart lowestTreePart = tree.trees.get(2);
        if (Objects.equals(lowestTreePart.getValue(), knight.getDirection())) {
            idleKnightSprite.setPosition(-99999, -99999);
            deadKnightSprite.setPosition(idleX, idleY);
            deathAnimationActive = true;
            elapsedTime = 0;
        }
        else {
            // switch knight direction and run chopping animation

            idleKnightSprite.setPosition(-99999, -99999);

            choppingKnightSprite.setPosition(idleX, idleY);

            choppingAnimationActive = true;
            elapsedTime = 0;
        }
    }

    // Update() function is the main function which handles game logic -
        // knight sprites position,
        // collision detection,
        // chop tree function,
        // to add a new tree after chopping
    public String update(float delta) {
        // Get the current lowest tree part=
        TreePart lowestTreePart = tree.trees.get(2);
        System.out.println("lowest tree direction: " + lowestTreePart.getValue());

        // Logic to run chopping knight animation
        if (choppingAnimationActive) {
            elapsedTime += delta;
            choppingKnightSprite.setPosition(idleX + 0.1f * elapsedTime, idleY);

            // Logic to end chopping knight animation, render idle knight again
            if (elapsedTime >= animationDuration) {
                choppingAnimationActive = false;
                elapsedTime = 0;

                choppingKnightSprite.setPosition(-99999, -99999);
                idleKnightSprite.setPosition(idleX, idleY);

                // Logic to check for knight collision and chop tree
                if (!Objects.equals(lowestTreePart.getValue(), knight.getDirection())) {
                    tree.chop();
                    tree.createNewTrunk();

                    // Checking for next collision after chopping the tree
                    lowestTreePart = tree.trees.get(2);
                    if (Objects.equals(lowestTreePart.getValue(), knight.getDirection())) {
                        System.out.println("chopped tree and died");

                        idleKnightSprite.setPosition(-99999, -99999);
                        deathAnimationActive = true;
                        elapsedTime = 0;
                    }
                }
            }
        }

        // Logic to handle death knight animation
        if (deathAnimationActive) {
            elapsedTime += delta;
            deadKnightSprite.setPosition(idleX + 0.1f * elapsedTime, idleY);

            // End of death animation
            if (elapsedTime >= deathAnimationDuration) {
                deathAnimationActive = false;
                elapsedTime = 0;
                return "lose";
            }
        }

        return "continue";
    }


    // Getter methods to access knight attributes
    public int getLives() {
        return knight.getLives();
    }

    public void setLives(int lives) {
        knight.setLives(lives);
    }

    public String getDirection() {
        return knight.getDirection();
    }

    public void setDirection(String direction) {
        knight.setDirection(direction);
    }

    // Add lives += 1 when knight chopes branch with extra life/shield
    public void addLives(int lives) {
        int currentLives = knight.getLives();
        currentLives += 1;
        knight.setLives(currentLives);
    }

    public void renderIdleKnight(SpriteBatch batch) {
        idleKnightSprite.render(batch);
    }

    public void setIdlePosition(float x, float y) {
        idleKnightSprite.setPosition(x, y);
    }

    public void setChoppingPosition(float x, float y) {
        choppingKnightSprite.setPosition(x, y);
    }

    public void renderChoppingKnight(SpriteBatch batch) {
//        choppingKnightSprite.startAnimation();
        choppingKnightSprite.render(batch);
    }

    public void renderDeadKnight(SpriteBatch batch) {
//        choppingKnightSprite.startAnimation();
        deadKnightSprite.render(batch);
    }

    public void setDeadPosition(float x, float y) {
        deadKnightSprite.setPosition(x, y);
    }

    public void disposeIdleKnight() {
        idleKnightSprite.dispose();
    }

    public void disposeChoppingKnight() {
        choppingKnightSprite.dispose();
    }

}

