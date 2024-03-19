package no.ntnu.game.Views;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameViewManager {
    private Stack<no.ntnu.game.Views.View> views;
    public GameViewManager() {views = new Stack<View>();}

    public void push (no.ntnu.game.Views.View view) {views.push(view);}

    public void pop() {views.pop().dispose();}

    public void set(no.ntnu.game.Views.View view) {
        views.pop().dispose();
        views.push(view);
    }

    public void update(float dt) {
        views.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        views.peek().render(sb);
    }
}
