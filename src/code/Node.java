package code;

public abstract class Node {

    State state;
    Node parent;
    Action action;
    int depth;
    int pathCost; // ?
    int timepnt;

    public Node(State state, Node parent, Action action, int depth, int pathCost, int timepnt) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.depth = depth;
        this.pathCost = pathCost;
        this.timepnt = timepnt;
    }
}