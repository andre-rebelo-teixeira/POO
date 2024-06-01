package Event;

import population.PopulationInterface;
import Pair.Pair;

import java.util.Map;

/**
 * This is an Abstract Class that creates the baseline for what an event is in
 * the context
 * of this simulation. The event has an associated ID, which is the ID of the
 * element of the
 * simulation that it will affect. The event will be extended in multiple other
 * classes, each
 * having its own handle function, allowing for polymorphism.
 *
 * The `GenericEvent` class provides a foundation for defining specific events
 * in the simulation,
 * encapsulating common properties and behaviors. Each subclass will implement
 * specific details
 * of the event handling.
 *
 * @version 1.0
 * @since 1.0
 * @see population.PopulationInterface
 * @see Pair.Pair
 * @see java.util.Map
 * @see java.util.HashMap
 * @see PEC
 * @see GenericEventComparator
 *
 * @author André Rebelo Teixeira
 */
public abstract class GenericEvent {

	/**
	 * The ID of the individual affected by the event.
	 */
	protected Integer individual_id;

	/**
	 * The time at which the event will be handled.
	 * Initialized to positive infinity by default.
	 */
	protected Double handling_time = Double.POSITIVE_INFINITY;

	/**
	 * An additional parameter for the event.
	 */
	protected Integer parameter;

	/**
	 * Constructor for GenericEvent.
	 *
	 * @param id        The ID of the individual affected by the event.
	 * @param parameter Additional parameter for the event.
	 */
	public GenericEvent(Integer id, Integer parameter) {
		this.individual_id = id;
		this.parameter = parameter;
	}

	/**
	 * This method will be inherited by all the classes that extend the GenericEvent
	 * class.
	 * It is used to keep a counter of how many times an event has occurred. Since
	 * the body
	 * of the method will be the same for all the dependent classes, it is defined
	 * once in
	 * this class and used via inheritance to save work.
	 *
	 * @param event_counter The map that counts the occurrences of each event type.
	 * @return Updated event counter map.
	 */
	protected Map<String, Integer> update_event_counter(Map<String, Integer> event_counter) {
		String name = this.get_class_name();
		event_counter.put(name, event_counter.getOrDefault(name, 0) + 1);
		return event_counter;
	}

	/**
	 * Abstract method that all classes inheriting from GenericEvent must define to
	 * return a String
	 * with the name of the class.
	 *
	 * @return The name of the class.
	 */
	public abstract String get_class_name();

	/**
	 * Abstract method that all classes inheriting from GenericEvent must define to
	 * handle the event.
	 *
	 * @param population The population interface affected by the event.
	 * @param pec        The pending event container.
	 * @return A pair containing the updated population interface and the pending
	 *         event container.
	 */
	public abstract Pair<PopulationInterface, EventContainer> handle(PopulationInterface population, EventContainer pec);

	/**
	 * Gets the ID of the individual affected by the event.
	 *
	 * @return The individual ID.
	 */
	public Integer getIndividual_id() {
		return individual_id;
	}

	/**
	 * Abstract method that all classes inheriting from GenericEvent must define to
	 * get the mean time
	 * for the event based on a comfort parameter.
	 *
	 * @param comfort The comfort parameter influencing the mean time.
	 * @return The mean time for the event.
	 */
	public abstract Double get_mean_time(Float comfort);

	/**
	 * Sets the handling time for the event.
	 *
	 * @param handling_time The time at which the event will be handled.
	 */
	public void setHandling_time(Double handling_time) {
		this.handling_time = handling_time;
	}

	/**
	 * Gets the handling time for the event.
	 *
	 * @return The handling time.
	 */
	public Double getHandling_time() {
		return handling_time;
	}
}
