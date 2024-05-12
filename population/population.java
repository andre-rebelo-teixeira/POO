package population;

import java.util.ArrayList;
import java.util.Vector;
import java.util.Comparator;


import population.individual;


public class population {
    private ArrayList<individual> individuals;
    private Integer size;
    private Integer number_of_planetary_systems;
    private Integer number_of_patrols;
    private Cost cost;

    public population(Integer size, Integer number_of_planetary_systems, Integer number_of_patrols, ArrayList <ArrayList <Integer>> cost_matrix){
        this.size = size;   
        this.number_of_planetary_systems = number_of_planetary_systems;
        this.number_of_patrols = number_of_patrols;

        this.cost = new Cost(this.number_of_patrols, this.number_of_planetary_systems, cost_matrix);

            
        for (int i = 0; i < this.size; i++){
            this.individuals.add(new individual(this.number_of_planetary_systems, this.number_of_patrols, this.cost));
        }
    };

    
    
}
