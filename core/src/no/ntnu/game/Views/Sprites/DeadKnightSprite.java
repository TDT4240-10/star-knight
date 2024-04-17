package no.ntnu.game.Views.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Dead Knight Sprite View class to render dead knight animation
 *
 * @author Han
 */
public class DeadKnightSprite {
    private Animation<TextureRegion> knightAnimation;
    private TextureRegion[] knightFrames;
    private Sprite knightSprite;

    private int knightHeight;
    private int knightWidth;

    private float stateTime;
    private float x, y; // Position of the sprite

    public DeadKnightSprite() {
        // Initialize sprite position
        x = 0;
        y = 0;

        // Load the textures from file
        Texture frame0= new Texture("death_frames/frame0.png");
        Texture frame1 = new Texture("death_frames/frame1.png");
        Texture frame2 = new Texture("death_frames/frame2.png");
        Texture frame3 = new Texture("death_frames/frame3.png");
        Texture frame4 = new Texture("death_frames/frame4.png");
        Texture frame5 = new Texture("death_frames/frame5.png");
        Texture frame6 = new Texture("death_frames/frame5.png");
        Texture frame7 = new Texture("death_frames/frame7.png");
        Texture frame8 = new Texture("death_frames/frame8.png");
        Texture frame9 = new Texture("death_frames/frame9.png");

        knightFrames = new TextureRegion[10];
        knightFrames[0] = new TextureRegion(frame0);
        knightFrames[1] = new TextureRegion(frame1);
        knightFrames[2] = new TextureRegion(frame2);
        knightFrames[3] = new TextureRegion(frame3);
        knightFrames[4] = new TextureRegion(frame4);
        knightFrames[5] = new TextureRegion(frame5);
        knightFrames[6] = new TextureRegion(frame6);
        knightFrames[7] = new TextureRegion(frame7);
        knightFrames[8] = new TextureRegion(frame8);
        knightFrames[9] = new TextureRegion(frame9);

        knightAnimation = new Animation<>(0.03f, knightFrames); // Frame duration 0.1 seconds
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
        // Render the sprite on the screen
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = knightAnimation.getKeyFrame(stateTime, true);

        // Draw the sprite onto the batch
        batch.begin();
        knightWidth = currentFrame.getRegionWidth() * 6;
        knightHeight = currentFrame.getRegionHeight() * 6;
        batch.draw(currentFrame, x, y, knightWidth, knightHeight);
        batch.end();

//        knightSprite.setRegion(currentFrame);
//        knightSprite.setPosition(250, 500);
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
