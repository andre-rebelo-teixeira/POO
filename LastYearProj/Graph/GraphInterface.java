/*
 * GraphInterface.java
 * 
 * Created on 16/06/2023
 */
package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface GraphInterface {
    public HashMap<Integer, Integer> getNodeEdgesWeight(int NodeId);

    public int getNumberOfNodes();

    public int getMaxEdgeWeight();

    // See the nodes of the graph
    public void printNodeList();

    // See the edges of the graph
    public void printEdgeList();

    // See the graph
    public void printGraph();

    public void createGraph(String filename);

    public void createGraph(int numberOfNodes, int maxEdgeWeight);
}