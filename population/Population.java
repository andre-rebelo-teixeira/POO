package population;

import ExponentialDistribution.ExponentialDistribution;
import ExponentialDistribution.ExponentialDistributionInterface;
import Pair.Pair;

import java.util.*;

/**
 * The Population class manages a collection of Individual objects within a population.
 * It provides methods for manipulating and retrieving information about these individuals,
 * such as adding, removing, and modifying their distributions, as well as computing and
 * tracking comfort levels and optimal solutions.
 */
public class Population implements PopulationInterface, Observer {

    /**
     * Stores the Individual objects in the population.
     */
    private HashMap<Integer, Individual> individuals;

    /**
     * Stores the best distributions.
     */
    private int[] best_distributions;

    /**
     * Keeps track of the current ID for individuals.
     */
    private Integer current_id;

    /**
     * Number of planetary systems.
     */
    private final Integer number_of_planetary_systems;

    /**
     * Number of patrols.
     */
    private final Integer number_of_patrols;

    /**
     * Cost matrix for patrol times.
     */
    private int[][] cost_matrix;

    /**
     * Minimum time for patrols.
     */
    private float t_min;

    /**
     * Flag indicating if the optimal solution is found.
     */
    private Boolean optimal_solution;

    /**
     * Stores information about the best individual.
     */
    private Pair<Float, String> best_individual_info;

    /**
     * Map of comfort values for individuals.
     */
    private ArrayList<Pair<Integer, Float>> comfort_map;

    /**
     * Instance of exponential distribution for randomness.
     */
    private ExponentialDistributionInterface expo_random;

    /**
     * Initializes a new population with a given size, number of planetary systems,
     * number of patrols, and cost matrix.
     *
     * @param size Number of individuals in the population.
     * @param number_of_planetary_systems Number of planetary systems.
     * @param number_of_patrols Number of patrols.
     * @param cost_matrix Matrix of patrol times.
     */
    public Population(Integer size, Integer number_of_planetary_systems, Integer number_of_patrols, int[][] cost_matrix) {
        this.number_of_planetary_systems = number_of_planetary_systems;
        this.expo_random = new ExponentialDistribution(0.0);
        Vector<Integer> planetary_system_vec = new Vector<>();
        this.number_of_patrols = number_of_patrols;
        this.individuals = new HashMap<>();
        this.cost_matrix = cost_matrix;
        this.optimal_solution = false;
        this.current_id = -1;
        this.compute_min_time();
        this.comfort_map = new ArrayList<>();
        this.best_individual_info = new Pair<Float, String>((float) -1.0, "");

        IndividualComparator individualComparator = new IndividualComparator();

        for (int i = 0; i < size; i++) {
            Individual ind = new Individual(this.get_new_id(),
                    this.number_of_planetary_systems,
                    this.number_of_patrols,
                    this.cost_matrix,
                    this.t_min);

            // Add individuals to the population
            this.individuals.put(ind.id, ind);

            // Add observer to the individuals
            ind.add_observer(this);
            ind.create_random_patrol_distribution();
        }

        for (int i = 0; i < this.number_of_planetary_systems; i++) {
            planetary_system_vec.add(i);
        }
    }

    /**
     * Removes an individual by ID.
     *
     * @param individual_id ID of the individual to remove.
     * @return True if the individual was removed, false otherwise.
     */
    @Override
    public Boolean remove_one_individual(Integer individual_id) {
        Individual ind = this.individuals.get(individual_id);
        if (ind != null) {
            this.individuals.remove(individual_id);
            return true;
        }
        return false;
    }

    /**
     * Removes multiple individuals by their IDs.
     *
     * @param individual_ids List of IDs of individuals to remove.
     * @return True if all individuals were removed, false otherwise.
     */
    @Override
    public Boolean remove_individuals(ArrayList<Integer> individual_ids) {
        boolean all_removed = true;

        for (Integer individual_id : individual_ids) {
            all_removed = all_removed && this.remove_one_individual(individual_id);
        }
        return all_removed;
    }

    /**
     * Removes all individuals from the population.
     *
     * @return True if all individuals were removed.
     */
    @Override
    public Boolean remove_all_individuals() {
        this.individuals.clear();
        return true;
    }

    /**
     * Changes the distribution of a specific individual.
     *
     * @param individual_id ID of the individual whose distribution to change.
     * @param num_changes Number of changes to apply.
     * @return True if the distribution was changed, false otherwise.
     */
    @Override
    public Boolean change_distribution_of_individual(Integer individual_id, Integer num_changes) {
        Individual ind = this.individuals.get(individual_id);

        if (ind != null) {
            ind.change_distribution(num_changes);
            return true;
        }
        return false;
    }

    /**
     * Changes the distribution of multiple individuals.
     *
     * @param individuals_changes List of pairs of individual IDs and the number of changes to apply.
     * @return True if all distributions were changed, false otherwise.
     */
    @Override
    public Boolean change_distribution_of_individuals(ArrayList<Pair<Integer, Integer>> individuals_changes) {
        boolean all_changed = true;
        for (Pair<Integer, Integer> individual_change : individuals_changes) {
            all_changed = all_changed &&
                    this.change_distribution_of_individual(individual_change.first(), individual_change.second());
        }
        return all_changed;
    }

    /**
     * Creates a new copy of an individual by ID.
     *
     * @param individual_id ID of the individual to copy.
     * @return ID of the new individual, or -1 if the copy failed.
     */
    @Override
    public Integer create_new_copy_of_individual(Integer individual_id) {
        Individual ind = this.individuals.get(individual_id);
        Integer new_id = this.get_new_id();

        if (ind != null) {
            Individual new_ind = new Individual(ind, new_id);
            this.individuals.put(new_id, new_ind);
            return new_id;
        }

        return -1;
    }

    /**
     * Creates new copies of multiple individuals.
     *
     * @param individuals_changes List of pairs of individual IDs and the number of changes to apply.
     * @return List of IDs of the new individuals.
     */
    @Override
    public ArrayList<Integer> create_new_copy_of_individuals(ArrayList<Pair<Integer, Integer>> individuals_changes) {
        ArrayList<Integer> new_ids = new ArrayList<>();
        for (Pair<Integer, Integer> individual_change : individuals_changes) {
            new_ids.add(this.create_new_copy_of_individual(individual_change.first()));
        }
        return new_ids;
    }

    /**
     * Returns the size of the population.
     *
     * @return Size of the population.
     */
    @Override
    public Integer get_population_size() {
        return this.individuals.size();
    }

    /**
     * Returns the comfort values of the individuals.
     *
     * @return List of pairs of individual IDs and their comfort values.
     */
    @Override
    public ArrayList<Pair<Integer, Float>> get_comfort_vector() {
        return this.comfort_map;
    }

    /**
     * Returns the comfort value of a specific individual.
     *
     * @param individual_id ID of the individual.
     * @return Comfort value of the individual.
     */
    @Override
    public Float get_comfort_value(Integer individual_id) {
        for (Pair <Integer, Float> p : this.comfort_map) {
            if (p.first() == individual_id) {
                return p.second();
            }
        }

        Individual ind = this.individuals.getOrDefault(individual_id, null);

        if (ind != null) {
            return this.t_min / ind.get_max_patrol_time();
        }

        return (float) -1.0;
    }

    /**
     * Checks if the optimal solution has been found.
     *
     * @return True if the optimal solution is found, false otherwise.
     */
    @Override
    public Boolean get_optimal_solution_found() {
        return this.optimal_solution;
    }

    /**
     * Starts a new epidemic among the individuals.
     */
    @Override
    public void start_new_epidemic() {
        System.out.println("Started an epidemic with " + this.individuals.size());
        ArrayList<Integer> individual_ids = new ArrayList<>();

        for (Individual individual : this.individuals.values()) {
            individual_ids.add(individual.getId());
        }

        ArrayList<Individual> temp = new ArrayList<>();
        PriorityQueue<Individual> best_individuals = this.create_priority_queue(5);

        while (best_individuals.peek() != null) {
            Individual ind = best_individuals.poll();
            temp.add(ind);
            individual_ids.remove(ind.getId());
        }

        Random rand = new Random();
        ArrayList<Integer> remove_individual_ids = new ArrayList<>();

        for (Integer individual_id : individual_ids) {
            Individual ind = this.individuals.get(individual_id);
            if (!(2.0 / 3.0 * ind.get_comfort_level() * 100 < rand.nextDouble(0, 100))) {
                remove_individual_ids.add(individual_id);
            }
        }

        for (Integer individual_id : remove_individual_ids) {
            this.individuals.remove(individual_id);
        }
    }

    /**
     * Returns an array of strings representing the best individuals.
     *
     * @return Array of strings with information about the best individuals.
     */
    @Override
    public String[] get_best_individuals_string() {
        ArrayList<Individual> temp = new ArrayList<>();
        PriorityQueue<Individual> best_individuals = this.create_priority_queue(12);
        String[] best_individuals_string = new String[6];
        int counter = 0;

        while (best_individuals.peek() != null && counter < 6) {
            boolean can_print = true;

            Individual ind = best_individuals.poll();
            if (!Objects.equals(this.best_individual_info.second(), ind.get_information_string())) {
                for (int i = 0; i < counter; i++) {
                    if (Objects.equals( best_individuals_string[i], ind.get_information_string() )) {
                        can_print = false;
                    }
                }
                if (can_print){
                    best_individuals_string[counter++] = ind.get_information_string();
                }
            }
        }

        return best_individuals_string;
    }

    /**
     * Returns the minimum time for patrols.
     *
     * @return Minimum patrol time.
     */
    @Override
    public Float get_time_min() {
        return this.t_min;
    }

    /**
     * Returns the best individual values.
     *
     * @return Pair containing the best comfort value and the information string.
     */
    @Override
    public Pair<Float, String> get_best_individual_values() {
        return this.best_individual_info;
    }

    /**
     * Updates the comfort value of an individual.
     *
     * @param individualId ID of the individual.
     * @param comfortValue New comfort value.
     */
    @Override
    public void update(int individualId, float comfortValue) {
        if (this.best_individual_info.first() < comfortValue) {
            Individual ind = this.individuals.get(individualId);
            this.best_individual_info = new Pair<Float, String>(comfortValue, ind.get_information_string());
        }

        Pair<Integer, Float> new_pair = new Pair<Integer, Float>(individualId, comfortValue);

        if (comfortValue == 1) {
            this.optimal_solution = true;
        }

        // Remove old value from the map
        for (Pair<Integer, Float> pair : this.comfort_map) {
            if (pair.first() == individualId) {
                this.comfort_map.remove(pair); // remove previous pair
                break;
            }
        }

        // Add new value to the map
        this.comfort_map.add(new Pair<Integer, Float>(individualId, comfortValue));
    }

    /**
     * Generates a new unique ID for individuals.
     *
     * @return New unique ID.
     */
    private Integer get_new_id() {
        this.current_id = this.current_id + 1;
        return this.current_id;
    }

    /**
     * Computes the minimum patrol time.
     */
    private void compute_min_time() {
        int sum = 0;

        for (int i = 0; i < this.number_of_planetary_systems; i++) {
            int min_patrol_time = Integer.MAX_VALUE;
            for (int j = 0; j < this.number_of_patrols; j++) {
                min_patrol_time = Math.min(min_patrol_time, cost_matrix[j][i]);
            }

            sum += min_patrol_time;
        }

        this.t_min = (float) sum / this.number_of_patrols;
    }

    /**
     * Creates a fixed size priority queue with the best individuals of the current set of individuals.
     *
     * @param size Desired size of the priority queue.
     * @return Priority queue containing the best current living individuals, all the individuals have different distributions
     */
    private PriorityQueue<Individual> create_priority_queue(Integer size) {
        IndividualComparator individualComparator = new IndividualComparator();
        PriorityQueue<Individual> q_ = new PriorityQueue<Individual>(individualComparator);
        PriorityQueue<Individual> q = new PriorityQueue<Individual>(individualComparator);
        ArrayList<Individual> temp = new ArrayList<>();

        q_.addAll(this.individuals.values());

        while (!q_.isEmpty() && q_.peek() != null) {
            Individual individual = q_.poll();
            boolean different_individual = true;

            if (temp.isEmpty()) {
                temp.add(individual);
            } else {
                for (Individual ind : temp) {
                    if (Objects.equals(individual.get_information_string(), ind.get_information_string())) {
                        different_individual = false;
                        break;
                    }
                }
            }

            if (different_individual) {
                temp.add(individual);
            }

            if (temp.size() == size) {
                break;
            }
        }

        q.addAll(temp);

        return q;
    }
}
