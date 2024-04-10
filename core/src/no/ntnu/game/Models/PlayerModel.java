package no.ntnu.game.Models;

import no.ntnu.game.firestore.Player;

public class PlayerModel {
    private static Player player;

    public static void setPlayer(Player player) {
        PlayerModel.player = player;
    }

    public static Player getPlayer() {
        return player;
    }
}

