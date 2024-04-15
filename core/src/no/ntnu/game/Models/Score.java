package no.ntnu.game.Models;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.firestore.GameState;

public class Score {

    private BitmapFont font;
    public GameRoomController gameRoomController;

    // Constructor
    public Score() {
        gameRoomController = GameRoomController.getInstance();
        font = new BitmapFont(); // Assuming you have a font for rendering text
    }

    // Method to increment score
    public void incrementScore(int amount) {
        GameRoomController.Actor actor = gameRoomController.getRoomActor();

        if (actor.equals(GameRoomController.Actor.CREATING)) {
           gameRoomController.incrementCreatingPlayerScore(amount);
        } else {
            gameRoomController.incrementJoiningPlayerScore(amount);
        }
    }

    // Method to decrement score
    public void decrementScore(int amount) {
        GameRoomController.Actor actor = gameRoomController.getRoomActor();

        if (actor.equals(GameRoomController.Actor.CREATING)) {
            gameRoomController.decrementCreatingPlayerScore(amount);
        } else {
            gameRoomController.decrementJoiningPlayerScore(amount);
        }
    }

    public void setScore(int score) {
        GameRoomController.Actor actor = gameRoomController.getRoomActor();

        if (actor.equals(GameRoomController.Actor.CREATING)) {
            gameRoomController.setCreatingPlayerScore(score);
        } else {
            gameRoomController.setJoiningPlayerScore(score);
        }
    }


    public int getLocalPlayerScore() {
        GameRoomController.Actor actor = gameRoomController.getRoomActor();
        if (actor.equals(GameRoomController.Actor.CREATING)) {
           return gameRoomController.getGameRoom().getCreatingPlayerState().getScore();
        } else {
            return gameRoomController.getGameRoom().getJoiningPlayerState().getScore();
        }
    }

    public int getRemotePlayerScore() {
        GameRoomController.Actor actor = gameRoomController.getRoomActor();
        if (actor.equals(GameRoomController.Actor.CREATING)) {
            return gameRoomController.getGameRoom().getJoiningPlayerState().getScore();
        } else {
            return gameRoomController.getGameRoom().getCreatingPlayerState().getScore();
        }
    }

    public void render(SpriteBatch batch) {
        // if game mode is last knight standing, render score
        batch.begin();
        // Calculate the position to center the text on the screen
        float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2; // Assuming average glyph width
        float y = Gdx.graphics.getHeight() - 500; // Center vertically
        font.getData().setScale(4f);
        font.draw(batch, "Score: " + getLocalPlayerScore(), x, y);
        if (gameRoomController.getRoomType().equals(GameRoomController.RoomType.ONLINE)) {
            font.draw(batch, "Opponent: " + getRemotePlayerScore(), x, y - 40);
        }
        batch.end();
    }

}
