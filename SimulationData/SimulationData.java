package SimulationData;

import ExponentialDistribution.ExponentialDistribution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class SimulationData {

    ExponentialDistribution exponentialDistribution;

    // input.txt file location
    String file_path;

    // C matrix containing times for policing the planetary systems 
    int[][] costMatrix;

    // Number of patrols and planets
    int n;
    int m;

    // Final instant of evolution
    int tau;

    // Initial and Maximum Population, respectively
    int v;
    int v_max;

    // Death, reproduction, and mutation, respectively
    int mu;
    int sigma;
    int phi;

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

    // Parser for -f option
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

    public void cost_matrix_generation() {
        // Create an empty matrix with dimensions number_of_patrols x number_of_planets
        this.costMatrix = new int[n][m];
        Random random = new Random();

        // Fill the matrix with random values between 1 and 10
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.costMatrix[i][j] = 1 + random.nextInt(10); // Generates a random number between 1 and 10
                System.out.print(this.costMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getInitialPopulationSize(){
        return this.v;
    }

    public int getFinalInstance(){
        return this.tau;
    }

    public int[][] getcostMatrix(){
        return this.costMatrix;
    }

    public int get_numb_patrols() {
        return this.n;
    }

    public int get_numb_planets() {
        return this.m;
    }

    public int get_max_individuals() {
        return this.v_max;
    }

    public void set_numb_patrols(int number_of_patrols) {
        this.n = number_of_patrols;
    }

    public void set_numb_planets(int number_of_planets) {
        this.m = number_of_planets;
    }

    public void set_max_individuals(int max_individuals) {
        this.v_max = max_individuals;
    }

    public int getPhi() {
        return phi;
    }

    public int getSigma() {
        return sigma;
    }

    public int getMu() {
        return mu;
    }

    public int getV_max() {
        return v_max;
    }

    public int getV() {
        return v;
    }

    public int getTau() {
        return tau;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public int[][] getCostMatrix() {
        return costMatrix;
    }

    public String getFile_path() {
        return file_path;
    }
}
