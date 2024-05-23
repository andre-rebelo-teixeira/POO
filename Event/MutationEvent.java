package Event;

import Event.GenericEvent;
import population.Population;

import java.util.Map;
import java.util.PriorityQueue;

public class MutationEvent extends GenericEvent{
    MutationEvent(Population population, Integer id, Integer time){
        super(population, id, time);

    }

    @Override
    String get_class_name() {
        return "MutationEvent";
    }

    @Override
    Map<String, Integer> handle(Map<String, Integer> event_counter) {
        this.population.mutate_individial(this.indiviual_id);
        return this.update_event_counter(event_counter);
    }
}
