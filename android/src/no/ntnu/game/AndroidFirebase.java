package no.ntnu.game;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AndroidFirebase implements FirebaseInterface {

    FirebaseDatabase database;
    // DatabaseReference myRef;

    public AndroidFirebase() {
        database = FirebaseDatabase.getInstance();

        // TODO: Remove this example
        // Example database interaction
        // myRef = database.getReference("message");
        // myRef.setValue("Hello, World!");
    }

    // TODO: Firebase logic here
    @Override
    public void SomeFunction() {
        System.out.println("Android Firebase");
    }
}
