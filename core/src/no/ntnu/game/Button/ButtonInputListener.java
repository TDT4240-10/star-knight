package no.ntnu.game.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;

import java.util.Objects;

import no.ntnu.game.Button.Button;
import no.ntnu.game.Views.CreateOrJoinRoomScreen;

public class ButtonInputListener extends InputAdapter {
    private Button button;
    public static Color Starknightdown = new Color(105 / 255f, 105 / 255f, 105 / 255f, 1 / 255f);
    public static Color Starknight = new Color(61 / 255f, 63 / 255f, 65 / 255f, 255 / 255f);
    public ButtonInputListener(Button button) {
        this.button = button;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Convert screen coordinates to world coordinates
        float touchX = screenX;
        float touchY = Gdx.graphics.getHeight() - screenY;

        // add the functionality of the buttons here
        switch(this.button.getName()) {
            case "LeftArrow":
                if (this.button.isPressed(touchX, touchY)) {
                    // Handle button press for LeftArrow
                    System.out.println("LeftArrow button pressed");
                    this.button.setColor(Starknightdown); // For example, change button color when pressed
                    return true; // Indicate that the touch event is handled
                }
                break;
            case "RightArrow":
                if (this.button.isPressed(touchX, touchY)) {
                    // Handle button press for RightArrow
                    System.out.println("RightArrow button pressed");
                    this.button.setColor(Starknightdown); // For example, change button color when pressed
                    return true; // Indicate that the touch event is handled
                }
                break;
            // Add more cases for other buttons if needed
            case "Play":
                if (this.button.isPressed(touchX, touchY)) {

                    // Handle button press for Play
                    System.out.println("Play button pressed");
                    this.button.setColor(Starknightdown); // For example, change button color when pressed
                    this.button.setGoNext(true);
                    return true; // Indicate that the touch event is handled
                }
                break;
            default:
                break;
        }

        return false; // Indicate that the touch event is not handled
    }



    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Convert screen coordinates to world coordinates
        float touchX = screenX;
        float touchY = Gdx.graphics.getHeight() - screenY;

        // Check if the touch is within the button bounds
        if (this.button.isPressed(touchX, touchY)) {
            // Handle button press
            System.out.println("Button let go: " + button);

            this.button.setColor(Starknight); // For example, change button color when pressed
            return true; // Indicate that the touch event is handled
        }

        return false; // Indicate that the touch event is not handled
    }
}