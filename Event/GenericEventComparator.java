package Event;

import java.util.Comparator;

/**
 * This is a Generic Comparator that will be used for sorting the priority Queue of the Event
 * The method of comparing will just compare is just the handling time of the event
 * This will sort the elements based on a reverse order, so events that having a smaller handling time
 *  will have a higher priority since they need to be handled first
 *
 * @author Andre Teixeira
 * @version 1.0
 *
 * @see GenericEvent
 * @see Comparator
 * @since 1.0
 */
class GenericEventComparator implements Comparator<GenericEvent> {
    @Override
    public int compare(GenericEvent e1, GenericEvent e2) {
        return Double.compare(e1.getHandling_time(), e2.getHandling_time());
    }
}
