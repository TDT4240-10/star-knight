package no.ntnu.game;

import com.google.firebase.firestore.Exclude;

import no.ntnu.game.firestore.FirebaseClass;

/**
 * FirebasePlayer is a class that extends FirebaseClass and is used to store
 * player data in the Firestore database.
 */
public class FirebasePlayer extends FirebaseClass {

    @Exclude
    private String documentId;

    @Exclude
    @Override
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Exclude
    @Override
    public String getDocumentId() {
        return documentId;
    }
}
