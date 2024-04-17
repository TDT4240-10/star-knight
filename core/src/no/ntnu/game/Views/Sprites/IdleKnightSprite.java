package no.ntnu.game.Views.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Idle Knight Sprite View class to render idle knight animation
 *
 * @author Han
 */
public class IdleKnightSprite {
    private final Animation<TextureRegion> knightAnimation;
    private final TextureRegion[] knightFrames;

    private int knightHeight;
    private int knightWidth;

    private float stateTime;
    private float x, y; // Position of the sprite

    public IdleKnightSprite() {
        // Initialize sprite position
        x = 0;
        y = 0;

        // Load the textures from file
        knightFrames = new TextureRegion[10];
        for (int i = 0; i < 10; i++) {
            knightFrames[i] = new TextureRegion(new Texture("idle_frames/idle_frame" + i + ".png"));
        }

        knightAnimation = new Animation<>(0.1f, knightFrames); // Frame duration 0.1 seconds
    }

    public void flipDirection() {
        for (TextureRegion region : knightFrames) {
            region.flip(true, false);
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        // Render the sprite on the screen
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
