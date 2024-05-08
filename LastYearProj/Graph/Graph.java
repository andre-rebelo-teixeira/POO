/*
 * Graph.java
 * 
 * Created on 16/06/2023 
 */
package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Graph is the class where we are able to join the information from the node
 * and edge Class
 * to be able to store something meaningfull. Since both classes are private to
 * the graph package
 * this is also responsible to create the nodes and edges, as well as implemente
 * the GraphInterface
 * to facilitate external access to the graph.
 * 
 * @author Manuel Gil Mata Ribeiro
 * @author André Teixeira
 * @author Pedro Lopes
 * @see Edge
 * @see Node
 * 
 */
public class Graph implements GraphInterface {
    private List<Node> nodeList;
    private List<Edge> edgeList;

    private int numberOfNodes;

    private int maxEdgeWeight;

    public Graph() {
        nodeList = new ArrayList<Node>();
        edgeList = new ArrayList<Edge>();
        this.numberOfNodes = 0;
        this.maxEdgeWeight = 0;
    }

    private void addNode(Node node) {
        nodeList.add(node);
    }

    private void addEdge(Edge edge) {
        edgeList.add(edge);
    }

    private void CreateNodes(int numberOfNodes) {
        for (int i = 0; i < numberOfNodes; i++) {
            Node node = new Node(i);
            addNode(node);
        }
    }

    private void handleLine(String[] tokens, int lineNumber) {
        int currentNode = lineNumber - 1;

        Node node = nodeList.get(currentNode);

        for (int i = 0; i < this.numberOfNodes; i++) {
            int weight = Integer.parseInt(tokens[i]);

            this.maxEdgeWeight = (weight > this.maxEdgeWeight) ? weight : this.maxEdgeWeight;

            Node nextNode = nodeList.get(i);

            Edge edge = new Edge(node, nextNode, weight);

            node.addOutgoingEdge(edge);

            nextNode.addOutgoingEdge(edge);

            addEdge(edge);
        }
    }

    // Generate an hamiltonian path
    private List<Edge> generatePath() {

        List<Edge> hamiltonPath = new ArrayList<Edge>();
        List<Integer> nodesToVisit = new ArrayList<Integer>();
        int firstNodeId = 0;
        int currentNodeID = 0;

        for (int i = 1; i < numberOfNodes; i++) {
            nodesToVisit.add(i);
        }

        for (int i = 1; i < numberOfNodes; i++) {
            // Get random number
            int indexInList = (int) (Math.random() * nodesToVisit.size());
            int nextNode = nodesToVisit.get(indexInList);

            // Remove node
            nodesToVisit.remove(nodesToVisit.get(indexInList));

            Edge edge = nodeList.get(currentNodeID).getEdgeToNode(nextNode);

            hamiltonPath.add(edge);

            currentNodeID = nextNode;
        }

        Edge edge = nodeList.get(currentNodeID).getEdgeToNode(firstNodeId);
        hamiltonPath.add(edge);

        return hamiltonPath;
    }

    // Remove a random number of edges from the graph with removing the hamilton
    // created
    private List<Edge> edgesToRemove(List<Edge> hamiltonPath) {
        List<Edge> edgesToRemove = new ArrayList<Edge>();

        // Copie all the edges i have in the graph
        edgesToRemove.addAll(this.edgeList);

        edgesToRemove.removeAll(hamiltonPath);

        int numberOfEdgesToRemove = (int) (Math.random() * (edgesToRemove.size() + 1));

        for (int i = 0; i < numberOfEdgesToRemove; i++) {
            int edgeToRemovePosition = (int) (Math.random() * edgesToRemove.size());

            Edge edge = edgesToRemove.get(edgeToRemovePosition);
            Node node;

            node = edge.getStartNode();
            node.removeEdge(edge);

            node = edge.getEndNode();
            node.removeEdge(edge);

            edgesToRemove.remove(edge);
        }

        edgesToRemove.addAll(hamiltonPath);

        return edgesToRemove;
    }

    // GraphInterface functions
    @Override
    public HashMap<Integer, Integer> getNodeEdgesWeight(int NodeId) {
        boolean hasNode = (NodeId < numberOfNodes) ? true : false;

        return (hasNode == true) ? nodeList.get(NodeId).getEdgesWeight() : null;
    }

    @Override
    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    @Override
    public int getMaxEdgeWeight() {
        return maxEdgeWeight;
    }

    @Override
    public void printNodeList() {
        System.out.println("Printing Node List");
        for (Node node : nodeList) {
            for (Edge edge : node.getOutgoingEdges()) {
                Node endNode = (node.getId() == edge.getStartNode().getId()) ? edge.getEndNode()
                        : edge.getStartNode();

                System.out.println("node " + node.getId() + " edge to " + endNode.getId() + " weight "
                        + edge.getWeight());
            }
            System.out.println();
        }

    }

    @Override
    public void printEdgeList() {
        System.out.println("Printing Edge List");
        for (Edge edge : edgeList) {
            edge.toString();
        }

    }

    @Override
    public void printGraph() {
        System.out.println("Printing Graph");
        for (Node node : nodeList) {
            node.toString();
        }
        for (Edge edge : edgeList) {
            edge.toString();
        }
    }

    @Override
    public void createGraph(String filename) {
        int linesRead = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");

                // First line is only simulation information, not stored in the graph
                if (linesRead == 0) {
                    continue;
                }

                handleLine(tokens, linesRead);

                for (String token : tokens) {
                    System.out.println(token);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createGraph(int numberOfNodes, int maxEdgeWeight) {
        this.numberOfNodes = numberOfNodes;
        this.maxEdgeWeight = maxEdgeWeight;
        // Create the nodes
        CreateNodes(numberOfNodes);

        // Create the edge list
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = i + 1; j < numberOfNodes; j++) {
                Node startNode = nodeList.get(i);
                Node endNode = nodeList.get(j);

                int weight = (int) (Math.random() * maxEdgeWeight + 1);

                // Creat edge
                Edge edge = new Edge(startNode, endNode, weight);
                // Add edge to the graph
                addEdge(edge);

                // System.out.println(startNode.getId() + " " + endNode.getId() + " " + weight);
                // Add edge to the outgoing edge list of the start node
                startNode.addOutgoingEdge(edge);

                // Add edge to the outgoing edge list of the end node
                endNode.addOutgoingEdge(edge);
            }
        }

        List<Edge> hamiltonPath = generatePath();

        edgeList = edgesToRemove(hamiltonPath);
    }

    // Hashing og a graph
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nodeList == null) ? 0 : nodeList.hashCode());
        result = prime * result + ((edgeList == null) ? 0 : edgeList.hashCode());
        return result;
    }

    // Comparing two graphs
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Graph other = (Graph) obj;
        if (nodeList == null) {
            if (other.nodeList != null)
                return false;
        } else if (!nodeList.equals(other.nodeList))
            return false;
        if (edgeList == null) {
            if (other.edgeList != null)
                return false;
        } else if (!edgeList.equals(other.edgeList))
            return false;
        return true;
    }

    // To String
    @Override
    public String toString() {
        return "Graph [nodeList=" + nodeList + ", edgeList=" + edgeList + "]";
    }

}
