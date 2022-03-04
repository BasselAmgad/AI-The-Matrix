package code.queue;

import code.MNode;
import code.Node;
import code.SearchProblem;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Gr2Queue implements GenericQueue<MNode> {

    private final PriorityQueue<MNode> queue;

    public Gr2Queue(SearchProblem problem) {
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
