package SimulInfo;

import java.io.BufferedReader;
import java.io.FileReader;

public class SimulInfo {
    private int n;
    private int maxEdgeWeight;
    private int Nest;
    private int alpha;
    private int beta;
    private int delta;
    private int eta;
    private int rho;
    private int pheromoneLevel;
    private int colonySize;
    private int finalInstant;

    public SimulInfo(String[] tokens) {
        this.n = Integer.parseInt(tokens[0]);
        this.maxEdgeWeight = Integer.parseInt(tokens[1]);
        this.Nest = Integer.parseInt(tokens[2]);
        this.alpha = Integer.parseInt(tokens[3]);
        this.beta = Integer.parseInt(tokens[4]);
        this.delta = Integer.parseInt(tokens[5]);
        this.eta = Integer.parseInt(tokens[6]);
        this.rho = Integer.parseInt(tokens[7]);
        this.pheromoneLevel = Integer.parseInt(tokens[8]);
        this.colonySize = Integer.parseInt(tokens[9]);
        this.finalInstant = Integer.parseInt(tokens[10]);
    }

    public SimulInfo(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            String[] tokens = line.split(" ");
            this.n = Integer.parseInt(tokens[0]);
            this.Nest = Integer.parseInt(tokens[1]);
            this.alpha = Integer.parseInt(tokens[2]);
            this.beta = Integer.parseInt(tokens[3]);
            this.delta = Integer.parseInt(tokens[4]);
            this.eta = Integer.parseInt(tokens[5]);
            this.rho = Integer.parseInt(tokens[6]);
            this.pheromoneLevel = Integer.parseInt(tokens[7]);
            this.colonySize = Integer.parseInt(tokens[8]);
            this.finalInstant = Integer.parseInt(tokens[9]);
            reader.close();
        } catch (Exception e) {
            System.out.println("ERROR: File not found.");
        }
    }

    public SimulInfo(int n, int Nest, int alpha, int beta, int delta, int eta, int rho, int pheromoneLevel,
            int colonySize, int finalInstant) {
        this.n = n;
        this.Nest = Nest;
        this.alpha = alpha;
        this.beta = beta;
        this.delta = delta;
        this.eta = eta;
        this.rho = rho;
        this.pheromoneLevel = pheromoneLevel;
        this.colonySize = colonySize;
        this.finalInstant = finalInstant;
    }

    public int getN() {
        return n;
    }

    public int getMaxEdgeWeight() {
        return maxEdgeWeight;
    }

    public int getNest() {
        return Nest;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getBeta() {
        return beta;
    }

    public int getDelta() {
        return delta;
    }

    public int getEta() {
        return eta;
    }

    public int getRho() {
        return rho;
    }

    public int getpheromoneLevel() {
        return pheromoneLevel;
    }

    public int getcolonySize() {
        return colonySize;
    }

    public int getfinalInstant() {
        return finalInstant;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setNest(int Nest) {
        this.Nest = Nest;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public void setRho(int rho) {
        this.rho = rho;
    }

    public void setpheromoneLevel(int pheromoneLevel) {
        this.pheromoneLevel = pheromoneLevel;
    }

    public void setcolonySize(int colonySize) {
        this.colonySize = colonySize;
    }

    public void setfinalInstant(int finalInstant) {
        this.finalInstant = finalInstant;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + n;
        result = prime * result + Nest;
        result = prime * result + alpha;
        result = prime * result + beta;
        result = prime * result + delta;
        result = prime * result + eta;
        result = prime * result + rho;
        result = prime * result + pheromoneLevel;
        result = prime * result + colonySize;
        result = prime * result + finalInstant;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SimulInfo other = (SimulInfo) obj;
        if (n != other.n)
            return false;
        if (Nest != other.Nest)
            return false;
        if (alpha != other.alpha)
            return false;
        if (beta != other.beta)
            return false;
        if (delta != other.delta)
            return false;
        if (eta != other.eta)
            return false;
        if (rho != other.rho)
            return false;
        if (pheromoneLevel != other.pheromoneLevel)
            return false;
        if (colonySize != other.colonySize)
            return false;
        if (finalInstant != other.finalInstant)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Input parameters \n\t\t\t" + n + ": number of nodes in the graph\n\t\t\t"
                + Nest + ": the nest node\n\t\t\t"
                + alpha + ": alpha, ant move event\n\t\t\t"
                + beta + ": beta, ant move event\n\t\t\t"
                + delta + ": delta, ant move event\n\t\t\t"
                + eta + ": eta, pheromone evaporation event\n\t\t\t"
                + rho + ": rho, pheromone evaporation event\n\t\t\t"
                + pheromoneLevel + ": pheromone level\n\t\t\t"
                + colonySize + ": AntColony size\n\t\t\t"
                + finalInstant + ": final instant\n";
    }

}
