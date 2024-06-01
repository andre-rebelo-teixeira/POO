package FixedSizePriorityQueue;

import java.util.Collection;
import java.util.PriorityQueue;

/**
 * FixedSizePriorityQueueInterface.java
 *
 * This interface defines the methods for a fixed-size priority queue, including
 * methods to add elements, retrieve elements, and get the size and maximum size
 * of the queue.
 *
 * Created on 01/06/2024
 *
 * @version 1.0
 * @since 1.0
 * @see java.util.PriorityQueue
 *
 * @author André Rebelo Teixeira
 */
public interface FixedSizePriorityQueueInterface<E> {

    /**
     * Adds an element to the priority queue.
     *
     * @param e The element to be added.
     * @return true if the element was added, false otherwise.
     */
    boolean add(E e);

    /**
     * Adds all elements from a collection to the priority queue.
     *
     * @param c The collection containing elements to be added.
     * @return true if the elements were added, false otherwise.
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * Retrieves, but does not remove, the head of the queue.
     *
     * @return The head of the queue, or null if the queue is empty.
     */
    E peek();

    /**
     * Retrieves and removes the head of the queue.
     *
     * @return The head of the queue, or null if the queue is empty.
     */
    E poll();

    /**
     * Retrieves and removes up to the specified number of elements from the queue.
     *
     * @param n The number of elements to retrieve and remove.
     * @return A priority queue containing the removed elements.
     */
    PriorityQueue<E> poll(Integer n);

    /**
     * Retrieves all elements in the queue.
     *
     * @return A priority queue containing all elements in the queue.
     */
    PriorityQueue<E> getAll();

    /**
     * Gets the current size of the queue.
     *
     * @return The current size of the queue.
     */
    int size();

    /**
     * Gets the maximum size of the queue.
     *
     * @return The maximum size of the queue.
     */
    int getMaxSize();
}
