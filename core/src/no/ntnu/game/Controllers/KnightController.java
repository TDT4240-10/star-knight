package no.ntnu.game.Controllers;


import com.badlogic.gdx.Gdx;
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
    private int idleX;
    private int idleY;

    private int phoneWidth;
    private float animationDuration = 0.5f; // Example duration in seconds
    private boolean choppingAnimationActive = false;
    private float elapsedTime = 0;


    // Constructor
    public KnightController(int idleX, int idleY) {
        knight = new KnightModel(1);
        choppingKnightSprite = new ChoppingKnightSprite();
        idleKnightSprite = new IdleKnightSprite();
        knight.setDirection("left");
        this.idleX = idleX;
        this.idleY = idleY;
    }

    public void setChoppingKnightSpritePosition(int x, int y) {
        choppingKnightSprite.setPosition(x, y);
    }

    // Methods to interact with the knight
    public void moveRight(SpriteBatch sb) {
        // switch knight direction and run chopping animation
        knight.setDirection("right");
        idleKnightSprite.setPosition(-99999,-99999);
        idleKnightSprite.flipDirection();

        phoneWidth = Gdx.graphics.getWidth();
        idleX = phoneWidth / 2 + idleX;

        choppingKnightSprite.setPosition(idleX, idleY);
        choppingKnightSprite.flipDirection();

        choppingAnimationActive = true;
        elapsedTime = 0;
    }

    public void moveLeft() {

        // switch knight direction and run chopping animation
        knight.setDirection("left");
        idleKnightSprite.setPosition(-99999,-99999);
        idleKnightSprite.flipDirection();

        idleX = idleX - (phoneWidth / 2);

        choppingKnightSprite.setPosition(idleX, idleY);
        choppingKnightSprite.flipDirection();

        choppingAnimationActive = true;
        elapsedTime = 0;
    }

    public void update(float delta) {
        if (choppingAnimationActive) {
            elapsedTime += delta;
            choppingKnightSprite.setPosition(idleX + 0.1f * elapsedTime, idleY);

            if (elapsedTime >= animationDuration) {
                choppingAnimationActive = false;
                elapsedTime = 0;

                choppingKnightSprite.setPosition(-99999,-99999);
                idleKnightSprite.setPosition(idleX, idleY);
            }
        }
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

