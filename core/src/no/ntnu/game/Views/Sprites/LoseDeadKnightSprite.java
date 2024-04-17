package no.ntnu.game.Views.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Animation of lost Knight dead in the center of the screen
 *
 * @author Deen
 */
public class LoseDeadKnightSprite {
    private final Animation<TextureRegion> knightAnimation;
    private float stateTime;
    private final TextureRegion[] knightFrames;
    private int knightHeight;
    private int knightWidth;
    private float x, y; // Position of the sprite

    public LoseDeadKnightSprite() {
        // Initialize sprite position
        x = 0;
        y = 0;

        // Load the textures from file
        Texture spriteSheet = new Texture(Gdx.files.internal("lose_dead_sheet.png"));
        int frameWidth = spriteSheet.getWidth() / 10;
        int frameHeight = spriteSheet.getHeight();
        knightFrames = new TextureRegion[10];

        for (int i = 0; i < 10; i++) {
            knightFrames[i] = new TextureRegion(spriteSheet, i * frameWidth, 0, frameWidth, frameHeight);
        }

        knightAnimation = new Animation<>(0.1f, knightFrames);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        // Render the knight sprite running left to right of the screen
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = knightAnimation.getKeyFrame(stateTime, true);

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
        for (TextureRegion frame : knightFrames) {
            frame.getTexture().dispose();
        }
    }

}
