/*
 * EventHandler.java
 * 
 * Created on 16/06/2023 
 */
package Simulation;

import java.util.ArrayList;

import AntColony.Colony;
import Path.Path;

class EventHandler {
	private Path _auxPath;
	private int timeOfEvaporation;
	private double unitsOfEvaporation;
	// private double alpha;
	// private double beta;
	private double delta;
	private Colony colony;

	public EventHandler(int timeOfEvaporation, double unitsOfEvaporation,
			double alpha, double beta, double delta, Colony colony) {
		this.timeOfEvaporation = timeOfEvaporation;
		this.unitsOfEvaporation = unitsOfEvaporation;
		// this.alpha = alpha;
		// this.beta = beta;
		this.delta = delta;
		this.colony = colony;
	}

	public int[] handleEvents(ArrayList<Event> events, ArrayList<Integer> nonMovingAntIds) {
		int eventCounter[] = { 0, 0 };
		ArrayList<EventEvaporatePheromone> pheromonesToEvaporate = new ArrayList<EventEvaporatePheromone>();
		for (Event event : events) {
			if (handleEvent(event, nonMovingAntIds) == 1) {
				if (event instanceof EventEvaporatePheromone) {
					eventCounter[0] += 1;
				} else if (event instanceof EventMoveAnt) {
					eventCounter[1] += 1;
					for (String edgeKey : _auxPath.getEdgesKeys()) {
						if (colony.getPheromoneValue(edgeKey) == 0) {
							events.add(new EventEvaporatePheromone(event.getCurrentInstant(),
									edgeKey, this.delta));
						}
					}
				}
				events.remove(event);
			}
		}

		for (EventEvaporatePheromone eventEvaporate : pheromonesToEvaporate) {
			events.add(eventEvaporate);
		}

		return eventCounter;
	}

	public int handleEvent(Event event, ArrayList<Integer> nonMovingAntIds) {
		event.setCurrentInstant(event.getCurrentInstant() + 1);
		if (event.getCurrentInstant() - event.getfinalInstant() == 0) {
			if (event instanceof EventEvaporatePheromone) {
				EventEvaporatePheromone eventEvaporatePheromone = (EventEvaporatePheromone) event;
				finalizeEvent(eventEvaporatePheromone);
			} else if (event instanceof EventMoveAnt) {
				EventMoveAnt eventMoveAnt = (EventMoveAnt) event;
				finalizeEvent(eventMoveAnt);
				nonMovingAntIds.add(eventMoveAnt.getAntId());
			}
			return 1;
		}
		return 0;
	}

	public void finalizeEvent(EventEvaporatePheromone event) {
		String edgeKey = event.getEdgeKey();
		double level = colony.getPheromoneValue(edgeKey);
		if (level - this.delta < 0) {
			colony.setPheromoneLevel(edgeKey, 0);
		} else {
			colony.setPheromoneLevel(edgeKey, colony.getPheromoneValue(edgeKey) - this.delta);
		}
	}

	public void finalizeEvent(EventMoveAnt event) {
		_auxPath = colony.finishAntMove(event.getAntId());
	}

	public void setTimeOfEvaporation(int t) {
		timeOfEvaporation = t;
	}

	public void setUnitsOfEvaporation(int u) {
		unitsOfEvaporation = u;
	}

	public int getTimeOfEvaporation() {
		return timeOfEvaporation;
	}

	public double getUnitsOfEvaporation() {
		return unitsOfEvaporation;
	}
}
