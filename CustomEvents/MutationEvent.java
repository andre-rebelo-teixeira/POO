package CustomEvents;

import Event.GenericEvent;
import Event.PEC;
import population.Population;
import population.PopulationInterface;

import java.util.Map;

/**
 * Represents an event where an individual's genetic distribution is changed.
 */
public class MutationEvent extends GenericEvent {
    private final Integer planet_to_change;

    /**
     * Constructor for MutationEvent.
     *
     * @param id The ID of the individual that will undergo mutation.
     * @param time The time at which the event will be handled.
     */
    public MutationEvent(Integer id, Double time, Integer planet_to_change) {
        super(id, time);
        this.planet_to_change = planet_to_change;
    }

    /**
     * Returns the name of the event class.
     *
     * @return The string "MutationEvent".
     */
    @Override
    public String get_class_name() {
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
    public Map<String, Integer> handle(Map<String, Integer> event_counter, PopulationInterface population, PEC pec) {
        population.change_distribution_of_individual(this.individual_id, this.planet_to_change);
        return this.update_event_counter(event_counter);
    }
}
