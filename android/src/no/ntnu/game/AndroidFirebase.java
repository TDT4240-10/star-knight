package no.ntnu.game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import no.ntnu.game.callback.FirebaseCallback;
import no.ntnu.game.firestore.GameRoom;
import no.ntnu.game.firestore.Player;

public class AndroidFirebase implements FirebaseInterface {

    private final FirebaseDatabase realtimeDatabase;
    private final FirebaseFirestore firestore;

    public AndroidFirebase() {
        realtimeDatabase = FirebaseDatabase.getInstance();
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
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
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

                    }
                });
    }

    @Override
    public void getRoomByCode(String code, FirebaseCallback<GameRoom> callback) {
        firestore.collection("gameRoom").whereEqualTo("roomCode", code).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                GameRoom room = null;
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        room = documentSnapshot.toObject(GameRoom.class);
                        room.setDocumentId(documentSnapshot.getId());
                        break;
                    }
                }
                callback.onCallback(room);
            }
        });
    }

    @Override
    public void saveRoom(GameRoom room, FirebaseCallback<GameRoom> callback) {
        firestore.collection("gameRoom").document(room.getDocumentId()).set(room).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                callback.onCallback(room);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onCallback(null);
                System.out.println(e);
            }
        });
    }

    @Override
    public void createRoomListener(GameRoom room, FirebaseCallback<GameRoom> callback) {
        firestore.collection("gameRoom").document(room.getDocumentId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null && value != null && value.exists()) {
                    GameRoom room = value.toObject(GameRoom.class);
                    System.out.println(room);
                    callback.onCallback(room);
                }
            }
        });
    }


}
