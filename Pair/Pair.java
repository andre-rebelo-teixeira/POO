package Pair;

/**
 * Pair.java
 * <p>
 * This class represents a generic pair of objects, providing methods to access the first and second elements.
 * <p>
 * Created on 01/06/2024
 *
 * @param <F> The type of the first element.
 * @param <S> The type of the second element.
 * @author André Rebelo Teixeira
 * @version 1.0
 * @since 1.0
 */
public record Pair< F, S >(F first, S second)
{
    /**
     * Constructor for the Pair class.
     *
     * @param first  The first element of the pair.
     * @param second The second element of the pair.
     */
    public Pair
    {
    }

    /**
     * Gets the first element of the pair.
     *
     * @return The first element.
     */
    @Override
    public F first()
    {
        return first;
    }

    /**
     * Gets the second element of the pair.
     *
     * @return The second element.
     */
    @Override
    public S second()
    {
        return second;
    }
}
