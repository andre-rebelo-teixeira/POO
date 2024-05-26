package CustomEvents;

import Event.GenericEvent;
import Event.PEC;
import population.Population;
import population.PopulationInterface;

import java.util.Map;

/**
 * Represents an event where an individual in the population reproduces.
 * This event will create a new individual as a copy of the existing one and apply changes to the new individual.
 */
public class ReproductionEvent extends GenericEvent {
	private final Integer number_of_changes;

	/**
	 * Constructor for ReproductionEvent.
	 *

	 * @param id The ID of the individual that will reproduce.
	 * @param time The time at which the event will be handled.
	 * @param number_of_changes The number of changes in the genetic distribution of the new individual.
	 */
	public ReproductionEvent (Integer id, Double time, Integer number_of_changes) {
		super(id, time);
		this.number_of_changes = number_of_changes;
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
	 * Handles the reproduction event by creating a new individual as a copy of the existing one
	 * and applying changes to the new individual. Updates the event counter.
	 *
	 * @param event_counter The map that will be counting the amount of time each Event type has occurred.
	 * @return Updated Event counter-map.
	 */
	@Override
	public Map<String, Integer> handle(Map<String, Integer> event_counter, PopulationInterface population, PEC pec) {
		Integer new_id = population.create_new_copy_of_individual(this.individual_id);
		population.change_distribution_of_individual(new_id, this.number_of_changes );
		return this.update_event_counter(event_counter);
	}
}
