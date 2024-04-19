package no.ntnu.game.Views.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * Animation of winning Knight running across the screen
 *
 * @author Deen
 */
public class WinRunningKnightSprite {
    private final Animation<TextureRegion> KNIGHT_ANIMATION;
    private float stateTime;
    private final TextureRegion[] KNIGHT_FRAMES;
    private int knightHeight;
    private int knightWidth;
    private float x, y; // Position of the sprite

    public WinRunningKnightSprite() {
        // Initialize sprite position
        x = 0;
        y = 0;

        // Load the textures from file
        Texture spriteSheet = new Texture(Gdx.files.internal("win_running_sheet.png"));
        int frameWidth = spriteSheet.getWidth() / 10;
        int frameHeight = spriteSheet.getHeight();
        KNIGHT_FRAMES = new TextureRegion[10];

        for (int i = 0; i < 10; i++) {
            KNIGHT_FRAMES[i] = new TextureRegion(spriteSheet, i * frameWidth, 0, frameWidth, frameHeight);
        }

        KNIGHT_ANIMATION = new Animation<>(0.1f, KNIGHT_FRAMES);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        // Render the knight sprite running left to right of the screen
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = KNIGHT_ANIMATION.getKeyFrame(stateTime, true);

        // Draw the sprite onto the batch
        batch.begin();
        knightWidth = currentFrame.getRegionWidth() * 6;
        knightHeight = currentFrame.getRegionHeight() * 6;
        batch.draw(currentFrame, x, y, knightWidth, knightHeight);
        batch.end();
    }

    public int getHeight() {
        return knightHeight;
    }

    public int getWidth() {
        return knightWidth;
    }

    public void dispose() {
        // Dispose of the sprite's texture when no longer needed
        for (TextureRegion frame : KNIGHT_FRAMES) {
            frame.getTexture().dispose();
        }
    }
}

