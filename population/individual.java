package population;

// import from java
import java.util.*;

import population.Patrol;
import population.PlanetarySystem;

public class individual {
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

        this.patrol_list = new ArrayList<Integer>();
        this.planet_list = new ArrayList<Integer>();

        for (int i = 0; i < number_of_patrols; i++) {
            this.patrol_list.add(new Patrol(i));
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
            this.patrol_list.get(patrol).patrol_new_plannet(i, planet_list.get(i));
            this.PlannetarySystem_patrol.put(i, patrol);
        }
    }

	public void assign_planetary_system_to_random_patrol(int planetary_system) {
        Random rand = new Random();
        int patrol = rand.nextInt(this.number_of_patrols);

        this.PlannetarySystem_patrol.remove(plantary_system);

        this.patrol_list.get(patrol).patrol_new_plannet(planetary_system, planet_list.get(planetary_system));
        this.PlannetarySystem_patrol.put(planetary_system, patrol);
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

    public Map<Integer, ArrayList<Integer>> getPlanets_patrols_id() {
        return planets_patrols_id;
    }

    public void setPlanets_patrols_id(Map<Integer, ArrayList<Integer>> planets_patrols_id) {
        this.planets_patrols_id = planets_patrols_id;
    }

    public ArrayList<Integer> getPatrol_list() {
        return patrol_list;
    }

    public void setPatrol_list(ArrayList<Integer> patrol_list) {
        this.patrol_list = patrol_list;
    }

    public ArrayList<Integer> getPlanet_list() {
        return planet_list;
    }

    public void setPlanet_list(ArrayList<Integer> planet_list) {
        this.planet_list = planet_list;
    }

    public int[][] getCost() {
        return this.cost_matrix;
    }

    public void setCost(int [][] cost) { 
        this.cost_matrix = cost;
    }
}
