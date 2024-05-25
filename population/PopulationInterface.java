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
}
