package no.ntnu.game;

/**
 * CoreFirebase is a class that will be overridden by the AndroidFirebase class.
 * It should only implement the methods that are common to all platforms,
 * but not contain any logic.
 * 
 * @author Magnus Byrkjeland
 */
public class CoreFirebase implements FirebaseInterface {

    @Override
    public void SomeFunction() {}
}
