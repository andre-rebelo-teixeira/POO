package Event;

import Event.GenericEvent;
import population.Population;

import java.util.Map;
import java.util.PriorityQueue;

public class MutationEvent extends GenericEvent{
    final Integer num_changes;

    MutationEvent(Population population, Integer id, Integer time, Integer num_changes){
        super(population, id, time);
        this.num_changes = num_changes;

    }

    @Override
    String get_class_name() {
        return "MutationEvent";
    }

    @Override
    Map<String, Integer> handle(Map<String, Integer> event_counter) {
        this.population.change_distribution_of_individual(this.individual_id, this.num_changes);
        return this.update_event_counter(event_counter);
    }
}
