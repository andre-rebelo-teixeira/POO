package CustomEvents;

import Event.PEC;
import Event.GenericEvent;
import population.PopulationInterface;
import java.util.function.Predicate;
import java.util.Map;
import Pair.Pair;

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

    @Override
    public Double get_mean_time(Float comfort)
    {
        return (1 - Math.log(1 - comfort)) * this.parameter;
    }

    /**
     * Handles the death event by removing the individual from the population.
     * Updates the event counter.
     *
     * @return Updated Event counter-map.
     */
    @Override
    public Pair<PopulationInterface, PEC> handle(PopulationInterface population, PEC pec) {
        pec.removeEvents(remove_events);
        population.remove_one_individual(this.getIndividual_id());
        pec.setEventCounter(  this.update_event_counter( pec.getEventCounter() )) ;
        return new  Pair<PopulationInterface, PEC> (population, pec);
    }
}
