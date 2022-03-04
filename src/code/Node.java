package code;

public abstract class Node {
    public int pathCost;
    String state;
    MNode parent;
    Operator operator;
    int depth;

    public Node(String state, MNode parent, Operator operator, int depth, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.operator = operator;
        this.depth = depth;
        this.pathCost = pathCost;
    }
}
