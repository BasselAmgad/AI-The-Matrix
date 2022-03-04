package code;

import java.util.*;

// Abstract class for defining the search problem
public abstract class SearchProblem {
    public Operator[] operators;
    public String initialState;
    //Keeping track of all visited states in order to prevent having nodes with repeated states
    public HashSet<String> visitedStates;
    public int expandedNodesCnt;

    public abstract boolean goalTestFUnction(State state);

    public abstract String problemOutput(MNode goal);

    public abstract int heuristic_1(MNode node);
    public abstract int heuristic_2(MNode node);

    public String genericSearchProcedure(Comparator<MNode> comp) {
        this.visitedStates = new HashSet<>();
        this.expandedNodesCnt = 0;
        PriorityQueue<MNode> queue = new PriorityQueue<>(comp);

        MNode root = new MNode(initialState, null, null, 0, 0);
        queue.add(root);
        while (!queue.isEmpty()) {
            MNode currentNode = queue.poll();
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
        PriorityQueue<MNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> -node.depth));

        MNode root = new MNode(initialState, null, null, 0, 0);
        queue.add(root);
        int maxDepthReached = -1;
        while (!queue.isEmpty()) {
            MNode currentNode = queue.poll();
            if (currentNode.depth > limit) {
                continue;
            }
            expandedNodesCnt++;
            maxDepthReached = Math.max(maxDepthReached, currentNode.depth);
            State state = new State(currentNode.state);
            if (goalTestFUnction(state)) {
                return (problemOutput(currentNode));
            }
            expand(queue, currentNode, visitedStates);
        }
        if (maxDepthReached == limit)
            return "cutoff";
        return "empty";
    }

    public String[] constructPlan(MNode leaf) {
        MNode current = leaf;
        LinkedList<String> sequence = new LinkedList<>();
        LinkedList<String> grids = new LinkedList<>();

        while (current.parent != null) {
            sequence.push(current.operator.getCode());
            State state = new State(current.state);
            grids.push(state.visualize());
            current = current.parent;
        }
        State state = new State(current.state);
        grids.push(state.visualize());

        String[] gridsArr = grids.toArray(new String[grids.size()]);
        String[] seqArr = sequence.toArray(new String[sequence.size()]);
        if (MatrixConfig.visualize) {
            for (int i = 0; i < gridsArr.length - 1; i++) {
                System.out.print(gridsArr[i]);
                System.out.println(seqArr[i]);
            }
            System.out.print(gridsArr[gridsArr.length - 1]);
        }
        return seqArr;
    }

    public void expand(PriorityQueue<MNode> queue, MNode currentNode, HashSet<String> visitedStates) {
        List<Operator> ops = Arrays.asList(operators);
//        Collections.shuffle(ops);
        for (Operator op : ops) {
            StateResult exp = op.applyOperator(new State(currentNode.state), currentNode.operator);
            if (exp instanceof StateResult.NewState) {
                String stateString = ((StateResult.NewState) exp).getResult();
                String hashString = ((StateResult.NewState) exp).getHashResult();
                int opCost = ((StateResult.NewState) exp).getActionCost();
                if (!visitedStates.contains(hashString)) {
                    visitedStates.add(hashString);
                    MNode newNode = new MNode(stateString, currentNode, op, currentNode.depth + 1, currentNode.pathCost+opCost);
                    queue.add(newNode);
                }
            }
        }
    }
}
