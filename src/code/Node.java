package code;

public class Node {
    //TODO: change Node class back to be abstract

    String state;
    Node parent;
    Operator operator;
    int depth;
    int pathCost; // ?

    public Node(String state, Node parent, Operator operator, int depth, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.operator = operator;
        this.depth = depth;
        this.pathCost = pathCost;
    }
}
