package Event;

import Event.GenericEvent;
import population.Population;
import java.util.Map;



public class ReproductionEvent extends GenericEvent {
	final Integer num_changes;

	public ReproductionEvent(Population population, Integer id, Integer time, Integer num_changes) {
		super(population, id, time);
		this.num_changes = num_changes;
	}

	@Override
	String get_class_name() {
		return "ReproductionEvent";
	}

	@Override
	Map<String, Integer> handle(Map<String, Integer> event_counter) {
		Integer  new_id = -1;
		new_id = this.population.create_new_copy_of_individual(this.individual_id);
		this.population.change_distribution_of_individual(new_id, this.num_changes);

		return this.update_event_counter(event_counter);
	}
}


