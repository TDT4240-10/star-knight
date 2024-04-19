package no.ntnu.game.Views.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Idle Knight Sprite View class to render idle knight animation
 *
 * @author Han
 */
public class IdleKnightSprite {
    private final Animation<TextureRegion> KNIGHT_ANIMATION;
    private final TextureRegion[] KNIGHT_FRAMES;

    private int knightHeight;
    private int knightWidth;

    private float stateTime;
    private float x, y; // Position of the sprite

    public IdleKnightSprite() {
        // Initialize sprite position
        x = 0;
        y = 0;

        // Load the textures from file
        KNIGHT_FRAMES = new TextureRegion[10];
        for (int i = 0; i < 10; i++) {
            KNIGHT_FRAMES[i] = new TextureRegion(new Texture("idle_frames/idle_frame" + i + ".png"));
        }

        KNIGHT_ANIMATION = new Animation<>(0.1f, KNIGHT_FRAMES); // Frame duration 0.1 seconds
    }

    public void flipDirection() {
        for (TextureRegion region : KNIGHT_FRAMES) {
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
