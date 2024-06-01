package population;

/**
 * Observer.java
 *
 * This interface defines the method for the Observer in the observer pattern,
 * providing a method to update the observer with changes in individual comfort values.
 *
 * @version 1.0
 * @since 1.0
 *
 * @author André Rebelo Teixeira
 */
public interface Observer {

    /**
     * Updates the observer with the new comfort value of an individual.
     * This method is called whenever the observed object is changed.
     *
     * @param individualId The ID of the individual whose comfort value has changed.
     * @param comfortValue The new comfort value of the individual.
     */
    void update(int individualId, float comfortValue);
}
