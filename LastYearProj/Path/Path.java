/*
 * Path.java
 * 
 * Created on 16/06/2023
 */

package Path;

import Pair.Pair;
import java.util.Vector;
import java.util.ArrayList;

public class Path implements PathInterface {
    int pathWeight;
    Vector<Pair<Integer, Integer>> path;
    ArrayList<String> edgeKeys;

    public Path() {
        path = new Vector<Pair<Integer, Integer>>();
        this.pathWeight = 0;
        this.edgeKeys = new ArrayList<String>();
    }

    @Override
    public boolean addNode(int node, int weight) {
        boolean alreadyContains = alreadyContains(node, 0);

        path.add(new Pair<Integer, Integer>(node, weight));
        pathWeight += weight;

        int smallerNodeId = (node < getLastNode().getFirst()) ? node : getLastNode().getFirst();
        int biggerNodeId = (node > getLastNode().getFirst()) ? node : getLastNode().getFirst();

        this.edgeKeys.add(smallerNodeId + "_" + biggerNodeId);
        return alreadyContains;
    }

    @Override
    public int checkHamiltoianCycle(int nestID, int numberOfNodes) {
        if (path.size() == numberOfNodes) {
            Pair<Integer, Integer> firstPair = path.get(0);
            Pair<Integer, Integer> lastPair = path.get(path.size() - 1);

            for (int i = 1; i < path.size(); i++) {
                Pair<Integer, Integer> pair = path.get(i);
                if (alreadyContains(pair.getFirst(), i)) {
                    return -1;
                }
            }

            if (firstPair.getFirst() == nestID && lastPair.getFirst() == nestID) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int getNextNode(int node) {
        for (int i = 0; i < path.size(); i++) {
            Pair<Integer, Integer> pair = path.get(i);

            if (pair.getFirst() == node) {
                Pair<Integer, Integer> nextPair = (i == path.size() - 1) ? null : path.get(i + 1);
                return (nextPair == null) ? -1 : nextPair.getFirst();
            }
        }

        return -1;
    }

    @Override
    public Vector<Pair<Integer, Integer>> getPath() {
        return path;
    }

    @Override
    public int getPathWeight() {
        return this.pathWeight;
    }

    @Override
    public Pair<Integer, Integer> getLastNode() {
        return path.get(path.size() - 1);
    }

    @Override
    public int checkCycles() {
        for (int i = 0; i < path.size(); i++) {
            Pair<Integer, Integer> pair = path.get(i);
            if (alreadyContains(pair.getFirst(), i + 1)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void removeCycle(int index) {
        for (int i = index; i < path.size(); i++) {
            Pair<Integer, Integer> pair = path.get(i);
            this.pathWeight -= pair.getSecond();
            path.remove(i);
        }
    }

    @Override
    public boolean nodeAlreadyTravaled(int nodeID) {
        for (int i = 0; i < path.size(); i++) {
            Pair<Integer, Integer> pair = path.get(i);
            if (pair.getFirst() == nodeID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getNodeId(int index) {
        Pair<Integer, Integer> pair = path.get(index);
        return pair.getFirst();
    }

    @Override
    public ArrayList<String> getEdgesKeys() {
        return this.edgeKeys;
    }

    private boolean alreadyContains(int node, int startIndex) {
        for (int i = startIndex; i < path.size(); i++) {
            Pair<Integer, Integer> pair = path.get(i);
            if (pair.getFirst() == node) {
                return true;
            }
        }
        return false;
    }

}