/*
 * Node.java
 * 
 * Created on 16/06/2023
 */
package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Node is responsible to store the relevant information about a node in the
 * graph, such as the id and the outgoing edges. It is private to the graph
 * package.
 * Making it so that its defenition is not exposed.
 * 
 * @author Manuel Gil Mata Ribeiro
 * @author André Teixeira
 * @author Pedro Lopes
 * @see Edge
 * @see ArrayList
 * @see HashMap
 * @see List
 */
class Node {

    private int id;
    private List<Edge> outgoingEdges;
    private HashMap<Integer, Integer> edgesWeight;

    /**
     * Constructor of node class. Initializes the outgoing edge list and the
     * edgeWeight
     * 
     * @param id Integer ID thah will be assigned to the node
     */
    public Node(int id) {
        this.id = id;
        this.outgoingEdges = new ArrayList<Edge>();
        this.edgesWeight = new HashMap<>();
    }

    /**
     * 
     * @return
     */
    int getId() {
        return this.id;
    }

    // Remove outgoing edge
    public void removeOutgoingEdge(Edge edge) {
        outgoingEdges.remove(edge);

        int exitNodeId = (this.id == edge.getStartNode().getId()) ? edge.getEndNode().getId()
                : edge.getStartNode().getId();

        edgesWeight.remove(exitNodeId);
    }

    // Add an outgoing edge to the node
    public void addOutgoingEdge(Edge edge) {
        outgoingEdges.add(edge);
        edgesWeight.put(edge.getEndNode().getId(), edge.getWeight());
    }

    // Get the outgoing edges of the node
    public List<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    // Get Edge weight list
    public HashMap<Integer, Integer> getEdgesWeight() {
        return edgesWeight;
    }

    public Edge getEdgeToNode(int NodeId) {
        for (Edge edge : outgoingEdges) {
            if (edge.getStartNode().getId() == NodeId || edge.getEndNode().getId() == NodeId) {
                return edge;
            }
        }
        return null;
    }

    public void removeEdge(Edge edge) {
        outgoingEdges.remove(edge);
    }

    @Override
    public String toString() {
        return "Node [id=" + id + ", outgoingEdges=" + outgoingEdges + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((outgoingEdges == null) ? 0 : outgoingEdges.hashCode());
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
        Node other = (Node) obj;
        if (id != other.id)
            return false;
        if (outgoingEdges == null) {
            if (other.outgoingEdges != null)
                return false;
        } else if (!outgoingEdges.equals(other.outgoingEdges))
            return false;
        return true;
    }
}
