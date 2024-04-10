package no.ntnu.game;

public abstract class FirebaseClass {
    private String documentId;
    private String collectionName;
    public abstract String getCollectionName();
    public String getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
