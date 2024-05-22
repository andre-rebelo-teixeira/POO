package Main;
import SimulationData.SimulationData;
import SimulationHandler.SimulationHandler;

public class Main {
    public static void main(String[] args) {
        SimulationData simData = new SimulationData();
        simData.comand_line_arguments_parser(args);
        
        // Print the parameters and generated or read cost matrix for verification
        System.out.println("Number of Patrols: " + simData.get_numb_patrols());
        System.out.println("Number of Planets: " + simData.get_numb_planets());
        System.out.println("Max Individuals: " + simData.get_max_individuals());
        System.out.println("Generated/Read Cost Matrix:");
        for (int[] row : simData.getcostMatrix()) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    
        SimulationHandler simul = new SimulationHandler(simData);
        simul.start();
    }
}
