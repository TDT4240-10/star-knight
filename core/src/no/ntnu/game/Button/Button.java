package no.ntnu.game.Button;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Interface for Buttons in the application
 *
 * @author Jeff
 */
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Button {

    void render(ShapeRenderer shapeRenderer, SpriteBatch batch);
    boolean isPressed(float touchX, float touchY);


    void dispose();

    void setColor(Color color);

    void setText(String text);
}