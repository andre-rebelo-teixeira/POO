package FixedSizePriorityQueue;


import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Collection;
import java.util.Iterator;

public class FixedSizePriorityQueue<E> implements FixedSizePriorityQueueInterface<E> {
    private final PriorityQueue<E> queue;
    private final int maxSize;
    private final Comparator<? super E> comparator;

    public FixedSizePriorityQueue(int maxSize, Comparator<? super E> comparator) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max size must be greater than 0");
        }
        this.queue = new PriorityQueue<>(maxSize, comparator);
        this.maxSize = maxSize;
        this.comparator = comparator;
    }

    @Override
    public boolean add(E e) {
        if (queue.size() < maxSize) {
            queue.add(e);
        } else if (comparator.compare(e, queue.peek()) > 0) {
            queue.poll();
            queue.add(e);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public E poll() {
        return queue.poll();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public boolean isFull() {
        return queue.size() == maxSize;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return queue.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }
}

