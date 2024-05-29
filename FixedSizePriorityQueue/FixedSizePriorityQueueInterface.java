package FixedSizePriorityQueue;
import java.util.Collection;
import java.util.PriorityQueue;

public interface FixedSizePriorityQueueInterface<E> {
    boolean add(E e);
    boolean addAll(Collection<? extends E> c);

    E peek();
    E poll();

    PriorityQueue<E> poll(Integer n);

    PriorityQueue<E> getAll();

    int size();
    int getMaxSize();
}