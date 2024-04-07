package no.ntnu.game.Views;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class ScreenManager {
    private Stack<Screen> screens;
    public ScreenManager() {
        screens = new Stack<Screen>();}

    public void push (Screen screen) {
        screens.push(screen);}

    public void set(Screen screen) {
        screens.pop().dispose();
        screens.push(screen);
    }

    public void update(float dt) {
        screens.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        screens.peek().render(sb);
    }
}