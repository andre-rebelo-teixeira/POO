package Main;

import population.Population;

public class Main {
    public static void main (String[] args) {
        System.out.println("Imperial Safety Simulation starting with the given parameters");

        int cost[][] = {    {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 12, 14, 15, 16, 17, 18, 18, 19, 20},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 12, 14, 15, 16, 17, 18, 18, 19, 20},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 12, 14, 15, 16, 17, 18, 18, 19, 20}};

        Population pop = new Population(4, 20, 3, cost);




    }
}
