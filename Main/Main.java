package Main;

import SimulationData.SimulationData;
import SimulationData.SimulationDataInterface;
import SimulationHandler.SimulationHandler;
import SimulationHandler.SimulationHandlerInterface;


/**
 * Main.java
 *
 * This class contains the main method for starting the simulation. It
 * initializes the simulation data,
 * parses command line arguments, and starts the simulation handler.
 *
 * @version 1.0
 * @since 1.0
 *
 * @see SimulationData.SimulationData
 * @see SimulationHandler.SimulationHandler
 *
 * @author André Rebelo Teixeira
 */
public class Main {

    /**
     * The main method for starting the simulation.
     * Initializes the simulation data, parses command line arguments, and starts
     * the simulation handler.
     *
     * @param args Command line arguments for the simulation.
     */
    public static void main(String[] args) {
        SimulationDataInterface simData = new SimulationData();
        simData.comand_line_arguments_parser(args);

        SimulationHandlerInterface simulate = new SimulationHandler(simData);
        simulate.start();
    }
}
