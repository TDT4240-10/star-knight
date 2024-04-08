package no.ntnu.game.Button;
import com.badlogic.gdx.graphics.Color;
/**
 * Abstract factory for Buttons in the application
 *
 * @author Jeff
 */
public class ButtonFactory {
    // the theme of the game is this color so button would be this
    public static Color Starknight = new Color(61 / 255f, 63 / 255f, 65 / 255f, 255 / 255f);

    public static Button createLeftArrowButton(float x, float y) {
        Button leftArrowButton = new CircleButton(x, y);
        leftArrowButton.setName("LeftArrow");
        leftArrowButton.setColor(Starknight); // Set color for left arrow button
        leftArrowButton.setText("<"); // Set text for left arrow button
        return leftArrowButton;
    }

    public static Button createRightArrowButton(float x, float y) {
        Button rightArrowButton = new CircleButton(x, y);
        rightArrowButton.setName("RightArrow");
        rightArrowButton.setColor(Starknight);
        rightArrowButton.setText(">");
        return rightArrowButton;
    }
    public static Button createSettingButton(float x, float y) {
        Button SettingButton = new CircleButton(x, y);
        SettingButton.setName("Setting");
        SettingButton.setColor(Starknight);
        SettingButton.setText("Settings");
        return SettingButton;
    }

    public static Button createTutorialButton(float x, float y) {
        Button TutorialButton = new RectangleButton(x, y);
        TutorialButton.setName("Tutorial");
        TutorialButton.setColor(Starknight);
        TutorialButton.setText("Tutorial");
        return TutorialButton;
    }

    public static Button createRectSettingsButton(float x, float y) {
        Button rectSettingsButton = new RectangleButton(x, y);
        rectSettingsButton.setName("RectSettings");
        rectSettingsButton.setColor(Starknight);
        rectSettingsButton.setText("Settings");
        return rectSettingsButton;
    }

    // create exit button
    public static Button createExitButton(float x, float y) {
        Button exitButton = new RectangleButton(x, y);
        exitButton.setName("ExitToMainMenu");
        exitButton.setColor(Starknight);
        exitButton.setText("Exit");
        return exitButton;
    }

    // this is for the menu screen where u need multiple buttons to navigate
    public static Button createMenuButton(float x, float y, String text) {
        Button SettingButton = new RectangleButton(x, y);
        SettingButton.setName(text);
        SettingButton.setColor(Starknight);
        SettingButton.setText(text);
        return SettingButton;
    }

    public static Button createJoinRoomButton(float x, float y) {
        Button joinRoomButton = new RectangleButton(x, y);
        joinRoomButton.setName("JoinRoom");
        joinRoomButton.setColor(Starknight);
        joinRoomButton.setText("Join");
        return joinRoomButton;
    }

    public static Button createCreateRoomButton(float x, float y) {
        Button createRoomButton = new RectangleButton(x, y);
        createRoomButton.setName("CreateRoom");
        createRoomButton.setColor(Starknight);
        createRoomButton.setText("Create");
        return createRoomButton;
    }

    // create play button
    public static Button createPlayButton(float x, float y) {
        Button playButton = new RectangleButton(x, y);
        playButton.setName("Play");
        playButton.setColor(Starknight);
        playButton.setText("Play");
        return playButton;
    }

    public static Button createTempPlayButton(float x, float y) {
        Button playButton = new RectangleButton(x, y);
        playButton.setName("TempPlay");
        playButton.setColor(Starknight);
        playButton.setText("Temp Play");
        return playButton;
    }

    // create start game button
    public static Button createStartGameButton(float x, float y) {
        Button startGameButton = new RectangleButton(x, y);
        startGameButton.setName("StartGame");
        startGameButton.setColor(Starknight);
        startGameButton.setText("Start");
        return startGameButton;
    }

    // create fastest knight button
    public static Button createFastestKnightButton(float x, float y) {
        Button fastestKnightButton = new RectangleButton(x, y);
        fastestKnightButton.setName("FastestKnight");
        fastestKnightButton.setText("Fastest Knight");
        return fastestKnightButton;
    }

    // create last knight standing button
    public static Button createLastKnightButton(float x, float y) {
        Button lastKnightStandingButton = new RectangleButton(x, y);
        lastKnightStandingButton.setName("LastKnight");
        lastKnightStandingButton.setText("Last Knight");
        return lastKnightStandingButton;
    }
}

