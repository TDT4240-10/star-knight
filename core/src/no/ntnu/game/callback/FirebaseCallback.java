package no.ntnu.game.Callback;

/**
 * FirebaseCallback provides a generic interface for creating a callback when
 * calling firebase functions.
 * 
 * @param <T> The object that should be provided in the callback method.
 * @author Casper Salminen Andreassen
 */

public interface FirebaseCallback<T> {
    void onCallback(T result);
}
