package population;

// import from java
import java.util.*;

import population.Patrol;
import population.PlanetarySystem;

class individual {
    private int number_of_planetary_systems;
    private int number_of_patrols;

	private float random;
    
    private ArrayList <Patrol> patrol_list;
    private ArrayList <PlanetarySystem> planet_list;
	
	private HashMap <PlanetarySystem, Patrol> PlanetarySystem_patrol;
	int [][] cost_matrix;


    public individual (int number_of_planetary_systems, int number_of_patrols, int[][] cost_matrix) {
        this.number_of_planetary_systems = number_of_planetary_systems;
        this.number_of_patrols = number_of_patrols;
       	this.cost_matrix = cost_matrix;

        this.patrol_list = new ArrayList<Patrol>();
        this.planet_list = new ArrayList<PlanetarySystem>();
        this.PlanetarySystem_patrol = new HashMap<PlanetarySystem, Patrol>();

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
            this.PlanetarySystem_patrol.put(this.planet_list.get(i), this.patrol_list.get(patrol));
        }

        for (Patrol patrol : this.patrol_list) {
            patrol.print_patrolled_plannets();
        }
    }

	public void assign_planetary_system_to_random_patrol(int planetary_system) {
        Random rand = new Random();
        int patrol = rand.nextInt(this.number_of_patrols);

        this.PlanetarySystem_patrol.remove(this.planet_list.get(planetary_system));

        this.patrol_list.get(patrol).patrol_new_planet(planetary_system, planet_list.get(planetary_system));
        this.PlanetarySystem_patrol.put(this.planet_list.get(planetary_system), this.patrol_list.get(patrol));
        return;
    }

	public float get_comfort_level(int t_min) {
		// the comfort level is defined as the time for this individual divided by the min time
		return (float) this.get_max_patrol_time() / t_min;
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


}
