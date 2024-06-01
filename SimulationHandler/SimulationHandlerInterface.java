package SimulationHandler;

import java.util.*;

import ExponentialDistribution.ExponentialDistributionInterface;
import SimulationData.SimulationDataInterface;
import population.PopulationInterface;
import Event.PEC;
import Event.GenericEvent;

/**
 * SimulationHandlerInterface.java
 *
 * This interface defines the methods for the SimulationHandler class, providing
 * methods
 * to initialize the simulation, update statistics, start the simulation, and
 * print observations.
 *
 * Created on 01/06/2024
 *
 * @version 1.0
 * @since 1.0
 *
 * @author André Rebelo Teixeira
 */
public interface SimulationHandlerInterface {

    /**
     * Updates the statistics of the simulation.
     *
     * This method should be called to update any statistics or metrics that are
     * being
     * tracked during the simulation. It should be implemented to gather and process
     * data points relevant to the simulation's progress and outcomes.
     */
    void updateStats();

    /**
     * Starts the simulation.
     *
     * This method should initialize and begin the simulation process. It is
     * expected to
     * set up any necessary parameters, initiate events, and manage the overall flow
     * of the simulation.
     */
    void start();

    /**
     * Prints the simulation observations.
     *
     * This method is responsible for outputting the observations and results of the
     * simulation.
     * It should be implemented to provide a clear and concise summary of the
     * simulation's
     * outcomes, including any statistical data, events, or notable occurrences.
     */
    void print_simulation_observation();
}
