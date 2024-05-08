/*
 * Ant.java
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

import Path.Path;

/**
 * An Ant can be defined as a simple agent that is able to move through a graph
 * within the Ant Colony Optimization Algorithm. From the paths taken by the
 * ants, the algorithm has the objective of finding the shortest path between
 * two nodes.
 *
 * @
 **/
public class Ant {
	private Path lastFinishedPath;
	private Path currentPath;
	private int lastEdgeWeight;
	final private int nest;
	private int nextNodeID;
	final private int antID;

	/**
	 * Constructor for the Ant class
	 *
	 * @param antId The ID of the ant
	 * @param nest  The id of the node where the nest is located
	 */
	public Ant(int antId, int nest) {

		this.currentPath = new Path();

		currentPath.addNode(nest, 0);

		this.lastFinishedPath = null;

		this.antID = antId;
		this.nest = nest;
		this.lastEdgeWeight = 0;
	}

	/**
	 * Returns the ID of the ant
	 *
	 * @param pheromone     Map containing the pheromone corresponding to a certain
	 *                      edge
	 * @param edgeWeight    List of edge weights of the nodes exiting the current
	 *                      Node
	 * @param alpha         Parameters related to the equation for the next node
	 *                      calculation
	 * @param beta          Parameters related to the equation for the next node
	 *                      calculation
	 * @param numberOfNodes Number of nodes in the graph
	 *
	 * @return
	 */
	public Path move(HashMap<String, Pheromone> pheromone, HashMap<Integer, Integer> edgeWeight, float alpha,
			float beta, int numberOfNodes) {

		HashMap<Integer, Float> nodeProbability = new HashMap<Integer, Float>();

		List<Integer> availableNodes = new ArrayList<Integer>();
		int sum = 0;

		for (Entry<Integer, Integer> entry : edgeWeight.entrySet()) {
			if (this.currentPath.nodeAlreadyTravaled(entry.getKey())) {
				continue;
			}
			int smallerNodeID = (entry.getKey() < this.getCurrentNode()) ? entry.getKey() : this.getCurrentNode();
			int biggerNodeID = (entry.getKey() > this.getCurrentNode()) ? entry.getKey() : this.getCurrentNode();

			availableNodes.add(entry.getKey());

			String key = smallerNodeID + "_" + biggerNodeID;

			double pheromoneValue = pheromone.get(key).getStrength();
			int edgeWeightValue = entry.getValue();

			nodeProbability.put(entry.getKey(), (float) alpha * (float) pheromoneValue / (beta * edgeWeightValue));
			sum += nodeProbability.get(entry.getValue());
		}

		for (Entry<Integer, Float> entry : nodeProbability.entrySet()) {
			nodeProbability.put(entry.getKey(), entry.getValue() / sum);
		}

		Random random = new Random();
		if (sum != 0) {
			float nodeProbabilityIndex = random.nextFloat();
			float inf = 0;
			float sup = 0;
			for (Entry<Integer, Float> entry : nodeProbability.entrySet()) {
				sup += entry.getValue();
				if (inf <= nodeProbabilityIndex && nodeProbabilityIndex < sup) {
					this.nextNodeID = entry.getKey();
					break;
				}
				inf = sup;
			}
		} else {
			int edgeWeightIndex = (int) Math.abs(random.nextGaussian()) * edgeWeight.size();
			int counter = 0;
			for (Entry<Integer, Integer> entry : edgeWeight.entrySet()) {
				if (counter == edgeWeightIndex) {
					this.nextNodeID = entry.getKey();
					break;
				}
				counter++;
			}
		}

		lastEdgeWeight = edgeWeight.get(this.nextNodeID);
		return this.lastFinishedPath;
	}

	public int getCurrentNode() {
		return currentPath.getLastNode().getFirst();
	}

	public void setCurrentNode(int currentNode) {
		this.currentPath.addNode(currentNode, lastEdgeWeight);
	}

	public Path finishMove(int numberOfNodes) {
		this.currentPath.addNode(this.nextNodeID, this.lastEdgeWeight);

		int index;
		if ((index = this.currentPath.checkCycles()) != -1) {
			this.currentPath.removeCycle(index);
		} else {
			int hamilton_cycle = this.currentPath.checkHamiltoianCycle(this.nest, numberOfNodes);

			if (hamilton_cycle == -1) {
				this.currentPath.removeCycle(hamilton_cycle);
				return null;
			} else if (hamilton_cycle == 1) {
				this.lastFinishedPath = this.currentPath;
				this.currentPath = new Path();
				return lastFinishedPath;
			}
		}
		return null;
	}

	public static Optional<Integer> findFirstDuplicateIndex(List<Integer> list) {
		return IntStream.range(0, list.size())
				.boxed()
				.filter(i -> list.subList(0, i).contains(list.get(i)))
				.findFirst();
	}

	protected int getTotalPathWeight() {
		return currentPath.getPathWeight();
	}

	public int getNextNode() {
		return nextNodeID;
	}

	public int getLastEdgeWeight() {
		return lastEdgeWeight;
	}
}
