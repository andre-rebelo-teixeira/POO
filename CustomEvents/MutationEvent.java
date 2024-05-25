package CustomEvents;

import Event.GenericEvent;
import population.Population;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Represents an event where an individual's genetic distribution is changed.
 */
public class MutationEvent extends GenericEvent {
    final Integer num_changes;

    /**
     * Constructor for MutationEvent.
     *
     * @param population The population involved in the event.
     * @param id The ID of the individual that will undergo mutation.
     * @param time The time at which the event will be handled.
     * @param num_changes The number of changes in the genetic distribution.
     */
    MutationEvent(Population population, Integer id, Integer time, Integer num_changes) {
        super(population, id, time);
        this.num_changes = num_changes;
    }

    /**
     * Returns the name of the event class.
     *
     * @return The string "MutationEvent".
     */
    @Override
    String get_class_name() {
        return "MutationEvent";
    }

    /**
     * Handles the mutation event by changing the genetic distribution of the individual.
     * Updates the event counter.
     *
     * @param event_counter The map that will be counting the amount of time each Event type has occurred.
     * @return Updated Event counter-map.
     */
    @Override
    Map<String, Integer> handle(Map<String, Integer> event_counter) {
        this.population.change_distribution_of_individual(this.individual_id, this.num_changes);
        return this.update_event_counter(event_counter);
    }
}
