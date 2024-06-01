package CustomEvents;

import Event.GenericEvent;
import Event.PEC;
import Pair.Pair;
import jdk.javadoc.doclet.Reporter;
import population.Population;
import population.PopulationInterface;
import ExponentialDistribution.ExponentialDistributionInterface;

import java.util.Map;

/**
 * Represents an event where an individual in the population reproduces.
 * This event will create a new individual as a copy of the existing one and
 * apply changes to the new individual.
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
	 * 
	 * @param id   The ID of the individual that will reproduce.
	 * @param time The time at which the event will be handled.
	 */
	public ReproductionEvent(Integer id,
			Integer m,
			Integer mu,
			Integer rho,
			Integer delta,
			Float comfort,
			Integer num_planets,
			ExponentialDistributionInterface exp) {
		super(id, rho);

		// these values will be used to compute the times for the death, reproduction
		// and mutation of the offspring
		this.m = m;
		this.mu = mu;
		this.rho = rho;
		this.delta = delta;

		this.num_planets = num_planets;

		// This value will be used to compute the time of the next events
		this.exp = exp;

		// This will be the number of changes we will compute for this event
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

	@Override
	public Double get_mean_time(Float comfort) {
		return (1 - Math.log(comfort)) * this.parameter;
	}

	/**
	 * Handles the reproduction event by creating a new individual as a copy of the
	 * existing one
	 * and applying changes to the new individual. Updates the event counter.
	 *
	 * @return Updated Event counter-map.
	 */
	@Override
	public Pair<PopulationInterface, PEC> handle(PopulationInterface population, PEC pec) {
		Integer new_id = population.create_new_copy_of_individual(this.individual_id);
		population.change_distribution_of_individual(new_id, this.number_of_changes);

		// We need to create a new reproduction event for the parent
		// Get the new comfort for the individual
		Float comfort = population.get_comfort_value(this.individual_id);
		Integer num_changes = compute_number_of_changes(comfort);

		exp.setMean((1 - Math.log(comfort)) * this.rho);
		double time = exp.getExponentialRandom();

		GenericEvent e = new ReproductionEvent(this.individual_id, this.m, this.mu, this.rho, this.delta, comfort,
				this.num_planets, this.exp);
		this.exp.setMean(e.get_mean_time(comfort));
		e.setHandling_time(this.exp.getExponentialRandom() + this.handling_time);
		pec.addEvent(e);

		// We need to create reproduction, mutation and death events for the children we
		// just spawned
		// Death
		exp.setMean((1 - Math.log(1 - comfort)) * this.mu);
		time = exp.getExponentialRandom();
		e = new DeathEvent(new_id, this.mu);
		this.exp.setMean(e.get_mean_time(comfort));
		e.setHandling_time(this.exp.getExponentialRandom() + this.handling_time);
		pec.addEvent(e);

		// reproduction
		comfort = population.get_comfort_value(new_id);
		num_changes = compute_number_of_changes(comfort);
		exp.setMean((1 - Math.log(comfort)) * this.rho);
		time = exp.getExponentialRandom();

		e = new ReproductionEvent(new_id, this.m, this.mu, this.rho, this.delta, comfort, this.num_planets, this.exp);
		this.exp.setMean(e.get_mean_time(comfort));
		e.setHandling_time(this.exp.getExponentialRandom() + this.handling_time);
		pec.addEvent(e);

		// mutation
		exp.setMean((1 - Math.log(comfort)) * this.delta);
		time = exp.getExponentialRandom();

		e = new MutationEvent(new_id, this.num_planets, this.delta, this.exp);
		this.exp.setMean(e.get_mean_time(comfort));
		e.setHandling_time(this.exp.getExponentialRandom() + this.handling_time);
		pec.addEvent(e);

		this.update_event_counter(pec.getEventCounter());
		return new Pair<PopulationInterface, PEC>(population, pec);
	}

	/**
	 *
	 */
	private Integer compute_number_of_changes(Float comfort) {
		return (int) (1 - comfort) * this.m;
	}

}
