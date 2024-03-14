package no.ntnu.game;

// import android.util.Log;

// import androidx.annotation.NonNull;

// import com.google.android.gms.tasks.OnFailureListener;
// import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
// import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

// import java.util.HashMap;
// import java.util.Map;

public class AndroidFirebase implements FirebaseInterface {

    // private static final String TAG = "DocSnippets";

    private final FirebaseDatabase realtimeDatabase;
    private final FirebaseFirestore firestore;
    // DatabaseReference myRef;

    public AndroidFirebase() {
        realtimeDatabase = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // REALTIME DATABASE EXAMPLE
        // TODO: Remove this example
        // myRef = realtimeDatabase.getReference("message");
        // myRef.setValue("Hello, World!");


        // FIRESTORE EXAMPLE
        // TODO: Remove this example
        // Create a new user with a first and last name
        // Map<String, Object> user = new HashMap<>();
        // user.put("first", "Ada");
        // user.put("last", "Lovelace");
        // user.put("born", 1815);

        // // Add a new document with a generated ID
        // firestore.collection("users")
        //         .add(user)
        //         .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        //             @Override
        //             public void onSuccess(DocumentReference documentReference) {
        //                 Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
        //             }
        //         })
        //         .addOnFailureListener(new OnFailureListener() {
        //             @Override
        //             public void onFailure(@NonNull Exception e) {
        //                 Log.w(TAG, "Error adding document", e);
        //             }
        //         });
        }

    // TODO: Firebase logic here
    @Override
    public void SomeFunction() {
        System.out.println("Android Firebase");
    }
}
