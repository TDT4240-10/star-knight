package no.ntnu.game.Button;
import com.badlogic.gdx.graphics.Color;

public class ButtonFactory {
    public static Button createLeftArrowButton(float x, float y) {
        Button leftArrowButton = new CircleButton(x, y);
        leftArrowButton.setColor(Color.GREEN); // Set color for left arrow button
        leftArrowButton.setText("V"); // Set text for left arrow button
        return leftArrowButton;
    }

    public static Button createRightArrowButton(float x, float y) {
        Button rightArrowButton = new CircleButton(x, y);
        rightArrowButton.setColor(Color.GREEN); // Set color for left arrow button
        rightArrowButton.setText("^"); // Set text for left arrow button
        return rightArrowButton;
    }
    public static Button SettingButton(float x, float y) {
        Button SettingButton = new CircleButton(x, y);
        SettingButton.setColor(Color.GREEN); // Set color for left arrow button
        SettingButton.setText("Settings"); // Set text for left arrow button
        return SettingButton;
    }
}
