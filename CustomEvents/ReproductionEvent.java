package CustomEvents;

import Event.GenericEvent;
import population.Population;
import java.util.Map;

/**
 * Represents an event where an individual in the population reproduces.
 * This event will create a new individual as a copy of the existing one and apply changes to the new individual.
 */
public class ReproductionEvent extends GenericEvent {
	final Integer num_changes;

	/**
	 * Constructor for ReproductionEvent.
	 *
	 * @param population The population involved in the event.
	 * @param id The ID of the individual that will reproduce.
	 * @param time The time at which the event will be handled.
	 * @param num_changes The number of changes in the genetic distribution of the new individual.
	 */
	public ReproductionEvent(Population population, Integer id, Integer time, Integer num_changes) {
		super(population, id, time);
		this.num_changes = num_changes;
	}

	/**
	 * Returns the name of the event class.
	 *
	 * @return The string "ReproductionEvent".
	 */
	@Override
	String get_class_name() {
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
	Map<String, Integer> handle(Map<String, Integer> event_counter) {
		Integer new_id = this.population.create_new_copy_of_individual(this.individual_id);
		this.population.change_distribution_of_individual(new_id, this.num_changes);
		return this.update_event_counter(event_counter);
	}
}
