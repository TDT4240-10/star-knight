package no.ntnu.game.Views.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Chopping Knight Sprite View class to render chopping knight animation
 *
 * @author Han
 */
public class ChoppingKnightSprite {
    private final Animation<TextureRegion> KNIGHT_ANIMATION;
    private final TextureRegion[] KNIGHT_FRAMES;
    private final Sprite KNIGHT_SPRITE;
    private float stateTime;
    private float x, y; // Position of the sprite

    public ChoppingKnightSprite() {
        // Initialize sprite position
        x = 0;
        y = 0;

        // Load the textures from file
        KNIGHT_FRAMES = new TextureRegion[6];
        for (int i = 0; i < KNIGHT_FRAMES.length; i++) {
            KNIGHT_FRAMES[i] = new TextureRegion(new Texture("chopping_frames/frame" + i + ".png"));
        }

        KNIGHT_ANIMATION = new Animation<>(0.02f, KNIGHT_FRAMES); // Frame duration 0.1 seconds
        KNIGHT_SPRITE = new Sprite(KNIGHT_ANIMATION.getKeyFrame(0));
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
        batch.begin();
        // Render the sprite on the screen
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = KNIGHT_ANIMATION.getKeyFrame(stateTime, true);

        // Draw the sprite onto the batch with scaling
        int knightWidth = currentFrame.getRegionWidth() * 6; // Original width multiplied by scale factor
        int knightHeight = currentFrame.getRegionHeight() * 6; // Original height multiplied by scale factor
        KNIGHT_SPRITE.setRegion(currentFrame);
        KNIGHT_SPRITE.setBounds(x, y, knightWidth, knightHeight); // Set bounds with scaled dimensions
        KNIGHT_SPRITE.draw(batch);
        batch.end();
    }

    public void setBounds(float x, float y, float width, float height) {
        KNIGHT_SPRITE.setBounds(x, y, width, height);
    }

    public void dispose() {
        // Dispose of the sprite's texture when no longer needed
        for (TextureRegion frame : KNIGHT_FRAMES) {
            frame.getTexture().dispose();
        }
    }
}
