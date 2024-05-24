/**
 *  PEC.java
 *  Create on 23/05/2024
 */

package Event;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

/**
 * This is PEC (pending event container) Implements a simple PriorityQueue Wrapper for the class
 * Generic event and all those that inherit from it
 * Methods for pooling peeking and adding events are implements making it easy to interact with the PEC
 *
 * @implNote since this uses the Priority Queue library this is NOT thread safe and should not be used for
 * multithreaded application
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

    public PEC() {
        GenericEventComparator comparator = new GenericEventComparator();
        this.event_queue = new PriorityQueue<>(comparator);
    }

    public void addEvent(GenericEvent event) {
        this.event_queue.add(event);
    }

    public GenericEvent poolEvent() {
        return this.event_queue.poll();
    }

    public GenericEvent peekEvent() {
        return this.event_queue.peek();
    }

    /**
     * Get an ArrayDequeue of multiple events that have a time that is bellow a certain threshold
     * This Can be used to get all the Event that have happened from a previous time stamp to the current
     * This implementation uses multiple methods of this function
     *
     * @param max_time This is the max time that we will be wanting to remove from the Priority Queue
     * @return ArrayDeque containing all the event that should be handled currently
     */
    public ArrayDeque<GenericEvent> getEventQueue(int max_time) {
        ArrayDeque<GenericEvent> q = new ArrayDeque<>();

        while  (this.peekEvent().getHandling_time() <= max_time) {
            q.add(this.poolEvent());
        }

        return q;
    }
}


