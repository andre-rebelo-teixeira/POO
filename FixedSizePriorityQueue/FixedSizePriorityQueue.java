package FixedSizePriorityQueue;


import java.util.Collection;
import java.util.Comparator;
import java.util.PrimitiveIterator;
import java.util.PriorityQueue;

public class FixedSizePriorityQueue<E> implements FixedSizePriorityQueueInterface<E> {
    private final PriorityQueue<E> queue;
    private final int maxSize;
    private int currentSize;
    private Comparator<? super E> comparator;

    public FixedSizePriorityQueue(int maxSize, Comparator<? super E> comparator) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max size must be greater than 0");
        }
        this.queue = new PriorityQueue<>(comparator);
        this.maxSize = maxSize;
        this.currentSize = 0;
        this.comparator = comparator;
    }

    @Override
    public boolean add(E e) {
        this.currentSize = this.currentSize == this.maxSize ? this.maxSize : this.currentSize + 1;
        return this.queue.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            if (!this.add(e)) {
                return false; 
            }
        }
        return true;
    }

    @Override
    public E peek() {
        if (this.currentSize == 0) {
            return null;
        }
        return queue.peek();
    }

    @Override
    public E poll() {
        if (this.currentSize == 0) {
            return null;
        }
        this.currentSize--;

        return queue.poll();

    }

    @Override
    public PriorityQueue<E> poll(Integer n) {
        PriorityQueue<E> pq = new PriorityQueue<E>(comparator);

        for (int i = 0; i < n && this.queue.peek() != null; i++) {
            pq.add(this.queue.poll());
        }

        return pq;
    }

    @Override
    public PriorityQueue<E> getAll() {
        return this.poll(this.maxSize);
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    /*
    @Override
    public boolean isFull() {
        return queue.size() == maxSize;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        PriorityQueue<E> q = new PriorityQueue<>(maxSize, comparator);

        for (E e : c) {
            q.offer(e);
        }

        for (int i = 0; i < this.maxSize && q.peek() != null; i++) {
            if (!this.add(q.poll())) {
                return false;
            }
        }
        return true;
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
*/
}


