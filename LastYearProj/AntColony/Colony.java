/*
 * Colony.java
 * 
 * Created on 16/06/2023 
 */
package AntColony;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Random;
import java.util.*;
import java.util.stream.*;

import Graph.Graph;

import Path.Path;
// Import java utils
import java.util.*;

public class Colony implements ColonyInterface {
	private int alpha;
	private int beta;

	private Vector<Path> pathVector;

	private Path bestPath;
	private int size;
	private Vector<Ant> antVector;

	private HashMap<String, Pheromone> pheromoneMap;

	private Graph graph;

	private int nest;

	public Colony(int numberOFAnts, int nest, Graph graph, int alpha, int beta) {
		this.alpha = alpha;
		this.beta = beta;
		this.graph = graph;
		this.nest = nest;

		this.size = 0;

		this.bestPath = null;

		this.pathVector = new Vector<Path>();

		this.antVector = new Vector<Ant>();
		for (int i = 0; i < numberOFAnts; i++) {
			antVector.add(new Ant(i, nest));
		}

		// Starts pheromones
		this.pheromoneMap = new HashMap<String, Pheromone>();
		for (int i = 0; i < this.graph.getNumberOfNodes(); i++) {
			HashMap<Integer, Integer> nodeNeighour = this.graph.getNodeEdgesWeight(i);

			for (Map.Entry<Integer, Integer> entry : nodeNeighour.entrySet()) {
				String key = (i < entry.getKey()) ? i + "-" + entry.getKey() : entry.getKey() + "-" + i;

				this.pheromoneMap.put(key, new Pheromone(0));
			}
		}
	}

	private String[] decodeEdgeKey(String edgeKey) {
		return edgeKey.split("-");
	}

	protected Vector<Ant> getAntVector() {
		return antVector;
	}

	protected int getSize() {
		return size;
	}

	// Class interfae wrapper

	// Move all the ants that have already stopped
	@Override
	public void moveAnts(int antID) {
		Path path = antVector.get(antID).move(pheromoneMap,
				this.graph.getNodeEdgesWeight(this.antVector.get(antID).getCurrentNode()),
				this.alpha,
				this.beta,
				this.graph.getNumberOfNodes());

		if (path != null) {
			this.pathVector.add(path);

			this.bestPath = (this.bestPath == null) ? path : this.bestPath;
			this.bestPath = (this.bestPath.getPathWeight() > path.getPathWeight()) ? path : this.bestPath;

		}
	}

	@Override
	// get Pheromone value
	public double getPheromoneValue(String edgeKey) {
		return this.pheromoneMap.get(edgeKey).getStrength();
	}

	@Override
	// Set Pheromone value
	public void setPheromoneLevel(String edgeKey, double pheromoneLevel) {
		Pheromone pheromone = this.pheromoneMap.get(edgeKey);
		pheromone.setStrength(pheromoneLevel);
		this.pheromoneMap.put(edgeKey, pheromone);
	}

	@Override
	public int getAntEdgeWeight(int AntId) {
		Ant ant = this.antVector.get(AntId);

		return ant.getLastEdgeWeight();

	}

	@Override
	public Path getBestPath() {
		return this.bestPath;
	}

	@Override
	public Path finishAntMove(int antId) {
		Path path = antVector.get(antId).finishMove(this.graph.getNumberOfNodes());

		if (path != null) {
			for (int i = 0; i < path.getPath().size() - 1; i++) {

				int smallerNodeId = (path.getNodeId(i) < path.getNodeId(i + 1))
						? path.getNodeId(i)
						: path.getNodeId(i + 1);

				int biggerNodeID = (path.getNodeId(i) > path.getNodeId(i + 1))
						? path.getNodeId(i)
						: path.getNodeId(i + 1);

				String key = smallerNodeId + "_" + biggerNodeID;

				HashMap<Integer, Integer> edgeWeight = graph.getNodeEdgesWeight(path.getNodeId(i));

				int sum = 0;

				for (Entry<Integer, Integer> entry : edgeWeight.entrySet()) {
					sum += entry.getValue();
				}

				this.pheromoneMap.get(key)
						.setStrength(this.pheromoneMap.get(key).getStrength() + sum / path.getPathWeight());
			}
		}
		return path;
	}

	@Override
	public Vector<Path> getPathVector() {
		return this.pathVector;
	}
}
