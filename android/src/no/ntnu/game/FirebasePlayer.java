package no.ntnu.game;

import com.google.firebase.firestore.Exclude;

import no.ntnu.game.firestore.FirebaseClass;

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
