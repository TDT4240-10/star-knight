package no.ntnu.game.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
/**
 * THIS IS button used for selecting game modes
 *
 * @author Jeff
 */
public class RectangleButton implements Button {
    private float x, y, width, height;
    private Color color;
    private boolean isPressed;
    private BitmapFont font;
    private String text;

    public RectangleButton(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = 100; // Default width
        this.height = 100; // Default height
        isPressed = false;
        this.font = new BitmapFont(); // Default font
        this.font.getData().setScale(5); // Set font scale
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.BLACK); // Set font color
        font.draw(batch, text, x, y); // Render text at button position
        batch.end();
    }

    @Override
    public boolean isPressed(float touchX, float touchY) {
        // Check if touch coordinates are within the rectangle bounds
        return touchX >= x && touchX <= x + width && touchY >= y && touchY <= y + height;
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
