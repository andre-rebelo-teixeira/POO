/*
 * EventMoveAnt.java
 * 
 * Created on 16/06/2023 
 */
package Simulation;

import java.util.Random;

class EventMoveAnt extends Event {
    private int antId;

    public EventMoveAnt(int instant, int antId, int edgeWeight, double delta) {
        super(instant, 0);
        Random random = new Random();
        double mean = delta * edgeWeight;
        this.setfinalInstant(instant + (int) Math.ceil(mean * Math.log(1 - random.nextDouble())));
        this.antId = antId;
    }

    public int getAntId() {
        return antId;
    }
}
