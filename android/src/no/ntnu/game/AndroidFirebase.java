package no.ntnu.game;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import no.ntnu.game.firestore.GameRoom;
import no.ntnu.game.firestore.Player;

public class AndroidFirebase implements FirebaseInterface {

    // private static final String TAG = "DocSnippets";

    private final FirebaseDatabase realtimeDatabase;
    private final FirebaseFirestore firestore;
    // DatabaseReference myRef;

    public AndroidFirebase() {
        realtimeDatabase = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }


    public String SerializeClass(FirebaseClass object) {
        String collection = object.getCollectionName();
        if (object.getDocumentId() == null) {
            return firestore.collection(collection).add(object).getResult().toString();
        } else {
            firestore.collection(collection).document(object.getDocumentId()).set(object);
        }
       return "";
    }

    @Override
    public void getPlayer(String username, PlayerCallback callback) {
        // Create a query
        CollectionReference collectionRef = firestore.collection("players");
        Query query = collectionRef.whereEqualTo("username", username);

        // Execute the query asynchronously
        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Iterate through the documents
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Handle each document
                             Player player = documentSnapshot.toObject(Player.class);
                             assert player != null;
                             player.setDocumentId(documentSnapshot.getId());
                             callback.onCallback(player);
                        }
                    }
                });
    }

    @Override
    public void joinRoom(String code, Player player, JoinRoomCallback callback) {
        CollectionReference collectionRef = firestore.collection("game_rooms");
        Query query = collectionRef.whereEqualTo("roomCode", code);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    GameRoom room = documentSnapshot.toObject(GameRoom.class);
                    assert room != null;
                    room.setDocumentId(documentSnapshot.getId());
                    room.addPlayer(player);
                    callback.onCallback(room);
                }
            }
        });
    }


    // TODO: Firebase logic here
    @Override
    public void SomeFunction() {
        System.out.println("Android Firebase");
    }


}
