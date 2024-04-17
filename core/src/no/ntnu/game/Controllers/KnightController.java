package no.ntnu.game.Controllers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Objects;

import no.ntnu.game.Models.KnightModel;
import no.ntnu.game.Models.PowerUp;
import no.ntnu.game.Models.PowerUpFactory;
import no.ntnu.game.Models.Score;
import no.ntnu.game.Models.TimeLimitBar;
import no.ntnu.game.Models.Settings;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import no.ntnu.game.Models.TreePart;
import no.ntnu.game.Models.TreeWithPowerUp;
import no.ntnu.game.Views.Sprites.ChoppingKnightSprite;
import no.ntnu.game.Views.Sprites.DeadKnightSprite;
import no.ntnu.game.Views.Sprites.IdleKnightSprite;

/**
 * KnightController class is the main controller class to handle knight sprites, tree and collision logic.
 *
 * @author Han
 */
public class KnightController {
    private KnightModel knight;
    private String gamemode;
    private ChoppingKnightSprite choppingKnightSprite;
    private IdleKnightSprite idleKnightSprite;
    private DeadKnightSprite deadKnightSprite;
    private int knightLeftX;
    private int knightRightX;
    private int knightY;
    private int currentKnightX;

    private TreeWithPowerUp tree;

    private int phoneWidth;
    private float animationDuration = 0.12f; // Example duration in seconds

    private float deathAnimationDuration = 0.3f;
    private Music backgroundMusic;
    private boolean choppingAnimationActive = false;
    private boolean deathAnimationActive = false;
    private boolean playChopSound = true;
    private float elapsedTime = 0;
    private TimeLimitBar timeLimitBar;
    private float maxTimeLimit;

    // Adjust the time to add for LastKnight, when knight successfully chops a tree branch here
    // Lower = more difficult, Higher time = easier
    private float timeToAdd = 0.2f;

    // Power Ups
    private PowerUp life1;
    private PowerUp life2;
    private PowerUp life3;
    private PowerUp doublepoints;
    private PowerUp bullet;


    private float powerUpY = 30;
    private float powerUpX1;
    private float powerUpX2;
    private float powerUpX3;
    private float time;
    private boolean life1Active = false;
    private boolean life2Active = false;
    private boolean life3Active = false;
    private boolean DoubleActive = false;
    private boolean bulletActive = false;

    private boolean playerDied = false;
    private PowerUpFactory powerUpFactory;

    private Score scoreCounter;
    //    private Timer timer;
    private Settings settings;
    private Sound chopSoundEffect;
    public GameRoomController gameRoomController;
    private TimeLimitBar bulletTimer;
    private float bulletTimerX;
    private float bulletTimerY;
    private boolean moreThanOneBullet = false;


    // Constructor with idle knight sprite X, Y coordinates and tree model attributes
    public KnightController(String gamemode, int idleX, int idleY, TreeWithPowerUp tree, TimeLimitBar timeLimitBar, float maxTimeLimit) {
        this.gamemode = gamemode;
        gameRoomController = GameRoomController.getInstance();
        scoreCounter = new Score();

        bulletTimer = new TimeLimitBar(2f, 2f, 300f, 20f, -99999, -99999);
//        bulletTimer = new TimeLimitBar(5f, 5f, 300f, 20f, (Gdx.graphics.getWidth() - 300f) / 2, Gdx.graphics.getHeight() - 100f);
        bulletTimerX = (Gdx.graphics.getWidth() - 300f) / 2;
        bulletTimerY = Gdx.graphics.getHeight() - 100f;

//        timer = new Timer();
        knight = new KnightModel(1);
        choppingKnightSprite = new ChoppingKnightSprite();
        idleKnightSprite = new IdleKnightSprite();
        deadKnightSprite = new DeadKnightSprite();

        life1 = PowerUpFactory.createLivesPowerUp();
        life2 = PowerUpFactory.createLivesPowerUp();
        life3 = PowerUpFactory.createLivesPowerUp();

        life1.setPosition(-99999, -99999);
        life2.setPosition(-99999, -99999);
        life3.setPosition(-99999, -99999);
        doublepoints = PowerUpFactory.createDoublePoints();
        bullet = PowerUpFactory.createBullet();
        phoneWidth = Gdx.graphics.getWidth();

        knight.setDirection("left");
        this.knightLeftX = idleX;
        this.knightRightX = phoneWidth / 2 + knightLeftX;
        this.currentKnightX = knightLeftX;
        this.knightY = idleY;

        this.tree = tree;
        this.timeLimitBar = timeLimitBar;
        this.maxTimeLimit = maxTimeLimit;

        // Set Power Up position
        System.out.println(life1.textureRegion.getRegionWidth());
        powerUpX1 = Gdx.graphics.getWidth() - life1.textureRegion.getRegionWidth() - 200;
        powerUpX2 = powerUpX1 - life1.textureRegion.getRegionWidth() - 200;
        powerUpX3 = powerUpX2 - life1.textureRegion.getRegionWidth() - 200;

        settings = Settings.getInstance();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("HinaCC0_011_Fallen_leaves(chosic.com).mp3"));
        backgroundMusic.setVolume(settings.getMusic());
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        chopSoundEffect = Gdx.audio.newSound(Gdx.files.internal("audio_cut.mp3"));
    }

    public void getLife1() {
        life1.setPosition(powerUpX1, powerUpY);
        life1Active = true;
        System.out.println("set life 1 pos");
    }

    public void getLife2() {
        life2.setPosition(powerUpX2, powerUpY);
        life2Active = true;
    }

    public void getLife3() {
        life3.setPosition(powerUpX3, powerUpY);
        life3Active = true;
    }

    public void getDouble(final PowerUp powerUp) {
        // Set DoubleActive to true
        DoubleActive = true;

        // Schedule a task to set DoubleActive to false after the duration
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                DoubleActive = false; // Reset DoubleActive to false after duration
            }
        }, powerUp.getDuration() / 1000f); // Duration needs to be in seconds, so divide by 1000
    }

    public void checkPowerUp() {
        PowerUp powerUp = tree.trees.get(0).getPowerup();
        if (Objects.equals(powerUp, null)) {
            System.out.println("tree direction: " + tree.trees.get(tree.trees.size() - 1).getValue());
            System.out.println("chopped tree with no powerup");
        }
        else {
            if (Objects.equals(powerUp.getName(), "heart")) {
                System.out.println("tree direction: " + tree.trees.get(tree.trees.size() - 1).getValue());
                System.out.println("chopped tree with heart");

                // Adding life powerup
                if (life2Active) {
                    getLife3();
                }
                else if (life1Active){
                    getLife2();
                }
                else {
                    getLife1();
                }
            } else if (Objects.equals(powerUp.getName(), "shield")) {
                System.out.println("tree direction: " + tree.trees.get(tree.trees.size() - 1).getValue());
                System.out.println("chopped tree with shield");
            }
            else if (Objects.equals(powerUp.getName(), "double")) {
                System.out.println("tree direction: " + tree.trees.get(tree.trees.size() - 1).getValue());
                System.out.println("chopped tree with double");
                // Adding double points
                getDouble(powerUp);
            }
            else if (Objects.equals(powerUp.getName(), "bullet")) {
                System.out.println("tree direction: " + tree.trees.get(tree.trees.size() - 1).getValue());
                System.out.println("chopped tree with bullet");
                // Adding double points
                if (bulletActive) {
                    bulletTimer.resetTime();
                }
                bulletActive = true;
                bulletTimer.setPosition(bulletTimerX, bulletTimerY - 50);
//                getBullet(powerUp);
            }
        }

        this.tree = tree;
    }

    public void removePowerUp() {
        if (life3Active) {
            life3.setPosition(-99999, -99999);
            life3Active = false;
        }
        else if (life2Active) {
            life2.setPosition(-99999, -99999);
            life2Active = false;
        }
        else if (life1Active) {
            life1.setPosition(-99999, -99999);
            life1Active = false;
        }
    }

    // Methods to interact with the knight

    // moveRight() is used when knight's direction is left and right button is clicked
    public void moveRight() {
        if (!playerDied) {
            knight.setDirection("right");
            choppingKnightSprite.flipDirection();
            idleKnightSprite.flipDirection();
            deadKnightSprite.flipDirection();

            TreePart lowestTreePart = tree.trees.get(0);
            System.out.println("move right, tree is: " + lowestTreePart.getValue());

            // If knight in opposite direction as branch -> no collision
            if (!Objects.equals(lowestTreePart.getValue(), knight.getDirection()) || bulletActive){
                // switch knight direction and run chopping animation

                idleKnightSprite.setPosition(-99999, -99999);
                choppingKnightSprite.setPosition(knightRightX, knightY);
                currentKnightX = knightRightX;

                choppingAnimationActive = true;
                elapsedTime = 0;

                if (Objects.equals(gamemode, "last_knight")) {
                    timeLimitBar.addTime(timeToAdd);
                }

                checkPowerUp();
            }

            // If knight in same direction as branch -> collision
            else if (Objects.equals(lowestTreePart.getValue(), knight.getDirection()) && !bulletActive) {
                idleKnightSprite.setPosition(-99999, -99999);
                deadKnightSprite.setPosition(knightRightX, knightY);
                currentKnightX = knightRightX;

                deathAnimationActive = true;
                elapsedTime = 0;
            }


        }

    }

    // stayRight() is used when knight's direction is right and right button is clicked
    public void stayRight() {
        if (!playerDied) {
            knight.setDirection("right");
            TreePart lowestTreePart = tree.trees.get(0);
            System.out.println("stay right, tree is: " + lowestTreePart.getValue());

            if (!Objects.equals(lowestTreePart.getValue(), knight.getDirection()) || bulletActive){
                // switch knight direction and run chopping animation

                idleKnightSprite.setPosition(-99999, -99999);

                choppingKnightSprite.setPosition(knightRightX, knightY);
                currentKnightX = knightRightX;

                choppingAnimationActive = true;
                elapsedTime = 0;

                if (Objects.equals(gamemode, "last_knight")) {
                    timeLimitBar.addTime(timeToAdd);
                }
                checkPowerUp();
            }

            else if (Objects.equals(lowestTreePart.getValue(), knight.getDirection()) && !bulletActive) {
                idleKnightSprite.setPosition(-99999, -99999);
                deadKnightSprite.setPosition(knightRightX, knightY);
                currentKnightX = knightRightX;

                deathAnimationActive = true;
                elapsedTime = 0;
            }
        }
    }

    // moveLeft() is used when knight's direction is right and left button is clicked
    public void moveLeft() {
        if (!playerDied) {
            knight.setDirection("left");
            choppingKnightSprite.flipDirection();
            idleKnightSprite.flipDirection();
            deadKnightSprite.flipDirection();

            TreePart lowestTreePart = tree.trees.get(0);
            System.out.println("move left, tree is: " + lowestTreePart.getValue());

            if(!Objects.equals(lowestTreePart.getValue(), knight.getDirection()) || bulletActive) {
                // switch knight direction and run chopping animation

                idleKnightSprite.setPosition(-99999, -99999);
                choppingKnightSprite.setPosition(knightLeftX, knightY);
                currentKnightX = knightLeftX;

                choppingAnimationActive = true;
                elapsedTime = 0;

                if (Objects.equals(gamemode, "last_knight")) {
                    timeLimitBar.addTime(timeToAdd);
                }
                checkPowerUp();
            }

            if (Objects.equals(lowestTreePart.getValue(), knight.getDirection()) && !bulletActive) {
                idleKnightSprite.setPosition(-99999, -99999);
                deadKnightSprite.setPosition(knightLeftX, knightY);
                currentKnightX = knightLeftX;

                deathAnimationActive = true;
                elapsedTime = 0;
            }
        }
    }

    // stayLeft() is used when knight's direction is left and left button is clicked
    public void stayLeft() {
        if (!playerDied) {
            knight.setDirection("left");
            TreePart lowestTreePart = tree.trees.get(0);
            System.out.println("stay left, tree is: " + lowestTreePart.getValue());

            if (!Objects.equals(lowestTreePart.getValue(), knight.getDirection()) || bulletActive){
                // switch knight direction and run chopping animation

                idleKnightSprite.setPosition(-99999, -99999);

                choppingKnightSprite.setPosition(knightLeftX, knightY);
                currentKnightX = knightLeftX;

                choppingAnimationActive = true;
                elapsedTime = 0;

                if (Objects.equals(gamemode, "last_knight")) {
                    timeLimitBar.addTime(timeToAdd);
                }
                checkPowerUp();
            }

            if (Objects.equals(lowestTreePart.getValue(), knight.getDirection()) && !bulletActive) {
                idleKnightSprite.setPosition(-99999, -99999);
                deadKnightSprite.setPosition(knightLeftX, knightY);
                currentKnightX = knightLeftX;

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
        // Get the current lowest tree part=
        TreePart lowestTreePart = tree.trees.get(0);
        if (lowestTreePart.getPowerup() != null){
            String powerUp = lowestTreePart.getPowerup().getName();
            //System.out.println("lowest tree direction: " + lowestTreePart.getValue() + ", " + powerUp);
        }
        else{
            //System.out.println("lowest tree direction: " + lowestTreePart.getValue() + ", no powerup");
        }

        if (bulletActive) {
            bulletTimer.updateTime(delta);
            System.out.println(bulletTimer.getCurrentTime());
            if (bulletTimer.isTimeUp()) {
                bulletTimer.setPosition(-99999, -99999);
                bulletActive = false;
            }
        }
        // Logic to run chopping knight animation
        if (choppingAnimationActive) {

            if(playChopSound){
                chopSoundEffect.play(settings.getSound() * 0.5f);
                playChopSound = false;
            }

            elapsedTime += delta;
            choppingKnightSprite.setPosition(currentKnightX + 0.1f * elapsedTime, knightY);

            // Logic to end chopping knight animation, render idle knight again
            if (elapsedTime >= animationDuration) {
                choppingAnimationActive = false;
                elapsedTime = 0;

                choppingKnightSprite.setPosition(-99999, -99999);
                idleKnightSprite.setPosition(currentKnightX, knightY);

                // Logic to check for knight collision and chop tree

                if (bulletActive) {

                    tree.chop();
                    tree.createNewTrunk();

                    // if game mode is last knight standing, increment score, else if game mode is fastest knight, decrement score
                    if (Objects.equals(gamemode, "last_knight")) {
                        if (DoubleActive) {
                            scoreCounter.incrementScore(2);

                        } else {
                            scoreCounter.incrementScore(1);
                        }
                    } else if (Objects.equals(gamemode, "fastest_knight")) {
                        System.out.println("fastest knight found, decrement score");
                        if (DoubleActive) {
                            scoreCounter.decrementScore(2);

                        } else {
                            scoreCounter.decrementScore(1);
                        }
                    }

                } else {
                    bulletTimer.setPosition(-99999, -99999);

                    if (!Objects.equals(lowestTreePart.getValue(), knight.getDirection())) {
                        tree.chop();
                        tree.createNewTrunk();

                        // if game mode is last knight standing, increment score, else if game mode is fastest knight, decrement score
                        if (Objects.equals(gamemode, "last_knight")) {
                            if (DoubleActive) {
                                scoreCounter.incrementScore(2);

                            } else {
                                scoreCounter.incrementScore(1);
                            }
                        } else if (Objects.equals(gamemode, "fastest_knight")) {
                            System.out.println("fastest knight found, decrement score");
                            if (DoubleActive) {
                                scoreCounter.decrementScore(2);

                            } else {
                                scoreCounter.decrementScore(1);
                            }
                        }

                        // Checking for next collision after chopping the tree
                        lowestTreePart = tree.trees.get(0);
                        if (Objects.equals(lowestTreePart.getValue(), knight.getDirection())) {
                            System.out.println("chopped tree and died");

                            idleKnightSprite.setPosition(-99999, -99999);
                            deathAnimationActive = true;
                            elapsedTime = 0;
                            this.stopMusic();
                        }
                    }
                }
            }
        }
        else {
            playChopSound = true;
        }

        // Logic to handle death knight animation
        if (deathAnimationActive) {

            playerDied = true;

            elapsedTime += delta;
            deadKnightSprite.setPosition(currentKnightX + 0.1f * elapsedTime, knightY);

            // End of death animation
            if (elapsedTime >= deathAnimationDuration) {
                deathAnimationActive = false;
                elapsedTime = 0;

                if (life1Active) {
                    playerDied = false;
                    System.out.println("collision, remove 1 life");
                    removePowerUp();
                    String oppositeDirection = getKnightOppositeDirection();
                    knight.setDirection(oppositeDirection);
                    if (Objects.equals(oppositeDirection, "left")) {
                        idleKnightSprite.setPosition(knightLeftX, knightY);
                        deadKnightSprite.setPosition(-99999, -99999);
                        currentKnightX = knightLeftX;
                    }
                    else{
                        idleKnightSprite.setPosition(knightRightX, knightY);
                        deadKnightSprite.setPosition(-99999, -99999);
                        currentKnightX = knightRightX;
                    }

                    idleKnightSprite.flipDirection();
                    choppingKnightSprite.flipDirection();
                    deadKnightSprite.flipDirection();


                    return "continue";
                }

                return "lose";
            }
        }

        return "continue";
    }

    public void setScore(int amount) {
        this.scoreCounter.setScore(amount);
    }

    public String getKnightOppositeDirection() {
        if (Objects.equals(knight.getDirection(), "left")) {
            return "right";
        }
        return "left";
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
        return scoreCounter.getLocalPlayerScore();
    }

    public void setDeadPosition(float x, float y) {
        deadKnightSprite.setPosition(x, y);
    }

    public void renderBulletTimer(ShapeRenderer shapeRenderer) {

        bulletTimer.render(shapeRenderer);
    }

    public void disposeIdleKnight() {
        idleKnightSprite.dispose();
    }

    public void disposeChoppingKnight() {
        choppingKnightSprite.dispose();
    }

    public void stopMusic() {
        if (backgroundMusic != null) {
            if(backgroundMusic.isPlaying()) {
                backgroundMusic.stop();
            }
        }
    };

}

