package no.ntnu.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RunningKnightSprite {
    private Animation<TextureRegion> knightAnimation;
    private Texture spriteSheet;
    private float stateTime;
    private TextureRegion[] knightFrames;
    private Sprite knightSprite;

    private int knightHeight;
    private int knightWidth;
    private float x, y; // Position of the sprite

    public RunningKnightSprite() {
        // Initialize sprite position
        x = 0;
        y = 0;

        // Load the textures from file
        spriteSheet = new Texture(Gdx.files.internal("running_sheet.png"));
        int frameWidth = spriteSheet.getWidth() / 10;
        int frameHeight = spriteSheet.getHeight();
        knightFrames = new TextureRegion[10];

        for (int i = 0; i < 10; i++) {
            knightFrames[i] = new TextureRegion(spriteSheet, i * frameWidth, 0, frameWidth, frameHeight);
        }

        knightAnimation = new Animation<>(0.1f, knightFrames);

        knightSprite = new Sprite(knightAnimation.getKeyFrame(0));
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
        // Render the knight sprite running left to right of the screen
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = knightAnimation.getKeyFrame(stateTime, true);

        System.out.println("State time: " + stateTime + ", Frame index: " + knightAnimation.getKeyFrameIndex(stateTime));

        // Draw the sprite onto the batch
        batch.begin();
        knightWidth = currentFrame.getRegionWidth() * 6;
        knightHeight = currentFrame.getRegionHeight() * 6;
        batch.draw(currentFrame, x, y, knightWidth, knightHeight);
        batch.end();
    }

    // Additional methods for sprite animation, if needed
    public void setBounds (float x, float y, float width, float height) {
        knightSprite.setBounds(x, y, width, height);
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

