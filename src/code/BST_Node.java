package code;

public class BST_Node extends Node implements Comparable<BST_Node>{

    public BST_Node(State state, Node parent, Action action, int depth, int pathCost, int timepnt) {
        super(state, parent, action, depth, pathCost, timepnt);
    }

    public int compareTo(BST_Node o){
        return timepnt - o.timepnt;
    }
}
