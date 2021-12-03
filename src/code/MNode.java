package code;

public class MNode extends Node{
    public MNode(String state, MNode parent, Operator operator, int depth, int pathCost) {
        super(state, parent, operator, depth, pathCost);
    }
}
