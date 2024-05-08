/*
* Event.java
* 
* Created on 16/06/2023
*/

package Simulation;

/**
 * Event is an abstract class that represents an event that can occur during the
 * simulation. It is used to implement the Strategy design pattern, allowing
 * different types of events to be created and handled by the same
 * Event Handler.
 * 
 * @author Manuel Gil Mata Ribeiro
 * @author André Teixeira
 * @author Pedro Lopes
 * @see EventHandler
 * 
 */
public abstract class Event {
    private int currentInstant;
    private int finalInstant;

    /**
     * Creates a new instance of Event.
     * 
     * @param currentInstant
     *                       The instant at which the event stars.
     * @param finalInstant
     *                       The instant at which the event ends.
     */
    public Event(int currentInstant, int finalInstant) {
        this.currentInstant = currentInstant;
        this.finalInstant = finalInstant;
    }

    /**
     * Getter for the current instant in which the event finds itself.
     * 
     * @return The current instant in which the event finds itself.
     */
    public int getCurrentInstant() {
        return currentInstant;
    }

    /**
     * Setter for the instant in which the event finds itself.
     * 
     * @param currentInstant
     *                       The instant in which the event finds itself.
     */
    public void setCurrentInstant(int currentInstant) {
        this.currentInstant = currentInstant;
    }

    /**
     * Getter for the instant in which the event ends.
     * 
     * @return The instant in which the event ends.
     */
    public int getfinalInstant() {
        return finalInstant;
    }

    /**
     * Setter for the instant in which the event ends.
     * 
     * @param finalInstant
     *                     The instant in which the event ends.
     */
    public void setfinalInstant(int finalInstant) {
        this.finalInstant = finalInstant;
    }
}
