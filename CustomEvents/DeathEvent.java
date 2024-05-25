package CustomEvents;

import population.Population;
import Event.GenericEvent;
import java.util.Map;

/**
 * Represents an event where an individual in the population dies.
 * This event will remove the individual from the population.
 */
public class DeathEvent extends GenericEvent {

    /**
     * Constructor for DeathEvent.
     *
     * @param population The population involved in the event.
     * @param id The ID of the individual that will die.
     * @param handling_time The time at which the event will be handled.
     */
    public DeathEvent(Population population, Integer id, Integer handling_time) {
        super(population, id, handling_time);
    }

    /**
     * Returns the name of the event class.
     *
     * @return The string "DeathEvent".
     */
    @Override
    String get_class_name() {
        return "DeathEvent";
    }

    /**
     * Handles the death event by removing the individual from the population.
     * Updates the event counter.
     *
     * @param event_counter The map that will be counting the amount of time each Event type has occurred.
     * @return Updated Event counter-map.
     */
    @Override
    Map<String, Integer> handle(Map<String, Integer> event_counter) {
        this.population.remove_one_individual(this.getIndividual_id());
        return this.update_event_counter(event_counter);
    }
}
