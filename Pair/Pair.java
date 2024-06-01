package Pair;

/**
 * Pair.java
 *
 * This class represents a generic pair of objects, providing methods to access the first and second elements.
 *
 * Created on 01/06/2024
 *
 * @version 1.0
 * @since 1.0
 *
 * @param <F> The type of the first element.
 * @param <S> The type of the second element.
 *
 * @author André Rebelo Teixeira
 */
public class Pair<F, S> {
    private final F first;
    private final S second;

    /**
     * Constructor for the Pair class.
     *
     * @param first The first element of the pair.
     * @param second The second element of the pair.
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the first element of the pair.
     *
     * @return The first element.
     */
    public F getFirst() {
        return first;
    }

    /**
     * Gets the second element of the pair.
     *
     * @return The second element.
     */
    public S getSecond() {
        return second;
    }
}
