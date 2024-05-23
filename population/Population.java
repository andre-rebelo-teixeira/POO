package population;

import ExponentialDistribution.ExponentialDistributionInterface;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;


public class Population {
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
}
