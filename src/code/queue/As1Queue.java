package code.queue;

import code.MNode;
import code.Node;
import code.SearchProblem;

import java.util.Comparator;
import java.util.PriorityQueue;

public class As1Queue implements GenericQueue<MNode> {

    private final PriorityQueue<MNode> queue;

    public As1Queue(SearchProblem problem) {
        queue = new PriorityQueue<>(Comparator.comparingInt(node -> problem.heuristic_1(node)));
    }

    @Override
    public void add(MNode node) {
        queue.add(node);
    }

    @Override
    public MNode poll() {
        return queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
