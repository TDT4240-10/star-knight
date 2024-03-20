package no.ntnu.game.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.sun.tools.javac.Main;

import no.ntnu.game.Views.CreateOrJoinRoomScreen;
import no.ntnu.game.Views.GameLobbyScreen;
import no.ntnu.game.Views.GameScreenManager;
import no.ntnu.game.Views.MainMenuScreen;
import no.ntnu.game.Views.SettingsScreen;
import no.ntnu.game.Views.TutorialScreen;

public class ButtonInputListener extends InputAdapter {
    private Button button;
    private GameScreenManager gsm;
    public static Color Starknightdown = new Color(105 / 255f, 105 / 255f, 105 / 255f, 1 / 255f);
    public static Color Starknight = new Color(61 / 255f, 63 / 255f, 65 / 255f, 255 / 255f);
    public ButtonInputListener(Button button, GameScreenManager gsm) {
        this.button = button;
        this.gsm = gsm;
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
            case "Play":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Starknightdown); // For example, change button color when pressed
                    System.out.println("Play button pressed, color set");
                    gsm.set(new CreateOrJoinRoomScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;
            case "JoinRoom":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Color.GREEN); // For example, change button color when pressed
                    System.out.println("Join Room button pressed, color set");
                    gsm.set(new GameLobbyScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;
            case "CreateRoom":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Color.GREEN); // For example, change button color when pressed
                    System.out.println("Create Room button pressed, color set");
                    gsm.set(new GameLobbyScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;
            case "StartGame":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Color.GREEN); // For example, change button color when pressed
                    System.out.println("Start Game button pressed, color set");
                    gsm.set(new MainMenuScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;
            case "Tutorial":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Starknightdown); // For example, change button color when pressed
                    System.out.println("Tutorial button pressed, color set");
                    gsm.push(new TutorialScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;

            case "RectSettings":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Color.GREEN); // For example, change button color when pressed
                    System.out.println("Settings button pressed, color set");
                    gsm.push(new SettingsScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;
            case "ExitToMainMenu":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Color.GREEN); // For example, change button color when pressed
                    System.out.println("Exit to Main Menu button pressed, color set");
                    gsm.set(new MainMenuScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;
            // Add more cases for other buttons if needed
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
            System.out.println("Button pressed: " + button);

            this.button.setColor(Starknight); // For example, change button color when pressed
            return true; // Indicate that the touch event is handled
        }

        return false; // Indicate that the touch event is not handled
    }
}