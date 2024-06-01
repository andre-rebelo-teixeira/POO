package SimulationData;

import java.util.Random;

/**
 * SimulationDataInterface.java
 *
 * This interface defines the methods for the SimulationData class, providing methods
 * to parse command line arguments, generate cost matrices, and access various simulation parameters.
 *
 * Created on 01/06/2024
 *
 * @version 1.0
 * @since 1.0
 *
 * @author André Rebelo Teixeira
 */
public interface SimulationDataInterface {

    /**
     * Parses command line arguments.
     * This method is responsible for parsing the command line arguments provided to the simulation.
     * It extracts and processes the necessary parameters required to configure and run the simulation.
     *
     * @param args The command line arguments.
     */
    void comand_line_arguments_parser(String[] args);

    /**
     * Parses the input file.
     * This method handles the parsing of an input file to extract simulation parameters and data.
     * It should read the file and populate the relevant fields and structures required for the simulation.
     */
    void file_parser();

    /**
     * Generates the cost matrix.
     * This method is responsible for generating the cost matrix used in the simulation. The cost matrix
     * typically represents costs or distances between different entities or locations in the simulation.
     */
    void cost_matrix_generation();

    /**
     * Gets the initial population size.
     *
     * @return The initial population size.
     */
    int getInitialPopulationSize();

    /**
     * Gets the final instance.
     *
     * @return The final instance of the simulation.
     */
    int getFinalInstance();

    /**
     * Gets the cost matrix.
     *
     * @return A 2D array representing the cost matrix.
     */
    int[][] getcostMatrix();

    /**
     * Gets the number of patrols.
     *
     * @return The number of patrols.
     */
    int get_numb_patrols();

    /**
     * Gets the number of planets.
     *
     * @return The number of planets.
     */
    int get_numb_planets();

    /**
     * Gets the maximum number of individuals.
     *
     * @return The maximum number of individuals.
     */
    int get_max_individuals();

    /**
     * Sets the number of patrols.
     *
     * @param number_of_patrols The number of patrols to set.
     */
    void set_numb_patrols(int number_of_patrols);

    /**
     * Sets the number of planets.
     *
     * @param number_of_planets The number of planets to set.
     */
    void set_numb_planets(int number_of_planets);

    /**
     * Sets the maximum number of individuals.
     *
     * @param max_individuals The maximum number of individuals to set.
     */
    void set_max_individuals(int max_individuals);

    /**
     * Gets the value of Phi.
     *
     * @return The value of Phi.
     */
    int getPhi();

    /**
     * Gets the value of Sigma.
     *
     * @return The value of Sigma.
     */
    int getSigma();

    /**
     * Gets the value of Mu.
     *
     * @return The value of Mu.
     */
    int getMu();

    /**
     * Gets the maximum value of V.
     *
     * @return The maximum value of V.
     */
    int getV_max();

    /**
     * Gets the value of V.
     *
     * @return The value of V.
     */
    int getV();

    /**
     * Gets the value of Tau.
     *
     * @return The value of Tau.
     */
    int getTau();

    /**
     * Gets the value of M.
     *
     * @return The value of M.
     */
    int getM();

    /**
     * Gets the value of N.
     *
     * @return The value of N.
     */
    int getN();

    /**
     * Gets the cost matrix.
     *
     * @return A 2D array representing the cost matrix.
     */
    int[][] getCostMatrix();

    /**
     * Gets the file path.
     *
     * @return The file path as a String.
     */
    String getFile_path();
}
