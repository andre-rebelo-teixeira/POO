package CustomEvents;

import Pair.Pair;
import Event.GenericEvent;
import Event.PEC;
import java.util.Random;
import population.Population;
import population.PopulationInterface;
import CustomEvents.ReproductionEvent;
import ExponentialDistribution.ExponentialDistributionInterface;

import java.util.Map;
import java.util.Random;

/**
 * Represents an event where an individual's genetic distribution is changed.
 */
public class MutationEvent extends GenericEvent {
    private final Integer num_planets;
    private final Integer delta;
    private final ExponentialDistributionInterface exp;

    /**
     * Constructor for MutationEvent.
     *
     * @param id The ID of the individual that will undergo mutation.
     */
    public MutationEvent(Integer id,
            Integer num_planets,
            Integer delta,
            ExponentialDistributionInterface exp) {
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

    @Override
    public Double get_mean_time(Float comfort) {
        return (1 - Math.log(comfort)) * this.parameter;
    }

    /**
     * Handles the mutation event by changing the genetic distribution of the
     * individual.
     * Updates the event counter.
     *
     * @param event_counter The map that will be counting the amount of time each
     *                      Event type has occurred.
     * @return Updated Event counter-map.
     */
    @Override
    public Pair<PopulationInterface, PEC> handle(PopulationInterface population, PEC pec) {
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
        return new Pair<PopulationInterface, PEC>(population, pec);
    }
}
