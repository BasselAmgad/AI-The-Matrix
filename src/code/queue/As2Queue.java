package code.queue;

import code.MNode;
import code.SearchProblem;

import java.util.Comparator;
import java.util.PriorityQueue;

public class As2Queue implements GenericQueue<MNode> {

    private final PriorityQueue<MNode> queue;

    public As2Queue(SearchProblem problem) {
        queue = new PriorityQueue<>(Comparator.comparingInt(node -> problem.heuristic_2(node)));
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
