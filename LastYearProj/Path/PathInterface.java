package Path;

import Pair.Pair;
import java.util.Vector;
import java.util.ArrayList;

public interface PathInterface {
    public Vector<Pair<Integer, Integer>> getPath();

    public boolean addNode(int node, int weight);

    public int checkHamiltoianCycle(int nestID, int numberOfNodes);

    public int getPathWeight();

    public int getNextNode(int node);

    public Pair<Integer, Integer> getLastNode();

    public int checkCycles();

    public void removeCycle(int index);

    public boolean nodeAlreadyTravaled(int nodeID);

    public int getNodeId(int index);

    public ArrayList<String> getEdgesKeys();
}
