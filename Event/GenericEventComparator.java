package Event;

import java.util.Comparator;

class GenericEventComparator implements Comparator<GenericEvent> {
    @Override
    public int compare(GenericEvent e1, GenericEvent e2) {
        return Integer.compare(e1.getHandling_time(), e2.getHandling_time());
    }
}
