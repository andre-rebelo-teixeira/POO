package Event;

import population.Population;

import java.util.Map;

public class DeathEvent extends GenericEvent{
    /**
     * @param population
     * @param id
     * @param handling_time
     */
    public DeathEvent(Population population, Integer id, Integer handling_time) {
        super(population, id, handling_time);
    }

    /**
     * Abstract class that all that inherit from GenericEvent must define that return a String with the name of the class
     *
     * @return String corresponding to the name of the class
     */
    @Override
    String get_class_name() {
        return "DeathEvent";
    }

    /**
     * @param event_counter 
     * @return
     */
    @Override
    Map<String, Integer> handle(Map<String, Integer> event_counter) {
        this.population.remove_one_individual(this.getIndividual_id());
        return this.update_event_counter(event_counter);
    }
}
