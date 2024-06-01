package population;

import java.util.Objects;

/**
 * Represents a planetary system identified by a unique ID.
 * This class is immutable; the ID can only be set through the constructor and cannot be changed.
 * To change the ID, a new instance of PlanetarySystem must be created.
 *
 * This class is final and cannot be subclassed.
 *
 * Example usage:
 * PlanetarySystem ps1 = new PlanetarySystem(1);
 * PlanetarySystem ps2 = new PlanetarySystem(ps1);
 * System.out.println(ps1.get_id()); // Outputs: 1
 *
 *
 */
final class PlanetarySystem {
	/**
	 * Id of the planetary system
	 */
	private final Integer id;

	/**
	 * Copy constructor to create a new PlanetarySystem based on an existing one.
	 * @param p The PlanetarySystem to copy.
	 */
	public PlanetarySystem(PlanetarySystem p) {
		this.id = p.id;
	}

	/**
	 * Constructs a PlanetarySystem with a specified ID.
	 * The ID of the PlanetarySystem is final and cannot be changed once set.
	 * @param id The unique ID of the PlanetarySystem.
	 */
	public PlanetarySystem(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the ID of the PlanetarySystem.
	 * @return The ID of the PlanetarySystem.
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
