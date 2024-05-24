package Event;

import population.Population;
import java.util.Map;


/**
 * This is an Abstract Class that will create the base line for what an event is in the context 
 * of this Simulation.
 * The event has an associated ID, this will be the ID of the element of the simulation that they 
 * will affect
 * The Event will be extended in multiple other files, each of the class it created will have its 
 * own handle function, this will allow for really easy polymorphism
 *
 * @author André Rebelo Teixeira
 *
 */
public abstract class GenericEvent {
	protected Integer individual_id;
	protected Population population;
	protected Integer handling_time;

	public GenericEvent (Population population, Integer id, Integer handling_time) {
		this.population = population; 
		this.individual_id = id;
	}

	/**
	 * This Method will be inherited by all the classes that extent the Generic Event class
	 * This class will be used to keep a counter of how many times an event has occurred, since the body of the class
	 * will be the same for all the dependent classed we can define it only once inside this Class and use inheritance to
	 * save work
	 *
	 * @param event_counter The map that will be counting the amount of time each Event type has occurred
	 * @return	Updated Event counter map
	 */
	protected Map<String, Integer> update_event_counter(Map<String, Integer> event_counter) {
		String name = this.get_class_name();
		event_counter.put(name, event_counter.getOrDefault(name, 0) + 1);
		return event_counter;
	}

	/**
	 * Abstract class that all that inherit from GenericEvent must define that return a String with the name of the class
	 *
	 * @return String corresponding to the name of the class
	 */
	abstract String get_class_name();

	abstract Map<String, Integer> handle(Map<String, Integer> event_counter);

	public Integer getIndividual_id() {
		return individual_id;
	}

	public Population getPopulation() {
		return population;
	}

	public Integer getHandling_time() {
		return handling_time;
	}
};
