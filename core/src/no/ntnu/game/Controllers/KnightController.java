package no.ntnu.game.Controllers;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;

import no.ntnu.game.Models.KnightModel;
import no.ntnu.game.Views.ChoppingKnightSprite;
import no.ntnu.game.Views.IdleKnightSprite;

public class KnightController {
    private KnightModel knight;
    private ChoppingKnightSprite choppingKnightSprite;
    private IdleKnightSprite idleKnightSprite;
    private SpriteBatch batch;

    // Constructor
    public KnightController(SpriteBatch batch) {
        knight = new KnightModel(1);
        choppingKnightSprite = new ChoppingKnightSprite();
        idleKnightSprite = new IdleKnightSprite();
        knight.setDirection("left");
        this.batch = batch;

    }

    public void setIdleKnightSpritePosition(int x, int y) {
        idleKnightSprite.setPosition(x, y);
    }

    public void setChoppingKnightSpritePosition(int x, int y) {
        choppingKnightSprite.setPosition(x, y);
    }

    // Methods to interact with the knight
    public void moveRight() {
        // switch knight direction and run chopping animation
        System.out.println("knight controller move right");

        knight.setDirection("right");
//        disposeIdleKnight();

//        setChoppingPosition(300, 500);
//        renderChoppingKnight(batch);
//        disposeChoppingKnight();
//
//        idleKnightSprite = new IdleKnightSprite();
//        idleKnightSprite.setPosition(800,500);
        idleKnightSprite.setBounds(800, 500, idleKnightSprite.getWidth() * 6, idleKnightSprite.getHeight() * 6); // Set bounds with scaled dimensions

//        setIdlePosition(800, 500);
        idleKnightSprite.flipDirection();
//        renderIdleKnight(batch);
    }

    public void moveLeft() {
        // switch knight direction and run chopping animation
        knight.setDirection("left");
        idleKnightSprite.flipDirection();

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

    public void disposeIdleKnight() {
        idleKnightSprite.dispose();
    }

    public void disposeChoppingKnight() {
        choppingKnightSprite.dispose();
    }
}

