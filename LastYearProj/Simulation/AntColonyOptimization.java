/*
 * AntColonyOptimization.java
 * 
 * Created on 16/06/2023
 */

package Simulation;

import java.util.*;
import AntColony.Colony;
import Graph.Graph;

import Path.Path;

import SimulInfo.SimulInfo;

/**
 * AntColonyOptimization is responsible for the simulation of the Ant Colony
 * Optimization
 * algorithm, being an implementation of the Simulation interface. It only
 * requires a graph
 * and the simulation parameters to be run. Such simulation is based on events
 * excuted at
 * discrete time step until the end of the simulation.
 * 
 * @author Manuel Gil Mata Ribeiro
 * @author André Teixeira
 * @author Pedro Lopes
 * @see ArrayList
 * @see Vector
 * @see Graph
 * @see Colony
 * @see Event
 * @see EventHandler
 * @see Simulation
 */

public class AntColonyOptimization implements Simulation {

    private static final int PRINT_FREQUENCY = 20;
    private int maxTime;
    private double delta;
    private Colony colony;
    private EventHandler eventHandler;
    private ArrayList<Event> events;
    private ArrayList<Integer> nonMovingAntId;
    private int observationNumber;
    private int numberMoveEvents;
    private int numberEvaporationsEvents;
    private int presentInstant;

    // private Vector<Path> pathVector;

    public AntColonyOptimization(Graph graph, SimulInfo info) {
        // pathVector = new Vector<Path>();

        this.maxTime = info.getfinalInstant();
        this.delta = info.getDelta();
        this.colony = new Colony(info.getcolonySize(), info.getNest(),
                graph, info.getAlpha(), info.getBeta());

        this.eventHandler = new EventHandler(info.getEta(),
                info.getRho(), info.getAlpha(),
                info.getBeta(), info.getDelta(), colony);
        this.nonMovingAntId = new ArrayList<Integer>();
        for (int i = 0; i < info.getcolonySize(); i++) {
            nonMovingAntId.add(i);
        }
    }

    public void updateStats(int numberEvaporationsEvents, int numberMoveEvents) {
        this.numberEvaporationsEvents += numberEvaporationsEvents;
        this.numberMoveEvents += numberMoveEvents;
        this.presentInstant++;
        this.observationNumber++;
    }

    public void displayStats() {
        System.out.println("Observation " + observationNumber + ": "
                + "\n\t\t\tPresent Instant: " + presentInstant
                + "\n\t\t\tNumber of Move Events: " + numberMoveEvents
                + "\n\t\t\tNumber of Evaporations Events: " + numberEvaporationsEvents
                + "\n\t\t\tBest Path: " + colony.getBestPath()
                + "\n\n");
    }

    public void start() {
        int[] eventCounter = { 0, 0 };
        for (int i = 1; i <= maxTime; i++) {

            for (int antId : nonMovingAntId) {
                colony.moveAnts(antId);
                events.add(new EventMoveAnt(i, antId, colony.getAntEdgeWeight(antId), this.delta));
                nonMovingAntId.remove(antId);
            }

            eventCounter = this.eventHandler.handleEvents(events, nonMovingAntId);

            updateStats(eventCounter[0], eventCounter[1]);

            if (i % PRINT_FREQUENCY == 0) {
                displayStats();
            }

        }

        displayStats();
    }
}