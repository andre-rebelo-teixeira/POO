Package population;

import java.util.ArrayList;

public class Cost {
    private ArrayList<ArrayList<Integer>> cost_vector;
    private int number_of_patrols;
    private int number_of_planets;

    public Cost(int number_of_patrols, int number_of_planets, ArrayList<ArrayList<Integer>> cost_vector) {
        this.number_of_patrols = number_of_patrols;
        this.number_of_planets = number_of_planets;
        this.cost_vector = cost_vector;
    }

    public int getNumber_of_planets() {
        return number_of_planets;
    }

    public void setNumber_of_planets(int number_of_planets) {
        this.number_of_planets = number_of_planets;
    }

    public int getNumber_of_patrols() {
        return number_of_patrols;
    }

    public void setNumber_of_patrols(int number_of_patrols) {
        this.number_of_patrols = number_of_patrols;
    }

    public ArrayList<ArrayList<Integer>> getCost_vector() {
        return cost_vector;
    }

    public void setCost_vector(ArrayList<ArrayList<Integer>> cost_vector) {
        this.cost_vector = cost_vector;
    }

    public Integer get_cost(int patrol_id, int planet_id) {
        if (this.number_of_patrols < patrol_id && this.number_of_planets < planet_id) {
            return -1;
        }
        return cost_vector.get(patrol_id).get(planet_id);
    }
}
