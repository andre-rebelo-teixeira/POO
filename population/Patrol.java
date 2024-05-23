package population;

import java.util.HashMap;
import population.PlanetarySystem;

class Patrol {
	private Integer id;
	private HashMap<Integer, PlanetarySystem> patrolled_planetary_systems;
	private Integer patrol_time[];

	public Patrol (Integer id, Integer[] patrol_time) {
		this.id = id;
		this.patrol_time = patrol_time;
		this.patrolled_planetary_systems = new HashMap<Integer, PlanetarySystem>();
	}

	public void patrol_new_planet(Integer id, PlanetarySystem planetary_system){
		this.patrolled_planetary_systems.put(id, planetary_system);
	}

	public void remove_planet_to_patrol(Integer id){
		this.patrolled_planetary_systems.remove(id);
		return;
	}

	public int get_patrol_time() {
		int sum = 0;

		for (Integer id : this.patrolled_planetary_systems.keySet()) {
			sum += this.patrol_time[id];
        }
		return sum;
	}

	public void print_patrolled_plannets() {
		System.out.println(this.id);
		for (Integer id : this.patrolled_planetary_systems.keySet()) {
			System.out.print(" " + id);
		}
		System.out.println();
	}
}

