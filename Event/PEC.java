package Event;

import java.util.PriorityQueue;
import java.util.Comparator;

import Event.GenericEvent;
import Event.GenericEventComparator;

public class PEC {
    private PriorityQueue<GenericEvent> event_queue;
    private GenericEventComparator comparator;

    public PEC() {
        this.comparator = new GenericEventComparator();
        this.event_queue = new PriorityQueue<GenericEvent>(this.comparator);
    }

    public void addEvent(GenericEvent event) {
        this.event_queue.add(event);
    }

    public GenericEvent getEvent() {
        return this.event_queue.poll();
    }

    public GenericEvent peekEvent() {
        return this.event_queue.peek();
    }
}


