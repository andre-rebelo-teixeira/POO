/*
 * EventEvaporatePheromone.java
 * 
 * Created on 16/06/2023 
 */

package Simulation;

import java.util.Random;

/**
 * EventEvaporatePheromone is a class that represents an ocurring event where
 * the pheromone in an edge evaporates over time.
 * 
 * @see Event
 * @see EventHandler
 */
class EventEvaporatePheromone extends Event {
    private String edgeKey;

    /**
     * Creates a new instance of EventEvaporatePheromone.
     * A pheromone evaporation event requires two main informations
     * the initial instant and an edge key that represents the edge
     * where the pheromone residing is evaporating.
     * 
     * @param instant
     * @param edgeKey
     */
    public EventEvaporatePheromone(int instant, String edgeKey,
            double pheromoneMeanTimeBetweenEvaporation) {
        super(instant, 0);
        Random random = new Random();
        double evaporationTime = pheromoneMeanTimeBetweenEvaporation
                * Math.log(1 - random.nextDouble());
        this.setfinalInstant((int) (instant + evaporationTime));
        this.edgeKey = edgeKey;
    }

    public String getEdgeKey() {
        return edgeKey;
    }
}
