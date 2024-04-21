package no.ntnu.game;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import no.ntnu.game.callback.FirebaseCallback;
import no.ntnu.game.firestore.GameRoom;
import no.ntnu.game.firestore.Player;

public class AndroidFirebase implements FirebaseInterface {
    private final FirebaseFirestore firestore;

    public AndroidFirebase() {
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void savePlayer(Player player) {
        firestore.collection("players").document(player.getDocumentId()).set(player);
    }

    @Override
    public void getPlayer(String username, FirebaseCallback<Player> callback) {
        // Create a query
        CollectionReference collectionRef = firestore.collection("players");
        Query query = collectionRef.whereEqualTo("username", username);
        query.get()
                .addOnCompleteListener(task -> {
                    Player player = null;
                    if (task.isSuccessful()) {
                        // Iterate through the documents
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            // Handle each document
                            player = documentSnapshot.toObject(Player.class);
                            player.setDocumentId(documentSnapshot.getId());
                            break;
                        }
                    }
                    callback.onCallback(player);

                });
    }

    @Override
    public void getRoomByCode(String code, FirebaseCallback<GameRoom> callback) {
        firestore.collection("gameRoom").whereEqualTo("roomCode", code).get().addOnCompleteListener(task -> {
            GameRoom room = null;
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    room = documentSnapshot.toObject(GameRoom.class);
                    room.setDocumentId(documentSnapshot.getId());
                    break;
                }
            }
            callback.onCallback(room);
        });
    }

    @Override
    public void saveRoom(GameRoom room, FirebaseCallback<GameRoom> callback) {
        firestore.collection("gameRoom").document(room.getDocumentId()).set(room)
                .addOnSuccessListener(unused -> callback.onCallback(room)).addOnFailureListener(e -> {
                    callback.onCallback(null);
                    e.printStackTrace();
                });
    }

    @Override
    public void createRoomListener(GameRoom room, FirebaseCallback<GameRoom> callback) {
        firestore.collection("gameRoom").document(room.getDocumentId()).addSnapshotListener((value, error) -> {
            if (error == null && value != null && value.exists()) {
                GameRoom room1 = value.toObject(GameRoom.class);
                callback.onCallback(room1);
            }
        });
    }

}
