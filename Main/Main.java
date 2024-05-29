package Main;
import SimulationData.SimulationData;
import SimulationHandler.SimulationHandler;

public class Main {
    public static void main(String[] args) {
        SimulationData simData = new SimulationData();
        simData.comand_line_arguments_parser(args);

        SimulationHandler simul = new SimulationHandler(simData);
        simul.start();
    }
}
