package population;

import java.util.ArrayList;
import Pair.Pair;

public interface PopulationInterface {
    public Boolean remove_one_individual(Integer individual_id);

    public Boolean remove_individuals (ArrayList<Integer> individual_ids);

    public Boolean remove_all_individuals();

    public Boolean change_distribution_of_individual(Integer individual_id, Integer num_changes);

    public Boolean change_distribution_of_individuals(ArrayList<Pair<Integer, Integer>> individuals_changes);

    public Integer create_new_copy_of_individual (Integer individual_id);

    public ArrayList<Integer> create_new_copy_of_individuals (ArrayList<Pair<Integer, Integer>> individuals_changes);

    public Integer get_population_size();

    public Float get_comfort_value(Integer individual_id);

    public ArrayList<Pair<Integer, Float>> get_comfort_vector();

    public Boolean get_optimal_solution_found();

    public String[] get_best_individuals_string();

    public void start_new_epidemic();

    public Float get_time_min();
}
