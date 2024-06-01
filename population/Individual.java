package population;

// import from java
import java.util.*;

class Individual {
    private int number_of_planetary_systems;
    private int number_of_patrols;
    private HashMap <Integer, Integer> planet_patrol;
    private ArrayList<Observer> observers;

	private float random;
    Random rand;
    
    private ArrayList <Patrol> patrol_list;
    private ArrayList <PlanetarySystem> planet_list;
	
	int [][] cost_matrix;
    Integer id;
    final Float t_min;

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

        for(PlanetarySystem p :  i.planet_list) {
            this.planet_list.add(new PlanetarySystem(p));
        }

        for (Patrol p : i.patrol_list) {
            this.patrol_list.add(new Patrol(p));
        }
    }

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
            for  (int j  = 0; j < number_of_planetary_systems; j++) {
                patrol_cost[j] = this.cost_matrix[i][j];
            }

            this.patrol_list.add(new Patrol(i,patrol_cost));
        }

        for (int i = 0; i < this.number_of_planetary_systems; i++) {
            this.planet_list.add(new PlanetarySystem(i));
        }
    }

    public int get_max_patrol_time(){
        int max_time = 0;

        for (int i = 0; i < this.number_of_patrols; i++) {
            int current_time = this.patrol_list.get(i).get_patrol_time();

            max_time = Math.max(max_time, current_time);
        }
        return max_time;
    }

    public void create_random_patrol_distribution() {
        Random rand = new Random();

        for (int i = 0; i < this.number_of_planetary_systems; i++){
            int patrol = rand.nextInt(this.number_of_patrols);  
            this.patrol_list.get(patrol).patrol_new_planet(i, planet_list.get(i));
            this.planet_patrol.put(i, patrol);

        }

        this.notify_observers();
    }

    public void  change_distribution(Integer number_changes) {
        ArrayList<Integer> all_planetary_systems = new ArrayList<Integer>();

        // Create an Array list with all the planetary systems id
        for (int i = 0; i < this.number_of_planetary_systems; i++) {
            all_planetary_systems.add(i);
        }

        // iterate through all the number of changes we need to do and calculate a new patrol for them
        for (int i = 0; i < number_changes; i++) {
            // Get a new planetary system from a random index
            int planetary_system_index = this.rand.nextInt(all_planetary_systems.size() - 1);
            all_planetary_systems.remove(planetary_system_index);

            int planetary_system = all_planetary_systems.get(planetary_system_index);

            //  get the patrol that patrols that planets
            Patrol p = this.patrol_list.get(this.planet_patrol.get(planetary_system));
            p.remove_planet_to_patrol(planetary_system);

            this.assign_planetary_system_to_random_patrol(planetary_system, p.getId());
        }
        this.notify_observers();
    }

	public void assign_planetary_system_to_random_patrol(int planetary_system, int prev_patrol_id) {
        int counter = 0;
        int new_patrol_id = prev_patrol_id;
        new_patrol_id = rand.nextInt(this.number_of_patrols);

        if (new_patrol_id == prev_patrol_id) {
            if (new_patrol_id == 0) {
                new_patrol_id++;
            }
            else if (new_patrol_id == this.number_of_patrols - 1) {
                new_patrol_id--;
            }
            else {
                new_patrol_id /= 2;
            }
        }

        this.patrol_list.get(new_patrol_id).patrol_new_planet(planetary_system, planet_list.get(planetary_system));
        this.planet_patrol.put(planetary_system, new_patrol_id);
    }

	public float get_comfort_level() {
		// the comfort level is defined as the time for this individual divided by the min time
		return (float)  this.t_min / this.get_max_patrol_time();
	}

    public int getNumber_of_planetary_systems() {
        return number_of_planetary_systems;
    }

    public void setNumber_of_planetary_systems(int number_of_planetary_systems) {
        this.number_of_planetary_systems = number_of_planetary_systems;
    }

    public int getNumber_of_patrols() {
        return number_of_patrols;
    }

    public void setNumber_of_patrols(int number_of_patrols) {
        this.number_of_patrols = number_of_patrols;
    }

    public float getRandom() {
        return random;
    }

    public void setRandom(float random) {
        this.random = random;
    }

    public ArrayList<Patrol> getPatrol_list() {
        return patrol_list;
    }

    public void setPatrol_list(ArrayList<Patrol> patrol_list) {
        this.patrol_list = patrol_list;
    }

    public int[][] getCost_matrix() {
        return cost_matrix;
    }

    public void setCost_matrix(int[][] cost_matrix) {
        this.cost_matrix = cost_matrix;
    }

    public Integer getId() {
        return id;
    }

    public void add_observer(Observer observer) {
        this.observers.add(observer);
    }

    public void remove_observer(Observer observer) {
        this.observers.remove(observer);
    }

    public void notify_observers() {
        for (Observer observer : observers) {
            observer.update(this.id, this.get_comfort_level());
        }
    }

    public String get_information_string() {
        StringBuilder node_info = new StringBuilder();
        node_info = new StringBuilder("{");
        for (int i = 0; i < this.number_of_patrols; i++) {
            Patrol patrol = this.patrol_list.get(i);

            node_info.append("{");
            node_info.append(patrol.get_patrolled_planets());
            node_info.append("},");
        }
        node_info.deleteCharAt(node_info.length() - 1);
        node_info.append("} : ");
        node_info.append(String.valueOf(this.get_max_patrol_time()));
        node_info.append(" : ");
        node_info.append(String.valueOf(this.get_comfort_level()));
        return node_info.toString();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Individual that = ( Individual ) o;
        if ( Objects.equals( planet_patrol, that.planet_patrol ) )
        {
            return true;
        }
        return false;
    }
}
