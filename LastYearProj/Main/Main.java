package Main;

import java.util.Arrays;

import Simulation.AntColonyOptimization;
import Graph.Graph;
import SimulInfo.SimulInfo;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph();
        SimulInfo info;

        switch (args[0]) {
            case "-r":
                try {
                    info = new SimulInfo(Arrays.copyOfRange(args, 1, args.length));
                    graph.createGraph(info.getN(), info.getMaxEdgeWeight());
                } catch (Exception e) {
                    System.out.println("Invalid Arguments. Leaving...");
                    return;
                }

                break;

            case "-f":
                try {
                    info = new SimulInfo(args[1]);
                    graph.createGraph(args[1]);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid Arguments. Leaving...");
                    return;
                }

            default:
                System.out.println("ERROR: Invalid number of argument.");
                return;
        }
        System.out.println(info.toString());

        // Execute Simulation
        AntColonyOptimization aco = new AntColonyOptimization(graph, info);
        aco.start();
    }

}
