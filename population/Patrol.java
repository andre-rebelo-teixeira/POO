package population;

import java.util.HashMap;
import population.PlanetarySystem;

/**
 * Represents a patrol that monitors planetary systems.
 * Each patrol has a unique ID, a set of patrolled planetary systems, and an array of patrol times.
 */
class Patrol {
	private Integer id;
	private HashMap<Integer, PlanetarySystem> patrolled_planetary_systems;
	private Integer[] patrol_time;

	/**
	 * Copy constructor to create a new patrol based on an existing one.
	 * @param p The patrol to copy from.
	 */
	public Patrol(Patrol p) {
		this.id = p.id;
		this.patrol_time = p.patrol_time;
		this.patrolled_planetary_systems = new HashMap<>();
		for (Integer i : p.patrolled_planetary_systems.keySet()) {
			this.patrolled_planetary_systems.put(i, new PlanetarySystem(p.patrolled_planetary_systems.get(i)));
		}
	}

	/**
	 * Constructor to create a new patrol with specified parameters.
	 * @param id The unique ID for the patrol.
	 * @param patrol_time The patrol time array.
	 */
	public Patrol(Integer id, Integer[] patrol_time) {
		this.id = id;
		this.patrol_time = patrol_time;
		this.patrolled_planetary_systems = new HashMap<Integer, PlanetarySystem>();
	}

	/**
	 * Adds a new planetary system to the patrol.
	 * @param id The ID of the planetary system.
	 * @param planetary_system The planetary system to patrol.
	 */
	public void patrol_new_planet(Integer id, PlanetarySystem planetary_system) {
		this.patrolled_planetary_systems.put(id, planetary_system);
	}

	/**
	 * Removes a planetary system from the patrol.
	 * @param id The ID of the planetary system to remove.
	 */
	public void remove_planet_to_patrol(Integer id) {
		this.patrolled_planetary_systems.remove(id);
	}

	/**
	 * Gets the total patrol time for all patrolled planetary systems.
	 * @return The total patrol time.
	 */
	public int get_patrol_time() {
		int sum = 0;
		for (Integer id : this.patrolled_planetary_systems.keySet()) {
			sum += this.patrol_time[id];
		}
		return sum;
	}

	/**
	 * Prints the IDs of the patrolled planetary systems to the console.
	 */
	public void print_patrolled_plannets() {
		System.out.println(this.id);
		for (Integer id : this.patrolled_planetary_systems.keySet()) {
			System.out.print(" " + id);
		}
		System.out.println();
	}

	/**
	 * Gets a string representation of the patrolled planetary systems.
	 * @return A string containing the IDs of the patrolled planetary systems.
	 */
	public String get_patrolled_planets() {
		StringBuilder patrol_info = new StringBuilder();
		for (Integer id : this.patrolled_planetary_systems.keySet()) {
			patrol_info.append(String.valueOf(id)).append(",");
		}
		if (!patrol_info.isEmpty()) {
			patrol_info.deleteCharAt(patrol_info.length() - 1);
		}
		return patrol_info.toString();
	}

	/**
	 * Gets the unique ID of the patrol.
	 * @return The unique ID.
	 */
	public Integer getId() {
		return id;
	}
}
