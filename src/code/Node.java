package code;

public class Node {
    //TODO: change Node class back to be abstract

    String state;
    Node parent;
    Action action;
    int depth;
    int pathCost; // ?

    public Node(String state, Node parent, Action action, int depth, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.depth = depth;
        this.pathCost = pathCost;
    }
}