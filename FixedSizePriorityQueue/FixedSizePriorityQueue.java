package FixedSizePriorityQueue;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * FixedSizePriorityQueue.java
 *
 * This class implements a fixed-size priority queue, providing methods to add elements,
 * retrieve elements, and get the size and maximum size of the queue. The priority queue
 * maintains a fixed maximum size, discarding elements as needed when the size limit is reached.
 *
 * Created on 01/06/2024
 *
 * @version 1.0
 * @since 1.0
 * @see java.util.PriorityQueue
 * @see FixedSizePriorityQueueInterface
 *
 * @author André Rebelo Teixeira
 */
public class FixedSizePriorityQueue<E> implements FixedSizePriorityQueueInterface<E> {
    private final PriorityQueue<E> queue;
    private final int maxSize;
    private int currentSize;
    private final Comparator<? super E> comparator;

    /**
     * Constructor for the FixedSizePriorityQueue class.
     *
     * @param maxSize The maximum size of the priority queue.
     * @param comparator The comparator to determine the order of the elements.
     */
    public FixedSizePriorityQueue(int maxSize, Comparator<? super E> comparator) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max size must be greater than 0");
        }
        this.queue = new PriorityQueue<>(comparator);
        this.maxSize = maxSize;
        this.currentSize = 0;
        this.comparator = comparator;
    }

    /**
     * Adds an element to the priority queue.
     *
     * @param e The element to be added.
     * @return true if the element was added, false otherwise.
     */
    @Override
    public boolean add(E e) {
        if (this.currentSize < this.maxSize) {
            this.currentSize++;
        } else {
            E head = queue.peek();
            if (comparator.compare(e, head) > 0) {
                queue.poll();
            } else {
                return false;
            }
        }
        return this.queue.add(e);
    }

    /**
     * Adds all elements from a collection to the priority queue.
     *
     * @param c The collection containing elements to be added.
     * @return true if the elements were added, false otherwise.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            if (!this.add(e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves, but does not remove, the head of the queue.
     *
     * @return The head of the queue, or null if the queue is empty.
     */
    @Override
    public E peek() {
        if (this.currentSize == 0) {
            return null;
        }
        return queue.peek();
    }

    /**
     * Retrieves and removes the head of the queue.
     *
     * @return The head of the queue, or null if the queue is empty.
     */
    @Override
    public E poll() {
        if (this.currentSize == 0) {
            return null;
        }
        this.currentSize--;
        return queue.poll();
    }

    /**
     * Retrieves and removes up to the specified number of elements from the queue.
     *
     * @param n The number of elements to retrieve and remove.
     * @return A priority queue containing the removed elements.
     */
    @Override
    public PriorityQueue<E> poll(Integer n) {
        PriorityQueue<E> pq = new PriorityQueue<>(comparator);
        for (int i = 0; i < n && this.queue.peek() != null; i++) {
            pq.add(this.queue.poll());
        }
        return pq;
    }

    /**
     * Retrieves all elements in the queue.
     *
     * @return A priority queue containing all elements in the queue.
     */
    @Override
    public PriorityQueue<E> getAll() {
        return this.poll(this.maxSize);
    }

    /**
     * Gets the current size of the queue.
     *
     * @return The current size of the queue.
     */
    @Override
    public int size() {
        return currentSize;
    }

    /**
     * Gets the maximum size of the queue.
     *
     * @return The maximum size of the queue.
     */
    @Override
    public int getMaxSize() {
        return maxSize;
    }
}
