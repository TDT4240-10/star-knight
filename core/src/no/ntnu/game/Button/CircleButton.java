package no.ntnu.game.Button;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


/**
 * This is for all the circle button used for the game
 *
 * @author Jeff
 */
public class CircleButton implements Button {
    private float x, y, radius;
    private Color color;
    private boolean isPressed;
    private BitmapFont font;
    private String text;

    private String name;
    private boolean goNext;
    // this color scheme is for all text in the game
    public static Color Starknighttext = new Color(199 / 255f, 199 / 255f, 176 / 255f, 255 / 255f);
    public static Color outlineColor = new Color(40/ 255f, 40/ 255f, 41/ 255f, 1/ 255f);
    public CircleButton(float x, float y) {
        this.x = x;
        this.y = y;
        this.radius = 150;
        this.text = "test"; // Default text
        this.font = new BitmapFont(); // Default font
        this.font.getData().setScale(8); // Set scale factor to 2 (doubles the size)
        this.name = name;
        this.goNext = false;
    }



    @Override
    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        shapeRenderer.begin(ShapeType.Filled);
        // Draw outline circle
        shapeRenderer.setColor(outlineColor);
        shapeRenderer.circle(x, y, radius + 15);
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, radius);
        shapeRenderer.end();
        // Render text
        batch.begin();
        font.setColor(Starknighttext); // Set font color
        font.draw(batch, text, x - font.getCapHeight() / 2f, y + font.getCapHeight() / 2f); // Adjust text position
        batch.end();
    }



    @Override
    public boolean isPressed(float touchX, float touchY) {
        return Math.pow(touchX - x, 2) + Math.pow(touchY - y, 2) <= Math.pow(radius, 2);
    }

    @Override
    public void setGoNext(boolean goNext) {
        this.goNext = goNext;
    }

    @Override
    public Boolean getGoNext(){
        return this.goNext;
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