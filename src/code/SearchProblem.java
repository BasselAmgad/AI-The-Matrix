package code;

import java.util.*;

public abstract class SearchProblem {
    public Operator[] operators;
    public String initialState;
    //Keeping track of all visited states in order to prevent having nodes with repeated states
    public HashSet<String> visitedStates;
    public int expandedNodesCnt;

    public abstract boolean goalTestFUnction(State state);
//    public abstract int pathCostFUnction(Operator operator);
    public abstract int pathCostFUnction(String stateString);
    public abstract String problemOutput(Node goal);

    public abstract int heuristic_1(Node node);
    public abstract int heuristic_2(Node node);

    public String genericSearchProcedure(Comparator<Node> comp){
        this.visitedStates = new HashSet<>();
        this.expandedNodesCnt = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>(comp);

        Node root = new Node(initialState, null, null, 0, 0);
        queue.add(root);
        while (!queue.isEmpty()){
            Node currentNode = queue.poll();
            expandedNodesCnt++;
            State state = new State(currentNode.state);
            if (goalTestFUnction(state)) {
                Matrix.visualize(state);
                return (problemOutput(currentNode));
            }
            expand(queue, currentNode, visitedStates);
        }
        return "No Solution";
    }
    public String DepthLimitedSearch(int limit){
        this.visitedStates = new HashSet<>();
        if (limit==0)
            this.expandedNodesCnt = 0;
//        else{
//            System.out.println(expandedNodesCnt);
//        }
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> -node.depth));

        Node root = new Node(initialState, null, null, 0, 0);
        queue.add(root);
        int maxDepthReached = -1;
        while (!queue.isEmpty()){
            Node currentNode = queue.poll();
            if (currentNode.depth > limit) {
//                System.out.println("Limit: "+limit+":  "+expandedNodesCnt);
//                return "cutoff";
                continue;
            }
//            System.out.printf("Node @depth=%d parent=%s, oldExpandedCnt=%d\n", currentNode.depth, currentNode.operator==null?"null":currentNode.operator.toString(), expandedNodesCnt);
            expandedNodesCnt++;
            maxDepthReached = Math.max(maxDepthReached, currentNode.depth);
            State state = new State(currentNode.state);
            if (goalTestFUnction(state)) {
                return (problemOutput(currentNode));
            }
            expand(queue, currentNode, visitedStates);
        }
        //TODO: this return means the queue got empty --> didn't return because of the cutoff limit but because no more expansions are possible ==> terminate IDS and don't try bigger limits
        if (maxDepthReached==limit)
            return "cutoff";
        return "empty";
    }


    public String[] constructPlan(Node leaf){
        Node current = leaf;
        LinkedList<String> sequence = new LinkedList<>();
        int cnt = 0;
        while (current.parent != null){
            sequence.push(current.operator.getCode());
            Matrix.visualize(current.state);
            if(MatrixConfig.visualize)
                System.out.println(current.operator.getCode());
            cnt++;
            current = current.parent;
        }
        Matrix.visualize(current.state);
        System.out.println(cnt);
        return sequence.toArray(new String[sequence.size()]);
    }
    public void expand(PriorityQueue<Node> queue, Node currentNode, HashSet<String> visitedStates){
        List<Operator> ops = Arrays.asList(operators);
//        Collections.shuffle(ops);
        for (Operator op : ops){
            StateResult exp = op.applyOperator(new State(currentNode.state), currentNode.operator);
            if (exp instanceof StateResult.NewState){
                String stateString = ((StateResult.NewState) exp).getResult();
                if (!visitedStates.contains(stateString)) {
                    visitedStates.add(stateString);
                    //TODO: what should pathCost inputs be ? operator ? operator and parent ? or opeartor only and the parent to it ?
//                    Node newNode = new Node(stateString, currentNode, op, currentNode.depth + 1, currentNode.pathCost + pathCostFUnction(op));
                    Node newNode = new Node(stateString, currentNode, op, currentNode.depth + 1, pathCostFUnction(stateString));
                    queue.add(newNode);
                }
            }
        }
    }

}