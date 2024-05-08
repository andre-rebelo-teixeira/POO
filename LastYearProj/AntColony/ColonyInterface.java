/*
 * ColonyInterface.java
 * 
 * Created on 16/06/2023
 */
package AntColony;

import Path.Path;
import java.util.Vector;

public interface ColonyInterface {

	public Path getBestPath();

	public Vector<Path> getPathVector();

	public void moveAnts(int AntID);

	public double getPheromoneValue(String edgeKey);

	public void setPheromoneLevel(String edgeKey, double pheromoneLevel);

	public Path finishAntMove(int AntID);

	public int getAntEdgeWeight(int AntId);
	// protected void move(graph)
}
