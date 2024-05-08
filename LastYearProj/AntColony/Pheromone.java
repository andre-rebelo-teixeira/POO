/*
 * Pheromone.java
 * 
 * Created on 16/06/2023
 */
package AntColony;

class Pheromone {
	private double strength;

	public Pheromone() {
		strength = 0;
	}

	public Pheromone(double strength) {
		this.strength = strength;
	}

	public void updateStrength(double pheromoneDelta) {
		this.strength -= pheromoneDelta;
	}

	public double getStrength() {
		return strength;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(strength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pheromone other = (Pheromone) obj;
		if (Double.doubleToLongBits(strength) != Double.doubleToLongBits(other.strength))
			return false;
		return true;
	}

}
