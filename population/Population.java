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

	private Integer size;
    private Integer number_of_planetary_systems;
    private Integer number_of_patrols;
	private int [][] cost_matrix;
	private int t_min;


	private ExponentialDistributionInterface expo_random;

    public Population(Integer size,
					  Integer number_of_planetary_systems,
					  Integer number_of_patrols,
					  int[][] cost_matrix){
        this.size = size;   
        this.number_of_planetary_systems = number_of_planetary_systems;
        this.number_of_patrols = number_of_patrols;
		this.individuals = new HashMap<>();
		this.plannetary_system_vec = new Vector<>();
		
		this.cost_matrix = cost_matrix;

        for (int i = 0; i < this.size; i++){
            individual ind = new individual(this.number_of_planetary_systems, this.number_of_patrols, this.cost_matrix);
        	ind.create_random_patrol_distribution();
			this.individuals.put(i, ind);
		}

		for (int i = 0; i < this.number_of_planetary_systems; i++) {
			this.plannetary_system_vec.add(i);
		}

    };
	
	public Boolean kill_individual(int individual_id) {
		this.individuals.remove(individual_id);
		return true;
	}
	

	public Boolean mutate_individial(int individual_id){
		individual ind = this.individuals.get(individual_id);
	
		return true;
	}

	public Boolean reproduct_individual(int individual_id) {
		individual ind = this.individuals.get(individual_id);
		Vector<Integer> aux_vec = this.plannetary_system_vec;
		Vector<Integer> planets_to_be_replaced = new Vector<Integer>();

		int num_changed_planets = (int)(1 - ind.get_comfort_level(this.t_min)) * this.number_of_planetary_systems;

		Random rand  = new Random();
		
		for (int i = 0; i < num_changed_planets; i++){
			int index = rand.nextInt(aux_vec.size());

			planets_to_be_replaced.add(index);
			aux_vec.remove(index);
		}

		// add planets once agai
			
		return true;
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
		boolean all_removed =true;

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
		Integer new_id = 0;

		if (ind != null) {
			//  Copy stuff here
			return new_id;

		}

		return new_id;
	}

	@Override
	public ArrayList<Pair<Integer, Integer>> create_new_copy_of_individuals(ArrayList<Pair<Integer, Integer>> individuals_changes) {
		return null;
	}
}
