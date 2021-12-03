package code.queue;

import java.util.LinkedList;
import java.util.Queue;

public class BfsQueue<T> implements GenericQueue<T> {

    private final Queue<T> queue = new LinkedList<>();

    @Override
    public void add(T node) {
        queue.add(node);
    }

    @Override
    public T poll() {
        return queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
