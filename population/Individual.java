package population;

// import from java
import java.util.*;

/**
 * Represents an individual in a population, with a specific number of planetary systems and patrols.
 * Each individual has a unique ID and maintains lists of planetary systems and patrols.
 * This class supports creating random patrol distributions, changing distributions, and notifying observers.
 */
class Individual {
    /**
     * The number of planetary systems associated with this individual.
     */
    private int number_of_planetary_systems;

    /**
     * The number of patrols associated with this individual.
     */
    private int number_of_patrols;

    /**
     * A map associating each planet's ID to the patrol that patrols it.
     * The key is the planet ID and the value is the patrol ID.
     */
    private HashMap<Integer, Integer> planet_patrol;

    /**
     * A list of observers that monitor changes to this individual.
     */
    private ArrayList<Observer> observers;

    /**
     * A random float value associated with this individual.
     */
    private float random;

    /**
     * An instance of the Random class used for generating random numbers.
     */
    Random rand;

    /**
     * A list of patrol objects associated with this individual.
     */
    private ArrayList<Patrol> patrol_list;

    /**
     * A list of planetary system objects associated with this individual.
     */
    private ArrayList<PlanetarySystem> planet_list;

    /**
     * A cost matrix representing the cost of each patrol for each planetary system.
     * The first dimension represents patrols and the second dimension represents planetary systems.
     */
    int[][] cost_matrix;

    /**
     * The unique ID of this individual.
     */
    Integer id;

    /**
     * The minimum time for comfort level calculation.
     * This value is final and cannot be changed once set.
     */
    final Float t_min;

    /**
     * Copy constructor to create a new individual based on an existing one.
     * @param i The individual to copy from.
     * @param id The unique ID for the new individual.
     */
    public Individual(Individual i, Integer id) {
        this.id = id;
        this.number_of_patrols = i.number_of_patrols;
        this.number_of_planetary_systems = i.number_of_planetary_systems;
        this.random = i.random;
        this.rand = i.rand;
        this.cost_matrix = i.cost_matrix;
        this.t_min = i.t_min;
        this.observers = i.observers;
        this.planet_patrol = i.planet_patrol;

        this.patrol_list = new ArrayList<>();
        this.planet_list = new ArrayList<>();

        for (PlanetarySystem p : i.planet_list) {
            this.planet_list.add(new PlanetarySystem(p));
        }

        for (Patrol p : i.patrol_list) {
            this.patrol_list.add(new Patrol(p));
        }
    }

    /**
     * Constructor to create a new individual with specified parameters.
     * @param id The unique ID for the new individual.
     * @param number_of_planetary_systems The number of planetary systems.
     * @param number_of_patrols The number of patrols.
     * @param cost_matrix The cost matrix for patrols.
     * @param t_min The minimum time for comfort level calculation.
     */
    public Individual(Integer id, int number_of_planetary_systems, int number_of_patrols, int[][] cost_matrix, Float t_min) {
        this.id = id;
        this.number_of_planetary_systems = number_of_planetary_systems;
        this.number_of_patrols = number_of_patrols;
        this.cost_matrix = cost_matrix;
        this.rand = new Random();
        this.t_min = t_min;
        this.observers = new ArrayList<>();
        this.planet_patrol = new HashMap<>();

        this.patrol_list = new ArrayList<Patrol>();
        this.planet_list = new ArrayList<PlanetarySystem>();

        for (int i = 0; i < number_of_patrols; i++) {
            Integer[] patrol_cost = new Integer[this.number_of_planetary_systems];
            for (int j = 0; j < number_of_planetary_systems; j++) {
                patrol_cost[j] = this.cost_matrix[i][j];
            }

            this.patrol_list.add(new Patrol(i, patrol_cost));
        }

        for (int i = 0; i < this.number_of_planetary_systems; i++) {
            this.planet_list.add(new PlanetarySystem(i));
        }
    }

    /**
     * Gets the maximum patrol time among all patrols.
     * @return The maximum patrol time.
     */
    public int get_max_patrol_time() {
        int max_time = Integer.MIN_VALUE;

        for (Patrol p : this.patrol_list) {
            max_time = Math.max(max_time, p.get_patrol_time());
        }

        return max_time;
    }

    /**
     * Creates a random patrol distribution for planetary systems.
     */
    public void create_random_patrol_distribution() {
        Random rand = new Random();

        for (PlanetarySystem p : this.planet_list) {
            int patrol = rand.nextInt(this.number_of_patrols);
            this.patrol_list.get(patrol).patrol_new_planet(p.get_id(), p);
            this.planet_patrol.put(p.get_id(), patrol);
        }

        this.notify_observers();
    }

    /**
     * Changes the patrol distribution by a specified number of changes.
     * @param number_changes The number of changes to make in the distribution.
     */
    public void change_distribution(Integer number_changes) {
        ArrayList<Integer> all_planetary_systems = new ArrayList<Integer>();

        // Create an Array list with all the planetary systems id
        for (int i = 0; i < this.number_of_planetary_systems; i++) {
            all_planetary_systems.add(i);
        }

        // Iterate through all the number of changes we need to do and calculate a new patrol for them
        for (int i = 0; i < number_changes; i++) {
            // Get a new planetary system from a random index
            int planetary_system_index = this.rand.nextInt(all_planetary_systems.size() - 1);
            all_planetary_systems.remove(planetary_system_index);

            int planetary_system = all_planetary_systems.get(planetary_system_index);

            // Get the patrol that patrols that planet
            Patrol p = this.patrol_list.get(this.planet_patrol.get(planetary_system));
            p.remove_planet_to_patrol(planetary_system);

            this.assign_planetary_system_to_random_patrol(planetary_system, p.getId());
        }
        this.notify_observers();
    }

    /**
     * Assigns a planetary system to a random patrol.
     * @param planetary_system The planetary system to be assigned.
     * @param prev_patrol_id The previous patrol ID.
     */
    public void assign_planetary_system_to_random_patrol(int planetary_system, int prev_patrol_id) {
        int counter = 0;
        int new_patrol_id = prev_patrol_id;
        new_patrol_id = rand.nextInt(this.number_of_patrols);

        if (new_patrol_id == prev_patrol_id) {
            if (new_patrol_id == 0) {
                new_patrol_id++;
            } else if (new_patrol_id == this.number_of_patrols - 1) {
                new_patrol_id--;
            } else {
                new_patrol_id /= 2;
            }
        }

        this.patrol_list.get(new_patrol_id).patrol_new_planet(planetary_system, planet_list.get(planetary_system));
        this.planet_patrol.put(planetary_system, new_patrol_id);
    }

    /**
     * Gets the comfort level of this individual.
     * The comfort level is defined as the minimum time divided by the maximum patrol time.
     * @return The comfort level.
     */
    public float get_comfort_level() {
        return (float) this.t_min / this.get_max_patrol_time();
    }

    /**
     * Gets the number of planetary systems.
     * @return The number of planetary systems.
     */
    public int getNumber_of_planetary_systems() {
        return number_of_planetary_systems;
    }

    /**
     * Sets the number of planetary systems.
     * @param number_of_planetary_systems The new number of planetary systems.
     */
    public void setNumber_of_planetary_systems(int number_of_planetary_systems) {
        this.number_of_planetary_systems = number_of_planetary_systems;
    }

    /**
     * Gets the number of patrols.
     * @return The number of patrols.
     */
    public int getNumber_of_patrols() {
        return number_of_patrols;
    }

    /**
     * Sets the number of patrols.
     * @param number_of_patrols The new number of patrols.
     */
    public void setNumber_of_patrols(int number_of_patrols) {
        this.number_of_patrols = number_of_patrols;
    }

    /**
     * Gets the random value associated with this individual.
     * @return The random value.
     */
    public float getRandom() {
        return random;
    }

    /**
     * Sets the random value associated with this individual.
     * @param random The new random value.
     */
    public void setRandom(float random) {
        this.random = random;
    }

    /**
     * Gets the list of patrols.
     * @return The list of patrols.
     */
    public ArrayList<Patrol> getPatrol_list() {
        return patrol_list;
    }

    /**
     * Sets the list of patrols.
     * @param patrol_list The new list of patrols.
     */
    public void setPatrol_list(ArrayList<Patrol> patrol_list) {
        this.patrol_list = patrol_list;
    }

    /**
     * Gets the cost matrix.
     * @return The cost matrix.
     */
    public int[][] getCost_matrix() {
        return cost_matrix;
    }

    /**
     * Sets the cost matrix.
     * @param cost_matrix The new cost matrix.
     */
    public void setCost_matrix(int[][] cost_matrix) {
        this.cost_matrix = cost_matrix;
    }

    /**
     * Gets the unique ID of this individual.
     * @return The unique ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique ID of this individual.
     * @param id The new unique ID.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Adds an observer to this individual.
     * @param observer The observer to add.
     */
    public void add_observer(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Removes an observer from this individual.
     * @param observer The observer to remove.
     */
    public void remove_observer(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notifies all observers of changes to this individual.
     */
    public void notify_observers() {
        for (Observer observer : observers) {
            observer.update(this.id, this.get_comfort_level());
        }
    }

    /**
     * Gets a string representation of the individual's information.
     * @return A string containing the individual's information.
     */
    public String get_information_string() {
        StringBuilder node_info = new StringBuilder();
        node_info = new StringBuilder("{");

        for (Patrol p : this.patrol_list) {
            node_info.append("{");
            node_info.append(p.get_patrolled_planets());
            node_info.append("},");
        }

        node_info.deleteCharAt(node_info.length() - 1);
        node_info.append("} : ");
        node_info.append(String.valueOf(this.get_max_patrol_time()));
        node_info.append(" : ");
        node_info.append(String.valueOf(this.get_comfort_level()));
        return node_info.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Objects.equals(planet_patrol, that.planet_patrol);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
