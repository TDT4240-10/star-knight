package no.ntnu.game.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * This is for all the rectangle button used for selecting game modes
 *
 * @author Jeff
 */
public class RectangleButton implements Button {
    private float x, y, width, height;
    private Color color;
    private boolean isPressed;
    private BitmapFont font;
    private String text;
    private String name;

    public static Color Starknighttext = new Color(199 / 255f, 199 / 255f, 176 / 255f, 255 / 255f);
    public static Color outlineColor = new Color(40/ 255f, 40/ 255f, 41/ 255f, 1/ 255f);
    public RectangleButton(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = 500; // Default width
        this.height = 200; // Default height
        isPressed = false;
        this.font = new BitmapFont(); // Default font
        this.font.getData().setScale(7); // Set font scale
        this.name = name;

    }

    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        shapeRenderer.begin(ShapeType.Filled);

        // Draw the outer rectangle
        shapeRenderer.setColor(outlineColor);
        float shadingOffset = Math.min(width, height) * 0.05f;

        float outerX = x - shadingOffset;
        float outerY = y - shadingOffset;
        float outerWidth = width + 2 * shadingOffset;
        float outerHeight = height + 2 * shadingOffset;
        shapeRenderer.rect(outerX, outerY, outerWidth, outerHeight);
        // Draw the inner rectangle
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.end();

        batch.begin();
        font.setColor(Starknighttext); // Set font color
        GlyphLayout layout = new GlyphLayout(); // Used to calculate text width
        layout.setText(font, text); // Set text for button
        // Calculate the position to center the text within the inner rectangle
        float textX = x + (width - layout.width) / 2f;
        float textY = y + (height + layout.height) / 2f;

        font.draw(batch, layout, textX, textY); // Draw text centered within the inner rectangle
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

    @Override
    public void setName(String name) {
        this.name = name;

    }

    @Override
    public String getName(){
        return this.name;
    }
}