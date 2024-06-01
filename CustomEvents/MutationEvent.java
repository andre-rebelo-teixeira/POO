package CustomEvents;

import Pair.Pair;
import Event.GenericEvent;
import Event.PEC;
import Event.EventContainer;
import java.util.Random;
import population.PopulationInterface;
import ExponentialDistribution.ExponentialDistributionInterface;

/**
 * Represents an event where an individual's genetic distribution is changed.
 *
 * The `MutationEvent` class extends the `GenericEvent` class and implements the functionality
 * for handling the mutation of an individual's genetic distribution in the population.
 *
 * @version 1.0
 * @since 1.0
 * @see Event.GenericEvent
 * @see Event.PEC
 * @see population.PopulationInterface
 * @see ExponentialDistribution.ExponentialDistributionInterface
 * @see Pair.Pair
 * @see java.util.Random
 *
 */
public class MutationEvent extends GenericEvent {

    /**
     * The number of planets affecting the genetic distribution.
     */
    private final Integer num_planets;

    /**
     * The parameter influencing the handling time of the event.
     */
    private final Integer delta;

    /**
     * The exponential distribution interface for calculating event times.
     */
    private final ExponentialDistributionInterface exp;

    /**
     * Constructor for MutationEvent.
     *
     * @param id The ID of the individual that will undergo mutation.
     * @param num_planets The number of planets affecting the genetic distribution.
     * @param delta The parameter influencing the handling time of the event.
     * @param exp The exponential distribution interface for calculating event times.
     */
    public MutationEvent(Integer id, Integer num_planets, Integer delta, ExponentialDistributionInterface exp) {
        super(id, delta);
        this.exp = exp;
        this.delta = delta;
        this.num_planets = num_planets;
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
     * Calculates the mean time for the event based on a comfort parameter.
     *
     * @param comfort The comfort parameter influencing the mean time.
     * @return The mean time for the event.
     */
    @Override
    public Double get_mean_time(Float comfort) {
        return (1 - Math.log(comfort)) * this.parameter;
    }

    /**
     * Handles the mutation event by changing the genetic distribution of the individual.
     * Updates the event counter.
     *
     * @param population The population interface affected by the event.
     * @param pec The pending event container.
     * @return A pair containing the updated population interface and the pending event container.
     */
    @Override
    public Pair<PopulationInterface, EventContainer> handle(PopulationInterface population, EventContainer pec) {
        Random rand = new Random();
        population.change_distribution_of_individual(this.individual_id, rand.nextInt(this.num_planets));

        Float comfort = population.get_comfort_value(this.individual_id);

        exp.setMean((1 - Math.log(comfort)) * this.delta);
        Double time = exp.getExponentialRandom();

        GenericEvent e = new MutationEvent(this.individual_id, this.num_planets, this.delta, this.exp);
        this.exp.setMean(e.get_mean_time(comfort));
        e.setHandling_time(this.exp.getExponentialRandom());
        pec.addEvent(e);

        pec.setEventCounter(this.update_event_counter(pec.getEventCounter()));
        return new Pair<>(population, pec);
    }
}
