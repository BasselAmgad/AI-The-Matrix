package code.queue;

import code.MNode;
import code.Node;

import java.util.Comparator;
import java.util.PriorityQueue;

public class UcQueue implements GenericQueue<MNode> {

    private final PriorityQueue<MNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.pathCost));

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
