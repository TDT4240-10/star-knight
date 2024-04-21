package no.ntnu.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Objects;

import no.ntnu.game.Models.KnightModel;
import no.ntnu.game.Models.PowerUp;
import no.ntnu.game.Models.PowerUpFactory;
import no.ntnu.game.Models.Score;
import no.ntnu.game.Models.TimeLimitBar;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import no.ntnu.game.Models.TreePart;
import no.ntnu.game.Models.TreeWithPowerUp;
import no.ntnu.game.Sound.ChopSoundEffectPlayer;
import no.ntnu.game.Views.Sprites.ChoppingKnightSprite;
import no.ntnu.game.Views.Sprites.DeadKnightSprite;
import no.ntnu.game.Views.Sprites.IdleKnightSprite;

/**
 * KnightController class is the main controller class to handle knight sprites,
 * tree and collision logic.
 *
 * @author Han
 */
public class KnightController {
    private final KnightModel KNIGHT;
    private final String GAME_MODE;
    private final ChoppingKnightSprite CHOPPING_KNIGHT_SPRITE;
    private final IdleKnightSprite IDLE_KNIGHT_SPRITE;
    private final DeadKnightSprite DEAD_KNIGHT_SPRITE;
    private final int KNIGHT_LEFT_X;
    private final int KNIGHT_RIGHT_X;
    private final int KNIGHT_Y;
    private int currentKnightX;

    private final TreeWithPowerUp TREE;

    private final int PHONE_WIDTH;
    private final float ANIMATION_DURATION = 0.12f; // Example duration in seconds

    private final float DEATH_ANIMATION_DURATION = 0.3f;

    private boolean choppingAnimationActive = false;
    private boolean deathAnimationActive = false;

    private final ChopSoundEffectPlayer SOUND_EFFECT_PLAYER;
    private float elapsedTime = 0;
    private final TimeLimitBar TIME_LIMIT_BAR;

    // Adjust the time to add for LastKnight, when knight successfully chops a tree
    // branch here
    // Lower = more difficult, Higher time = easier
    private float timeToAdd = 0.2f;

    // Power Ups
    private PowerUp life1;
    private PowerUp life2;
    private PowerUp life3;

    private final float POWERUP_Y = 30;
    private final float POWERUP_X1;
    private final float POWERUP_X2;
    private final float POWERUP_X3;
    private boolean life1Active = false;
    private boolean life2Active = false;
    private boolean life3Active = false;
    private boolean DoubleActive = false;
    private boolean bulletActive = false;

    private boolean playerDied = false;

    private final Score SCORE_COUNTER;
    public GameRoomController gameRoomController;
    private final TimeLimitBar BULLET_TIMER;
    private final float BULLET_TIMER_X;
    private final float BULLET_TIMER_Y;

    // Constructor with idle knight sprite X, Y coordinates and tree model
    // attributes
    public KnightController(String gamemode, int idleX, int idleY, TreeWithPowerUp tree, TimeLimitBar timeLimitBar,
            float maxTimeLimit) {
        this.GAME_MODE = gamemode;
        gameRoomController = GameRoomController.getInstance();
        SOUND_EFFECT_PLAYER = new ChopSoundEffectPlayer();
        SCORE_COUNTER = new Score();

        BULLET_TIMER = new TimeLimitBar(2f, 2f, 300f, 20f, -99999, -99999);
        BULLET_TIMER_X = (Gdx.graphics.getWidth() - 300f) / 2;
        BULLET_TIMER_Y = Gdx.graphics.getHeight() - 100f;

        KNIGHT = new KnightModel(1);
        CHOPPING_KNIGHT_SPRITE = new ChoppingKnightSprite();
        IDLE_KNIGHT_SPRITE = new IdleKnightSprite();
        DEAD_KNIGHT_SPRITE = new DeadKnightSprite();

        // Initialize lives sprites
        PowerUpFactory powerUpFactory = new PowerUpFactory();
        life1 = powerUpFactory.createLivesPowerUp();
        life2 = powerUpFactory.createLivesPowerUp();
        life3 = powerUpFactory.createLivesPowerUp();
        life1.setPosition(-99999, -99999);
        life2.setPosition(-99999, -99999);
        life3.setPosition(-99999, -99999);

        PHONE_WIDTH = Gdx.graphics.getWidth();

        KNIGHT.setDirection("left");
        this.KNIGHT_LEFT_X = idleX;
        this.KNIGHT_RIGHT_X = PHONE_WIDTH / 2 + KNIGHT_LEFT_X;
        this.currentKnightX = KNIGHT_LEFT_X;
        this.KNIGHT_Y = idleY;

        this.TREE = tree;
        this.TIME_LIMIT_BAR = timeLimitBar;

        // Set Power Up position
        POWERUP_X1 = Gdx.graphics.getWidth() - life1.textureRegion.getRegionWidth() - 200;
        POWERUP_X2 = POWERUP_X1 - life1.textureRegion.getRegionWidth() - 200;
        POWERUP_X3 = POWERUP_X2 - life1.textureRegion.getRegionWidth() - 200;
    }

    /*
     * Method to show the life power up sprite on the screen
     */
    public void getLife1() {
        life1.setPosition(POWERUP_X1, POWERUP_Y);
        life1Active = true;
    }

    /*
     * Method to show the life power up sprite on the screen
     */
    public void getLife2() {
        life2.setPosition(POWERUP_X2, POWERUP_Y);
        life2Active = true;
    }

    /*
     * Method to show the life power up sprite on the screen
     */
    public void getLife3() {
        life3.setPosition(POWERUP_X3, POWERUP_Y);
        life3Active = true;
    }

    public void getDouble(final PowerUp POWER_UP) {
        // Set DoubleActive to true
        DoubleActive = true;

        // Schedule a task to set DoubleActive to false after the duration
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                DoubleActive = false; // Reset DoubleActive to false after duration
            }
        }, POWER_UP.getDuration() / 1000f); // Duration needs to be in seconds, so divide by 1000
    }

    public void checkPowerUp() {
        PowerUp powerUp = TREE.trees.get(0).getPowerup();

        // Chopped tree with no powerup
        // Do nothing
        if (Objects.equals(powerUp, null)) {
            return;
        }

        if (Objects.equals(powerUp.getName(), "heart")) {
            // Adding life powerup
            if (life2Active) {
                getLife3();
            } else if (life1Active) {
                getLife2();
            } else {
                getLife1();
            }
            return;

        }

        if (Objects.equals(powerUp.getName(), "double")) {
            getDouble(powerUp);
            return;
        }

        if (Objects.equals(powerUp.getName(), "bullet")) {
            if (bulletActive) {
                BULLET_TIMER.resetTime();
            }
            bulletActive = true;
            BULLET_TIMER.setPosition(BULLET_TIMER_X, BULLET_TIMER_Y - 50);
            return;
        }

    }

    public void removePowerUp() {
        if (life3Active) {
            life3.setPosition(-99999, -99999);
            life3Active = false;
        } else if (life2Active) {
            life2.setPosition(-99999, -99999);
            life2Active = false;
        } else if (life1Active) {
            life1.setPosition(-99999, -99999);
            life1Active = false;
        }
    }

    // Methods to interact with the knight

    // moveRight() is used when knight's direction is left and right button is
    // clicked
    public void moveRight() {
        if (!playerDied) {
            KNIGHT.setDirection("right");
            CHOPPING_KNIGHT_SPRITE.flipDirection();
            IDLE_KNIGHT_SPRITE.flipDirection();
            DEAD_KNIGHT_SPRITE.flipDirection();

            TreePart lowestTreePart = TREE.trees.get(0);

            // If knight in opposite direction as branch -> no collision
            if (!Objects.equals(lowestTreePart.getValue(), KNIGHT.getDirection()) || bulletActive) {
                // switch knight direction and run chopping animation

                IDLE_KNIGHT_SPRITE.setPosition(-99999, -99999);
                CHOPPING_KNIGHT_SPRITE.setPosition(KNIGHT_RIGHT_X, KNIGHT_Y);
                currentKnightX = KNIGHT_RIGHT_X;

                choppingAnimationActive = true;
                SOUND_EFFECT_PLAYER.play();
                elapsedTime = 0;

                if (Objects.equals(GAME_MODE, "last_knight")) {
                    TIME_LIMIT_BAR.addTime(timeToAdd);
                }

                checkPowerUp();
            }

            // If knight in same direction as branch -> collision
            else if (Objects.equals(lowestTreePart.getValue(), KNIGHT.getDirection()) && !bulletActive) {
                IDLE_KNIGHT_SPRITE.setPosition(-99999, -99999);
                DEAD_KNIGHT_SPRITE.setPosition(KNIGHT_RIGHT_X, KNIGHT_Y);
                currentKnightX = KNIGHT_RIGHT_X;

                deathAnimationActive = true;
                elapsedTime = 0;
            }

        }

    }

    // stayRight() is used when knight's direction is right and right button is
    // clicked
    public void stayRight() {
        if (!playerDied) {
            KNIGHT.setDirection("right");
            TreePart lowestTreePart = TREE.trees.get(0);

            if (!Objects.equals(lowestTreePart.getValue(), KNIGHT.getDirection()) || bulletActive) {
                // switch knight direction and run chopping animation

                IDLE_KNIGHT_SPRITE.setPosition(-99999, -99999);

                CHOPPING_KNIGHT_SPRITE.setPosition(KNIGHT_RIGHT_X, KNIGHT_Y);
                currentKnightX = KNIGHT_RIGHT_X;

                choppingAnimationActive = true;
                SOUND_EFFECT_PLAYER.play();
                elapsedTime = 0;

                if (Objects.equals(GAME_MODE, "last_knight")) {
                    TIME_LIMIT_BAR.addTime(timeToAdd);
                }
                checkPowerUp();
            }

            else if (Objects.equals(lowestTreePart.getValue(), KNIGHT.getDirection()) && !bulletActive) {
                IDLE_KNIGHT_SPRITE.setPosition(-99999, -99999);
                DEAD_KNIGHT_SPRITE.setPosition(KNIGHT_RIGHT_X, KNIGHT_Y);
                currentKnightX = KNIGHT_RIGHT_X;

                deathAnimationActive = true;
                elapsedTime = 0;
            }
        }
    }

    // moveLeft() is used when knight's direction is right and left button is
    // clicked
    public void moveLeft() {
        if (!playerDied) {
            KNIGHT.setDirection("left");
            CHOPPING_KNIGHT_SPRITE.flipDirection();
            IDLE_KNIGHT_SPRITE.flipDirection();
            DEAD_KNIGHT_SPRITE.flipDirection();

            TreePart lowestTreePart = TREE.trees.get(0);

            if (!Objects.equals(lowestTreePart.getValue(), KNIGHT.getDirection()) || bulletActive) {
                // switch knight direction and run chopping animation

                IDLE_KNIGHT_SPRITE.setPosition(-99999, -99999);
                CHOPPING_KNIGHT_SPRITE.setPosition(KNIGHT_LEFT_X, KNIGHT_Y);
                currentKnightX = KNIGHT_LEFT_X;

                choppingAnimationActive = true;
                SOUND_EFFECT_PLAYER.play();
                elapsedTime = 0;

                if (Objects.equals(GAME_MODE, "last_knight")) {
                    TIME_LIMIT_BAR.addTime(timeToAdd);
                }
                checkPowerUp();
            }

            if (Objects.equals(lowestTreePart.getValue(), KNIGHT.getDirection()) && !bulletActive) {
                IDLE_KNIGHT_SPRITE.setPosition(-99999, -99999);
                DEAD_KNIGHT_SPRITE.setPosition(KNIGHT_LEFT_X, KNIGHT_Y);
                currentKnightX = KNIGHT_LEFT_X;

                deathAnimationActive = true;
                elapsedTime = 0;
            }
        }
    }

    // stayLeft() is used when knight's direction is left and left button is clicked
    public void stayLeft() {
        if (!playerDied) {
            KNIGHT.setDirection("left");
            TreePart lowestTreePart = TREE.trees.get(0);

            if (!Objects.equals(lowestTreePart.getValue(), KNIGHT.getDirection()) || bulletActive) {
                // switch knight direction and run chopping animation

                IDLE_KNIGHT_SPRITE.setPosition(-99999, -99999);

                CHOPPING_KNIGHT_SPRITE.setPosition(KNIGHT_LEFT_X, KNIGHT_Y);
                currentKnightX = KNIGHT_LEFT_X;

                choppingAnimationActive = true;
                SOUND_EFFECT_PLAYER.play();
                elapsedTime = 0;

                if (Objects.equals(GAME_MODE, "last_knight")) {
                    TIME_LIMIT_BAR.addTime(timeToAdd);
                }
                checkPowerUp();
            }

            if (Objects.equals(lowestTreePart.getValue(), KNIGHT.getDirection()) && !bulletActive) {
                IDLE_KNIGHT_SPRITE.setPosition(-99999, -99999);
                DEAD_KNIGHT_SPRITE.setPosition(KNIGHT_LEFT_X, KNIGHT_Y);
                currentKnightX = KNIGHT_LEFT_X;

                deathAnimationActive = true;
                elapsedTime = 0;
            }
        }
    }

    // Update() function is the main function which handles game logic -
    // knight sprites position,
    // collision detection,
    // chop tree function,
    // to add a new tree after chopping
    public String update(float delta) {
        TreePart lowestTreePart = TREE.trees.get(0);

        if (bulletActive) {
            BULLET_TIMER.updateTime(delta);
            if (BULLET_TIMER.isTimeUp()) {
                BULLET_TIMER.setPosition(-99999, -99999);
                bulletActive = false;
            }
        }
        // Logic to run chopping knight animation
        if (choppingAnimationActive) {

            elapsedTime += delta;
            CHOPPING_KNIGHT_SPRITE.setPosition(currentKnightX + 0.1f * elapsedTime, KNIGHT_Y);

            // Logic to end chopping knight animation, render idle knight again
            if (elapsedTime >= ANIMATION_DURATION) {
                choppingAnimationActive = false;
                elapsedTime = 0;

                CHOPPING_KNIGHT_SPRITE.setPosition(-99999, -99999);
                IDLE_KNIGHT_SPRITE.setPosition(currentKnightX, KNIGHT_Y);

                // Logic to check for knight collision and chop tree

                if (bulletActive) {

                    TREE.chop();
                    TREE.createNewTrunk();

                    // if game mode is last knight standing, increment score, else if game mode is
                    // fastest knight, decrement score
                    if (Objects.equals(GAME_MODE, "last_knight")) {
                        if (DoubleActive) {
                            SCORE_COUNTER.incrementScore(2);

                        } else {
                            SCORE_COUNTER.incrementScore(1);
                        }
                    } else if (Objects.equals(GAME_MODE, "fastest_knight")) {
                        if (DoubleActive) {
                            SCORE_COUNTER.decrementScore(2);

                        } else {
                            SCORE_COUNTER.decrementScore(1);
                        }
                    }

                } else {
                    BULLET_TIMER.setPosition(-99999, -99999);

                    if (!Objects.equals(lowestTreePart.getValue(), KNIGHT.getDirection())) {
                        TREE.chop();
                        TREE.createNewTrunk();

                        // if game mode is last knight standing, increment score, else if game mode is
                        // fastest knight, decrement score
                        if (Objects.equals(GAME_MODE, "last_knight")) {
                            if (DoubleActive) {
                                SCORE_COUNTER.incrementScore(2);

                            } else {
                                SCORE_COUNTER.incrementScore(1);
                            }
                        } else if (Objects.equals(GAME_MODE, "fastest_knight")) {
                            if (DoubleActive) {
                                SCORE_COUNTER.decrementScore(2);

                            } else {
                                SCORE_COUNTER.decrementScore(1);
                            }
                        }

                        // Checking for next collision after chopping the tree
                        lowestTreePart = TREE.trees.get(0);
                        if (Objects.equals(lowestTreePart.getValue(), KNIGHT.getDirection())) {
                            IDLE_KNIGHT_SPRITE.setPosition(-99999, -99999);
                            deathAnimationActive = true;
                            elapsedTime = 0;
                        }
                    }
                }
            }
        }

        // Logic to handle death knight animation
        if (deathAnimationActive) {

            playerDied = true;

            elapsedTime += delta;
            DEAD_KNIGHT_SPRITE.setPosition(currentKnightX + 0.1f * elapsedTime, KNIGHT_Y);

            // End of death animation
            if (elapsedTime >= DEATH_ANIMATION_DURATION) {
                deathAnimationActive = false;
                elapsedTime = 0;

                if (life1Active) {
                    playerDied = false;
                    // Remove life power up
                    removePowerUp();
                    String oppositeDirection = getKnightOppositeDirection();
                    KNIGHT.setDirection(oppositeDirection);
                    if (Objects.equals(oppositeDirection, "left")) {
                        IDLE_KNIGHT_SPRITE.setPosition(KNIGHT_LEFT_X, KNIGHT_Y);
                        DEAD_KNIGHT_SPRITE.setPosition(-99999, -99999);
                        currentKnightX = KNIGHT_LEFT_X;
                    } else {
                        IDLE_KNIGHT_SPRITE.setPosition(KNIGHT_RIGHT_X, KNIGHT_Y);
                        DEAD_KNIGHT_SPRITE.setPosition(-99999, -99999);
                        currentKnightX = KNIGHT_RIGHT_X;
                    }

                    IDLE_KNIGHT_SPRITE.flipDirection();
                    CHOPPING_KNIGHT_SPRITE.flipDirection();
                    DEAD_KNIGHT_SPRITE.flipDirection();

                    return "continue";
                }

                return "lose";
            }
        }

        return "continue";
    }

    public void setScore(int amount) {
        this.SCORE_COUNTER.setScore(amount);
    }

    public String getKnightOppositeDirection() {
        if (Objects.equals(KNIGHT.getDirection(), "left")) {
            return "right";
        }
        return "left";
    }

    public String getDirection() {
        return KNIGHT.getDirection();
    }

    public void renderIdleKnight(SpriteBatch batch) {
        IDLE_KNIGHT_SPRITE.render(batch);
    }

    public void setIdlePosition(float x, float y) {
        IDLE_KNIGHT_SPRITE.setPosition(x, y);
    }

    public void setChoppingPosition(float x, float y) {
        CHOPPING_KNIGHT_SPRITE.setPosition(x, y);
    }

    public void renderChoppingKnight(SpriteBatch batch) {
        CHOPPING_KNIGHT_SPRITE.render(batch);
    }

    public void renderDeadKnight(SpriteBatch batch) {
        DEAD_KNIGHT_SPRITE.render(batch);
    }

    public void renderLife1(SpriteBatch batch) {
        life1.render(batch);
    }

    public void renderLife2(SpriteBatch batch) {
        life2.render(batch);
    }

    public void renderLife3(SpriteBatch batch) {
        life3.render(batch);
    }

    public int getScore() {
        return SCORE_COUNTER.getLocalPlayerScore();
    }

    public void setDeadPosition(float x, float y) {
        DEAD_KNIGHT_SPRITE.setPosition(x, y);
    }

    public void renderBulletTimer(ShapeRenderer shapeRenderer) {

        BULLET_TIMER.render(shapeRenderer);
    }

    public void disposeIdleKnight() {
        IDLE_KNIGHT_SPRITE.dispose();
    }

    public void disposeChoppingKnight() {
        CHOPPING_KNIGHT_SPRITE.dispose();
    }

}
