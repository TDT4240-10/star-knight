package no.ntnu.game.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


/**
 * THIS IS button used for the game
 *
 * @author Jeff
 */
public class CircleButton implements Button {
    private float x, y, radius;
    private Color color;
    private boolean isPressed;
    private BitmapFont font;
    private String text;

    public CircleButton(float x, float y) {
        this.x = x;
        this.y = y;
        this.radius = 150;
        this.text = "test"; // Default text
        this.font = new BitmapFont(); // Default font
        this.font.getData().setScale(5); // Set scale factor to 2 (doubles the size)

    }



    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, radius);
        shapeRenderer.end();
        // Render text
        batch.begin();
        font.setColor(Color.BLACK); // Set font color
        font.draw(batch, text, x - font.getCapHeight() / 2f, y + font.getCapHeight() / 2f); // Adjust text position
        batch.end();
    }



    @Override
    public boolean isPressed(float touchX, float touchY) {
        return Math.pow(touchX - x, 2) + Math.pow(touchY - y, 2) <= Math.pow(radius, 2);
    }

    @Override
    public void dispose() {
        // Dispose any resources if needed
        font.dispose();
    }

    @Override
    public void setColor(Color color) {
        this.color = color;

    }

    @Override
    public void setText(String text) {
        this.text = text;

    }
}