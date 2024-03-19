package no.ntnu.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainMenuScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    Texture logo;

    BitmapFont font; // Declare the font variable

    public MainMenuScreen(SpriteBatch batch) {
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
        logo = new Texture("starknight_logo.png");
        font = new BitmapFont(); // Load the font
        font.getData().setScale(3); // Set the font scale to 2 for double size
    }

    @Override
    public void render(float delta) {
        // Clear the screen with grey color
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Calculate the position to center the logo vertically
        float logoWidth = logo.getWidth();
        float logoHeight = logo.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float logoX = (screenWidth - logoWidth) / 2;
        float logoY = (2 * screenHeight) / 3 - logoHeight / 2; // 1/3 from the top

        // Draw the logo texture
        batch.begin();
        batch.draw(logo, logoX, logoY);
        batch.end();

        // Set up font parameters
        font.setColor(Color.BLACK); // Set text color

        // Draw buttons
        float buttonWidth = 400; // Adjust as needed
        float buttonHeight = 200; // Adjust as needed
        float buttonSpacing = 80; // Adjust as needed
        float buttonX = (screenWidth - buttonWidth) / 2;
        float buttonY = logoY - buttonSpacing - buttonHeight; // Position below the logo

        // Temporary buttons, will be instantiated from button interface in future

        // Draw three buttons stacked vertically below the logo
        for (int i = 0; i < 3; i++) {
            // Draw button shape
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(buttonX, buttonY - i * (buttonHeight + buttonSpacing), buttonWidth, buttonHeight);
            shapeRenderer.end();

            // Draw button text
            batch.begin();
            GlyphLayout layout = new GlyphLayout(); // Used to calculate text width
            layout.setText(font, getButtonText(i)); // Set text for button
            float textX = buttonX + (buttonWidth - layout.width) / 2; // Center text horizontally
            float textY = buttonY - i * (buttonHeight + buttonSpacing) + (buttonHeight + layout.height) / 2; // Center text vertically
            font.draw(batch, layout, textX, textY); // Draw text
            batch.end();
        }
    }

    private String getButtonText(int index) {
        // Return button text based on index
        switch (index) {
            case 0:
                return "Play";
            case 1:
                return "Tutorial";
            case 2:
                return "Settings";
            default:
                return "";
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        logo.dispose();
    }
}