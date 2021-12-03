package code.queue;

import java.util.Stack;

public class DfsQueue<T> implements GenericQueue<T> {

    private final Stack<T> stack = new Stack<>();

    @Override
    public void add(T node) {
        stack.add(node);
    }

    @Override
    public T poll() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
