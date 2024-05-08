/*
 * Edge.java
 * 
 * Created on 16/06/2023 
 */
package Graph;

/**
 * Edge is responsible to store the relevant information about an edge in the,
 * such as the start node, the end node and the weight. It is private to the
 * graph package and therefore only exist inside a graph.
 * 
 * @author Manuel Gil Mata Rebeiro
 * @author André Teixeira
 * @author Pedro Lopes
 * @see Node
 */
class Edge {

    private Node startNode;
    private Node endNode;
    private int weight;

    public Edge(Node startNode, Node endNode, int weight) {
        // this.pheromoneLevel = pheromoneLevel;
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }

    // get the start node of the edge
    public Node getStartNode() {
        return this.startNode;
    }

    // get the end node of the edge
    public Node getEndNode() {
        return this.endNode;
    }

    // get the weight of the edge
    public int getWeight() {
        return this.weight;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((startNode == null) ? 0 : startNode.hashCode());
        result = prime * result + ((endNode == null) ? 0 : endNode.hashCode());
        result = prime * result + weight;
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
        Edge other = (Edge) obj;
        if (startNode == null) {
            if (other.startNode != null)
                return false;
        } else if (!startNode.equals(other.startNode))
            return false;
        if (endNode == null) {
            if (other.endNode != null)
                return false;
        } else if (!endNode.equals(other.endNode))
            return false;
        if (weight != other.weight)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Edge [startNode=" + startNode + ", endNode=" + endNode + ", weight=" + weight + "]";
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
