package Event;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.function.Predicate;

/**
 * EventContainer.java
 *
 * This interface defines the methods for managing a container of events in a priority queue,
 * providing methods to add, poll, peek, and remove events based on certain criteria.
 *
 * @version 1.0
 * @since 1.0
 *
 * @see java.util.ArrayDeque
 * @see java.util.Map
 * @see java.util.function.Predicate
 *
 * @author André Teixeira
 */
public interface EventContainer {

    /**
     * Adds an event to the priority queue.
     *
     * @param event The event to be added.
     */
    void addEvent(GenericEvent event);

    /**
     * Polls an event from the priority queue.
     *
     * @return The event with the highest priority.
     */
    GenericEvent poolEvent();

    /**
     * Peeks at the event with the highest priority without removing it from the queue.
     *
     * @return The event with the highest priority.
     */
    GenericEvent peekEvent();

    /**
     * Gets an ArrayDeque of multiple events that have a time below a certain threshold.
     * This can be used to get all the events that have happened from a previous timestamp to the current.
     *
     * @param max_time This is the maximum time that we will be wanting to remove from the PriorityQueue.
     * @return ArrayDeque containing all the events that should be handled currently.
     */
    ArrayDeque<GenericEvent> getEventQueue(int max_time);

    /**
     * Remove all the objects from the Queue that follow a certain Filter
     *
     * @param e Filter That will be used to remove the object
     */
    void removeEvents(Predicate<GenericEvent> e);

    /**
     * Gets the number of events in the priority queue.
     *
     * @return The number of events.
     */
    Integer get_num_events();

    /**
     * Gets the event counter-map.
     *
     * @return The event counter-map.
     */
    Map<String, Integer> getEventCounter();

    /**
     * Sets the event counter-map.
     *
     * @param eventCounter The event counter-map.
     */
    void setEventCounter(Map<String, Integer> eventCounter);

    /**
     * Gets the size of the priority queue.
     *
     * @return The size of the priority queue.
     */
    Integer size();
}
