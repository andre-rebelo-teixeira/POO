/**
 *  PEC.java
 *  Created on 23/05/2024
 */

package Event;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

/**
 * This is PEC (Pending Event Container). It implements a simple PriorityQueue wrapper for the class
 * GenericEvent and all those that inherit from it.
 * Methods for polling, peeking, and adding events are implemented, making it easy to interact with the PEC.
 *
 * @implNote Since this uses the PriorityQueue library, this is NOT thread-safe and should not be used for
 * multithreaded applications.
 *
 * @author Andre Teixeira
 * @version 1.0
 * @see GenericEvent
 * @see java.util.PriorityQueue
 * @see java.util.Comparator
 * @since version 1.0
 */
public class PEC {
    private final PriorityQueue<GenericEvent> event_queue;

    /**
     * Constructor for PEC. Initializes the event queue with a custom comparator.
     */
    public PEC() {
        GenericEventComparator comparator = new GenericEventComparator();
        this.event_queue = new PriorityQueue<>(comparator);
    }

    /**
     * Adds an event to the priority queue.
     *
     * @param event The event to be added.
     */
    public void addEvent(GenericEvent event) {
        this.event_queue.add(event);
    }

    /**
     * Polls an event from the priority queue.
     *
     * @return The event with the highest priority.
     */
    public GenericEvent poolEvent() {
        return this.event_queue.poll();
    }

    /**
     * Peeks at the event with the highest priority without removing it from the queue.
     *
     * @return The event with the highest priority.
     */
    public GenericEvent peekEvent() {
        return this.event_queue.peek();
    }

    /**
     * Gets an ArrayDeque of multiple events that have a time below a certain threshold.
     * This can be used to get all the events that have happened from a previous timestamp to the current.
     *
     * @param max_time This is the maximum time that we will be wanting to remove from the PriorityQueue.
     * @return ArrayDeque containing all the events that should be handled currently.
     */
    public ArrayDeque<GenericEvent> getEventQueue(int max_time) {
        ArrayDeque<GenericEvent> q = new ArrayDeque<>();

        while  (this.peekEvent().getHandling_time() <= max_time) {
            q.add(this.poolEvent());
        }

        return q;
    }
}
