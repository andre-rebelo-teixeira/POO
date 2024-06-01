package CustomEvents;

import Event.PEC;
import Event.GenericEvent;
import Event.EventContainer;
import population.PopulationInterface;
import java.util.function.Predicate;
import Pair.Pair;

/**
 * Represents an event where an individual in the population dies.
 * This event will remove the individual from the population.
 * The `DeathEvent` class extends the `GenericEvent` class and implements the functionality
 * for handling the death of an individual in the population.
 *
 * @version 1.0
 * @since 1.0
 * @see Event.GenericEvent
 * @see Event.PEC
 * @see population.PopulationInterface
 * @see Pair.Pair
 *
 * @author André Rebelo Teixeira
 */
public class DeathEvent extends GenericEvent {

    /**
     * A predicate used to filter and remove events related to the individual who is dying.
     * This predicate checks if the event's individual ID matches the ID of this death event.
     */
    private final Predicate<GenericEvent> remove_events;
    
    /**
     * Constructor for DeathEvent.
     *
     * @param id The ID of the individual that will die.
     * @param mu The parameter influencing the handling time of the event.
     */
    public DeathEvent(Integer id, Integer mu) {
        super(id, mu);
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
     * Calculates the mean time for the event based on a comfort parameter.
     *
     * @param comfort The comfort parameter influencing the mean time.
     * @return The mean time for the event.
     */
    @Override
    public Double get_mean_time(Float comfort) {
        return (1 - Math.log(1 - comfort)) * this.parameter;
    }

    /**
     * Handles the death event by removing the individual from the population.
     * Updates the event counter.
     *
     * @param population The population interface affected by the event.
     * @param pec The pending event container.
     * @return A pair containing the updated population interface and the pending event container.
     */
    @Override
    public Pair<PopulationInterface, EventContainer> handle(PopulationInterface population, EventContainer pec) {
        pec.removeEvents(remove_events);
        population.remove_one_individual(this.getIndividual_id());
        pec.setEventCounter(this.update_event_counter(pec.getEventCounter()));
        return new Pair<>(population, pec);
    }
}
