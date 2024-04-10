package no.ntnu.game.Controllers;

public class GameModeController {

    public enum GameMode {
        NONE, FASTEST_KNIGHT, LAST_KNIGHT
    }

    private static GameModeController instance; // The single instance

    private GameMode currentGameMode;

    // Private constructor to prevent instantiation
    public GameModeController() {
        // Initially, no game mode is selected
        this.currentGameMode = GameMode.NONE;
    }

    // Static method to get the instance
    public static GameModeController getInstance() {
        if (instance == null) {
            instance = new GameModeController();
        }
        return instance;
    }

    public void resetGameMode() {
        // Reset the current game mode to NONE or any initial state
        this.currentGameMode = GameMode.NONE;
        // print out the current game mode
        System.out.println("Game mode has been reset to: " + this.currentGameMode);
        // If there are any other states or configurations managed by GameModeController, reset them here as well
    }

    // Method to set the game mode
    public void setGameMode(GameMode gameMode) {
        // print out the current game mode
        System.out.println("Game mode has been set to: " + gameMode);
        this.currentGameMode = gameMode;
    }

    // Method to get the current game mode
    public GameMode getCurrentGameMode() {
        // print out the current game mode
        System.out.println("Current game mode is: " + this.currentGameMode);
        return this.currentGameMode;
    }

    // Utility methods for checking the active game mode
    public boolean isFastestKnightMode() {
        return this.currentGameMode == GameMode.FASTEST_KNIGHT;
    }

    public boolean isLastKnightMode() {
        return this.currentGameMode == GameMode.LAST_KNIGHT;
    }

    public boolean isNoGameModeSelected() {
        return this.currentGameMode == GameMode.NONE;
    }
}
