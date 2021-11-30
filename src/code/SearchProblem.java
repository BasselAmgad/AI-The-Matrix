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
    public abstract int heuristic_3(Node node);
    public abstract int heuristic_4(Node node);
    public abstract int heuristic_5(Node node);

    public String genericSearchProcedure(Comparator<Node> comp) {
        this.visitedStates = new HashSet<>();
        this.expandedNodesCnt = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>(comp);

        Node root = new Node(initialState, null, null, 0, 0);
        queue.add(root);
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            expandedNodesCnt++;
            State state = new State(currentNode.state);
            if (goalTestFUnction(state)) {
                return (problemOutput(currentNode));
            }
            expand(queue, currentNode, visitedStates);
        }
        return "No Solution";
    }

    public String DepthLimitedSearch(int limit) {
        this.visitedStates = new HashSet<>();
        if (limit == 0)
            this.expandedNodesCnt = 0;
//        else{
//            System.out.println(expandedNodesCnt);
//        }
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> -node.depth));

        Node root = new Node(initialState, null, null, 0, 0);
        queue.add(root);
        int maxDepthReached = -1;
        while (!queue.isEmpty()) {
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
        if (maxDepthReached == limit)
            return "cutoff";
        return "empty";
    }

    public String[] constructPlan(Node leaf) {
        Node current = leaf;
        LinkedList<String> sequence = new LinkedList<>();
        LinkedList<String> grids = new LinkedList<>();
        LinkedList<Integer> pathCosts = new LinkedList<>();
        LinkedList<Integer> h1s = new LinkedList<>();
        LinkedList<Integer> h2s = new LinkedList<>();
        LinkedList<Integer> h3s = new LinkedList<>();
        LinkedList<Integer> h4s = new LinkedList<>();
        LinkedList<Integer> h5s = new LinkedList<>();
        LinkedList<Integer> deaths = new LinkedList<>();
        LinkedList<Integer> kills = new LinkedList<>();

        int cnt = 0;
        int goalPathCost = current.pathCost;
        while (current.parent != null) {
            sequence.push(current.operator.getCode());
            State state = new State(current.state);
            grids.push(state.visualize());
            pathCosts.push(current.pathCost);
            h1s.push(heuristic_1(current));
            h2s.push(heuristic_2(current));
            h3s.push(heuristic_3(current));
            h4s.push(heuristic_4(current));
            h5s.push(heuristic_5(current));
            deaths.push(state.countDead);
            kills.push(state.countKilled);
            cnt++;
            current = current.parent;
        }
        State state = new State(current.state);
        grids.push(state.visualize());
        pathCosts.push(current.pathCost);
        h1s.push(heuristic_1(current));
        h2s.push(heuristic_2(current));
        h3s.push(heuristic_3(current));
        h4s.push(heuristic_4(current));
        h5s.push(heuristic_5(current));
        deaths.push(state.countDead);
        kills.push(state.countKilled);

        String[] gridsArr = grids.toArray(new String[grids.size()]);
        String[] seqArr = sequence.toArray(new String[sequence.size()]);
        if (MatrixConfig.visualize) {
            for (int i = 0; i < gridsArr.length - 1; i++) {
//                for (int i = 0; i < gridsArr.length; i++) {
                System.out.printf("depth=%d\tpath_cost=%d\trem=%d\th1=%d\th2=%d\th4=%d\tdeaths=%d\tkills=%d\n", i, pathCosts.get(i), goalPathCost-pathCosts.get(i), h1s.get(i), h2s.get(i), h4s.get(i), deaths.get(i), kills.get(i));
                System.out.print(gridsArr[i]);
                System.out.println(seqArr[i]);

            }
            System.out.printf("depth=%d\tpath_cost=%d\trem=%d\th1=%d\th2=%d\th4=%d\tdeaths=%d\tkills=%d\n", gridsArr.length-1, pathCosts.get(gridsArr.length-1), goalPathCost-pathCosts.get(gridsArr.length-1), h1s.get(gridsArr.length-1), h2s.get(gridsArr.length-1), h4s.get(gridsArr.length-1), deaths.get(gridsArr.length-1), kills.get(gridsArr.length-1));
            System.out.print(gridsArr[gridsArr.length - 1]);

//            System.out.println(cnt);
        }
        return seqArr;
    }

    public void expand(PriorityQueue<Node> queue, Node currentNode, HashSet<String> visitedStates) {
        List<Operator> ops = Arrays.asList(operators);
//        Collections.shuffle(ops);
        for (Operator op : ops) {
            StateResult exp = op.applyOperator(new State(currentNode.state), currentNode.operator);
            if (exp instanceof StateResult.NewState) {
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
