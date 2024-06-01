package SimulationData;

import ExponentialDistribution.ExponentialDistribution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * SimulationData.java
 *
 * This class manages the simulation data, including parsing command line arguments,
 * generating cost matrices, and accessing various simulation parameters.
 *
 * Created on 01/06/2024
 *
 * @version 1.0
 * @since 1.0
 * @see ExponentialDistribution.ExponentialDistribution
 * @see java.util.Random
 * @see java.io.BufferedReader
 * @see java.io.FileReader
 *
 * @author André Rebelo Teixeira
 */
public class SimulationData implements SimulationDataInterface {

    private ExponentialDistribution exponentialDistribution;

    // Input file location
    private String file_path;

    // Cost matrix containing times for policing the planetary systems
    private int[][] costMatrix;

    // Number of patrols and planets
    private int n;
    private int m;

    // Final instant of evolution
    private int tau;

    // Initial and maximum population, respectively
    private int v;
    private int v_max;

    // Death, reproduction, and mutation, respectively
    private int mu;
    private int sigma;
    private int phi;

    /**
     * Parses command line arguments to initialize simulation parameters.
     *
     * @param args The command line arguments.
     */
    @Override
    public void comand_line_arguments_parser(String[] args) {
        if (args.length > 0) {
            if ("-r".equals(args[0])) {
                if (args.length == 9) {
                    this.n = Integer.parseInt(args[1]);
                    this.m = Integer.parseInt(args[2]);
                    this.tau = Integer.parseInt(args[3]);
                    this.v = Integer.parseInt(args[4]);
                    this.v_max = Integer.parseInt(args[5]);
                    this.mu = Integer.parseInt(args[6]);
                    this.sigma = Integer.parseInt(args[7]);
                    this.phi = Integer.parseInt(args[8]);
                    cost_matrix_generation();
                } else {
                    System.err.println("Invalid number of arguments for -r option");
                }
            } else if ("-f".equals(args[0])) {
                if (args.length == 2) {
                    this.file_path = args[1];
                    file_parser();
                } else {
                    System.err.println("Invalid number of arguments for -f option");
                }
            } else {
                System.err.println("Invalid option: " + args[0]);
            }
        } else {
            System.err.println("No arguments provided");
        }
    }

    /**
     * Parses the input file to initialize simulation parameters.
     */
    @Override
    public void file_parser() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String[] params = reader.readLine().split(" ");
            this.n = Integer.parseInt(params[0]);
            this.m = Integer.parseInt(params[1]);
            this.tau = Integer.parseInt(params[2]);
            this.v = Integer.parseInt(params[3]);
            this.v_max = Integer.parseInt(params[4]);
            this.mu = Integer.parseInt(params[5]);
            this.sigma = Integer.parseInt(params[6]);
            this.phi = Integer.parseInt(params[7]);

            this.costMatrix = new int[n][m];
            for (int i = 0; i < n; i++) {
                String[] costs = reader.readLine().split(" ");
                for (int j = 0; j < m; j++) {
                    this.costMatrix[i][j] = Integer.parseInt(costs[j]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    /**
     * Generates a random cost matrix for policing the planetary systems.
     */
    @Override
    public void cost_matrix_generation() {
        this.costMatrix = new int[n][m];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.costMatrix[i][j] = 1 + random.nextInt(10); // Generates a random number between 1 and 10
                System.out.print(this.costMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Gets the initial population size.
     *
     * @return The initial population size.
     */
    @Override
    public int getInitialPopulationSize() {
        return this.v;
    }

    /**
     * Gets the final instance of evolution.
     *
     * @return The final instance.
     */
    @Override
    public int getFinalInstance() {
        return this.tau;
    }

    /**
     * Gets the cost matrix.
     *
     * @return The cost matrix.
     */
    @Override
    public int[][] getcostMatrix() {
        return this.costMatrix;
    }

    /**
     * Gets the number of patrols.
     *
     * @return The number of patrols.
     */
    @Override
    public int get_numb_patrols() {
        return this.n;
    }

    /**
     * Gets the number of planets.
     *
     * @return The number of planets.
     */
    @Override
    public int get_numb_planets() {
        return this.m;
    }

    /**
     * Gets the maximum number of individuals.
     *
     * @return The maximum number of individuals.
     */
    @Override
    public int get_max_individuals() {
        return this.v_max;
    }

    /**
     * Sets the number of patrols.
     *
     * @param number_of_patrols The number of patrols.
     */
    @Override
    public void set_numb_patrols(int number_of_patrols) {
        this.n = number_of_patrols;
    }

    /**
     * Sets the number of planets.
     *
     * @param number_of_planets The number of planets.
     */
    @Override
    public void set_numb_planets(int number_of_planets) {
        this.m = number_of_planets;
    }

    /**
     * Sets the maximum number of individuals.
     *
     * @param max_individuals The maximum number of individuals.
     */
    @Override
    public void set_max_individuals(int max_individuals) {
        this.v_max = max_individuals;
    }

    /**
     * Gets the phi parameter.
     *
     * @return The phi parameter.
     */
    @Override
    public int getPhi() {
        return phi;
    }

    /**
     * Gets the sigma parameter.
     *
     * @return The sigma parameter.
     */
    @Override
    public int getSigma() {
        return sigma;
    }

    /**
     * Gets the mu parameter.
     *
     * @return The mu parameter.
     */
    @Override
    public int getMu() {
        return mu;
    }

    /**
     * Gets the maximum number of individuals.
     *
     * @return The maximum number of individuals.
     */
    @Override
    public int getV_max() {
        return v_max;
    }

    /**
     * Gets the initial population size.
     *
     * @return The initial population size.
     */
    @Override
    public int getV() {
        return v;
    }

    /**
     * Gets the final instance of evolution.
     *
     * @return The final instance.
     */
    @Override
    public int getTau() {
        return tau;
    }

    /**
     * Gets the number of planets.
     *
     * @return The number of planets.
     */
    @Override
    public int getM() {
        return m;
    }

    /**
     * Gets the number of patrols.
     *
     * @return The number of patrols.
     */
    @Override
    public int getN() {
        return n;
    }

    /**
     * Gets the cost matrix.
     *
     * @return The cost matrix.
     */
    @Override
    public int[][] getCostMatrix() {
        return costMatrix;
    }

    /**
     * Gets the file path of the input file.
     *
     * @return The file path.
     */
    @Override
    public String getFile_path() {
        return file_path;
    }
}
