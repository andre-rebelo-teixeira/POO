package Event;

import Event.GenericEvent;
import population.Population;
import java.util.Map;



public class ReproductionEvent extends GenericEvent {

	public ReproductionEvent(Population population, Integer id) {
		super(population, id);
	}

	@Override
	String get_class_name() {
		return "ReproductionEvent";
	}

	@Override
	Map<String, Integer> handle(Map<String, Integer> event_counter) {
		this.population.reproduct_individual(this.indiviual_id);
		return this.update_event_counter(event_counter);
	}
}


