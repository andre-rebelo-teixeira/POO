package CustomEvents;

import Event.GenericEvent;
import Event.PEC;
import Pair.Pair;
import population.PopulationInterface;
import ExponentialDistribution.ExponentialDistributionInterface;

/**
 * Represents an event where an individual in the population reproduces.
 * This event will create a new individual as a copy of the existing one and
 * apply changes to the new individual.
 *
 * The `ReproductionEvent` class extends the `GenericEvent` class and implements
 * the functionality for handling the reproduction of an individual in the population.
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
public class ReproductionEvent extends GenericEvent {
	private final Integer number_of_changes;
	private final Integer m;
	private final Integer mu;
	private final Integer rho;
	private final Integer delta;
	private final Integer num_planets;
	ExponentialDistributionInterface exp;

	/**
	 * Constructor for ReproductionEvent.
	 *
	 * @param id The ID of the individual that will reproduce.
	 * @param m Parameter influencing the number of changes.
	 * @param mu Parameter influencing the handling time of death events.
	 * @param rho Parameter influencing the handling time of reproduction events.
	 * @param delta Parameter influencing the handling time of mutation events.
	 * @param comfort Comfort level influencing the event.
	 * @param num_planets Number of planets affecting the genetic distribution.
	 * @param exp The exponential distribution interface for calculating event times.
	 */
	public ReproductionEvent(Integer id, Integer m, Integer mu, Integer rho, Integer delta, Float comfort, Integer num_planets, ExponentialDistributionInterface exp) {
		super(id, rho);

		this.m = m;
		this.mu = mu;
		this.rho = rho;
		this.delta = delta;
		this.num_planets = num_planets;
		this.exp = exp;
		this.number_of_changes = compute_number_of_changes(comfort);
	}

	/**
	 * Returns the name of the event class.
	 *
	 * @return The string "ReproductionEvent".
	 */
	@Override
	public String get_class_name() {
		return "ReproductionEvent";
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
	 * Handles the reproduction event by creating a new individual as a copy of the existing one
	 * and applying changes to the new individual. Updates the event counter.
	 *
	 * @param population The population interface affected by the event.
	 * @param pec The pending event container.
	 * @return A pair containing the updated population interface and the pending event container.
	 */
	@Override
	public Pair<PopulationInterface, PEC> handle(PopulationInterface population, PEC pec) {
		Integer new_id = population.create_new_copy_of_individual(this.individual_id);
		population.change_distribution_of_individual(new_id, this.number_of_changes);

		// Create a new reproduction event for the parent
		Float comfort = population.get_comfort_value(this.individual_id);
		Integer num_changes = compute_number_of_changes(comfort);

		exp.setMean((1 - Math.log(comfort)) * this.rho);
		double time = exp.getExponentialRandom();

		GenericEvent e = new ReproductionEvent(this.individual_id, this.m, this.mu, this.rho, this.delta, comfort, this.num_planets, this.exp);
		this.exp.setMean(e.get_mean_time(comfort));
		e.setHandling_time(this.exp.getExponentialRandom() + this.handling_time);
		pec.addEvent(e);

		// Create reproduction, mutation and death events for the offspring
		// Death event
		exp.setMean((1 - Math.log(1 - comfort)) * this.mu);
		time = exp.getExponentialRandom();
		e = new DeathEvent(new_id, this.mu);
		this.exp.setMean(e.get_mean_time(comfort));
		e.setHandling_time(this.exp.getExponentialRandom() + this.handling_time);
		pec.addEvent(e);

		// Reproduction event
		comfort = population.get_comfort_value(new_id);
		num_changes = compute_number_of_changes(comfort);
		exp.setMean((1 - Math.log(comfort)) * this.rho);
		time = exp.getExponentialRandom();

		e = new ReproductionEvent(new_id, this.m, this.mu, this.rho, this.delta, comfort, this.num_planets, this.exp);
		this.exp.setMean(e.get_mean_time(comfort));
		e.setHandling_time(this.exp.getExponentialRandom() + this.handling_time);
		pec.addEvent(e);

		// Mutation event
		exp.setMean((1 - Math.log(comfort)) * this.delta);
		time = exp.getExponentialRandom();

		e = new MutationEvent(new_id, this.num_planets, this.delta, this.exp);
		this.exp.setMean(e.get_mean_time(comfort));
		e.setHandling_time(this.exp.getExponentialRandom() + this.handling_time);
		pec.addEvent(e);

		this.update_event_counter(pec.getEventCounter());
		return new Pair<>(population, pec);
	}

	/**
	 * Computes the number of changes based on the comfort parameter.
	 *
	 * @param comfort The comfort parameter.
	 * @return The number of changes.
	 */
	private Integer compute_number_of_changes(Float comfort) {
		return (int) ((1 - comfort) * this.m);
	}
}
