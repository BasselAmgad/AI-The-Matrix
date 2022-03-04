package code.queue;

public interface GenericQueue<T> {

    void add(T node);

    T poll();

    boolean isEmpty();
}
