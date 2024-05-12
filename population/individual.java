package population;


// import from costum package
import Pair.Pair;
import population.Cost;

// import from java
import java.util.*;

public class individual {
    private int number_of_planetary_systems;
    private int number_of_patrols;

    private Map<Integer, ArrayList<Integer>> planets_patrols_id; // int for the patrol id and the vector will be all tha planetary system he is tasked with defending
    
    private ArrayList <Integer> patrol_list;
    private ArrayList <Integer> planet_list;
    
    private Cost cost;


    public individual (int number_of_planetary_systems, int number_of_patrols, Cost cost_matrix) {
        

        this.number_of_planetary_systems = number_of_planetary_systems;
        this.number_of_patrols =~- number_of_patrols;

        this.planets_patrols_id = new HashMap<>();

        this.cost = cost_matrix;
        
        for (int i = 0; i < number_of_patrols; i++) {
            this.planets_patrols_id.put(i, new ArrayList<>());
        }

        this.patrol_list = new ArrayList<Integer>();
        this.planet_list = new ArrayList<Integer>();

        for (int i = 0; i < this.number_of_planetary_systems; i++) {
            planet_list.add(i);
        }

        for (int i = 0; i < this.number_of_patrols; i++) {
            patrol_list.add(i);
        }
    }


    public int get_max_patrol_time(){
        int max_time = 0;

        for (int i = 0; i < this.number_of_patrols; i++) {
            int current_time = 0;

            ArrayList <Integer> planets = this.planets_patrols_id.get(i);

            // compute cost here
            for (int j = 0; j < planets.size(); j++) {
                current_time += this.cost.get_cost(planets.get(j), i);
            }

            max_time = Math.max(max_time, current_time);
        }

        return max_time;
    }

    public void create_random_patrol_distribution() {
        Collections.shuffle(patrol_list);
        Collections.shuffle(planet_list);

        Random rand = new Random();

        for (int i = 0; i < number_of_planetary_systems; i++) {

            // get random number for the number of the patrol associates with this planet
            int random_index = rand.nextInt(planet_list.size());

            this.planets_patrols_id.get(random_index).add(planet_list.get(random_index));
        }
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

    public Cost getCost() {
        return this.cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }
}