package code.queue;

import code.Node;

import java.util.Comparator;
import java.util.PriorityQueue;

public class UcQueue implements GenericQueue<Node> {

    private final PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.pathCost));

    @Override
    public void add(Node node) {
        queue.add(node);
    }

    @Override
    public Node poll() {
        return queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
