package CustomEvents;

import Event.PEC;
import Event.GenericEvent;
import population.PopulationInterface;
import java.util.function.Predicate;
import java.util.Map;

/**
 * Represents an event where an individual in the population dies.
 * This event will remove the individual from the population.
 */
public class DeathEvent extends GenericEvent {
    Predicate<GenericEvent> remove_events;
    /**
     * Constructor for DeathEvent
     * * @param id The ID of the individual that will die.
     * @param handling_time The time at which the event will be handled.
     */
    public DeathEvent(Integer id, Double handling_time) {
        super(id, handling_time);
        this.remove_events = event -> event.getIndividual_id().equals(this.getIndividual_id());
    }

    /**
     * Returns the name of the event class.
     *
     * @return The string "DeathEvent".
     */
    @Override
    public String get_class_name() {
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
    public Map<String, Integer> handle(Map<String, Integer> event_counter, PopulationInterface population, PEC pec) {
        pec.removeEvents(remove_events);
        population.remove_one_individual(this.getIndividual_id());
        return this.update_event_counter(event_counter);
    }
}
