package population;

import ExponentialDistribution.ExponentialDistributionInterface;
import Pair.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;


public class Population implements PopulationInterface {
    private HashMap<Integer, individual> individuals;
    private Vector<Integer> plannetary_system_vec;

	private Integer current_id;
	private final Integer size;
    private final Integer number_of_planetary_systems;
    private final Integer number_of_patrols;
	private final int [][] cost_matrix;
	private int t_min;

	private ExponentialDistributionInterface expo_random;

    public Population(Integer size,
					  Integer number_of_planetary_systems,
					  Integer number_of_patrols,
					  int[][] cost_matrix) {

        this.number_of_planetary_systems = number_of_planetary_systems;
		this.plannetary_system_vec = new Vector<>();
        this.number_of_patrols = number_of_patrols;
		this.individuals = new HashMap<>();
		this.cost_matrix = cost_matrix;
		this.current_id = 0;
        this.size = size;

        for (int i = 0; i < this.size; i++){
            individual ind = new individual(this.number_of_planetary_systems, this.number_of_patrols, this.cost_matrix);
        	ind.create_random_patrol_distribution();
			this.individuals.put(i, ind);
		}

		for (int i = 0; i < this.number_of_planetary_systems; i++) {
			this.plannetary_system_vec.add(i);
		}
    }

	@Override
	public Boolean remove_one_individual(Integer individual_id) {
		individual ind = this.individuals.get(individual_id);
		if (ind != null) {
			this.individuals.remove(individual_id);
			return true;
		}
		return false;
	}

	@Override
	public Boolean remove_individuals(ArrayList<Integer> individual_ids) {
		boolean all_removed = true;

		for (Integer individual_id : individual_ids){
			all_removed = all_removed && this.remove_one_individual(individual_id);
		}
		return all_removed;
	}

	@Override
	public Boolean remove_all_individuals() {
		this.individuals.clear();
		return true;
	}

	@Override
	public Boolean change_distribution_of_individual(Integer individual_id, Integer num_changes) {
		individual ind = this.individuals.get(individual_id);

		if (ind != null) {
			ind.change_distribution(num_changes);
			return true;
		}
		return false;
	}

	@Override
	public Boolean change_distribution_of_individuals(ArrayList<Pair<Integer, Integer>> individuals_changes) {
		boolean all_changed = true;
		for (Pair<Integer, Integer> individual_change : individuals_changes) {
			all_changed = all_changed && this.change_distribution_of_individual(individual_change.getFirst(), individual_change.getSecond());
		}
		return all_changed;
	}

	@Override
	public Integer create_new_copy_of_individual(Integer individual_id) {
		individual ind = this.individuals.get(individual_id);
		Integer new_id = this.get_new_id();

		// Simply puts, still need to shuffle the distributions around
		if (ind != null) {
			this.individuals.put(new_id, ind);
			return new_id;
		}

		return -1;
	}

	@Override
	public ArrayList<Integer> create_new_copy_of_individuals(ArrayList<Pair<Integer, Integer>> individuals_changes) {
		ArrayList <Integer> new_ids = new ArrayList<>();
		for (Pair<Integer, Integer> individual_change : individuals_changes) {
			new_ids.add((this.create_new_copy_of_individual(individual_change.getFirst()));
		}
		return new_ids;
	}	

	private Integer get_new_id() {
		this.current_id = this.current_id + 1;
		return	this.current_id;
	}
}
