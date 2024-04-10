package no.ntnu.game.Button;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Objects;

import no.ntnu.game.Controllers.GameModeController;
import no.ntnu.game.Controllers.KnightController;
import no.ntnu.game.FirebaseInterface;
import no.ntnu.game.Models.PlayerModel;
import no.ntnu.game.PlayerCallback;
import no.ntnu.game.StarKnight;
import no.ntnu.game.Views.CreateOrJoinRoomScreen;
import no.ntnu.game.Views.CreateGameLobbyScreen;
import no.ntnu.game.Views.PlayerLoginScreen;
import no.ntnu.game.Views.ScreenManager;
import no.ntnu.game.Views.JoinGameLobbyScreen;
import no.ntnu.game.Views.GameScreen;
import no.ntnu.game.Views.MainMenuScreen;
import no.ntnu.game.Views.SettingsScreen;
import no.ntnu.game.Views.TutorialScreen;
import no.ntnu.game.firestore.Player;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.sun.tools.javac.Main;
import no.ntnu.game.Views.YouLoseGameScreen;
import no.ntnu.game.Views.YouWinGameScreen;

public class ButtonInputListener extends InputAdapter {
    private Button button;
    private GameModeController gameModeController;

    public static Color Starknightdown = new Color(105 / 255f, 105 / 255f, 105 / 255f, 1 / 255f);
    public static Color Starknight = new Color(61 / 255f, 63 / 255f, 65 / 255f, 255 / 255f);
    private ScreenManager gsm;
    private KnightController knightController;
    private TextField textField;
    private SpriteBatch batch;
    public Player player;

    private FirebaseInterface FI = StarKnight.getFirebaseInterface();

    public ButtonInputListener(Button button, ScreenManager gsm, KnightController knightController, TextField textField, SpriteBatch batch) {
        this.button = button;
        this.gsm = gsm;
        this.knightController = knightController;
        this.textField = textField;
        this.batch = batch;

        this.gameModeController = GameModeController.getInstance();
    }

    @Override
    // touchDown is when user is clicking the screen. one can also hold down on the screen and touchDown is activated.
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Convert screen coordinates to world coordinates
        float touchX = screenX;
        float touchY = Gdx.graphics.getHeight() - screenY;

        // add the functionality of the buttons here
        switch(this.button.getName()) {
            case "Login":
                if (this.button.isPressed(touchX, touchY)) {
                    String username = textField.getText();
                    if (!username.isEmpty()) {
                        FI.getPlayer(username, new PlayerCallback() {
                            @Override
                            public void onCallback(Player player) {
                                if (player == null) {
                                    // create new player
                                    Player newPlayer = new Player(username);
                                    FI.SerializeClass(newPlayer);
                                    PlayerModel.setPlayer(newPlayer);
                                } else {
                                    PlayerModel.setPlayer(player);
                                }
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        gsm.set(new MainMenuScreen(gsm));
                                    }
                                });
                            }
                        });
                    }

                    System.out.println("Login mode pressed, color set, username: " + username);
                    return true; // Indicate that the touch event is handled
                }
                break;
            
            case "LeftArrow":
                if (this.button.isPressed(touchX, touchY)) {
                    // Handle button press for LeftArrow
                    System.out.println("LeftArrow button pressed");
                    if (Objects.equals(knightController.getDirection(), "right")) {
                        // Run chopping animation
                        knightController.moveLeft();
                    }
                    else {
                        knightController.stayLeft();
                    }
                    return true; // Indicate that the touch event is handled
                }
                break;
            
            case "RightArrow":
                if (this.button.isPressed(touchX, touchY)) {
                    // Handle button press for RightArrow
                    System.out.println("RightArrow button pressed");

                    if (Objects.equals(knightController.getDirection(), "left")) {
                        // Run chopping animation
                        knightController.moveRight();
                    }
                    else {
                        knightController.stayRight();
                    }

                    return true; // Indicate that the touch event is handled
                }
                break;
            
            case "Play":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Starknightdown); // For example, change button color when pressed
                    System.out.println("Play button pressed, color set");
                    // TODO start should bring you to the selection of game mode instead of GameScreen
//                    gsm.set(new ChooseGameModeScreen(gsm));
                    gsm.set(new CreateOrJoinRoomScreen(gsm));
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
//                    if(knightController != null){
//                        knightController.stopMusic();
//                    }
                    gsm.set(new MainMenuScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;
            
            case "JoinRoom":
                if (this.button.isPressed(touchX, touchY)) {
                    String joinRoomID = textField.getText();

                    // check if roomID exists in firestore
                    // if exists, get the room object
                    // if not, stay on the same screen
                    if (!joinRoomID.isEmpty()) {
                        // todo deen join room
                        // check if roomID exists in firestore
                        // if exists, get the room object
                        // if not, stay on the same screen
                        System.out.println("Join Room button pressed, color set, roomID: " + joinRoomID);
                        gsm.set(new JoinGameLobbyScreen(gsm));
                    }

                    this.button.setColor(Color.GREEN); // For example, change button color when pressed
                    System.out.println("Join Room button pressed, color set");
                    gsm.set(new JoinGameLobbyScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;

            case "CreateRoom":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Color.GREEN); // For example, change button color when pressed
                    System.out.println("Create Room button pressed, color set");
                    gsm.set(new CreateGameLobbyScreen(gsm));
                    GameModeController.getInstance().resetGameMode();
                    return true; // Indicate that the touch event is handled
                }
                break;
            
            case "StartGame":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Color.GREEN); // For example, change button color when pressed
                    System.out.println("Start Game button pressed, color set");
                    GameModeController.GameMode currentMode = gameModeController.getCurrentGameMode();
                    switch (currentMode) {
                        case FASTEST_KNIGHT:
                            gsm.set(new GameScreen(gsm));
                            System.out.println("Starting Fastest Knight game...");
                            break;
                        case LAST_KNIGHT:
                            gsm.set(new GameScreen(gsm));
                            System.out.println("Starting Last Knight game...");
                            break;
                        default:
                            System.out.println("No game mode selected");
//                            gsm.set(new GameScreen(gsm));
                            // Possibly show an error or prompt to select a game mode
                            break;
                    }
//                    gsm.set(new GameScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;
            
            case "FastestKnight":
                if (this.button.isPressed(touchX, touchY)) {
                    // if game mode is not fastest knight, set the color to down
                    if(!GameModeController.getInstance().isFastestKnightMode())
                    {
                        gameModeController.setGameMode(GameModeController.GameMode.FASTEST_KNIGHT);
                    }
                    return true;

            case "RectSettings":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Starknightdown); // For example, change button color when pressed
                    System.out.println("Settings button pressed, color set");
                    gsm.push(new SettingsScreen(gsm));
                    return true; // Indicate that the touch event is handled

                }
                break;
                  
            case "LastKnight":
                if (this.button.isPressed(touchX, touchY)) {
                    this.button.setColor(Starknightdown); // For example, change button color when pressed
                    System.out.println("Exit to Main Menu button pressed, color set");
                    gsm.set(new MainMenuScreen(gsm));
                    return true; // Indicate that the touch event is handled
                }
                break;
                  
            case "FastestKnight":
                if (this.button.isPressed(touchX, touchY)) {
                    if(this.button.getColor() == Starknightdown)
                    {
                        System.out.println("FastestKnight2 mode pressed, color set");

                        this.button.setColor(Starknight); // For example, change button color when pressed
                        return true; // Indicate that the touch event is handled

                    }
                    this.button.setColor(Starknightdown); // For example, change button color when pressed
                    System.out.println("FastestKnight mode pressed, color set");

                    return true; // Indicate that the touch event is handled
                }
                break;
                  
            case "LastKnight":
                if (this.button.isPressed(touchX, touchY)) {
                    if(this.button.getColor() == Starknightdown)
                    {
                        System.out.println("LastKnight2 mode pressed, color set");

                        this.button.setColor(Starknight); // For example, change button color when pressed
                        return true; // Indicate that the touch event is handled

                    }
                    this.button.setColor(Starknightdown); // For example, change button color when pressed
                    System.out.println("LastKnight mode pressed, color set");
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
    // touchUp is when user is no longer clicking
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Convert screen coordinates to world coordinates
        float touchX = screenX;
        float touchY = Gdx.graphics.getHeight() - screenY;

        // Handle button press
        System.out.println("Button pressed: " + button);
        switch(this.button.getName()) {

            case "FastestKnight":
                if (this.button.isPressed(touchX, touchY)) {
                    System.out.println("FastestKnight mode pressed, color set");
                    return true; // Indicate that the touch event is handled
                }
                break;
            case "LastKnight":
                if (this.button.isPressed(touchX, touchY)) {
                    System.out.println("LastKnight mode pressed, color set");
                    return true; // Indicate that the touch event is handled
                }
                break;
            // Add more cases for other buttons if needed

            default:
                if (this.button.isPressed(touchX, touchY)) {

                    this.button.setColor(Starknight); // For example, change button color when pressed
                    return true;
                    // Indicate that the touch event is handled
                }
                break;

        }

        switch(this.button.getName()) {
            case "FastestKnight":
                if (this.button.isPressed(touchX, touchY)) {
                    System.out.println("FastestKnight mode pressed, color set");
                    return true; // Indicate that the touch event is handled
                }
                break;
            case "LastKnight":
                if (this.button.isPressed(touchX, touchY)) {
                    System.out.println("LastKnight mode pressed, color set");
                    return true; // Indicate that the touch event is handled
                }
                break;
            // Add more cases for other buttons if needed

            default:
                if (this.button.isPressed(touchX, touchY)) {

                    this.button.setColor(Starknight); // For example, change button color when pressed
                    return true;
                    // Indicate that the touch event is handled
                }
                break;


        }
        return false; // Indicate that the touch event is not handled
    }
}
