package FixedSizePriorityQueue;
import java.util.Collection;

public interface FixedSizePriorityQueueInterface<E> extends Collection<E> {
    boolean add(E e);
    E peek();
    E poll();
    int size();
    int getMaxSize();
    boolean isFull();
}