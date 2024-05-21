package SimulData;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

import population.population;

public class SimulData {

    private int n; //number of patrols
    private int m; //number of plannetary systems
    private int final_t; //final instant of evolution
    private int v; // initial population
    private int v_max; // maximum population
    private int death; // death event
    private int reproduction; //reproduction event
    private int mutation; //mutation event


    public SimulData(String[] tokens) {
        this.n = Integer.parseInt(tokens[0]);
        this.m = Integer.parseInt(tokens[1]);
        this.final_t = Integer.parseInt(tokens[2]);
        this.v = Integer.parseInt(tokens[3]);
        this.v_max = Integer.parseInt(tokens[4]);
        this.death = Integer.parseInt(tokens[5]);
        this.reproduction = Integer.parseInt(tokens[6]);
        this.mutation = Integer.parseInt(tokens[7]);
    }


    public SimulData(String filename) {
        // read input file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            String[] tokens = line.split(" ");

            this.n = Integer.parseInt(tokens[0]);
            this.m = Integer.parseInt(tokens[1]);
            this.final_t = Integer.parseInt(tokens[2]);
            this.v = Integer.parseInt(tokens[3]);
            this.v_max = Integer.parseInt(tokens[4]);
            this.death = Integer.parseInt(tokens[5]);
            this.reproduction = Integer.parseInt(tokens[6]);
            this.mutation = Integer.parseInt(tokens[7]);
            
            reader.close();
        } catch (Exception e) {
            //error message 
            System.out.println("ERROR: File not found.");
        }

    }

    public SimulInfo(int n, int m, int final_t, int v, int v_max, int death, int reproduction, int mutation) {
        
        this.n = n;
        this.m = m;
        this.final_t = final_t;
        this.v = v;
        this.v_max = v_max;
        this.death = death;
        this.reproduction = reproduction;
        this.mutation = mutation;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getFinal_t() {
        return final_t;
    }

    public int getV() {
        return v;
    }

    public int getV_max() {
        return v_max;
    }

    public int getDeath() {
        return death;
    }

    public int getReproduction() {
        return reproduction;
    }

    public int getMutation() {
        return mutation;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setFinal_t(int final_t) {
        this.final_t = final_t;
    }

    public void setV(int v) {
        this.v = v;
    }

    public void setV_max(int v_max) {
        this.v_max = v_max;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public void setReproduction(int reproduction) {
        this.reproduction = reproduction;
    }

    public void setMutation(int mutation) {
        this.mutation = mutation;
    }

    

}