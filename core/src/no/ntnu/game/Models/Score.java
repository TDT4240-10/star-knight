package no.ntnu.game.Models;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.ntnu.game.Controllers.GameRoomController;
import no.ntnu.game.firestore.GameState;

public class Score {

    private BitmapFont font;
    public GameRoomController gameRoomController;
    private int soloScore = 0;

    // Constructor
    public Score() {
        gameRoomController = GameRoomController.getInstance();

        font = new BitmapFont(); // Assuming you have a font for rendering text
    }

    // Method to increment score
    public void incrementScore(int amount) {
        GameRoomController.Actor actor = gameRoomController.getRoomActor();
        GameRoomController.RoomType roomType = gameRoomController.getRoomType();

        if (roomType.equals(GameRoomController.RoomType.SOLO)) {
            this.soloScore = this.soloScore + amount;
        } else {
            if (actor.equals(GameRoomController.Actor.CREATING)) {
               gameRoomController.getGameRoom().incrementCreatingPlayerScore(amount);
            } else {
                gameRoomController.getGameRoom().incrementJoiningPlayerScore(amount);
            }
        }
    }

    // Method to decrement score
    public void decrementScore(int amount) {
        GameRoomController.Actor actor = gameRoomController.getRoomActor();
        GameRoomController.RoomType roomType = gameRoomController.getRoomType();

        if (roomType.equals(GameRoomController.RoomType.SOLO)) {
            this.soloScore = this.soloScore - amount;
        } else {
            if (actor.equals(GameRoomController.Actor.CREATING)) {
                gameRoomController.getGameRoom().decrementCreatingPlayerScore(amount);
            } else {
                gameRoomController.getGameRoom().decrementJoiningPlayerScore(amount);
            }
        }
    }

    public int getSoloScore() {
        return this.soloScore;
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
        if (gameRoomController.isLastKnightMode()) {
            batch.begin();
            // Calculate the position to center the text on the screen
            float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2; // Assuming average glyph width
            float y = Gdx.graphics.getHeight() - 500; // Center vertically
            font.getData().setScale(4f);
            font.draw(batch, "Score: " + getSoloScore(), x, y);
            batch.end();
        }
        // if game mode is fastest knight
        else if (gameRoomController.isFastestKnightMode()) {
            batch.begin();
            // Calculate the position to center the text on the screen
            float x = (Gdx.graphics.getWidth() - font.getXHeight() * 7) / 2; // Assuming average glyph width
            float y = Gdx.graphics.getHeight() - 500; // Center vertically
            font.getData().setScale(4f);
            font.draw(batch, "Score: " + getSoloScore(), x, y);
            batch.end();
        }
    }
}
