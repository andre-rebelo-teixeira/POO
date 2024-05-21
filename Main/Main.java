package Main;

import java.util.Arrays;


import SimulData.SimulData;

public class Main {

    public static void main(String[] args) {

        //Falta inicializar a matriz
        //Graph graph = new Graph();
        SimulData data;

        switch (args[0]) {
            case "-r":
                try {
                    data = new SimulData(Arrays.copyOfRange(args, 1, args.length));
                   // graph.createGraph(info.getN(), info.getMaxEdgeWeight());
                } catch (Exception e) {
                    System.out.println("Invalid Arguments. Leaving...");
                    return;
                }

                break;

            case "-f":
                try {
                    data = new SimulData(args[1]);
                    //graph.createGraph(args[1]);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid Arguments. Leaving...");
                    return;
                }

            default:
                System.out.println("ERROR: Invalid number of arguments.");
                return;
        }
        System.out.println(info.toString());

        // Execute Simulation
        //AntColonyOptimization aco = new AntColonyOptimization(graph, info);
        //aco.start();
    }

}
