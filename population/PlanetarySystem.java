package population;

import java.util.Objects;

/**
 * PlanetarySystem -> This class is used to represent the information of one planetary system,
 *
 * Currently only is used to store the ID
 *
 * @author Andre Rebelo Teixeira
 */
final class PlanetarySystem {
	private final Integer id;

	public PlanetarySystem(PlanetarySystem p) {
		this.id = p.id;
	}

	/**
	 *
	 * method that allows us to Create an object of type PlanetarySystem,
	 * The id of the object is final and can only be set through the constructor, in case we can to change the id,
	 * we must delete the object and create a new one
	 *
	 * @param id - This will be the ID of the planetary System
	 */
	public PlanetarySystem (Integer id) {
		this.id = id;
	}

	/**
	 * This method is used to get the ID of the object
	 *
	 * @return The object ID
	 */
	public int get_id() {
		return this.id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlanetarySystem that = (PlanetarySystem) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "PlanetarySystem{" +
				"id=" + id +
				'}';
	}
}
	
