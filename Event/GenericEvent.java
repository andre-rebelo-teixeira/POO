package Event;

import population.population;
//import SimulInfo.SimulInfo;

/**
 * This is an Abstract Class that will create the base line for what an event is in the context 
 * of this Simulation.
 * The event has an associated ID, this will be the ID of the element of the simulation that they 
 * will affect
 * The Event will be extended in multiple other files, each of the class it created will have its 
 * own handle function, this will allow for really easy polymorfirsm
 *
 * @author André Rebelo Teixeira
 *
 */
public abstract class GenericEvent {
	protected Integer indiviual_id;
	protected population population;

	public GenericEvent (population population, Integer id) {
		this.population = population; 
		this.indiviual_id = id;
	}

	abstract void handle();
};
