package code;

public class Node {
    //TODO: change Node class back to be abstract

    public int pathCost; // ?
    String state;
    Node parent;
    Operator operator;
    int depth;

    public Node(String state, Node parent, Operator operator, int depth, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.operator = operator;
        this.depth = depth;
        this.pathCost = pathCost;
    }
}
