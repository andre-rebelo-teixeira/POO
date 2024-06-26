/**
 *  PEC.java
 *  Created on 23/05/2024
 */

package Event;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.function.Predicate;

/**
 * This is PEC (Pending Event Container). It implements a simple PriorityQueue wrapper for the class
 * GenericEvent and all those that inherit from it.
 * Methods for polling, peeking, and adding events are implemented, making it easy to interact with the PEC.
 * Since this uses the PriorityQueue library, this is NOT thread-safe and should not be used for
 * multithreaded applications.
 * Please take into consideration that the PriorityQueue is not organized in memory, but it is organized when
 * using the functions implemented in the PEC.
 *
 * @author Andre Teixeira
 * @version 1.0
 * @see GenericEvent
 * @see java.util.PriorityQueue
 * @see java.util.Comparator
 * @see java.util.function.Predicate
 * @since version 1.0
 */
public class PEC implements EventContainer {
    private final PriorityQueue<GenericEvent> event_queue;
    private Map<String, Integer> EventCounter;

    /**
     * Constructor for PEC. Initializes the event queue with a custom comparator.
     */
    public PEC() {
        GenericEventComparator comparator = new GenericEventComparator();
        this.event_queue = new PriorityQueue<>(comparator);
        this.EventCounter = new HashMap<>();
    }

    /**
     * Adds an event to the priority queue.
     *
     * @param event The event to be added.
     */
    @Override
    public void addEvent(GenericEvent event) {
        this.event_queue.add(event);
    }

    /**
     * Polls an event from the priority queue.
     *
     * @return The event with the highest priority.
     */
    @Override
    public GenericEvent poolEvent() {
        return this.event_queue.poll();
    }

    /**
     * Peeks at the event with the highest priority without removing it from the queue.
     *
     * @return The event with the highest priority.
     */
    @Override
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
    @Override
    public ArrayDeque<GenericEvent> getEventQueue(int max_time) {
        ArrayDeque<GenericEvent> q = new ArrayDeque<>();

        while (this.peekEvent() != null && this.peekEvent().getHandling_time() <= max_time) {
            q.add(this.poolEvent());
        }

        return q;
    }

    /**
     * Remove all the objects from the Queue that follow a certain Filter.
     *
     * @param e Filter that will be used to remove the object.
     */
    @Override
    public void removeEvents(Predicate<GenericEvent> e) {
        this.event_queue.removeIf(e);
    }

    /**
     * Gets the number of events in the priority queue.
     *
     * @return The number of events.
     */
    @Override
    public Integer get_num_events() {
        return this.event_queue.size();
    }

    /**
     * Gets the event counter map.
     *
     * @return The event counter map.
     */
    @Override
    public Map<String, Integer> getEventCounter() {
        return EventCounter;
    }

    /**
     * Sets the event counter map.
     *
     * @param eventCounter The event counter map.
     */
    @Override
    public void setEventCounter(Map<String, Integer> eventCounter) {
        EventCounter = eventCounter;
    }

    /**
     * Gets the size of the priority queue.
     *
     * @return The size of the priority queue.
     */
    @Override
    public Integer size() {
        return this.event_queue.size();
    }
}
