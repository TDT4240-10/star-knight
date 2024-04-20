package no.ntnu.game.Firestore;

import no.ntnu.game.FirebaseCompatible;

public abstract class FirebaseClass implements FirebaseCompatible {
    private String documentId;

    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
