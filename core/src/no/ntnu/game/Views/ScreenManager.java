package no.ntnu.game.Views;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.EmptyStackException;
import java.util.Stack;
/**
 * A class to manage the screens in the game. Methods to push, set, update and render screens.
 *
 * @author Deen
 */
public class ScreenManager {
    private Stack<Screen> screens;
    public ScreenManager() {
        screens = new Stack<>();
    }

    public void push (Screen screen) {
        screens.push(screen);}

    public void set(Screen screen) {
        if (screens.empty()) {
            screens.push(screen);
        } else {
            screens.pop().dispose();
            screens.push(screen);
        }

    }

    public void update(float dt) {
        try {
            screens.peek().update(dt);
        } catch (EmptyStackException e) {
            System.out.println("Emoy stack");
        }
    }

    public void render(SpriteBatch sb) {
        try {
            screens.peek().render(sb);
        } catch (EmptyStackException e) {
            System.out.println("Empty stack");
        }
    }
}