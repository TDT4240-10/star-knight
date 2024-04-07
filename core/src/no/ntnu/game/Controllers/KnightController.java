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


    // Constructor
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

    public void setChoppingKnightSpritePosition(int x, int y) {
        choppingKnightSprite.setPosition(x, y);
    }

    // Methods to interact with the knight
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

//    public void deathAnimation() {
//        idleKnightSprite.setPosition(-99999,-99999);
//
//        deadKnightSprite.setPosition(idleX, idleY);
//
//        deathAnimationActive = true;
//        elapsedTime = 0;
//    }

    // Function to move knight sprite, check for collision, chop tree and update new tree
    public String update(float delta) {
        TreePart lowestTreePart = tree.trees.get(2);
        System.out.println("lowest tree direction: " + lowestTreePart.getValue());

        if (choppingAnimationActive) {
            elapsedTime += delta;
            choppingKnightSprite.setPosition(idleX + 0.1f * elapsedTime, idleY);

            if (elapsedTime >= animationDuration) {
                choppingAnimationActive = false;
                elapsedTime = 0;

                choppingKnightSprite.setPosition(-99999, -99999);
                idleKnightSprite.setPosition(idleX, idleY);

                if (!Objects.equals(lowestTreePart.getValue(), knight.getDirection())) {
                    tree.chop();
                    tree.createNewTrunk();

                    // Checking for collision after chop
                    lowestTreePart = tree.trees.get(2);
                    if (Objects.equals(lowestTreePart.getValue(), knight.getDirection())) {
                        System.out.println("chopped tree and died");

                        idleKnightSprite.setPosition(-99999, -99999);
                        deathAnimationActive = true;
                        elapsedTime = 0;
                    }
                    TreePart newTreePart = tree.trees.get(0);
                    System.out.println("new tree direction: " + newTreePart.getValue());
                    System.out.println("tree chopped");
                }
            }
        }

        if (deathAnimationActive) {
            elapsedTime += delta;
            deadKnightSprite.setPosition(idleX + 0.1f * elapsedTime, idleY);

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

