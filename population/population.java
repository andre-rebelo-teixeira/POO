ipackage population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import population.individual;
import population.Patrol;
import population.PlannetarySystem;
import ExponentialDistribution.ExponentialDistributionInterface;     


public class population {
    private HashMap<Integer, individual> individuals;
    private Vector<Integer> plannetary_system_vec;

	private Integer size;
    private Integer number_of_planetary_systems;
    private Integer number_of_patrols;
	private int [][] cost_matrix;
	private int t_min;


	private ExponentialDistributionInterface expo_random;

    public population(Integer size, Integer number_of_planetary_systems, Integer number_of_patrols, int[][] cost_matrix){
        this.size = size;   
        this.number_of_planetary_systems = number_of_planetary_systems;
        this.number_of_patrols = number_of_patrols;
		
		this.cost_matrix = cost_matrix;

        for (int i = 0; i < this.size; i++){
            this.individuals.put(i, new individual(this.number_of_planetary_systems, this.number_of_patrols, this.cost_matrix));
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
		Vector<Integer> plannets_to_be_replaced = new Vector<>();

		int num_changed_plannets = (int)(1 - ind.get_confort_level(this.t_min)) * this.number_of_planetary_systems;

		Random rand  = new Random();
		
		for (int i = 0; i < num_changed_plannets; i++){
			int index = rand.nextInt(aux_vec.size());
				
			plannets_to_be_replaced.add(index);
			aux_vec.remove(index);
		}

		// add plannets once agains

		
			
		return true;
	}

	

	
}
