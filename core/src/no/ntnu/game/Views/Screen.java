package no.ntnu.game.Views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class Screen {
    protected Vector3 mouse; // this is a protected xyz coordinate system
    protected ScreenManager gvm;

    protected Screen(ScreenManager gvm) {
        this.gvm = gvm;
        mouse = new Vector3();
    }
    // make methods

    protected abstract void handleInput();
    public abstract void update(float dt); // delta time is the time diff between one frame rendered and next frame rendered
    public abstract void render(SpriteBatch sb);

    // dispose method to prevent memory leaks
    public abstract void dispose();
}