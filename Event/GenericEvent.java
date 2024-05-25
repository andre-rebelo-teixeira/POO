package Event;

import population.Population;
import java.util.Map;

/**
 * This is an Abstract Class that will create the base line for what an event is in the context
 * of this Simulation.
 * The event has an associated ID, this will be the ID of the element of the simulation that they
 * will affect.
 * The Event will be extended in multiple other files, each of the classes created will have its
 * own handle function, this will allow for really easy polymorphism.
 *
 * @author André Rebelo Teixeira
 *
 */
public abstract class GenericEvent {
	protected Integer individual_id;
	protected Population population;
	protected Integer handling_time;

	/**
	 * Constructor for GenericEvent.
	 *
	 * @param population The population involved in the event.
	 * @param id The ID of the individual affected by the event.
	 * @param handling_time The time at which the event will be handled.
	 */
	public GenericEvent(Population population, Integer id, Integer handling_time) {
		this.population = population;
		this.individual_id = id;
	}

	/**
	 * This Method will be inherited by all the classes that extend the GenericEvent class.
	 * This method will be used to keep a counter of how many times an event has occurred. Since the body of the method
	 * will be the same for all the dependent classes, we can define it only once inside this Class and use inheritance to
	 * save work.
	 *
	 * @param event_counter The map that will be counting the amount of time each Event type has occurred.
	 * @return Updated Event counter-map.
	 */
	protected Map<String, Integer> update_event_counter(Map<String, Integer> event_counter) {
		String name = this.get_class_name();
		event_counter.put(name, event_counter.getOrDefault(name, 0) + 1);
		return event_counter;
	}

	/**
	 * Abstract method that all classes that inherit from GenericEvent must define to return a String with the name of the class.
	 *
	 * @return String corresponding to the name of the class.
	 */
	abstract String get_class_name();

	/**
	 * Abstract method that all classes that inherit from GenericEvent must define to handle the event.
	 *
	 * @param event_counter The map that will be counting the amount of time each Event type has occurred.
	 * @return Updated Event counter-map.
	 */
	abstract Map<String, Integer> handle(Map<String, Integer> event_counter);

	/**
	 * Gets the ID of the individual affected by the event.
	 *
	 * @return The individual ID.
	 */
	public Integer getIndividual_id() {
		return individual_id;
	}

	/**
	 * Gets the population involved in the event.
	 *
	 * @return The population.
	 */
	public Population getPopulation() {
		return population;
	}

	/**
	 * Gets the time at which the event will be handled.
	 *
	 * @return The handling time.
	 */
	public Integer getHandling_time() {
		return handling_time;
	}

	/**
	 * Sets the time at which the event will be handled.
	 *
	 * @param handling_time The new handling time.
	 */
	public void setHandling_time(Integer handling_time) {
		this.handling_time = handling_time;
	}
}