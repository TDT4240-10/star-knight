package no.ntnu.game.Views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * A class to manage the screens in the game. Methods to push, set, update and
 * render screens.
 *
 * @author Deen
 */
public class ScreenManager {
    private Stack<Screen> screens;

    public ScreenManager() {
        screens = new Stack<>();
    }

    public void push(Screen screen) {
        screens.push(screen);
    }

    public void set(Screen screen) {
        if (!screens.isEmpty()) {
            screens.pop().dispose(); // Dispose the current screen
        }
        screens.push(screen);
        screen.create(); // Initialize the new screen here, regardless of stack's previous state
    }

    public void update(float dt) {
        try {
            screens.peek().update(dt);
        } catch (EmptyStackException e) {
        }
    }

    public void render(SpriteBatch sb) {
        try {
            screens.peek().render(sb);
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
    }
}