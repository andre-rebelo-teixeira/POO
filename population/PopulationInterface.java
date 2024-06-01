package population;

import java.util.ArrayList;
import Pair.Pair;

/**
 * PopulationInterface.java
 *
 * This interface defines the methods for managing a population of individuals
 * within a simulation,
 * providing methods to manipulate individuals, change distributions, create
 * copies, and retrieve
 * various statistics and information about the population.
 *
 * Created on 01/06/2024
 *
 * @version 1.0
 * @since 1.0
 *
 * @author André Rebelo Teixeira
 */
public interface PopulationInterface {

    /**
     * Removes a single individual from the population.
     *
     * @param individual_id The ID of the individual to be removed.
     * @return True if the individual was successfully removed, false otherwise.
     */
    public Boolean remove_one_individual(Integer individual_id);

    /**
     * Removes multiple individuals from the population.
     *
     * @param individual_ids A list of IDs of the individuals to be removed.
     * @return True if the individuals were successfully removed, false otherwise.
     */
    public Boolean remove_individuals(ArrayList<Integer> individual_ids);

    /**
     * Removes all individuals from the population.
     *
     * @return True if all individuals were successfully removed, false otherwise.
     */
    public Boolean remove_all_individuals();

    /**
     * Changes the distribution of a single individual.
     *
     * @param individual_id The ID of the individual whose distribution is to be
     *                      changed.
     * @param num_changes   The number of changes to be made to the distribution.
     * @return True if the distribution was successfully changed, false otherwise.
     */
    public Boolean change_distribution_of_individual(Integer individual_id, Integer num_changes);

    /**
     * Changes the distribution of multiple individuals.
     *
     * @param individuals_changes A list of pairs, where each pair contains the ID
     *                            of the individual
     *                            and the number of changes to be made to their
     *                            distribution.
     * @return True if the distributions were successfully changed, false otherwise.
     */
    public Boolean change_distribution_of_individuals(ArrayList<Pair<Integer, Integer>> individuals_changes);

    /**
     * Creates a new copy of a single individual.
     *
     * @param individual_id The ID of the individual to be copied.
     * @return The ID of the newly created copy.
     */
    public Integer create_new_copy_of_individual(Integer individual_id);

    /**
     * Creates new copies of multiple individuals.
     *
     * @param individuals_changes A list of pairs, where each pair contains the ID
     *                            of the individual
     *                            to be copied and the number of copies to be
     *                            created.
     * @return A list of IDs of the newly created copies.
     */
    public ArrayList<Integer> create_new_copy_of_individuals(ArrayList<Pair<Integer, Integer>> individuals_changes);

    /**
     * Gets the current population size.
     *
     * @return The size of the population.
     */
    public Integer get_population_size();

    /**
     * Gets the comfort value of a single individual.
     *
     * @param individual_id The ID of the individual whose comfort value is to be
     *                      retrieved.
     * @return The comfort value of the individual.
     */
    public Float get_comfort_value(Integer individual_id);

    /**
     * Gets the comfort values for all individuals in the population.
     *
     * @return A list of pairs, where each pair contains the ID of the individual
     *         and their comfort value.
     */
    public ArrayList<Pair<Integer, Float>> get_comfort_vector();

    /**
     * Checks if the optimal solution has been found.
     *
     * @return True if the optimal solution has been found, false otherwise.
     */
    public Boolean get_optimal_solution_found();

    /**
     * Gets the best individual values.
     *
     * @return A pair containing the best value and a string representation of the
     *         best individual.
     */
    public Pair<Float, String> get_best_individual_values();

    /**
     * Gets string representations of the best individuals.
     *
     * @return An array of strings, where each string represents one of the best
     *         individuals.
     */
    public String[] get_best_individuals_string();

    /**
     * Starts a new epidemic within the population.
     * This method triggers the beginning of a new epidemic event in the population
     * simulation.
     */
    public void start_new_epidemic();

    /**
     * Gets the minimum time value.
     *
     * @return The minimum time value.
     */
    public Float get_time_min();
}
